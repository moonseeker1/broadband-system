package com.system.businessPeople.service;

import com.ruoyi.system.api.model.LoginUser;
import com.system.businessPeople.domain.dto.RegisterBody;
import com.system.businessPeople.domain.entity.BusinessPeople;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
public interface IBusinessPeopleService extends IService<BusinessPeople> {

    void add(RegisterBody registerBody);

    LoginUser login(String phoneNumber, String password);

    List<BusinessPeople> listBusinessPeople(BusinessPeople businessPeople);
}
