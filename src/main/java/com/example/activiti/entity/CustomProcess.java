package com.example.activiti.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *流程信息
 * Author:guixiang
 * Date:2018-09-05
 */
@Entity
@Table(name = "act_re_process")
public class CustomProcess
{
    //流程ID
    @Id
    private String id;
    //流程模型ID
    private String modelId;
    //流程名称
    private String name;
    //流程描述信息
    private String description;
    //流程版本
    private Integer version;
    //流程状态
    private String status;
    //流程分类
    private String category;
    //流程创建者
    private String creator;
    //流程创建时间
    private Date createTime;
    //流程内容
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id='" + id + '\'' +
                ", modelId='" + modelId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}
