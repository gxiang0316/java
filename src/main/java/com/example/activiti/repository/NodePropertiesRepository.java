package com.example.activiti.repository;

import com.example.activiti.entity.NodeProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author:guixiang
 * Date:2018-09-06
 */
public interface NodePropertiesRepository extends JpaRepository<NodeProperties,String> , JpaSpecificationExecutor<NodeProperties>
{
}
