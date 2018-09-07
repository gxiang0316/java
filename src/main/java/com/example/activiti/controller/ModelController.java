package com.example.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Author:guixiang
 * Date:2018-09-05
 */
@RestController
@RequestMapping("/model")
public class ModelController
{
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProcessEngine processEngine;

    /**
     * 创建模型
     */
    @RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            Model modelData = repositoryService.newModel();
            //设置一些默认信息
            String name = "new-process";
            String description = "";
            int revision = 1;
            String key = "process";
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(key);
            //保存模型
            repositoryService.saveModel(modelData);
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        }
        catch (Exception e)
        {
            System.out.println("创建模型失败：");
        }
    }

    /**
     * 导入模型
     * @param uploadfile
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void deployUploadedFile( @RequestParam("uploadfile") MultipartFile uploadfile , HttpServletRequest request , HttpServletResponse response)
    {
        InputStreamReader in = null;
        try
        {
            try
            {
                boolean validFile = false;
                String fileName = uploadfile.getOriginalFilename();
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn"))
                {
                    validFile = true;
                    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                    in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null)
                    {
//                        notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED,
//                                i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMN_EXPLANATION));
                        System.out.println("err1");
                    }
                    else
                    {
                        if (bpmnModel.getLocationMap().isEmpty())
                        {
//                            notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_BPMNDI,
//                                    i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMNDI_EXPLANATION));
                            System.out.println("err2");
                        }
                        else
                        {
                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName()))
                            {
                                processName = bpmnModel.getMainProcess().getName();
                            }
                            else
                            {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);
                            repositoryService.saveModel(modelData);
                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
                            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
                        }
                    }
                }
                else
                {
//                    notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_FILE,
//                            i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_FILE_EXPLANATION));
                    System.out.println("err3");
                }
            }
            catch (Exception e)
            {
                String errorMsg = e.getMessage().replace(System.getProperty("line.separator"), "<br/>");
//                notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED, errorMsg);
                System.out.println("err4");
            }
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
//                    notificationManager.showErrorNotification("Server-side error", e.getMessage());
                    System.out.println("err5");
                }
            }
        }
    }

    @RequestMapping(value = "/export/{modelId}",method = RequestMethod.GET)
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try
        {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            //获取节点信息
            byte[] arg0 = repositoryService.getModelEditorSource(modelData.getId());
            JsonNode editorNode = objectMapper.readTree(arg0);
            //将节点信息转换为xml
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            IOUtils.copy(in, response.getOutputStream());
//                String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            String filename = modelData.getName() + ".bpmn20.xml";
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            response.flushBuffer();
        }
        catch (Exception e)
        {
            PrintWriter out = null;
            try
            {
                out = response.getWriter();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            out.write("未找到对应数据");
            e.printStackTrace();
        }
    }

    /**
     * 发布模型为流程定义
     */
    @RequestMapping(value = "/deploy/{modelId}",method = RequestMethod.GET)
    @ResponseBody
    public Object deploy(@PathVariable String modelId) throws Exception
    {
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return "数据模型不符要求，请至少设计一条主线流程。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return "SUCCESS";
    }

    public void test()
    {
        processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        IdentityService identityService = processEngine.getIdentityService();
        processEngine.getHistoryService();

    }
}
