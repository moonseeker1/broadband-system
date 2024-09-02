package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.BroadbandCombo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteComboService", value = "system-combo")
public interface RemoteComboService {
    @GetMapping("/broadband-combo/{id}")
    public R<BroadbandCombo> get(@PathVariable("id")  Long id,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
