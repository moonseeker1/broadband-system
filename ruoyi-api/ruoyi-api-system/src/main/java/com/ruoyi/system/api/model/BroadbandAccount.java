package com.ruoyi.system.api.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * account对象 broadband_account
 * 
 * @author wugou
 * @date 2024-08-30
 */
@Data
public class BroadbandAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @TableId
    /** $column.columnComment */
    private Long accountId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phoneNumber;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long comboId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date beginTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date endTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal longitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal latitude;
    private String name;

    /** 账户余额 */
    @Excel(name = "账户余额")
    private BigDecimal amount;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
