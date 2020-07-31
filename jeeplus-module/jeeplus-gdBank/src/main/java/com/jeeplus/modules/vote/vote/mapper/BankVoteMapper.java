/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.vote.vote.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.vote.vote.entity.BankVote;

import java.util.List;

/**
 * 投票MAPPER接口
 * @author cheny
 * @version 2020-01-17
 */
@Mapper
@Repository
public interface BankVoteMapper extends BaseMapper<BankVote> {

    public void updatevotenum(BankVote bankVote);

    public BankVote votenum(BankVote bankVote);

    public List<BankVote> voteinfodata(BankVote bankVote);

    public BankVote getSelectInfo(String id);

    public int updataselect(BankVote bankVote);

    public List<BankVote> getTpList(BankVote bankVote1);

    public void saveTpRecord(BankVote bankVote2);

    public BankVote selectVoteId();
}