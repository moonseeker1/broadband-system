package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BusinessPeople;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteAccountService", value = "system-account")
public interface RemoteAccountService {
    @PostMapping("account/remote/list")
    public R<List<BusinessPeople>> remoteList(@RequestBody BroadbandAccount broadbandAccount, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
