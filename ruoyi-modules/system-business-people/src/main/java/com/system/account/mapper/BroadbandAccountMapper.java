package com.system.account.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.account.domain.entity.BroadbandAccount;
import org.apache.ibatis.annotations.Mapper;


/**
 * accountMapper接口
 * 
 * @author wugou
 * @date 2024-08-30
 */
@Mapper
public interface BroadbandAccountMapper extends BaseMapper<BroadbandAccount>
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
     * 删除account
     *
     * @param accountId account主键
     * @return 结果
     */
    public int deleteBroadbandAccountByAccountId(Long accountId);

    /**
     * 批量删除account
     *
     * @param accountIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBroadbandAccountByAccountIds(Long[] accountIds);
}
