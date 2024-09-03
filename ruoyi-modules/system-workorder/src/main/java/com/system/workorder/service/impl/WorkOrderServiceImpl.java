package com.system.workorder.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.workorder.domain.entity.WorkOrder;
import com.system.workorder.mapper.WorkOrderMapper;
import com.system.workorder.service.IWorkOrderService;
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

}
