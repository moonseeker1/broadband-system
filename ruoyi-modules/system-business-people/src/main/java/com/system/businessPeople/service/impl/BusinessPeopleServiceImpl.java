package com.system.businessPeople.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import com.system.businessPeople.domain.dto.RegisterBody;
import com.system.businessPeople.domain.entity.BusinessPeople;
import com.system.businessPeople.mapper.BusinessPeopleMapper;
import com.system.businessPeople.service.IBusinessPeopleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@Service
public class BusinessPeopleServiceImpl extends ServiceImpl<BusinessPeopleMapper, BusinessPeople> implements IBusinessPeopleService {
    @Autowired
    BusinessPeopleMapper businessPeopleMapper;
    @Override
    public void add(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        LambdaQueryWrapper<BusinessPeople> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username),BusinessPeople::getUserName,username);
        List<BusinessPeople> list = businessPeopleMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            throw new ServiceException("该用户已注册");
        }
        BusinessPeople businessPeople = new BusinessPeople();
        businessPeople.setBusinessPeopleId(IdUtil.getSnowflake().nextIdStr());
        businessPeople.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
        businessPeople.setUserName(registerBody.getUsername());
        businessPeopleMapper.insert(businessPeople);
    }

    @Override
    public LoginUser login(String phoneNumber, String password) {
        if (StringUtils.isAnyBlank(phoneNumber, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        // 查询用户信息
        LambdaQueryWrapper<BusinessPeople> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(phoneNumber),BusinessPeople::getUserName,phoneNumber);
        List<BusinessPeople> list = businessPeopleMapper.selectList(lambdaQueryWrapper);
        if(list.isEmpty()){
            throw new ServiceException("账户未注册");
        }else{
            if(SecurityUtils.matchesPassword(password,list.get(0).getPassword())){
                LoginUser useInfo = new LoginUser();
                useInfo.setUserid(Long.valueOf(list.get(0).getBusinessPeopleId()));
                useInfo.setUsername(list.get(0).getUserName());
                return useInfo;
            }else{
                throw new ServiceException("密码错误");
            }
        }
    }

    @Override
    public List<BusinessPeople> listBusinessPeople(BusinessPeople businessPeople) {
        LambdaQueryWrapper<BusinessPeople> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(businessPeople.getUserName()),BusinessPeople::getUserName,businessPeople.getUserName())
                .eq(StringUtils.isNotBlank(businessPeople.getNodeId()),BusinessPeople::getNodeId,businessPeople.getNodeId())
                .like(StringUtils.isNotBlank(businessPeople.getNodeName()),BusinessPeople::getNodeName,businessPeople.getNodeName())
                .like(StringUtils.isNotBlank(businessPeople.getName()),BusinessPeople::getName,businessPeople.getName());
        List<BusinessPeople> list = businessPeopleMapper.selectList(lambdaQueryWrapper);
        return list;
    }
}
