package com.system.combo.service;

import com.system.combo.domain.entity.BroadbandCombo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
public interface IBroadbandComboService extends IService<BroadbandCombo> {


    List<BroadbandCombo> listCombo(BroadbandCombo broadbandCombo);
}
