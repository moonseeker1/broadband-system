package com.system.businessPeople.controller;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteAccountService;
import com.ruoyi.system.api.RemoteBusinessService;
import com.ruoyi.system.api.RemoteServiceService;
import com.ruoyi.system.api.RemoteWorkOrderService;
import com.ruoyi.system.api.model.BroadbandAccount;
import com.ruoyi.system.api.model.BroadbandService;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.api.model.WorkOrder;
import com.system.businessPeople.domain.dto.LoginBody;
import com.system.businessPeople.domain.dto.RegisterBody;
import com.system.businessPeople.domain.dto.WorkOrderDetail;
import com.system.businessPeople.domain.entity.BusinessPeople;
import com.system.businessPeople.domain.vo.BusinessPeopleInfo;
import com.system.businessPeople.service.IBusinessPeopleService;
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
    public R<List<BusinessPeople>> remoteList(BusinessPeople businessPeople){
        List<BusinessPeople> list = businessPeopleService.listBusinessPeople(businessPeople);
        return R.ok(list);
    }
    @GetMapping("/listOrder")
    public TableDataInfo listOrder(WorkOrder workOrder){
        startPage();
        workOrder.setBusinessPeopleId(SecurityUtils.getUserId().toString());
        List<WorkOrder> list = remoteWorkOrderService.list(workOrder, SecurityConstants.INNER).getData();
        return getDataTable(list);
    }
    @GetMapping("/finish/{id}")
    public AjaxResult finish(@PathVariable String id){
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderId(id);
        workOrder.setState(1);
        remoteWorkOrderService.update(workOrder,SecurityConstants.INNER);
        return success();
    }

}
