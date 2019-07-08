package com.xu.activiti;

import com.fasterxml.uuid.impl.UUIDUtil;
import org.activiti.engine.*;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HelloWorldTest {
//    ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();




    @Test
    public void getDefaultProcessEngine() {
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = pe.getTaskService();
        RepositoryService repositoryService = pe.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("sayhelloleave.bpmn").deploy();
        Assert.assertNotNull(taskService);
        Assert.assertNotNull(pe);
    }

    @Test
    public void startProcess() {
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = pe.getRuntimeService();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("startUserId","xuweiming");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",params);
        Assert.assertNotNull(processInstance);
    }

    @Test
    public void queryTask() {
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = pe.getTaskService();
        Task task = taskService.createTaskQuery().taskDelegationState(DelegationState.RESOLVED).singleResult();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("applyUser","xuweiming");
        params.put("days",2);
        params.put("approved","同意");
        // 被委派人处理完成任务
//        taskService.resolveTask(task.getId(),params);
        taskService.complete(task.getId(),params);
        Assert.assertNotNull(task);
    }
    @Test
    public void testUser() {
        IdentityService identityService = activitiRule.getIdentityService();
        User user = identityService.newUser("xuweiming");
        user.setFirstName("Xu");
        user.setLastName("WeiMing");
        user.setEmail("1775842152@qq.com");
        identityService.saveUser(user);
        User userInDb = identityService.createUserQuery().userId("xuweiming").singleResult();
        Assert.assertNotNull(userInDb);
    }
    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString());
    }
}
