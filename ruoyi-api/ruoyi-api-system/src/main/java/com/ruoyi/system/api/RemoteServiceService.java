package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.BroadbandService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteServiceService", value = "system-service")
public interface RemoteServiceService {
    @GetMapping("/broadband-service/{id}")
    public R<BroadbandService> getById(@PathVariable("id")String id ,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
