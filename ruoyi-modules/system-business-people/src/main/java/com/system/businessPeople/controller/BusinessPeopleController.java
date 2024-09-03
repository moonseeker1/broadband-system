package com.system.businessPeople.controller;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import com.system.businessPeople.domain.dto.LoginBody;
import com.system.businessPeople.domain.dto.RegisterBody;
import com.system.businessPeople.domain.entity.BusinessPeople;
import com.system.businessPeople.domain.vo.BusinessPeopleInfo;
import com.system.businessPeople.service.IBusinessPeopleService;
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
    public AjaxResult delete(@PathVariable Long id){
        businessPeopleService.removeById(id);
        //TODO:后续校验业务员是否存在工单
        return success();
    }
    @GetMapping("/{id}")
    public R<BusinessPeople> getById(@PathVariable String id){
        BusinessPeople businessPeople = businessPeopleService.getById(id);
        return R.ok(businessPeople);
    }
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody BusinessPeople businessPeople){
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
}
