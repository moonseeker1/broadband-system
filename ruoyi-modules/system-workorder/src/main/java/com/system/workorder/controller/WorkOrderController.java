package com.system.workorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.system.workorder.domain.entity.WorkOrder;

import com.system.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        workOrderService.save(workorder);
        return success();
    }
    @DeleteMapping("{id}")
    public AjaxResult delete(@PathVariable Long id){
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(id!=null,WorkOrder::getWorkOrderId,id);
        WorkOrder workorder = workOrderService.getById(id);
        if(workorder.getState()!=2){
            throw new ServiceException("订单未完成，不可删除");
        }
        workOrderService.removeById(id);
        return success();
    }

}
