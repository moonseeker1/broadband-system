package com.system.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.system.service.domain.entity.ServiceType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author filepiece
 */
@Data
public class ServiceTreeSelect<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 父类id  */
    private String parentId;
    /** 创建人**/
    private String createBy;
    /** 创建时间**/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 说明  */
    private String remark;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ServiceTreeSelect> children;
    public ServiceTreeSelect()
    {
    }

    public  ServiceTreeSelect(ServiceType serviceType) {
        this.id = serviceType.getTypeId();
        this.label = serviceType.getTypeName();
        this.parentId = serviceType.getParentId();
        this.setChildren(serviceType.getChildren().stream().map(ServiceTreeSelect::new).collect(Collectors.toList()));
    }
}
