package com.jeeplus.modules.publicController;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.download.download.entity.BankDownload;
import com.jeeplus.modules.download.download.service.BankDownloadService;
import com.jeeplus.modules.employeevoice.leavemessage.entity.BankEmployeeVoice;
import com.jeeplus.modules.employeevoice.leavemessage.service.BankEmployeeVoiceService;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.service.BankConferenceRoomReservationService;
import com.jeeplus.modules.programatcontent.programatcont.entity.DistributeContent;
import com.jeeplus.modules.programatcontent.programatcont.entity.DistributeContentSum;
import com.jeeplus.modules.programatcontent.programatcont.mapper.DistributeContentMapper;
import com.jeeplus.modules.programatcontent.programatcont.service.DistributeContentService;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.videomanage.video.entity.BankVideo;
import com.jeeplus.modules.videomanage.video.service.BankVideoService;
import com.jeeplus.modules.vote.vote.entity.BankVote;
import com.jeeplus.modules.vote.vote.service.BankVoteService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("${adminPath}/public")
public class ControllerPublic extends BaseController {

    @Autowired
    private DistributeContentService distributeContentService; //稿件
    @Autowired
    private DistributeContentMapper distributeContentMapper; //稿件mapper
    @Autowired
    private BankConferenceRoomReservationService bankConferenceRoomReservationService; //会议室
    @Autowired
    private UserMapper userMapper; //用户
    @Autowired
    private BankDownloadService bankDownloadService; //下载中心
    @Autowired
    private BankVideoService bankVideoService;
    @Autowired
    private OfficeService officeService; //分组
    @Autowired
    private MenuMapper menuMapper; //菜单
    @Autowired
    private BankVoteService bankVoteService; //投票
    @Autowired
    private BankEmployeeVoiceService bankEmployeeVoiceService;

    /**
     * 根据栏目分类查询稿件数据--首页查询
     * @Pram name 稿件栏目名称
     * @Pram nameparent 上一级稿件栏目名称(如果该栏目有多个,可以传入该参数)
     */
    @ResponseBody
    @RequestMapping(value = "dataJson")
    public Object dataJson(@RequestParam(value = "name",defaultValue = "",required = false) String name,
                           @RequestParam(value = "nameparent",defaultValue = "",required = false)String nameparent,
                           HttpServletRequest request, HttpServletResponse response, Model model) {
//        List<DistributeContent> resultlist = new ArrayList<>();
        //加入筛选条件
        List<DistributeContent> list = null;//查询栏目数据
        try {
            DistributeContent distributeContent=new DistributeContent();
            distributeContent.setProgramatIdName(name.equals("")?null:name);
            distributeContent.setProgramatParentidName(nameparent.equals("")?null:nameparent);
            DistributeContent programatId = distributeContentService.findprogramatId(distributeContent);
            //只展示审核通过的发稿
            distributeContent.setStatepid(2);
            distributeContent.setStateparentid(6);
            distributeContent.setId(programatId.getId());//栏目id
            list = distributeContentService.findListtg(distributeContent);
            for (int i = 0; i <list.size() ; i++) {    //将当前传入的栏目id 赋值给ProgramatParentid,方便转跳list列表页面,查看
                list.get(i).setProgramatParentid(programatId.getId());
            }
//        List<DistributeContent> programatParentidList = distributeContentService.findProgramatParentidList(programatId);//查询稿件在副栏目展示的数据
//        for(DistributeContent d  :programatParentidList){
//            list.add(d);
//        }

            //设置对公、零售、综合、内控的3张默认图片(首页版本已经更改了，不需要判断了)
//        if(list!=null&&list.size()>0){
//            String pid = list.get(0).getProgramatId();
//            if("97e59921be1e4e178eb4dc8b48cc91ee".equals(pid)||"bbc61b7435e44de488c230b3c296df54".equals(pid)||
//                    "8a17d9cc3f0941b3985a053cdc75b891".equals(pid)||"4c1b80219888478592953f7fb0857558".equals(pid)){
//                for(DistributeContent img:list){
//                    if(img.getAccessorys()==null||"".equalsIgnoreCase(img.getAccessorys())){
//                        img.setAccessorys("/static/common/image2/dg1.jpg|/static/common/image2/dg2.jpg|/static/common/image2/dg3.jpg");
//                    }else {
//                        String imgs = img.getAccessorys();
//                        String idArray[]  = imgs.split("\\|");
//                        if(idArray.length==1){//有一张
//                            imgs += "|/static/common/image2/dg1.jpg|/static/common/image2/dg2.jpg";
//                            img.setAccessorys(imgs);
//                        }
//                        if(idArray.length==2){//有2张
//                            imgs += "|/static/common/image2/dg1.jpg";
//                            img.setAccessorys(imgs);
//                        }
//                    }
//                }
//            }
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 查询会议室数据
     */
    @ResponseBody
    @RequestMapping(value = "dataBankConference")
    public Object dataBankConference(HttpServletRequest request, HttpServletResponse response, Model model) {
        BankConferenceRoomReservation bankConferenceRoomReservation=new BankConferenceRoomReservation();
        bankConferenceRoomReservation.setIsNoticeMeeting("1");
        return bankConferenceRoomReservationService.findList(bankConferenceRoomReservation);
    }


    /**
     * 查询全部用户
     */
    @ResponseBody
    @RequestMapping(value = "userInfo")
    public Object userInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Map<String,Object>> list= null;
        try {
            DistributeContent distributeContent=new DistributeContent();
            distributeContent.setProgramatId("de9acf76f1fc4453add35982eb27b204");//优秀党务工作者栏目
            User user=new User();
//         List<User> allList = userMapper.findList(user);//查询用户改为查询优秀党务工作者栏目
            List<DistributeContent> allList = distributeContentService.findListtg(distributeContent);
            list = new ArrayList<>();
            for (int i = 0; i <allList.size() ; i++) {
                LinkedHashMap<String,Object> map=new LinkedHashMap<>();
    //            map.put("name",allList.get(i).getName());
                map.put("photo",allList.get(i).getAccessorys());
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 下载中心
     */
    @ResponseBody
    @RequestMapping(value = "download")
    public Page<BankDownload> download(BankDownload bankDownload, HttpServletRequest request, HttpServletResponse response, Model model) {
//        List<BankDownload> list = bankDownloadService.findList(bankDownload);
        Page<BankDownload> page = null;
        try {
            page = bankDownloadService.findPage(new Page<BankDownload>(request, response), bankDownload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }


    /**
     * 查询排行
     * type 1,部门,2,活跃度,3,热度
     * month 1当月,2历史
     */
    @ResponseBody
    @RequestMapping(value = "statistics")
    public Object statisticsSum(int type,int month){
     //   statistics();//测试环境每次调用---不然无法获取实时数据
        List<DistributeContentSum> sdlist = null;
        try {
            sdlist = distributeContentMapper.distributeContentSumAll(type, month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdlist;
    }

    /**
     * 获取视频文件 ,现已不用,视频读取的是图文新闻,包含视频或图片展示
     */
    @ResponseBody
    @RequestMapping(value = "getVideo")
    public Object getVideo(){
        List<BankVideo> list = null;
        try {
            BankVideo bb=new BankVideo();
            list = bankVideoService.findList(bb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /***
     * 初始化定时器
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "statisTest")
      public Object statisTest(){
        statistics();
        return "从新统计成功！";
      }

//    public static void main(String[] args) {
//        ControllerPublic con=new ControllerPublic();
//        con.statistics();
//    }
    //定时器
    //每2秒0/2 * * * * ?
    //每天0点0 0 0 * * ?
    //每天定时统计数据
    @Scheduled(cron  = "0 0 0 * * ?")
    public void statistics(){
        try {
            System.out.println("定时器");
            distributeContentMapper.delDisSum();//从新统计之前先清空原数据（每天晚上12点统计执行）
            //查询历史发稿数量按部门分组
            DistributeContent d1 = new DistributeContent();
            d1.setAuthor("历史");//查询条件，如果author为null 就查询本月,否则就查询历史
            List<DistributeContent> d1d = distributeContentMapper.distributeContentType1(d1);//按部门分类查询
            for (int i = 0; i <d1d.size() ; i++) {
                DistributeContentSum ds = new DistributeContentSum();
                ds.setNowMonth(2); //设置该记录为历史记录
                ds.setOfficename(d1d.get(i).getOfficeName());//设置记录的部门名称
                ds.setSum(d1d.get(i).getStepSum());//发稿数量
                ds.setType(1); //分组类型
                distributeContentMapper.addDisSum(ds);//插入统计表中
            }
            //查询当月发稿数量按部门分组
            List<DistributeContent> d2d = distributeContentMapper.distributeContentType1(new DistributeContent());
            for (int i = 0; i <d2d.size() ; i++) {
                DistributeContentSum ds=new DistributeContentSum();
                ds.setNowMonth(1);//设置该记录为当月记录
                ds.setOfficename(d1d.get(i).getOfficeName());
                ds.setSum(d1d.get(i).getStepSum());
                ds.setType(1);
                distributeContentMapper.addDisSum(ds);
            }

            //查询历史发稿数量按活跃度（安人）分组
            DistributeContent d2=new DistributeContent();
            d2.setAuthor("历史");
            List<DistributeContent> dhy = distributeContentMapper.distributeContentType2(d2);
            for (int i = 0; i <dhy.size() ; i++) {
                DistributeContentSum ds=new DistributeContentSum();
                ds.setNowMonth(2);
                ds.setOfficename(dhy.get(i).getOfficeName());
                ds.setSum(dhy.get(i).getStepSum());
                ds.setType(2);
                distributeContentMapper.addDisSum(ds);
            }
            //查询当月发稿数量按活跃度（安人）分组
            List<DistributeContent> dhy2 = distributeContentMapper.distributeContentType2(new DistributeContent());
            for (int i = 0; i <dhy2.size() ; i++) {
                DistributeContentSum ds=new DistributeContentSum();
                ds.setNowMonth(1);
                ds.setOfficename(dhy2.get(i).getOfficeName());
                ds.setSum(dhy2.get(i).getStepSum());
                ds.setType(2);
                distributeContentMapper.addDisSum(ds);
            }

            //按热度
            DistributeContent d3=new DistributeContent();
            d2.setAuthor("历史");
            List<DistributeContent> dhy3 = distributeContentMapper.distributeContentType3(d3);
            for (int i = 0; i <dhy3.size() ; i++) {
                DistributeContentSum ds=new DistributeContentSum();
                ds.setNowMonth(2);
                ds.setOfficename(dhy3.get(i).getOfficeName());
                ds.setSum(dhy3.get(i).getStepSum());
                ds.setType(3);
                distributeContentMapper.addDisSum(ds);
            }

            List<DistributeContent> dhy4 = distributeContentMapper.distributeContentType3(new DistributeContent());
            for (int i = 0; i <dhy4.size() ; i++) {
                DistributeContentSum ds=new DistributeContentSum();
                ds.setNowMonth(1);
                ds.setOfficename(dhy4.get(i).getOfficeName());
                ds.setSum(dhy4.get(i).getStepSum());
                ds.setType(3);
                distributeContentMapper.addDisSum(ds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取部室网栏二级菜单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getNavMenu")
    public List<Menu> getNavMenu(Menu menu){
        return officeService.getNavMenu(menu);
    }

    /**
     * 获取经营机构二级菜单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getNavMenuInstitutions")
    public List<Menu> getNavMenuInstitutions(Menu menu){
        return officeService.getNavMenuInstitutions(menu);
    }

    /**
     * 获取前端页面导航栏部室网栏菜单的下级菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getNavMenuList")
    public Map<Menu,List> getNavMenuList(Menu menu){
        Map<Menu,List> mapResult= new LinkedHashMap<>();
        List<Menu> list = null;
        try {
            list = officeService.getNavMenuList(menu);
            for(int i = 0;i<list.size();i++){
                String menuId = list.get(i).getId();
                List<DistributeContent> distributeContentList = distributeContentMapper.getContentById(menuId);
                mapResult.put(list.get(i),distributeContentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapResult;
    }

    /**
     * 获取详情页数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "contentdata")
    public DistributeContent getContentdata(String id){
        DistributeContent distributeContent  = null;
        try {
            distributeContent = distributeContentMapper.get(id);
            Integer onclickNum  = distributeContent.getOnclickNum();
            if(onclickNum==null){
                onclickNum = 0;
                onclickNum++;
                distributeContent.setOnclickNum(onclickNum);
            }else {
                onclickNum++;
                distributeContent.setOnclickNum(onclickNum);
            }
            Integer result = distributeContentMapper.updateOnclickNum(distributeContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distributeContent;
    }



    /**
     * 根据用户搜索标题内容返回集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDistributeList")
    //Map<String, Object>
    public Object getDistributeList(HttpServletRequest request, HttpServletResponse response,int pageNo){
        Page<DistributeContent> page = null;
        try {
            DistributeContent distributeContent=new DistributeContent();
            String title = request.getParameter("title");
            if(!"all".equals(title)){
                distributeContent.setTitle(title);
            }
            Page<DistributeContent> pg = new Page<>(request, response);
            pg.setPageSize(20);
            pg.setPageNo(pageNo);
            page = distributeContentService.findPage(pg, distributeContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

    /**
     * 根据栏目id查询数据集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDistributeListById")
    public Object getDistributeListById(HttpServletRequest request, HttpServletResponse response,int pageNo){
        Page<DistributeContent> page = null;
        try {
            DistributeContent distributeContent = new DistributeContent();
            String psnameId = request.getParameter("id");
            distributeContent.setProgramatId(psnameId);
            Page<DistributeContent> pg = new Page<>(request, response);
            pg.setPageSize(10);
            pg.setPageNo(pageNo);
            page = distributeContentService.findPage1(pg, distributeContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    /*
    * *更新栏目name为id
    * */
    @ResponseBody
    @RequestMapping(value = "ttt")
    public Object ttt(){
        List<Menu> list = menuMapper.findAllListAs();
        List<String> li=new ArrayList<>();
        System.out.println(list.size());
        for (int i = 0; i <list.size() ; i++) {
               String path=list.get(i).getHref()+"";
            if (null!=path && !"".equals(path) && path.contains("name") && !path.contains("name=all")){

//             li.add(path.substring(path.lastIndexOf("=")));
                String href="/programatcontent/programatcont/distributeContent/list?name="+list.get(i).getId();
                int i1 = menuMapper.updateByHref(list.get(i).getId(), href);
                System.out.println(i1);
                li.add(path);
            }
        }
        return li;
    }


    /**
     * 投票
     */
    @ResponseBody
    @RequestMapping(value = "vote")
    public AjaxJson vote(BankVote bankVote, Model model) {
        AjaxJson j = new AjaxJson();
        try {
            BankVote bankVote1 = bankVoteService.getSelectInfo(bankVote.getId());//投票选项（返回selectId，bankVoteId，voteNum，themeName等）
            bankVote1.setVotePreson(UserUtils.getUser().getId());
            List<BankVote> resulrList = bankVoteService.getTpList(bankVote1);//查询投票记录
            if(UserUtils.getUser().getId()==null){
                j.setSuccess(false);
                j.setMsg("请先的登录账号！");
            }else {
                if(resulrList.size()<=0){//可以投票
                    BankVote num = bankVoteService.votenum(bankVote);//查询投票数
                    Integer n = num.getVoteNum();
                    n++;
                    bankVote.setVoteNum(n);
                    bankVoteService.updatevotenum(bankVote);//更新选项投票数
                    BankVote bankVote2 = new BankVote();
                    bankVote2.setVotePreson(UserUtils.getUser().getId());
                    bankVote2.setId(UUID.randomUUID().toString());
                    bankVote2.setSelectId(bankVote1.getSelectId());
                    bankVote2.setBankVoteId(bankVote1.getBankVoteId());
                    bankVoteService.saveTpRecord(bankVote2);
                    j.setSuccess(true);
                    j.setMsg("投票成功");
                }else{
                    j.setSuccess(false);
                    j.setMsg("请不要多次投票！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 投票详情页面数据
     */
    @ResponseBody
    @RequestMapping(value = "voteinfodata")
    public List<BankVote> voteinfodata(BankVote bankVote, Model model) {
        List<BankVote> resultlist = new ArrayList<>();
        try {
          resultlist = bankVoteService.voteinfodata(bankVote);//查询主题的投票详情
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    @ResponseBody
    @RequestMapping(value = "selectVoteId")
    public BankVote selectActivVoteId (HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        return bankVoteService.selectVoteId();
    }

    /**
     * 员工心声列表数据(展示在首页上的)
     */
    @ResponseBody
    @RequestMapping(value = "voicedataweb")
    public Map<String, Object> dataweb(BankEmployeeVoice bankEmployeeVoice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BankEmployeeVoice> page = null;
        try {
            page = bankEmployeeVoiceService.findPage(new Page<BankEmployeeVoice>(request, response), bankEmployeeVoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBootstrapData(page);
    }

    /**
     * 员工心声详情数据(展示在首页上的)
     */
    @ResponseBody
    @RequestMapping(value = "getReplyData")
    public List<BankEmployeeVoice> getReplyData(String id) {
        List<BankEmployeeVoice> list = null;
        try {
            BankEmployeeVoice bankEmployeeVoice = bankEmployeeVoiceService.get(id);
            list = bankEmployeeVoiceService.getReply(bankEmployeeVoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 获取当前登录用户
     * */
    @ResponseBody
    @RequestMapping(value = "getLoginUser")
    public Object getReplyData() {
       return UserUtils.getUser();
    }

}
