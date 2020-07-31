/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.backgroundmanage.imagemanage.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.backgroundmanage.imagemanage.entity.BankImage;

/**
 * 背景MAPPER接口
 * @author cheny
 * @version 2019-12-10
 */
@Mapper
@Repository
public interface BankImageMapper extends BaseMapper<BankImage> {

    /**
     * 更换背景图片
     * @param path
     */
    public void updataBackgroundImage(String path,String imgaeType);

    /**
     * 获取背景图片路径
     */
    public String getPath(BankImage bankImage);
}