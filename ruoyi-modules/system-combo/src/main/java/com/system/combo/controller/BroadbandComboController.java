package com.system.combo.controller;


import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.system.combo.domain.entity.BroadbandCombo;
import com.system.combo.service.IBroadbandComboService;
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
@RequestMapping("/broadband-combo")
public class BroadbandComboController extends BaseController {
    @Autowired
    IBroadbandComboService broadbandComboService;
    @Autowired
    RemoteAccountService remoteAccountService;
    @PostMapping("/add")
    public AjaxResult add(@RequestBody BroadbandCombo broadbandCombo){
        broadbandCombo.setComboId(IdUtil.getSnowflakeNextId());
        broadbandComboService.save(broadbandCombo);
        return success();
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BroadbandCombo broadbandCombo){
        broadbandComboService.updateById(broadbandCombo);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id){
        BroadbandAccount broadbandAccount = new BroadbandAccount();
        broadbandAccount.setComboId(id);
        if(!remoteAccountService.remoteList(broadbandAccount, SecurityConstants.INNER).getData().isEmpty()){
            throw new ServiceException("套餐下仍有用户");
        }
        else{
            broadbandComboService.removeById(id);
        }
        return success();
    }
    @GetMapping("/{id}")
    public R<BroadbandCombo> get(@PathVariable Long id){
        BroadbandCombo broadbandCombo = broadbandComboService.getById(id);
        return R.ok(broadbandCombo);
    }
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody BroadbandCombo broadbandCombo){
        startPage();
        List<BroadbandCombo> list = broadbandComboService.listCombo(broadbandCombo);
        return getDataTable(list);
    }



}
