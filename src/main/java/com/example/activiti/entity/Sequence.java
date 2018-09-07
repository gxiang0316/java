package com.example.activiti.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 顺序流信息
 * Author:guixiang
 * Date:2018-09-06
 */
@Entity(name = "act_re_sequence")
public class Sequence
{
    //顺序流ID
    @Id
    private String id;
    //流程ID
    private String processId;
    //顺序流名称
    private String name;
    //顺序流描述信息
    private String description;
    //源节点
    private String sourceRef;
    //目标节点
    private String targetRef;
    //流条件
    private String conditionExpress;
    //是否默认流
    private boolean isDefault;

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

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public String getConditionExpress() {
        return conditionExpress;
    }

    public void setConditionExpress(String conditionExpress) {
        this.conditionExpress = conditionExpress;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "id='" + id + '\'' +
                ", processId='" + processId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sourceRef='" + sourceRef + '\'' +
                ", targetRef='" + targetRef + '\'' +
                ", condition='" + conditionExpress + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
