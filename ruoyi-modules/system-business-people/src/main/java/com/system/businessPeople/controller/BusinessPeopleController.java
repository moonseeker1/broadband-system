package com.system.businessPeople.controller;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.*;
import com.ruoyi.system.api.model.*;
import com.system.businessPeople.domain.dto.LoginBody;
import com.system.businessPeople.domain.dto.RegisterBody;
import com.system.businessPeople.domain.dto.WorkOrderDetail;
import com.system.businessPeople.domain.entity.BusinessPeople;
import com.system.businessPeople.domain.vo.BusinessPeopleInfo;
import com.system.businessPeople.service.IBusinessPeopleService;
import org.apache.poi.util.StringUtil;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.ruoyi.common.core.web.controller.BaseController;
import static com.ruoyi.common.core.web.domain.AjaxResult.success;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/business-people")
public class BusinessPeopleController extends BaseController{
    @Autowired
    private IBusinessPeopleService businessPeopleService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RemoteWorkOrderService remoteWorkOrderService;
    @Autowired
    private RemoteServiceService remoteServiceService;
    @Autowired
    private RemoteAccountService remoteAccountService;
    @Autowired
    private RemoteNodeService remoteNodeService;

    @PostMapping("/add")
    public AjaxResult add(@RequestBody RegisterBody registerBody){
        businessPeopleService.add(registerBody);
        return success();
    }
    @PostMapping("/login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = businessPeopleService.login(form.getUsername(), form.getPassword());
        return R.ok(tokenService.createAccountToken(userInfo));
    }
    @GetMapping("/getInfo")
    public R<?> getInfo(){
        BusinessPeopleInfo businessPeopleInfo = new BusinessPeopleInfo();
        businessPeopleInfo.setUserId(SecurityUtils.getUserId().toString());
        businessPeopleInfo.setUserName(SecurityUtils.getUsername());
        return R.ok(businessPeopleInfo);
    }
    @PutMapping()
    public AjaxResult update(@RequestBody BusinessPeople businessPeople){
        businessPeopleService.updateById(businessPeople);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable String id){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setBusinessPeopleId(id);
        if(!remoteWorkOrderService.list(workOrder,SecurityConstants.INNER).getData().isEmpty()){
            throw new ServiceException("业务员仍有工单存在");
        }
        businessPeopleService.removeById(id);
        return success();
    }
    @GetMapping("/{id}")
    public R<BusinessPeople> getById(@PathVariable String id){
        BusinessPeople businessPeople = businessPeopleService.getById(id);
        return R.ok(businessPeople);
    }
    @GetMapping("/list")
    public TableDataInfo list(BusinessPeople businessPeople){
        startPage();
        List<BusinessPeople> list = businessPeopleService.listBusinessPeople(businessPeople);
        return getDataTable(list);
    }
    /**
     *  远程调用查询
     */
    @PostMapping("/remote/list")
    public R<List<BusinessPeople>> remoteList(@RequestBody BusinessPeople businessPeople){
        List<BusinessPeople> list = businessPeopleService.listBusinessPeople(businessPeople);
        return R.ok(list);
    }
    @PostMapping ("/node")
    public AjaxResult addNode(@RequestBody BusinessPeople businessPeople){

            BusinessPeople businessPeople1 = businessPeopleService.getById(businessPeople);
            if(StringUtils.isNotBlank(businessPeople1.getNodeId())){
                Node lastNode = remoteNodeService.getById(businessPeople1.getNodeId(),SecurityConstants.INNER).getData();
                lastNode.setBusinessPeopleCount(lastNode.getBusinessPeopleCount()-1);
                remoteNodeService.update(lastNode,SecurityConstants.INNER);
            }
            if(StringUtils.isNotBlank(businessPeople.getNodeId())){
                Node nowNode = remoteNodeService.getById(businessPeople.getNodeId(),SecurityConstants.INNER).getData();
                nowNode.setBusinessPeopleCount(nowNode.getBusinessPeopleCount()+1);
                remoteNodeService.update(nowNode,SecurityConstants.INNER);
            }
            businessPeopleService.updateById(businessPeople);
            return success();
    }
    @GetMapping("/finish/{id}")
    public AjaxResult finish(@PathVariable String id){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderId(id);
        workOrder.setState(1);
        BusinessPeople businessPeople = businessPeopleService.getById(SecurityUtils.getUserId());
        businessPeople.setOrderCount(businessPeople.getOrderCount()-1);
        businessPeopleService.updateById(businessPeople);
        remoteWorkOrderService.update(workOrder,SecurityConstants.INNER);
        return success();
    }

}
