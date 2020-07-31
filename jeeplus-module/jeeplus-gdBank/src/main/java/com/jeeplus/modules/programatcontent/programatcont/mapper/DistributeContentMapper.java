/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.programatcontent.programatcont.mapper;

import com.jeeplus.modules.programatcontent.programatcont.entity.*;
import com.jeeplus.modules.verifiertrue.verifiertrues.entity.BankVerifierTrue;
import com.jeeplus.modules.verifierts.verifier.entity.BankVerifier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发稿内容MAPPER接口
 * @author chenl
 * @version 2019-11-22
 */
@Mapper
@Repository
public interface DistributeContentMapper extends BaseMapper<DistributeContent> {


    //查询所有新闻下的栏目
    public List<BankMnue> findBMAll();

    //根据name查询该栏目的id
    public BankMnue findByName(String name);

    //根据id查询该栏目的name
    public BankMnue findById(String id);

    //根据栏目id ,查询审核信息表bank_verifier,拿到该栏目的审核人员
    public BankVerifier findByDisbuteContentId(String id);

    //根据副栏目id ,查询审核信息表bank_verifier,拿到该栏目的审核人员
    public BankVerifier findByDisbuteContentFuId(String id);

    //添加审核人员信息到bank_distribute_verifier
    public int addBankDistributeVerifier(BankDistributeVerifier bankDistributeVerifier);

    //查询审核人员信息到bank_distribute_verifier根据distributeContentId
    public List<BankDistributeVerifier> findBankDistributeVerifierList(String distributeContentId);

    //避免编辑时的重复添加审核人到bank_distribute_verifier表
    //vid 审核人id,type栏目或副栏目 disContentId是发布信息表的id
    public int findChenkBankdVer(@Param("vid") String vid,@Param("type") int type, @Param("disContentId")String disContentId);

    //删除时同时删除bank_distribute_verifier表关联的distributeContentId的数据
    public int deleteBankdVer(String distributeContentId);

    //审核通过不通过,修改bank_distribute_verifier表的vstate的状态1;默认 2,通过,3 驳回
    public int updateBybank_distribute_verifier(@Param("ids")String ids,@Param("userId") String userId,@Param("state") int state);

    //统计审核是否通过全部  根据distributeContentId
    public StateChenck findStateChents(String distributeContentId);

    //通过审核统计的状态,确定该distributeContentId是否通过 就是本表的id   不为空就修改
    public int updateDistributeContentState(@Param("id")String id,@Param("statepid")Integer statepid,@Param("stateparentid")Integer stateparentid);

    //修改remarks(副栏目审核人id数组)
    public int updateRemarksById(@Param("id")String id,@Param("remark")String remark);

    //根据栏目id,栏目或者副栏目,获取审核人信息
    public BankVerifierTrue findByProgramatID(String programid);

    //根据作者id查询部门名称
    public String findOfficeId(String id);

    //根据作者id查询部门id
    public String findOffice(String id);

    //定时器统计数据
    public int addDisSum(DistributeContentSum ds);
    public int delDisSum();
    //type1,部门,2,活跃度,3,热度   month当月 1,历史 2
    public List<DistributeContentSum> distributeContentSumAll(@Param("type")int type,@Param("month")int month);
    //查询DistributeContent数据 type1,部门,2,活跃度,3,热度
    //传入一个author 如果author不为null 就查询本月,否则就查询历史
    public List<DistributeContent> distributeContentType1(DistributeContent ds);
    public List<DistributeContent> distributeContentType2(DistributeContent ds);
    public List<DistributeContent> distributeContentType3(DistributeContent ds);

    /**
     * 根据网栏部室二级菜单id查询发稿
     * @param menuId
     * @return
     */
    public List<DistributeContent> getContentById(String menuId);
    // List<DistributeContent> distributeContentType3();

    /*更新查阅量*/
    public int updateOnclickNum(DistributeContent onclickNum);

    /*管理员是否置顶*/
    public void admintop(DistributeContent distributeContent);

    /**
     * 如果修改审核人,则修改未审核栏目的审核人
     */
    public int updateVeriftyByID(@Param("veriftyId") String veriftyId,@Param("programat_id")String programat_id);
    //由于副栏目可有多个,比较麻烦  //根据id 修改remark字段
    public int updateVeriftyByParentid(@Param("did") String did,@Param("remark")String remark);
    //根据副栏目id查询所有未审核通过的
    public List<DistributeContent> findDistrByProgramatParentid(@Param("programat_parentid") String programat_parentid);

     /**
      * 查询所有部门部门负责人
      * 直接使用BankMnue接收
      * */
    public List<BankMnue> findDepartmentReviewerAll();

    //查询首页二级页面栏目的数据，需要审核通过的
    public List<DistributeContent> findPage1(DistributeContent distributeContent);

    public DistributeContent findprogramatId(DistributeContent distributeContent);

    public List<DistributeContent> findListtg(DistributeContent distributeContent);

    public int again(DistributeContent distributeContent);


    //查询副栏目可选的栏目(type =1 不可见  type=2 可见)
    public List<BankOpenItem> findOpenItemAll(BankOpenItem bankOpenItem);
    //查看全部条数
    public int findAllOpenItemCount();
    //修改
    public int updateBankOpenItem(BankOpenItem bankOpenItem);
    //删除
    public int delBaknOpenItem();
    //增加
    public int addBankOpenItem(BankOpenItem bankOpenItem);
    //保存正文
    public void insertContentText(DistributeContent distributeContent);
    //updata正文
    public void updataContentText(DistributeContent distributeContent);

    //delete
    public void deleteContentText(String id);
}