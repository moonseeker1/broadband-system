package com.system.account.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.LoginBody;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.system.account.service.IBroadbandAccountService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * accountController
 * 
 * @author wugou
 * @date 2024-08-30
 */
@RestController
@RequestMapping("/account")
public class BroadbandAccountController extends BaseController
{
    @Autowired
    private IBroadbandAccountService broadbandAccountService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询account列表
     */
    @RequiresPermissions("system:account:list")
    @GetMapping("/list")
    public TableDataInfo list(BroadbandAccount broadbandAccount)
    {
        startPage();
        List<BroadbandAccount> list = broadbandAccountService.selectBroadbandAccountList(broadbandAccount);
        return getDataTable(list);
    }

    /**
     * 导出account列表
     */
    @RequiresPermissions("system:account:export")
    @Log(title = "account", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BroadbandAccount broadbandAccount)
    {
        List<BroadbandAccount> list = broadbandAccountService.selectBroadbandAccountList(broadbandAccount);
        ExcelUtil<BroadbandAccount> util = new ExcelUtil<BroadbandAccount>(BroadbandAccount.class);
        util.exportExcel(response, list, "account数据");
    }

    /**
     * 获取account详细信息
     */
    @RequiresPermissions("system:account:query")
    @GetMapping(value = "/{accountId}")
    public AjaxResult getInfo(@PathVariable("accountId") Long accountId)
    {
        return success(broadbandAccountService.selectBroadbandAccountByAccountId(accountId));
    }

    /**
     * 新增account
     */
    @RequiresPermissions("system:account:add")
    @Log(title = "account", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BroadbandAccount broadbandAccount)
    {
        return toAjax(broadbandAccountService.insertBroadbandAccount(broadbandAccount));
    }

    /**
     * 修改account
     */
    @RequiresPermissions("system:account:edit")
    @Log(title = "account", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BroadbandAccount broadbandAccount)
    {
        return toAjax(broadbandAccountService.updateBroadbandAccount(broadbandAccount));
    }

    /**
     * 删除account
     */
    @RequiresPermissions("system:account:remove")
    @Log(title = "account", businessType = BusinessType.DELETE)
	@DeleteMapping("/{accountIds}")
    public AjaxResult remove(@PathVariable Long[] accountIds)
    {
        return toAjax(broadbandAccountService.deleteBroadbandAccountByAccountIds(accountIds));
    }
    @GetMapping("/sendCode/{phoneNumber}")
    public AjaxResult sendCode(@PathVariable String phoneNumber){
        broadbandAccountService.sendCode(phoneNumber);
        return success();

    }
    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody registerBody){
        broadbandAccountService.register(registerBody);
        return success();
    }
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = broadbandAccountService.login(form.getPhoneNumber(), form.getPassword());
        return R.ok(tokenService.createAccountToken(userInfo));
    }
}
