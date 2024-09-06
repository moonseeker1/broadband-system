package com.ruoyi.job.task;

import cn.hutool.core.date.DateTime;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.RemoteComboService;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BroadbandCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.utils.StringUtils;

import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{   @Autowired
    RemoteAccountService remoteAccountService;
    @Autowired
    RemoteComboService remoteComboService;
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
    public void accountComboScan(){
        BroadbandAccount broadbandAccount = new BroadbandAccount();
        List<BroadbandAccount> list = remoteAccountService.remoteList(broadbandAccount, SecurityConstants.INNER).getData();
        for(int i=0;i<list.size();i++){
            broadbandAccount = list.get(i);
            if(broadbandAccount.getStatus().equals("0")){
                continue;
            }
            if(broadbandAccount.getStatus().equals("3")){
                if(broadbandAccount.getEndTime().before(DateTime.now())){
                    broadbandAccount.setStatus("0");
                    broadbandAccount.setComboId(null);
                    broadbandAccount.setBeginTime(null);
                    broadbandAccount.setEndTime(null);
                    remoteAccountService.update(broadbandAccount,SecurityConstants.INNER);
                    continue;
                }
            }
            BroadbandCombo broadbandCombo = remoteComboService.get(broadbandAccount.getComboId(),SecurityConstants.INNER).getData();
            if(broadbandAccount.getStatus().equals("2")){
                if(broadbandAccount.getAmount().compareTo(broadbandCombo.getPrice())>=0){
                    broadbandAccount.setStatus("1");
                    broadbandAccount.setAmount(broadbandAccount.getAmount().subtract(broadbandCombo.getPrice()));
                    broadbandAccount.setBeginTime(DateTime.now());
                    if(broadbandCombo.getUnit()==0){
                        broadbandAccount.setEndTime(DateUtils.addMonths(DateTime.now(),broadbandCombo.getValue()));
                    }
                    if(broadbandCombo.getUnit()==1){
                        broadbandAccount.setEndTime(DateUtils.addYears(DateTime.now(),broadbandCombo.getValue()));
                    }
                    remoteAccountService.update(broadbandAccount,SecurityConstants.INNER);
                }
            }
            if(broadbandAccount.getStatus().equals("1")){
                if(broadbandAccount.getEndTime().before(DateTime.now())){
                    if(broadbandAccount.getAmount().compareTo(broadbandCombo.getPrice())<0){
                        broadbandAccount.setStatus("2");
                        remoteAccountService.update(broadbandAccount,SecurityConstants.INNER);
                    }
                    else {
                        broadbandAccount.setAmount(broadbandAccount.getAmount().subtract(broadbandCombo.getPrice()));
                        broadbandAccount.setBeginTime(DateTime.now());
                        if(broadbandCombo.getUnit()==0){
                            broadbandAccount.setEndTime(DateUtils.addMonths(DateTime.now(),broadbandCombo.getValue()));
                        }
                        if(broadbandCombo.getUnit()==1){
                            broadbandAccount.setEndTime(DateUtils.addYears(DateTime.now(),broadbandCombo.getValue()));
                        }
                        remoteAccountService.update(broadbandAccount,SecurityConstants.INNER);
                    }
                }
            }

        }
    }
    public void ryNoParams1()
    {
        System.out.println("执行无参方法");
    }
}
