/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.administrativearchives.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.consignfileapproval.administrativearchives.entity.AdministrativeArchivesApprove;

import java.util.List;

/**
 * 文书档案利用审批单MAPPER接口
 * @author chenl
 * @version 2019-12-02
 */
@Mapper
@Repository
public interface AdministrativeArchivesApproveMapper extends BaseMapper<AdministrativeArchivesApprove> {



    public int updatePass(@Param("id") String id, @Param("state") Integer state, @Param("sta")Integer sta,@Param("remarks")String remarks);

    int updataChoices(@Param("id") String id,@Param("choices") Integer choices);


    //查询 申请人是当前用户或者 审核人里包含当前用户
    List<AdministrativeArchivesApprove> findListConAll(@Param("userId")String userId,
                                                       @Param("pageNo")Integer pageNo,
                                                       @Param("pageSize")Integer pageSize,
                                                       @Param("stateStep")Integer stateStep,
                                                       @Param("beginTime")String beginTime,
                                                       @Param("endTime")String endTime,
                                                       @Param("useDepartment")String useDepartment,
                                                       @Param("withWay")String withWay,
                                                       @Param("beginReturnTime")String beginReturnTime,
                                                       @Param("endReturnTime")String endReturnTime,
                                                       @Param("state")Integer state
    );
    Integer findListConAllCount(@Param("userId")String userId);

}