package com.system.workorder.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.workorder.domain.entity.WorkOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@Mapper
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

}
