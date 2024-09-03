package com.system.account.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteComboService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.BroadbandCombo;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;
import com.system.account.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.system.account.mapper.BroadbandAccountMapper;
import com.system.account.service.IBroadbandAccountService;

/**
 * accountService业务层处理
 * 
 * @author wugou
 * @date 2024-08-30
 */
@Service
public class BroadbandAccountServiceImpl extends ServiceImpl<BroadbandAccountMapper,BroadbandAccount> implements IBroadbandAccountService
{
    @Autowired
    private BroadbandAccountMapper broadbandAccountMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private RemoteComboService remoteComboService;

    @Override
    public void register(RegisterBody registerBody) {
        String phoneNumber = registerBody.getPhoneNumber();
        String password = registerBody.getPassword();
        password = SecurityUtils.encryptPassword(password);
        String code = registerBody.getCode();
        String cacheCode = redisService.getCacheObject(phoneNumber);
        LambdaQueryWrapper<BroadbandAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(phoneNumber),BroadbandAccount::getPhoneNumber,phoneNumber);
        List<BroadbandAccount> list = broadbandAccountMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            throw new ServiceException("该用户已注册");
        }
        if(StringUtils.isNotBlank(cacheCode)){
            if(cacheCode.equals(code)){
                BroadbandAccount broadbandAccount = new BroadbandAccount();
                broadbandAccount.setAccountId(IdUtil.getSnowflake().nextIdStr());
                broadbandAccount.setPhoneNumber(phoneNumber);
                broadbandAccount.setPassword(password);
                broadbandAccount.setAmount(BigDecimal.ZERO);
                broadbandAccountMapper.insert(broadbandAccount);
            }else{
                throw new ServiceException("验证码错误");
            }
        }else{
            throw new ServiceException("未获取验证码");
        }
    }

    @Override
    public void sendCode(String phoneNumber) {
        LambdaQueryWrapper<BroadbandAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(phoneNumber),BroadbandAccount::getPhoneNumber,phoneNumber);
        List<BroadbandAccount> list = broadbandAccountMapper.selectList(queryWrapper);
        if(list.isEmpty()){
            Random random = new Random();
            String code = Integer.toString(100000 + random.nextInt(900000));
            Map<String,Object> param = new HashMap<>();
            param.put("code", code);
            smsUtil.send(param,phoneNumber);
            long timeout = 60;
            redisService.setCacheObject(phoneNumber,code,timeout,TimeUnit.SECONDS);
        }else{
            throw new ServiceException("该用户已注册");
        }
    }

    @Override
    public LoginUser login(String phoneNumber, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(phoneNumber, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        // 查询用户信息
        LambdaQueryWrapper<BroadbandAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(phoneNumber),BroadbandAccount::getPhoneNumber,phoneNumber);
        List<BroadbandAccount> list = broadbandAccountMapper.selectList(lambdaQueryWrapper);
        if(list.isEmpty()){
            throw new ServiceException("账户未注册");
        }else{
            if(SecurityUtils.matchesPassword(password,list.get(0).getPassword())){
                LoginUser useInfo = new LoginUser();
                useInfo.setUserid(Long.valueOf(list.get(0).getAccountId()));
                useInfo.setUsername(list.get(0).getPhoneNumber());
                return useInfo;
            }else{
                throw new ServiceException("密码错误");
            }
        }

    }

    @Override
    public List<BroadbandAccount> selectBroadbandAccount(BroadbandAccount broadbandAccount) {
        LambdaQueryWrapper<BroadbandAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(broadbandAccount.getPhoneNumber()),BroadbandAccount::getPhoneNumber,broadbandAccount.getPhoneNumber())
                .eq(StringUtils.isNotBlank(broadbandAccount.getName()),BroadbandAccount::getName,broadbandAccount.getName())
                .eq(broadbandAccount.getComboId()!=null,BroadbandAccount::getComboId,broadbandAccount.getComboId());
        List<BroadbandAccount> list = broadbandAccountMapper.selectList(lambdaQueryWrapper);
        return list;
    }

    @Override
    public void addCombo(String id) {
        Long userId = SecurityUtils.getUserId();
        BroadbandAccount broadbandAccount = broadbandAccountMapper.selectById(userId);
        BroadbandCombo broadbandCombo = remoteComboService.get(id,SecurityConstants.INNER).getData();
        if(broadbandAccount.getComboId()!=null){
            throw new ServiceException("您已存在套餐无法添加");
        }
        else{
            if(broadbandAccount.getAmount().compareTo(broadbandCombo.getPrice())<0){
                throw new ServiceException("你的余额不足");
            }
            broadbandAccount.setComboId(id);
            broadbandAccount.setStatus("1");
            broadbandAccount.setBeginTime(DateTime.now());

            if(broadbandCombo.getUnit()==0){
                broadbandAccount.setEndTime(DateUtils.addMonths(DateTime.now(),broadbandCombo.getValue()));
            }
            if(broadbandCombo.getUnit()==1){
                broadbandAccount.setEndTime(DateUtils.addYears(DateTime.now(),broadbandCombo.getValue()));
            }
            broadbandAccountMapper.updateById(broadbandAccount);
        }
    }
}
