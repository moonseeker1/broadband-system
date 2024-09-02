package com.system.node.controller;


import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.uuid.IdUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteBusinessService;
import com.ruoyi.system.api.model.BusinessPeople;
import com.system.node.domain.entity.Node;
import com.system.node.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wugou
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/node")
public class NodeController extends BaseController {
    @Autowired
    INodeService nodeService;
    @Autowired
    RemoteBusinessService businessService;
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Node node){
        node.setNodeId(IdUtil.getSnowflake().nextId());
        nodeService.save(node);
        return success();
    }
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id){
        BusinessPeople businessPeople = new BusinessPeople();
        businessPeople.setNodeId(id);
        if(!businessService.remoteList(businessPeople, SecurityConstants.INNER).getData().isEmpty()){
            throw new ServiceException("节点下存在业务员");
        }
        nodeService.removeById(id);
        return success();
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody Node node){
        nodeService.updateById(node);
        return success();
    }
    @GetMapping("/{id}")
    public R<Node> getById(@PathVariable Long id){
        Node node = nodeService.getById(id);
        return R.ok(node);
    }
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody Node node){
        startPage();
        List<Node> list = nodeService.listNode(node);
        return getDataTable(list);
    }


}
