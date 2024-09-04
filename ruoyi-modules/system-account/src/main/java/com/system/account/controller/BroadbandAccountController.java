package com.system.account.controller;

import java.util.List;

import cn.hutool.core.date.DateTime;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteComboService;
import com.ruoyi.system.api.model.BroadbandCombo;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.LoginBody;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;
import com.system.account.domain.vo.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.account.service.IBroadbandAccountService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
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
    @Autowired
    private RemoteComboService remoteComboService;


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
        accountInfo.setUserId(SecurityUtils.getUserId().toString());
        accountInfo.setUserName(SecurityUtils.getUsername());
        return success(accountInfo);
    }
    @PutMapping()
    public AjaxResult update(@RequestBody BroadbandAccount broadbandAccount){
        broadbandAccountService.updateById(broadbandAccount);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id){
        broadbandAccountService.removeById(id);
        //TODO:后续校验用户是否存在套餐
        return success();
    }
    @GetMapping("/{id}")
    public R<BroadbandAccount> getById(@PathVariable String id){
        BroadbandAccount broadbandAccount = broadbandAccountService.getById(id);
        return R.ok(broadbandAccount);
    }
    @GetMapping("/list")
    public TableDataInfo list(BroadbandAccount broadbandAccount){
        startPage();
        List<BroadbandAccount> list = broadbandAccountService.selectBroadbandAccount(broadbandAccount);
        return getDataTable(list);
    }
    @PostMapping("/remote/list")
    public R<List<BroadbandAccount>> remoteList(@RequestBody BroadbandAccount broadbandAccount){
        List<BroadbandAccount> list = broadbandAccountService.selectBroadbandAccount(broadbandAccount);
        return R.ok(list);
    }
    @GetMapping("/combo")
    public AjaxResult getCombo(){
        Long id = SecurityUtils.getUserId();
        BroadbandAccount broadbandAccount = broadbandAccountService.getById(id);
        BroadbandCombo broadbandCombo = remoteComboService.get(broadbandAccount.getComboId(), SecurityConstants.INNER).getData();
        return success(broadbandCombo);
    }
    @PostMapping("/addCombo/{id}")
    public AjaxResult addCombo(@PathVariable String id){
        broadbandAccountService.addCombo(id);
        return success();
    }
    @PostMapping("/addAmount")
    public AjaxResult addAmount(@RequestBody BroadbandAccount broadbandAccount){
        broadbandAccount.setAccountId(SecurityUtils.getUserId().toString());
        BroadbandAccount broadbandAccount1 = broadbandAccountService.getById(SecurityUtils.getUserId());
        broadbandAccount.setAmount(broadbandAccount1.getAmount().add(broadbandAccount.getAmount()));
        broadbandAccountService.updateById(broadbandAccount);
        return success();
    }
    @GetMapping("/cancelCombo")
    public AjaxResult cancelCombo(){
        BroadbandAccount broadbandAccount = new BroadbandAccount();
        broadbandAccount.setStatus("3");
        broadbandAccount.setAccountId(SecurityUtils.getUserId().toString());
        broadbandAccountService.updateById(broadbandAccount);
        return success();
    }
}
