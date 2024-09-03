package com.system.workorder.domain.vo;

import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BroadbandService;
import com.ruoyi.system.api.model.BusinessPeople;
import com.system.workorder.domain.entity.WorkOrder;
import lombok.Data;

@Data
public class WorkOrderDetails {
    WorkOrder workOrder;
    BroadbandAccount broadbandAccount;
    BusinessPeople businessPeople;
    BroadbandService broadbandService;
}
