package com.jeeplus.modules.bankimagemanagement.image.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.bankimagemanagement.image.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageMapper extends BaseMapper<Image> {

}
