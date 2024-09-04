package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.BroadbandCombo;
import com.ruoyi.system.api.model.WorkOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteWorkOrderService", value = "system-workorder")
public interface RemoteWorkOrderService {
    @GetMapping("/workOrder/remote/list")
    public R<List<WorkOrder>> list(WorkOrder workOrder);
}
