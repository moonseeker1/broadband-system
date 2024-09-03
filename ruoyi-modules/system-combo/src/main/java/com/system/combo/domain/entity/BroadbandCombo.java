package com.system.combo.domain.entity;

import java.math.BigDecimal;
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
@TableName("broadband_combo")
public class BroadbandCombo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "combo_id")
    private String comboId;
    private String comboName;

    /**
     * 0-按月，1-按年
//     */
    private Integer unit;

    private Integer value;

    private BigDecimal price;

    /**
     * 创建用户id
     */
    private String createBy;
    private Integer bandwidth;

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
