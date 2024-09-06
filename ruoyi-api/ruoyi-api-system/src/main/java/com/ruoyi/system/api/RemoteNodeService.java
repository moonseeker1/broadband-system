package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteLogFallbackFactory;

import com.ruoyi.system.api.model.Node;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteNodeService", value = "system-node")
public interface RemoteNodeService {
    @PostMapping ("/node/remote/list")
    public R<List<Node>> remoteList(@RequestBody Node node, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @PutMapping("/node/update")
    public AjaxResult update(@RequestBody Node node,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping("/node/{id}")
    public R<Node> getById(@PathVariable("id") String id,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
