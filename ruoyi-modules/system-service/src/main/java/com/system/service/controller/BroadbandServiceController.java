package com.system.service.controller;


import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteWorkOrderService;
import com.ruoyi.system.api.model.WorkOrder;
import com.system.service.domain.entity.BroadbandService;
import com.system.service.domain.entity.ServiceType;
import com.system.service.service.IBroadbandServiceService;
import com.system.service.service.IServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spi.service.contexts.SecurityContext;

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
@RequestMapping("/broadband-service")
public class BroadbandServiceController extends BaseController {
    @Autowired
    IBroadbandServiceService broadbandServiceService;
    @Autowired
    IServiceTypeService serviceTypeService;
    @Autowired
    RemoteWorkOrderService remoteWorkOrderService;
    @PostMapping("/add")
    public AjaxResult add(@RequestBody BroadbandService broadbandService){
        broadbandService.setBroadbandServiceId(IdUtil.getSnowflakeNextIdStr());
        ServiceType serviceType = serviceTypeService.getById(broadbandService.getTypeId());
        broadbandService.setTypeName(serviceType.getTypeName());
        broadbandServiceService.save(broadbandService);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setServiceId(id);
        if(!remoteWorkOrderService.list(workOrder, SecurityConstants.INNER).getData().isEmpty()){
            throw new ServiceException("存在服务订单无法删除");
        }
        broadbandServiceService.removeById(id);
        return success();
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BroadbandService broadbandService){
        broadbandServiceService.updateById(broadbandService);
        return success();
    }
    @GetMapping("/{id}")
    public R<BroadbandService> getById(@PathVariable String id){
        BroadbandService broadbandService = broadbandServiceService.getById(id);
        return R.ok(broadbandService);
    }

    @GetMapping("/list")
    public TableDataInfo list(BroadbandService broadbandService){
        startPage();
        List<BroadbandService> list = broadbandServiceService.listBroadbandService(broadbandService);
        return getDataTable(list);
    }
}
