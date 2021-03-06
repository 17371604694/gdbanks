package com.jeeplus.modules.bankimagemanagement.image.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.backgroundmanage.imagemanage.entity.BankImage;

public class Image extends DataEntity<Image> {

    private static final long serialVersionUID = 1L;
    private String state;		// 状态
    private String imageName;		// 名称
    private String imageSize;		// 大小
    private String imagePath;		// 路径
    private String imageType;		// 类型
    private String uid;		// 关联用户id

    public Image() {
        super();
    }

    public Image(String id){
        super(id);
    }

    @ExcelField(title="状态", align=2, sort=7)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @ExcelField(title="名称", align=2, sort=8)
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @ExcelField(title="大小", align=2, sort=9)
    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    @ExcelField(title="路径", align=2, sort=10)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @ExcelField(title="类型", align=2, sort=11)
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @ExcelField(title="关联用户id", align=2, sort=12)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
