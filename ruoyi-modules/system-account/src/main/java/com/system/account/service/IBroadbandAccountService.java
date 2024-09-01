package com.system.account.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;

/**
 * accountService接口
 * 
 * @author wugou
 * @date 2024-08-30
 */
public interface IBroadbandAccountService extends IService<BroadbandAccount>
{

    void register(RegisterBody registerBody);

    void sendCode(String phoneNumber);

    LoginUser login(String phoneNumber, String password);

    List<BroadbandAccount> selectBroadbandAccount(BroadbandAccount broadbandAccount);
}
