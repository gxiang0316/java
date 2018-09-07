package com.example.activiti.parse.num;

/**
 * 流程节点类型
 * @author guixiang
 * @date 2018-09-07
 */
public enum NodeType
{
    START("0","开始事件"),

    TASK("1","用户任务"),

    EXCLUSIVE("2","互斥网关"),

    PARALLEL("3","并行网关"),

    INCLUSIVE("4","包容性网关"),

    END("5","结束事件"),

    ERROREND("6","结束错误事件"),

    CANCELEND("7","结束取消事件"),

    TERMINATEEND("8","结束终止事件");


    private String value;

    private String desc;

    NodeType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
