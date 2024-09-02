package com.system.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.system.service.domain.entity.BroadbandService;
import com.system.service.mapper.BroadbandServiceMapper;
import com.system.service.service.IBroadbandServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BroadbandServiceServiceImpl extends ServiceImpl<BroadbandServiceMapper, BroadbandService> implements IBroadbandServiceService {
    @Autowired
    BroadbandServiceMapper broadbandServiceMapper;
    @Override
    public List<BroadbandService> listBroadbandService(BroadbandService broadbandService) {
        LambdaQueryWrapper<BroadbandService> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(broadbandService.getBroadbandServiceName()),BroadbandService::getBroadbandServiceName,broadbandService.getBroadbandServiceName())
                .eq(broadbandService.getTypeId()!=null,BroadbandService::getTypeId,broadbandService.getTypeId());
        List<BroadbandService> list = broadbandServiceMapper.selectList(lambdaQueryWrapper);
        return list;
    }
}
