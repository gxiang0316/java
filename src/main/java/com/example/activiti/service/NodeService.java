package com.example.activiti.service;

import com.example.activiti.entity.Node;
import com.example.activiti.repository.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 节点服务类
 * Author:guixiang
 * Date:2018-09-06
 */
@Service
public class NodeService
{
    private static final Logger LOG = LoggerFactory.getLogger(NodeService.class);

    @Autowired
    private NodeRepository repository;

    public Node saveNode(Node node)
    {
        LOG.debug("保存节点信息，参数={}",node);
        Node result = repository.save(node);
        LOG.debug("保存节点信息，结果={}",result);
        return result;
    }
}
