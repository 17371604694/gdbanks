/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifierts.verifier.mapper;

import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.verifierts.verifier.entity.BankMenu;
import com.jeeplus.modules.verifierts.verifier.entity.BankUser;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.verifierts.verifier.entity.BankVerifier;

import java.util.List;

/**
 * 添加审核人审核栏目MAPPER接口
 * @author chenl
 * @version 2019-11-21
 */
@Mapper
@Repository
public interface BankVerifierMapper extends BaseMapper<BankVerifier> {


    //查询所有栏目
    public List<Menu> findLanMuAll();

    //判断是否重复,根据名称,因为获取不到id
    public BankVerifier findByParmName(String name);

    //添加选中用户
    public int addUsers(BankUser bankUser);

    //删除选中用户
    public int delUsers(String id);

    //查询全部
    public List<BankUser> finBankUserAll();

    //删除全部
    public int delUsersAll();

    //获取栏目临时
    public List<BankMenu> findBankMenuAll();


    //查询所有一级栏目
    public List<BankMenu> findBankMenuFistAll();
    //查询该id下是否有其他下级栏目
    public List<BankMenu> findBankMenuExtByIdAll(String id);



}