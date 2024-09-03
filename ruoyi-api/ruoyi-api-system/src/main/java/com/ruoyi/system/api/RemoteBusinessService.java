package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteFileFallbackFactory;
import com.ruoyi.system.api.model.BusinessPeople;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteBusinessService", value = "system-business-people")
public interface RemoteBusinessService {
    @GetMapping ("business-people/remote/list")
    public R<List<BusinessPeople>> remoteList(BusinessPeople businessPeople,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping("/business-people/{id}")
    public R<BusinessPeople> getById(@PathVariable("id") String id,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
