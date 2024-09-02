package com.system.combo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.system.combo.domain.entity.BroadbandCombo;
import com.system.combo.mapper.BroadbandComboMapper;
import com.system.combo.service.IBroadbandComboService;
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
public class BroadbandComboServiceImpl extends ServiceImpl<BroadbandComboMapper, BroadbandCombo> implements IBroadbandComboService {
    @Autowired
    BroadbandComboMapper broadbandComboMapper;
    @Override
    public List<BroadbandCombo> listCombo(BroadbandCombo broadbandCombo) {
        LambdaQueryWrapper<BroadbandCombo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(broadbandCombo.getUnit()!=null,BroadbandCombo::getUnit,broadbandCombo.getUnit())
                .eq(StringUtils.isNotBlank(broadbandCombo.getComboName()), BroadbandCombo::getComboName, broadbandCombo.getComboName())
                .eq(broadbandCombo.getBandwidth()!=null,BroadbandCombo::getBandwidth,broadbandCombo.getBandwidth());
        List<BroadbandCombo> list = broadbandComboMapper.selectList(lambdaQueryWrapper);
        return list;
    }
}
