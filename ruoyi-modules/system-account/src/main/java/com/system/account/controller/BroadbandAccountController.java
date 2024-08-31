package com.system.account.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.LoginBody;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;
import com.system.account.domain.vo.AccountInfo;
import org.apache.catalina.security.SecurityUtil;
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
    @PostMapping("/login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = broadbandAccountService.login(form.getPhoneNumber(), form.getPassword());
        return R.ok(tokenService.createAccountToken(userInfo));
    }
    @GetMapping("/getInfo")
    public AjaxResult getInfo(){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserId(SecurityUtils.getUserId());
        accountInfo.setUserName(SecurityUtils.getUsername());
        return success(accountInfo);
    }
    @PutMapping()
    public AjaxResult update(@RequestBody BroadbandAccount broadbandAccount){
        broadbandAccountService.updateById(broadbandAccount);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id){
        broadbandAccountService.removeById(id);
        //TODO:后续校验用户是否存在套餐
        return success();
    }
}
