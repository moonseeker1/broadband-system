package com.system.service.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.system.service.domain.entity.BroadbandService;
import com.system.service.domain.entity.ServiceType;
import com.system.service.service.IBroadbandServiceService;
import com.system.service.service.IServiceTypeService;
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
@RequestMapping("/service-type")
public class ServiceTypeController extends BaseController {
    @Autowired
    IServiceTypeService serviceTypeService;
    @Autowired
    IBroadbandServiceService broadbandServiceService;
    @GetMapping("/typeTree")
    public AjaxResult typeTree(ServiceType serviceType) {
        LambdaQueryWrapper<ServiceType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(serviceType.getTypeName()),ServiceType::getTypeName,serviceType.getTypeName());
        List<ServiceType> serviceTypes = serviceTypeService.list(lambdaQueryWrapper);
        return success(serviceTypeService.buildTypeTreeSelect(serviceTypes, serviceType));
    }
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ServiceType serviceType){
        serviceType.setTypeId(IdUtil.getSnowflakeNextIdStr());
        serviceTypeService.save(serviceType);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id){
        serviceTypeService.delete(id);
        return success();
    }
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id){
        ServiceType serviceType = serviceTypeService.getById(id);
        return success(serviceType);
    }
    @PutMapping("/update")

    public AjaxResult update(@RequestBody ServiceType serviceType){
        LambdaQueryWrapper<BroadbandService> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(serviceType.getTypeId()),BroadbandService::getTypeId,serviceType.getTypeId());
        BroadbandService broadbandService = new BroadbandService();
        broadbandService.setTypeName(serviceType.getTypeName());
        broadbandServiceService.update(broadbandService,lambdaQueryWrapper);
        serviceTypeService.updateById(serviceType);
        return success();
    }
}
