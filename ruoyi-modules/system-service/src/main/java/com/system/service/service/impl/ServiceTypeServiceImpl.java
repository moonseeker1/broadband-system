package com.system.service.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.system.service.domain.ServiceTreeSelect;
import com.system.service.domain.entity.BroadbandService;
import com.system.service.domain.entity.ServiceType;
import com.system.service.mapper.BroadbandServiceMapper;
import com.system.service.mapper.ServiceTypeMapper;
import com.system.service.service.IServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@Service
public class ServiceTypeServiceImpl extends ServiceImpl<ServiceTypeMapper, ServiceType> implements IServiceTypeService {
    @Autowired
    ServiceTypeMapper serviceTypeMapper;
    @Autowired
    BroadbandServiceMapper broadbandServiceMapper;
    @Override
    public List<ServiceTreeSelect> buildTypeTreeSelect(List<ServiceType> ServiceTypes, ServiceType ServiceType) {
        List<ServiceType> typeTrees = buildTypeTree(ServiceTypes, ServiceType);
        return typeTrees.stream().map(ServiceTreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<ServiceType> buildTypeTree(List<ServiceType> ServiceTypes, ServiceType serviceType) {

        List<ServiceType> returnList = new ArrayList<ServiceType>();
        List<String> tempList = ServiceTypes.stream().map(ServiceType::getTypeId).collect(Collectors.toList());
        List<ServiceType> selectList = StringUtils.isEmpty(serviceType.getParentId())?ServiceTypes.stream().filter(modelType -> modelType.getParentId().equals("0")).collect(Collectors.toList()):ServiceTypes.stream().filter(modelType -> modelType.getParentId().equals(serviceType.getParentId())).collect(Collectors.toList());
        for (Iterator<ServiceType> iterator = selectList.iterator(); iterator.hasNext();)
        {
            ServiceType modelType = (ServiceType) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
//            if (!tempList.contains(modelType.getParentId()))
//            {
            recursionFn(ServiceTypes, modelType);
            returnList.add(modelType);
//            }

        }
        if (returnList.isEmpty())
        {
            returnList = ServiceTypes;
        }
        return returnList;

    }

    @Override
    public List<ServiceType> buildTypeTree(List<ServiceType> ServiceTypes) {

        List<ServiceType> returnList = new ArrayList<ServiceType>();
        List<String> tempList = ServiceTypes.stream().map(ServiceType::getTypeId).collect(Collectors.toList());
        for (Iterator<ServiceType> iterator = ServiceTypes.iterator(); iterator.hasNext();)
        {
            ServiceType ServiceType = (ServiceType) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(ServiceType.getParentId()))
            {
                recursionFn(ServiceTypes, ServiceType);
                returnList.add(ServiceType);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = ServiceTypes;
        }
        return returnList;

    }

    @Override
    public void delete(String id) {
        LambdaQueryWrapper<ServiceType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(id),ServiceType::getParentId,id);
        List<ServiceType> list = serviceTypeMapper.selectList(lambdaQueryWrapper);
        if(!list.isEmpty()){
            throw new ServiceException("存在子分类，无法删除");
        }else{
            LambdaQueryWrapper<BroadbandService> lambdaQueryWrapper1= new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(StringUtils.isNotBlank(id),BroadbandService::getTypeId,id);
            List<BroadbandService> list1 = broadbandServiceMapper.selectList(lambdaQueryWrapper1);
            if(!list1.isEmpty()){
                throw new ServiceException("存在分类服务，无法删除");
            }
        }
    }


    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t 子节点
     */

    private void recursionFn(List<ServiceType> list, ServiceType t)
    {
        // 得到子节点列表
        List<ServiceType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ServiceType tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ServiceType> getChildList(ServiceType t)
    {
        LambdaQueryWrapper<ServiceType> modelTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        modelTypeLambdaQueryWrapper.eq(ServiceType::getParentId, t.getTypeId());
        List<ServiceType> tlist = serviceTypeMapper.selectList(modelTypeLambdaQueryWrapper);

        return tlist;
    }

    private boolean hasChild(ServiceType t)
    {
        return !getChildList(t).isEmpty();
    }


    /**
     * 得到子节点列表
     */
    private List<ServiceType> getChildList(List<ServiceType> list, ServiceType t)
    {
        List<ServiceType> tlist = new ArrayList<ServiceType>();
        Iterator<ServiceType> it = list.iterator();
        while (it.hasNext())
        {
            ServiceType n = (ServiceType) it.next();
            if (n.getParentId().equals(t.getTypeId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }
    private boolean hasChild(List<ServiceType> list, ServiceType t)
    {
        return !getChildList(list, t).isEmpty();
    }
}
