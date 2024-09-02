package com.system.service.service;

import com.system.service.domain.ServiceTreeSelect;
import com.system.service.domain.entity.ServiceType;
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
public interface IServiceTypeService extends IService<ServiceType> {

    List <ServiceTreeSelect> buildTypeTreeSelect(List<ServiceType> serviceTypes, ServiceType serviceType);


    List<ServiceType> buildTypeTree(List<ServiceType> ServiceTypes, ServiceType ServiceType);

    List<ServiceType> buildTypeTree(List<ServiceType> ServiceTypes);

    void delete(String id);
}
