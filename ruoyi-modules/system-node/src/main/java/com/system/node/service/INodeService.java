package com.system.node.service;

import com.system.node.domain.entity.Node;
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
public interface INodeService extends IService<Node> {

    List<Node> listNode(Node node);
}
