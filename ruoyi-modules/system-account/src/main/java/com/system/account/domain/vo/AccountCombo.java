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
        AccountCombo accountCombo = new AccountCombo();
        accountCombo.setComboId(broadbandCombo.getComboId());
        accountCombo.setComboName(broadbandCombo.getComboName());
        accountCombo.setBandwidth(broadbandCombo.getBandwidth());
        accountCombo.setPrice(broadbandCombo.getPrice());
        accountCombo.setUnit(broadbandCombo.getUnit());
        accountCombo.setValue(broadbandCombo.getValue());
    }
    public AccountCombo(){

    }
}
