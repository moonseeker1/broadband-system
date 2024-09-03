package com.system.workorder.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.system.workorder.domain.entity.WorkOrder;
import com.system.workorder.domain.vo.WorkOrderDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
public interface IWorkOrderService extends IService<WorkOrder> {

    WorkOrderDetails getDetails(String id);
}
