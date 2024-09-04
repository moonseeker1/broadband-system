package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.model.BroadbandCombo;
import com.ruoyi.system.api.model.WorkOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteWorkOrderService", value = "system-workorder")
public interface RemoteWorkOrderService {
    @PostMapping ("/workOrder/remote/list")
    public R<List<WorkOrder>> list(@RequestBody WorkOrder workOrder,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @PostMapping("/workOrder/update")
    public AjaxResult update(@RequestBody WorkOrder workOrder,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
