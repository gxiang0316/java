package com.example.activiti.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 节点属性
 * Author:guixiang
 * Date:2018-09-06
 */
@Entity(name = "act_re_node_properties")
public class NodeProperties
{
    //节点ID
    @Id
    private String nodeId;
    //流程ID
    private String processId;
    //表单编号
    private String formKey;
    //启动器
    private String initiator;
    //优先级
    private String priority;
    //是否异步
    private boolean isAsync;
    //是否互斥
    private boolean isExclusive;
    //是否补偿
    private boolean isForcompensation;
    //分配用户
    private String assignee;
    //到期时间
    private String dueDate;
    //流序
    private String flowOrder;
    //错误引用（结束错误事件）
    private String errorRef;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public void setExclusive(boolean exclusive) {
        isExclusive = exclusive;
    }

    public boolean isForcompensation() {
        return isForcompensation;
    }

    public void setForcompensation(boolean forcompensation) {
        isForcompensation = forcompensation;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getFlowOrder() {
        return flowOrder;
    }

    public void setFlowOrder(String flowOrder) {
        this.flowOrder = flowOrder;
    }

    public String getErrorRef() {
        return errorRef;
    }

    public void setErrorRef(String errorRef) {
        this.errorRef = errorRef;
    }

    @Override
    public String toString() {
        return "NodeProperties{" +
                "nodeId='" + nodeId + '\'' +
                ", processId='" + processId + '\'' +
                ", formKey='" + formKey + '\'' +
                ", initiator='" + initiator + '\'' +
                ", priority='" + priority + '\'' +
                ", isAsync=" + isAsync +
                ", isExclusive=" + isExclusive +
                ", isForcompensation=" + isForcompensation +
                ", assignee='" + assignee + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", flowOrder='" + flowOrder + '\'' +
                ", errorRef='" + errorRef + '\'' +
                '}';
    }
}
