package com.example.activiti.service;

import com.example.activiti.entity.NodeProperties;
import com.example.activiti.repository.NodePropertiesRepository;
import com.example.activiti.repository.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 节点属性服务类
 * Author:guixiang
 * Date:2018-09-06
 */
@Service
public class NodePropertiesService
{
    private static final Logger LOG = LoggerFactory.getLogger(NodePropertiesService.class);

    @Autowired
    NodePropertiesRepository repository;

    public NodeProperties saveNodeProperties(NodeProperties nodeProperties)
    {
        LOG.debug("保存节点属性信息，参数={}",nodeProperties);
        NodeProperties result = repository.save(nodeProperties);
        LOG.debug("保存节点属性信息，结果={}",result);
        return result;
    }
}
