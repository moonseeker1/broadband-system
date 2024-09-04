package com.system.account.domain.vo;

import com.ruoyi.system.api.model.BroadbandCombo;
import lombok.Data;

import java.util.Date;

@Data
public class AccountCombo extends BroadbandCombo {
    private Date beginTime;
    private Date endTime;
    private String status;
    public AccountCombo(BroadbandCombo broadbandCombo){

        this.setComboId(broadbandCombo.getComboId());
        this.setComboName(broadbandCombo.getComboName());
        this.setBandwidth(broadbandCombo.getBandwidth());
        this.setPrice(broadbandCombo.getPrice());
        this.setUnit(broadbandCombo.getUnit());
        this.setValue(broadbandCombo.getValue());
    }
    public AccountCombo(){

    }
}
