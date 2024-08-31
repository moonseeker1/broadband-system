package com.system.account.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.api.model.LoginUser;
import com.system.account.domain.dto.RegisterBody;
import com.system.account.domain.entity.BroadbandAccount;

/**
 * accountService接口
 * 
 * @author wugou
 * @date 2024-08-30
 */
public interface IBroadbandAccountService extends IService<BroadbandAccount>
{
    /**
     * 查询account
     * 
     * @param accountId account主键
     * @return account
     */
    public BroadbandAccount selectBroadbandAccountByAccountId(Long accountId);

    /**
     * 查询account列表
     * 
     * @param broadbandAccount account
     * @return account集合
     */
    public List<BroadbandAccount> selectBroadbandAccountList(BroadbandAccount broadbandAccount);

    /**
     * 新增account
     * 
     * @param broadbandAccount account
     * @return 结果
     */
    public int insertBroadbandAccount(BroadbandAccount broadbandAccount);

    /**
     * 修改account
     * 
     * @param broadbandAccount account
     * @return 结果
     */
    public int updateBroadbandAccount(BroadbandAccount broadbandAccount);

    /**
     * 批量删除account
     * 
     * @param accountIds 需要删除的account主键集合
     * @return 结果
     */
    public int deleteBroadbandAccountByAccountIds(Long[] accountIds);

    /**
     * 删除account信息
     * 
     * @param accountId account主键
     * @return 结果
     */
    public int deleteBroadbandAccountByAccountId(Long accountId);

    void register(RegisterBody registerBody);

    void sendCode(String phoneNumber);

    LoginUser login(String phoneNumber, String password);
}
