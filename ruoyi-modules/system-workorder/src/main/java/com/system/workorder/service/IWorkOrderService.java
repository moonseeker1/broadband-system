package com.system.workorder.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.system.workorder.domain.entity.WorkOrder;
import com.system.workorder.domain.vo.WorkOrderDetails;

import java.util.List;

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

    List<WorkOrder> listWorkOrder(WorkOrder workOrder);

    long getDistance(String startLonLat, String endLonLat);

    void generateOrder(WorkOrder workorder);
}
