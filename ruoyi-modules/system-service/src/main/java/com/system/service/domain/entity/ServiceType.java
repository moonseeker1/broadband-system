package com.system.service.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("service_type")
public class ServiceType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编目id
     */
    @Getter
    @TableId(value = "type_id", type = IdType.AUTO)
    private String typeId;

    /**
     * 编目名称
     */
    private String typeName;

    /**
     * 父级编目id
     */
    private String parentId;

    /**
     * 创建用户id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
    @TableField(exist = false)
    private List<ServiceType> children = new ArrayList<ServiceType>();

}
