package com.example.activiti.service;

import com.example.activiti.entity.CustomProcess;
import com.example.activiti.repository.CustomProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程服务类
 * Author:guixiang
 * Date:2018-09-05
 */
@Service
public class CustomProcessService
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomProcessService.class);

    @Autowired
    private CustomProcessRepository repository;

    public CustomProcess saveProcess(CustomProcess process)
    {
        LOG.debug("保存流程信息，参数：{}" + process);
        CustomProcess result = repository.save(process);
        LOG.debug("保存流程信息，结果：{}" + result);
        return result;
    }


}
