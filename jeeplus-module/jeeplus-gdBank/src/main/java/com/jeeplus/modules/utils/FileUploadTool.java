package com.jeeplus.modules.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import com.jeeplus.modules.exception.uploadexception.FileTypeNotAllowException;
import com.jeeplus.modules.exception.uploadexception.SizeBeyondException;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.videomanage.video.entity.BankVideo;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadTool {

    //视频转码工具类
    TransfMediaTool transfMediaTool = new TransfMediaTool();
    public final static String IMG_PATH_PREFIX = "static/upload";
    // 文件最大800M
    private static long upload_maxsize = 800 * 1024 * 1024;
    // 文件允许格式
    private static String[] allowFiles = {".rar", ".doc", ".docx", ".zip",
            ".pdf", ".txt", ".swf", ".xlsx", ".gif", ".png", ".jpg", ".jpeg",
            ".bmp", ".xls", ".mp4", ".flv", ".ppt", ".avi", ".mpg", ".wmv",
            ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb"};
    // 允许转码的视频格式（ffmpeg）
    private static String[] allowFLV = {".avi", ".mpg", ".wmv", ".3gp",
            ".mov", ".asf", ".asx", ".vob"};
    // 允许的视频转码格式(mencoder)
    private static String[] allowAVI = {".wmv9", ".rm", ".rmvb"};

    public BankVideo createFile(MultipartFile multipartFile, HttpServletRequest request) throws SizeBeyondException, FileTypeNotAllowException, FileNotFoundException {
        BankVideo entity = new BankVideo();
        boolean bflag = false;
        String fileName = multipartFile.getOriginalFilename().toString();
        if (multipartFile.getSize() != 0 && !multipartFile.isEmpty()) {  // 判断文件不为空
            bflag = true;
            if (multipartFile.getSize() <= upload_maxsize) {// 判断文件大小
                bflag = true;
                if (this.checkFileType(fileName)) { // 文件类型判断
                    bflag = true;
                } else {
                    bflag = false;
                    System.out.println("文件类型不允许！");
                    throw new FileTypeNotAllowException("文件类型不允许！");
                }
            } else {
                bflag = false;
                System.out.println("文件大小超范围，最大800M！");
                throw new SizeBeyondException("文件大小超范围，最大800M！");
            }
        } else {
            bflag = false;
            System.out.println("文件为空！");
            throw new FileNotFoundException("文件为空！");
        }
        if (bflag) {
           String rootPath = "C:/gdBanks/";

            String uid = UserUtils.getUser().getId()+"/";
            String realPathDirStr = rootPath+uid;//文件真实存放路径
            File realPathDir = new File(realPathDirStr);
            if (!realPathDir.exists()) {
                realPathDir.mkdirs();
            }
            String fileFirst = this.getName(fileName);
            String fileEnd = this.getFileExt(fileName); // 文件扩展名
            String fileNamedirs = realPathDirStr + fileFirst+fileEnd;// 绝对路径
            File filedirs = new File(fileNamedirs);
            try {
                multipartFile.transferTo(filedirs); // 转入文件
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 源文件保存路径
            String savePath = filedirs.getAbsolutePath();
            String finalFileDir = "/files/"+fileNamedirs.substring(rootPath.length());// 相对路径，数据库存放路径
            String size = this.getSize(filedirs);// size存储为String

            // 转码Avi
            if (this.checkAVIType(fileEnd)) {
                // 设置转换为AVI格式后文件的保存路径
                String codcAviPath = realPathDir + fileFirst + ".avi";
                // 获取配置的转换工具（mencoder.exe）的存放路径
                String mencoderPath = request.getSession().getServletContext().getRealPath("/tools/mencoder.exe");
                savePath = transfMediaTool.processAVI(mencoderPath, filedirs.getAbsolutePath(), codcAviPath);
                fileEnd = this.getFileExt(codcAviPath);//获取转换后的扩展名

            }

            if (savePath != null) {
                // 转码Flv
                if (this.checkMediaType(fileEnd)) {
                    try {
                        // 设置转换为flv格式后文件的保存路径
                        String codcFilePath = realPathDir + fileFirst + ".flv";
                        // 获取配置的转换工具（ffmpeg.exe）的存放路径
                        String ffmpegPath = request.getSession().getServletContext().getRealPath("/tools/ffmpeg.exe");
                        transfMediaTool.processFLV(ffmpegPath, savePath, codcFilePath);
//                        fileDir = logoPathDir + newFileName + ".flv";
//                        builder = new StringBuilder(fileDir);
//                        finalFileDir = builder.substring(1);
                        finalFileDir = "/userfiles/"+codcFilePath.substring(rootPath.length());// 转码成flv 格式之后数据库存放路径
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }

            //文件信息存入数据库
            entity.setVideoSize(size);
            entity.setVideoPath(finalFileDir);
            entity.setVideoName(fileFirst+fileEnd);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setCreateDate(timestamp);
            entity.setVideoType(fileEnd);
        } else {
            return null;
        }
        return entity;
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 视频类型判断(flv)
     *
     * @param
     * @return
     */
    private boolean checkMediaType(String fileEnd) {
        Iterator<String> type = Arrays.asList(allowFLV).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileEnd.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 视频类型判断(AVI)
     *
     * @param
     * @return
     */
    private boolean checkAVIType(String fileEnd) {
        Iterator<String> type = Arrays.asList(allowAVI).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileEnd.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     *
     * @return
     */
    private String getName(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.contains(ext)) {
                String newFileName = fileName.substring(0, fileName.lastIndexOf(ext));
                return newFileName;
            }
        }
        return "";
    }

    /**
     * 文件大小，返回kb.mb
     *
     * @return
     */
    private String getSize(File file) {
        String size = "";
        long fileLength = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength < 1024) {
            size = df.format((double) fileLength) + "BT";
        } else if (fileLength < 1048576) {
            size = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824) {
            size = df.format((double) fileLength / 1048576) + "MB";
        } else {
            size = df.format((double) fileLength / 1073741824) + "GB";
        }
        return size;
    }

    public static void main(String[] args) {
      /*  String rootPath = "C:/gdBanks/";
        String a = rootPath+"abvabvhvbjv.jpg";
        System.out.println(a);
        System.out.println(a.substring(rootPath.length()-1));*/
      String rootPath = "src/main/resources/"+IMG_PATH_PREFIX;
      File file=new File(rootPath);
      if (!file.exists()){
          file.mkdirs();
      }
        System.out.println("wangcheng");



    }
}
