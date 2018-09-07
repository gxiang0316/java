package com.example.activiti.service;

import com.example.activiti.entity.Sequence;
import com.example.activiti.repository.SequenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 顺序流服务类
 * Author:guixiang
 * Date:2018-09-06
 */
@Service
public class SequenceService
{
    private static final Logger LOG = LoggerFactory.getLogger(SequenceService.class);

    @Autowired
    private SequenceRepository repository;

    public Sequence saveSequence(Sequence sequence)
    {
        LOG.debug("保存顺序流信息，参数：{}" + sequence);
        Sequence result = repository.save(sequence);
        LOG.debug("保存顺序流信息，结果：{}" + result);
        return result;
    }
}
