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

import static com.ruoyi.common.core.web.domain.AjaxResult.success;


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
public class BusinessPeopleController {
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
    public AjaxResult getInfo(){
        BusinessPeopleInfo businessPeopleInfo = new BusinessPeopleInfo();
        businessPeopleInfo.setUserId(SecurityUtils.getUserId());
        businessPeopleInfo.setUserName(SecurityUtils.getUsername());
        return success(businessPeopleInfo);
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
}
