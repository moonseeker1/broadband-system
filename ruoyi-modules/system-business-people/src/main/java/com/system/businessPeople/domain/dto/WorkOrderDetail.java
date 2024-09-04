package com.system.businessPeople.domain.dto;

import com.ruoyi.system.api.model.WorkOrder;
import lombok.Data;

@Data
public class WorkOrderDetail {
    private WorkOrder workOrder;
    private String  serviceName;
    private String accountName;
}
