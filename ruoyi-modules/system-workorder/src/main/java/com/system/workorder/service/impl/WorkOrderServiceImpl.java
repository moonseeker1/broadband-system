package com.system.workorder.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.RemoteBusinessService;
import com.ruoyi.system.api.RemoteServiceService;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BroadbandService;
import com.ruoyi.system.api.model.BusinessPeople;
import com.system.workorder.domain.entity.WorkOrder;
import com.system.workorder.domain.vo.WorkOrderDetails;
import com.system.workorder.mapper.WorkOrderMapper;
import com.system.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {
    @Autowired
    WorkOrderMapper workOrderMapper;
    @Autowired
    RemoteBusinessService remoteBusinessService;
    @Autowired
    RemoteAccountService remoteAccountService;
    @Autowired
    RemoteServiceService remoteServiceService;
    @Override
    public WorkOrderDetails getDetails(String id) {
        WorkOrder workOrder = workOrderMapper.selectById(id);
        BroadbandAccount broadbandAccount = remoteAccountService.getById(workOrder.getAccountId(), SecurityConstants.INNER).getData();
        BusinessPeople businessPeople = remoteBusinessService.getById(workOrder.getBusinessPeopleId(),SecurityConstants.INNER).getData();
        BroadbandService broadbandService = remoteServiceService.getById(workOrder.getServiceId(),SecurityConstants.INNER).getData();
        WorkOrderDetails workOrderDetails = new WorkOrderDetails();
        workOrderDetails.setWorkOrder(workOrder);
        workOrderDetails.setBroadbandService(broadbandService);
        workOrderDetails.setBroadbandAccount(broadbandAccount);
        workOrderDetails.setBusinessPeople(businessPeople);
        return workOrderDetails;
    }
}
