package com.system.service.controller;


import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.system.service.domain.entity.BroadbandService;
import com.system.service.service.IBroadbandServiceService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/broadband-service")
public class BroadbandServiceController extends BaseController {
    @Autowired
    IBroadbandServiceService broadbandServiceService;
    @PostMapping("/add")
    public AjaxResult add(@RequestBody BroadbandService broadbandService){
        broadbandService.setBroadbandServiceId(IdUtil.getSnowflakeNextIdStr());
        BroadbandService broadbandService1 = broadbandServiceService.getById(broadbandService);
        broadbandService.setBroadbandServiceName(broadbandService1.getBroadbandServiceName());
        broadbandServiceService.save(broadbandService);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id){
        //TODO:判断是否存在该服务工单
        broadbandServiceService.removeById(id);
        return success();
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BroadbandService broadbandService){
        broadbandServiceService.updateById(broadbandService);
        return success();
    }
    @GetMapping("/{id}")
    public R<BroadbandService> getById(@PathVariable Long id){
        BroadbandService broadbandService = broadbandServiceService.getById(id);
        return R.ok(broadbandService);
    }

    @GetMapping("/list")
    public TableDataInfo list(@RequestBody BroadbandService broadbandService){
        startPage();
        List<BroadbandService> list = broadbandServiceService.listBroadbandService(broadbandService);
        return getDataTable(list);
    }
}
