package com.system.node.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.system.node.domain.entity.Node;
import com.system.node.mapper.NodeMapper;
import com.system.node.service.INodeService;
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
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {
    @Autowired
    NodeMapper nodeMapper;
    @Override
    public List<Node> listNode(Node node) {
        LambdaQueryWrapper<Node> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(node.getNodeName()),Node::getNodeName,node.getNodeName());
        List<Node> list = nodeMapper.selectList(lambdaQueryWrapper);
        return list;
    }
}
