package com.system.workorder.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.RemoteBusinessService;
import com.system.workorder.domain.entity.WorkOrder;

import com.system.workorder.domain.vo.WorkOrderDetails;
import com.system.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/workOrder")
public class WorkOrderController extends BaseController {
    @Autowired
    IWorkOrderService workOrderService;

    @PostMapping("/generateOrder")
    public AjaxResult generateOrder(@RequestBody WorkOrder workorder){
        workOrderService.generateOrder(workorder);

        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id){
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id!=null,WorkOrder::getWorkOrderId,id);
        WorkOrder workorder = workOrderService.getById(id);
        if(workorder.getState()!=2){
            throw new ServiceException("订单未完成，不可删除");
        }
        workOrderService.removeById(id);
        return success();
    }
    @GetMapping("/{id}")
    public AjaxResult getDetails(@PathVariable String id){
        WorkOrderDetails workOrderDetails = workOrderService.getDetails(id);
        return success(workOrderDetails);
    }
    @PostMapping("/remote/list")
    public R<List<WorkOrder>> list(WorkOrder workOrder){
        List<WorkOrder> list = workOrderService.listWorkOrder(workOrder);
        return R.ok(list);
    }
    @GetMapping("/list")
    public TableDataInfo listWorkOrder(WorkOrder workOrder){
        startPage();
        List<WorkOrder> list = workOrderService.listWorkOrder(workOrder);
        return getDataTable(list);
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody WorkOrder workOrder){
        workOrderService.updateById(workOrder);
        return success();
    }
    @GetMapping("/list/BusinessPeopleOrder")
    public TableDataInfo listBusinessPeopleOrder(WorkOrder workOrder){
        startPage();
        workOrder.setBusinessPeopleId(SecurityUtils.getUserId().toString());
        List<WorkOrder> list = workOrderService.listWorkOrder(workOrder);
        return getDataTable(list);
    }
    @GetMapping("/list/AccountOrder")
    public TableDataInfo listAccountOrder(WorkOrder workOrder){
        startPage();
        workOrder.setAccountId(SecurityUtils.getUserId().toString());
        List<WorkOrder> list = workOrderService.listWorkOrder(workOrder);
        return getDataTable(list);
    }

}
