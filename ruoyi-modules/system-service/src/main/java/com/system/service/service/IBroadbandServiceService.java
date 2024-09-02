package com.system.service.service;

import com.system.service.domain.entity.BroadbandService;
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
public interface IBroadbandServiceService extends IService<BroadbandService> {


    List<BroadbandService> listBroadbandService(BroadbandService broadbandService);
}
