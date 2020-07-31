/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifiertrue.verifiertrues.mapper;

import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.verifierts.verifier.entity.BankMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.verifiertrue.verifiertrues.entity.BankVerifierTrue;

import java.util.List;

/**
 * 添加审核人审核栏目MAPPER接口
 * @author chenl
 * @version 2019-12-09
 */
@Mapper
@Repository
public interface BankVerifierTrueMapper extends TreeMapper<BankVerifierTrue> {

    //添加审核人
    public int addBank(BankVerifierTrue bankVerifierTrue);
    //查询是否存在
    public BankVerifierTrue findByClunmName(String name);

    //根据父id查询
    public List<BankVerifierTrue> findAllByPid(String id);

    //设置审核人
    public int updateById(@Param("verify") String verify,@Param("remark")String remark, @Param("id")String id);

    //查询所有栏目
    public List<Menu> findLanMuAll();

    //获取栏目临时
    public List<BankMenu> findBankMenuAll();

    //查询所有一级栏目
    public List<BankMenu> findBankMenuFistAll();

    //查询该id下是否有其他下级栏目
    public List<BankMenu> findBankMenuExtByIdAll(String id);

    public int deleteByAll();

    //获取需要审核的的一级栏目
    public List<Menu> getbs();

    //根据用户id查询 栏目id
    public List<BankVerifierTrue> findBankTrueByVerifyPerson(String id);

}