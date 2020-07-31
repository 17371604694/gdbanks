/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.mail.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.mail.entity.MailCompose;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 发件箱MAPPER接口
 *
 * @author jeeplus
 * @version 2015-11-15
 */
@Mapper
@Repository
public interface MailComposeMapper extends BaseMapper<MailCompose> {
    public int getCount(MailCompose entity);
}