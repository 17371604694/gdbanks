/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.vote.vote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.vote.vote.entity.BankVote;
import com.jeeplus.modules.vote.vote.mapper.BankVoteMapper;


/**
 * 投票Service
 * @author cheny
 * @version 2020-01-17
 */
@Service
@Transactional(readOnly = true)
public class BankVoteService extends CrudService<BankVoteMapper, BankVote> {

	@Autowired
	private BankVoteMapper bankVoteMapper;

	public BankVote get(String id) {
		return super.get(id);
	}
	
	public List<BankVote> findList(BankVote bankVote) {
		return super.findList(bankVote);
	}
	
	public Page<BankVote> findPage(Page<BankVote> page, BankVote bankVote) {
		return super.findPage(page, bankVote);
	}

	public Page<BankVote> findPage1(Page<BankVote> page, BankVote bankVote) {
		return super.findPage1(page, bankVote);
	}
	
	@Transactional(readOnly = false)
	public void save(BankVote bankVote) {
		super.save(bankVote);
	}

	/*保存选项*/
	@Transactional(readOnly = false)
	public void saveselect(BankVote bankVote) {
		super.saveselect(bankVote);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankVote bankVote) {
		super.delete(bankVote);
	}

	@Transactional(readOnly = false)
	public void deleteselect(String id) {
		super.deleteselect(id);
	}

	/*更新投票数*/
	@Transactional(readOnly = false)
	public void updatevotenum(BankVote bankVote) {
		bankVoteMapper.updatevotenum(bankVote);
	}

	@Transactional(readOnly = false)
	public BankVote votenum(BankVote bankVote) {
		return  bankVoteMapper.votenum(bankVote);
	}

	/*查询主题的投票详情*/
	@Transactional(readOnly = false)
	public  List<BankVote> voteinfodata(BankVote bankVote) {
		return  bankVoteMapper.voteinfodata(bankVote);
	}

	/*查询选项详情*/
    public BankVote getSelectInfo(String id) {
		return  bankVoteMapper.getSelectInfo(id);
    }

    /*更新选项*/
    @Transactional(readOnly = false)
	public int updataselect(BankVote bankVote) {
		return  bankVoteMapper.updataselect(bankVote);
	}

	/*查询当前用户投票记录*/
	public List<BankVote> getTpList(BankVote bankVote1) {
		return  bankVoteMapper.getTpList(bankVote1);
	}

	@Transactional(readOnly = false)
	public void saveTpRecord(BankVote bankVote2) {
		  bankVoteMapper.saveTpRecord(bankVote2);
	}

    public BankVote selectVoteId() {
		return bankVoteMapper.selectVoteId();
    }
}