/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.advertisingaudit.advertising.mapper;

import com.jeeplus.modules.consignfileapproval.confileapproval.entity.ConsignFileApproval;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.advertisingaudit.advertising.entity.AdvertisingAudit;

import java.util.List;

/**
 * 广告审核MAPPER接口
 * @author chenl
 * @version 2019-12-10
 */
@Mapper
@Repository
public interface AdvertisingAuditMapper extends BaseMapper<AdvertisingAudit> {

    /**
     * 广告审核操作
     * @param advertisingAudit
     */
    public void updataStateStepAndStateSuccess(AdvertisingAudit advertisingAudit);


    int updatePass(@Param("id") String id, @Param("state") Integer state, @Param("sta")Integer sta, @Param("remarks")String remarks);

    int updataChoices(@Param("id") String id,@Param("choices") Integer choices);

    //查询 申请人是当前用户或者 审核人里包含当前用户
    List<AdvertisingAudit> findListConAll(@Param("userId")String userId, @Param("pageNo")Integer pageNo,
                                          @Param("pageSize")Integer pageSize,
                                          @Param("stateStep")Integer stateStep,
                                          @Param("beginTime")String beginTime,
                                          @Param("endTime")String endTime,
                                          @Param("applicantUnit")String applicantUnit,
                                          @Param("state")Integer state
    );
    Integer findListConAllCount(@Param("userId")String userId);

}