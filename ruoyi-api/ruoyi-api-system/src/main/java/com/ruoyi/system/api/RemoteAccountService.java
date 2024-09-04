package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BusinessPeople;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteAccountService", value = "system-account")
public interface RemoteAccountService {
    @PostMapping("account/remote/list")
    public R<List<BroadbandAccount>> remoteList(@RequestBody BroadbandAccount broadbandAccount, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping("/account/{id}")
    public R<BroadbandAccount> getById(@PathVariable("id") String id,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @PostMapping("/account/update")
    public AjaxResult update(@RequestBody BroadbandAccount broadbandAccount,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
