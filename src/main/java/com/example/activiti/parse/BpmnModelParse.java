package com.example.activiti.parse;

import com.example.activiti.entity.CustomProcess;
import com.example.activiti.entity.Node;
import com.example.activiti.entity.NodeProperties;
import com.example.activiti.entity.Sequence;
import com.example.activiti.parse.num.NodeType;
import com.example.activiti.service.CustomProcessService;
import com.example.activiti.service.NodePropertiesService;
import com.example.activiti.service.NodeService;
import com.example.activiti.service.SequenceService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 流程解析
 * @author guixiang
 * @date 2018-09-07
 */
@Service
public class BpmnModelParse
{
    @Autowired
    private CustomProcessService processService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private NodePropertiesService nodePropertiesService;

    /**
     * 解析为流程信息对象
     * @param model
     * @param bpmnModel
     */
    public void converterToProcess(Model model, BpmnModel bpmnModel)
    {
        for(Process process : bpmnModel.getProcesses())
        {

            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            //得到流程图的xml
            String xml = new String(bpmnBytes);
            saveProcess(model,process,xml);
        }
    }

    protected void saveProcess(Model model,Process mProcess,String xml)
    {
        CustomProcess process = new CustomProcess();
        process.setModelId(model.getId());
        process.setContent(xml);
        process.setName(mProcess.getName());
        process.setId(mProcess.getId());
        process.setDescription(mProcess.getDocumentation());
        process.setVersion(model.getVersion());
        process.setCreateTime(new Date());
        processService.saveProcess(process);
    }

    /**
     * 解析为节点信息对象
     * 解析为节点属性信息对象
     * 解析为顺序流信息对象
     * @param bpmnModel
     */
    public void converterToObject(BpmnModel bpmnModel)
    {
        for (Process process : bpmnModel.getProcesses())
        {
            if (process.getFlowElements().isEmpty() && process.getLanes().isEmpty()) {
                // empty process, ignore it
                continue;
            }
            for (FlowElement flowElement : process.getFlowElements()) {
                converter(flowElement,process.getId());
            }
        }
    }

    protected void converter(FlowElement flowElement,String processId)
    {
        if(flowElement instanceof StartEvent)
        {
            //开始节点
            //保存开始事件节点
            saveNode(flowElement, NodeType.START.getValue(),processId);
            StartEvent startEvent = (StartEvent)flowElement;
            //保存开始节点属性新
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setNodeId(startEvent.getId());
            nodeProperties.setFormKey(startEvent.getFormKey());
            nodeProperties.setInitiator(startEvent.getInitiator());
            nodeProperties.setProcessId(processId);
            nodePropertiesService.saveNodeProperties(nodeProperties);
        }
        else if(flowElement instanceof UserTask)
        {
            //任务节点
            //保存任务节点
            saveNode(flowElement,NodeType.TASK.getValue(),processId);
            UserTask userTask = (UserTask)flowElement;
            //保存任务节点属性信息
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setNodeId(userTask.getId());
            nodeProperties.setFormKey(userTask.getFormKey());
            nodeProperties.setAssignee(userTask.getAssignee());
            nodeProperties.setAsync(userTask.isAsynchronous());
            nodeProperties.setDueDate(userTask.getDueDate());
            nodeProperties.setExclusive(userTask.isNotExclusive());
            nodeProperties.setPriority(userTask.getPriority());
            nodeProperties.setForcompensation(userTask.isForCompensation());
            nodeProperties.setProcessId(processId);
            nodePropertiesService.saveNodeProperties(nodeProperties);
        }
        else if(flowElement instanceof Gateway)
        {
            //网关
            String type = "";
            if(flowElement instanceof ExclusiveGateway)
            {
                //互斥网关
                type = NodeType.EXCLUSIVE.getValue();
            }
            else if(flowElement instanceof ParallelGateway)
            {
                //并行网关
                type = NodeType.PARALLEL.getValue();
            }
            else if(flowElement instanceof InclusiveGateway)
            {
                //包容性网关
                type = NodeType.INCLUSIVE.getValue();
            }
            saveNode(flowElement,type,processId);
            Gateway gateway = (Gateway)flowElement;
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setNodeId(gateway.getId());
            nodeProperties.setProcessId(processId);
            nodeProperties.setFlowOrder(gateway.getDefaultFlow());
            nodePropertiesService.saveNodeProperties(nodeProperties);
        }
        else if(flowElement instanceof SequenceFlow)
        {
            //顺序流
            SequenceFlow sequenceFlow = (SequenceFlow)flowElement;
            //保存顺序流信息
            Sequence sequence = new Sequence();
            sequence.setId(sequenceFlow.getId());
            sequence.setName(sequenceFlow.getName());
            sequence.setDescription(sequenceFlow.getDocumentation());
            sequence.setProcessId(processId);
            sequence.setConditionExpress(sequenceFlow.getConditionExpression());
            sequence.setSourceRef(sequenceFlow.getSourceRef());
            sequence.setTargetRef(sequenceFlow.getTargetRef());
            sequence.setDefault(false);
            sequenceService.saveSequence(sequence);
        }
        else if(flowElement instanceof EndEvent)
        {
            //结束事件
            EndEvent endEvent = (EndEvent)flowElement;
            //普通结束事件
            String type = NodeType.END.getValue();
            String errorCode = "";
            for(EventDefinition eventDefinition :endEvent.getEventDefinitions())
            {
                if(eventDefinition instanceof ErrorEventDefinition)
                {
                    //结束错误事件
                    type = NodeType.ERROREND.getValue();
                    errorCode = ((ErrorEventDefinition) eventDefinition).getErrorCode();
                }
                else if(eventDefinition instanceof CancelEventDefinition)
                {
                    //结束取消事件
                    type = NodeType.CANCELEND.getValue();
                }
                else if(eventDefinition instanceof TerminateEventDefinition)
                {
                    //结束终止事件
                    type = NodeType.TERMINATEEND.getValue();
                }
            }
            saveNode(flowElement,type,processId);
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setNodeId(flowElement.getId());
            nodeProperties.setProcessId(processId);
            nodeProperties.setErrorRef(errorCode);
        }
    }

    /**
     * 保存节点信息
     * @param flowElement
     * @param type
     * @param processId
     */
    private void saveNode(FlowElement flowElement,String type,String processId)
    {
        Node node = new Node();
        node.setId(flowElement.getId());
        node.setName(flowElement.getName());
        node.setDescription(flowElement.getDocumentation());
        node.setProcessId(processId);
        node.setNodeType(type);
        nodeService.saveNode(node);
    }
}
