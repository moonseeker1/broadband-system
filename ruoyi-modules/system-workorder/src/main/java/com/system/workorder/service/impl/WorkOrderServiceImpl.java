package com.system.workorder.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.StringUtils;
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

import java.util.List;

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

    @Override
    public List<WorkOrder> listWorkOrder(WorkOrder workOrder) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(workOrder.getServiceId()),WorkOrder::getServiceId,workOrder.getServiceId())
                .eq(StringUtils.isNotBlank(workOrder.getAccountId()),WorkOrder::getAccountId,workOrder.getAccountId())
                .eq(StringUtils.isNotBlank(workOrder.getBusinessPeopleId()),WorkOrder::getBusinessPeopleId,workOrder.getBusinessPeopleId())
                .like(StringUtils.isNotBlank(workOrder.getWorkOrderName()),WorkOrder::getWorkOrderName,workOrder.getWorkOrderName());
        List<WorkOrder> list = workOrderMapper.selectList(lambdaQueryWrapper);
        return list;
    }
}
