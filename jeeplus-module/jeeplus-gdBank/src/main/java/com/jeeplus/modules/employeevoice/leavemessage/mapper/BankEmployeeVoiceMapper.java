/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.employeevoice.leavemessage.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.employeevoice.leavemessage.entity.BankEmployeeVoice;

import java.util.List;

/**
 * 员工心声MAPPER接口
 * @author cheny
 * @version 2019-12-18
 */
@Mapper
@Repository
public interface BankEmployeeVoiceMapper extends BaseMapper<BankEmployeeVoice> {

    /**
     *
     * 保存回复
     * @param bankEmployeeVoice
     */
    public void saveReply(BankEmployeeVoice bankEmployeeVoice);

    /**
     *根据留言id,查询回复
     */
    public List<BankEmployeeVoice> getReply(BankEmployeeVoice bankEmployeeVoice);

    /**
     * 更新回复状态
     */
    public void updateisReply(BankEmployeeVoice bankEmployeeVoice);

    //更新回复
    public void updataReply(BankEmployeeVoice bankEmployeeVoice);

    public List<BankEmployeeVoice> getReplyList(BankEmployeeVoice bankEmployeeVoice1);
}