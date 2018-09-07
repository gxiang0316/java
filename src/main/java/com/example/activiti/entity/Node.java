package com.example.activiti.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 节点信息
 * Author:guixiang
 * Date:2018-09-06
 */
@Entity(name = "act_re_node")
public class Node
{
    //节点ID
    @Id
    private String id;
    //流程ID
    private String processId;
    //节点名称
    private String name;
    //节点描述
    private String description;
    //节点状态
    private String status;
    //节点类型
    private String nodeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", processId='" + processId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", nodeType='" + nodeType + '\'' +
                '}';
    }
}
