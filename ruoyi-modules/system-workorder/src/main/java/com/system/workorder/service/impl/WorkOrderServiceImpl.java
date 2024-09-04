package com.system.workorder.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.RemoteBusinessService;
import com.ruoyi.system.api.RemoteNodeService;
import com.ruoyi.system.api.RemoteServiceService;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BroadbandService;
import com.ruoyi.system.api.model.BusinessPeople;

import com.ruoyi.system.api.model.Node;
import com.system.workorder.config.GaoDeConfig;
import com.system.workorder.domain.dto.NodeDistance;
import com.system.workorder.domain.entity.WorkOrder;
import com.system.workorder.domain.vo.WorkOrderDetails;
import com.system.workorder.mapper.WorkOrderMapper;
import com.system.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {
    @Autowired
    WorkOrderMapper workOrderMapper;
    @Autowired
    RemoteBusinessService remoteBusinessService;
    @Autowired
    RemoteAccountService remoteAccountService;
    @Autowired
    RemoteServiceService remoteServiceService;
    @Autowired
    RemoteNodeService remoteNodeService;
    @Autowired
    GaoDeConfig gaoDeConfig;

    @Override
    public WorkOrderDetails getDetails(String id) {
        WorkOrder workOrder = workOrderMapper.selectById(id);
        BroadbandAccount broadbandAccount = remoteAccountService.getById(workOrder.getAccountId(), SecurityConstants.INNER).getData();
        BusinessPeople businessPeople = remoteBusinessService.getById(workOrder.getBusinessPeopleId(),SecurityConstants.INNER).getData();
        BroadbandService broadbandService = remoteServiceService.getById(workOrder.getServiceId(),SecurityConstants.INNER).getData();
        WorkOrderDetails workOrderDetails = new WorkOrderDetails();
        workOrderDetails.setWorkOrder(workOrder);
        workOrderDetails.setBroadbandService(broadbandService);
        workOrderDetails.setBroadbandAccount(broadbandAccount);
        workOrderDetails.setBusinessPeople(businessPeople);
        return workOrderDetails;
    }

    @Override
    public List<WorkOrder> listWorkOrder(WorkOrder workOrder) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(workOrder.getServiceId()),WorkOrder::getServiceId,workOrder.getServiceId())
                .like(StringUtils.isNotBlank(workOrder.getAccountName()),WorkOrder::getAccountName,workOrder.getAccountName())
                .like(StringUtils.isNotBlank(workOrder.getBusinessPeopleName()),WorkOrder::getBusinessPeopleName,workOrder.getBusinessPeopleName())
                .like(StringUtils.isNotBlank(workOrder.getWorkOrderName()),WorkOrder::getWorkOrderName,workOrder.getWorkOrderName())
                .like(StringUtils.isNotBlank(workOrder.getServiceName()),WorkOrder::getServiceName,workOrder.getServiceName())
                .eq(StringUtils.isNotBlank(workOrder.getBusinessPeopleId()),WorkOrder::getBusinessPeopleId,workOrder.getBusinessPeopleId())
                .eq(StringUtils.isNotBlank(workOrder.getAccountId()),WorkOrder::getAccountId,workOrder.getAccountId());
        List<WorkOrder> list = workOrderMapper.selectList(lambdaQueryWrapper);
        return list;
    }
    @Override
    public long getDistance(String startLonLat, String endLonLat){
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String queryUrl = "http://restapi.amap.com/v3/distance?key="+gaoDeConfig.getMapKey()+"&origins="+startLonLat+"&destination="+endLonLat+"&type=0";
        String queryResult = HttpUtil.get(queryUrl);
        //System.out.println(queryResult);
        JSONObject job  = JSONObject.parseObject(queryResult);
        JSONArray ja   = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Long.parseLong(jobO.get("distance").toString());
        //System.out.println("距离："+result);
        return result;
    }

    @Override
    public void generateOrder(WorkOrder workorder) {

        workorder.setWorkOrderId(IdUtil.getSnowflakeNextIdStr());
        workorder.setAccountId(SecurityUtils.getUserId().toString());
        workorder.setAccountName(SecurityUtils.getUsername());
        BroadbandService broadbandService = remoteServiceService.getById(workorder.getWorkOrderId(),SecurityConstants.INNER).getData();
        workorder.setServiceName(broadbandService.getBroadbandServiceName());
        workorder.setWorkOrderName(broadbandService.getBroadbandServiceName()+ LocalDateTimeUtil.now());
        BroadbandAccount broadbandAccount = remoteAccountService.getById(SecurityUtils.getUserId().toString(),SecurityConstants.INNER).getData();
        Node node = new Node();
        List<Node> list = remoteNodeService.remoteList(node,SecurityConstants.INNER).getData();
        List<NodeDistance> distances = new ArrayList<>();
        String endLonLat = broadbandAccount.getLongitude()+","+broadbandAccount.getLatitude();
        for(int i=0;i<list.size();i++){
            node = list.get(i);
            NodeDistance nodeDistance = new NodeDistance();
            nodeDistance.setId(node.getNodeId());
            String startLonLat = list.get(i).getLongitude()+","+list.get(i).getLatitude().toString();
            Long distance = getDistance(startLonLat,endLonLat);
            nodeDistance.setDistance(distance);
            distances.add(nodeDistance);
        }
        Collections.sort(distances, new Comparator<NodeDistance>() {
            @Override
            public int compare(NodeDistance n1,NodeDistance n2) {
                return (int)(n1.getDistance()-n2.getDistance());
            }
        });
        BusinessPeople businessPeople = new BusinessPeople();
        businessPeople.setNodeId(distances.get(0).getId());
        List<BusinessPeople> businessPeoples = remoteBusinessService.remoteList(businessPeople,SecurityConstants.INNER).getData();
        Collections.sort(businessPeoples, new Comparator<BusinessPeople>() {
            @Override
            public int compare(BusinessPeople o1, BusinessPeople o2) {
                return o1.getOrderCount()-o2.getOrderCount();
            }
        });
        businessPeople = businessPeoples.get(0);
        businessPeople.setOrderCount(businessPeople.getOrderCount()+1);
        remoteBusinessService.update(businessPeople,SecurityConstants.INNER);
        workorder.setBusinessPeopleId(businessPeoples.get(0).getBusinessPeopleId());
        workorder.setBusinessPeopleName(businessPeoples.get(0).getName());
        workOrderMapper.insert(workorder);



    }
}
