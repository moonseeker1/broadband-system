package com.system.workorder.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("workorder")
public class Workorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "workorder_id", type = IdType.AUTO)
    private Long workorderId;

    private String workorderName;

    private Integer serviceId;

    private Integer businessPeopleId;

    private Integer accountId;

    /**
     * 编目id
     */
    private String typeId;

    /**
     * 编目名称
     */
    private String typeName;

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


}
