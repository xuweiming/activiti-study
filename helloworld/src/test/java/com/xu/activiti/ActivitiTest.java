package com.xu.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * activiti测试类
 */
public class ActivitiTest {
    ProcessEngine pe;
    TaskService taskService;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    @Before
    public void init() {
        pe = ProcessEngines.getDefaultProcessEngine();
        taskService = pe.getTaskService();
        repositoryService = pe.getRepositoryService();
        runtimeService = pe.getRuntimeService();
    }

    @Test
    public void testAddDeployment() {
        addClasspathResourceDeployment("sayhelloleave.bpmn");
    }
    @Test
    public void testStartProcess() {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("startUserId","xuweiming");
        params.put("userName","xuweiming");
        startProcessInstanceByKey("myProcess",params);
    }

    public Deployment addClasspathResourceDeployment(String resourceName) {
        return repositoryService.createDeployment().addClasspathResource(resourceName).deploy();
    }

    public ProcessInstance startProcessInstanceByKey(String key, Map<String,Object> params) {
        return runtimeService.startProcessInstanceByKey(key, params);
    }
}
