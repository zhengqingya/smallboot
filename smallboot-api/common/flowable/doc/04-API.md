# API

> 参考 https://www.cnblogs.com/songweipeng/p/17119106.html

### 1.FormService

表单数据的管理； 是可选服务，也就是说Flowable没有它也能很好地运行，而不必牺牲任何功能。这个服务引入了开始表单(start form)
与任务表单(task form)的概念。 开始表单是在流程实例启动前显示的表单，

而任务表单是用户完成任务时显示的表单。Flowable可以在BPMN 2.0流程定义中定义这些表单。表单服务通过简单的方式暴露这些数据。再次重申，表单不一定要嵌入流程定义，因此这个服务是可选的

```
formService.getStartFormKey() // 获取表单key
formService.getRenderedStartForm()  // 查询表单json（无数据）
```

### 2.RepositoryService

提供了在编辑和发布审批流程的api。主要是模型管理和流程定义的业务api

这个服务提供了管理与控制部署(deployments)与流程定义(process definitions)的操作

查询引擎现有的部署与流程定义。 暂停或激活部署中的某些流程，或整个部署。暂停意味着不能再对它进行操作，激活刚好相反，重新使它可以操作。
获取各种资源，比如部署中保存的文件，

或者引擎自动生成的流程图。 获取POJO版本的流程定义。它可以用Java而不是XML的方式查看流程。

```
1.提供了带条件的查询模型流程定义的api
repositoryService.createXXXQuery()
例如：
repositoryService.createModelQuery().list() 模型查询 
repositoryService.createProcessDefinitionQuery().list() 流程定义查询

repositoryService.createXXXXQuery().XXXKey(XXX) （查询该key是否存在）

2.提供一大波模型与流程定义的通用方法
模型相关
repositoryService.getModel()  （获取模型）
repositoryService.saveModel()  （保存模型）
repositoryService.deleteModel() （删除模型）
repositoryService.createDeployment().deploy(); （部署模型）
repositoryService.getModelEditorSource()  （获得模型JSON数据的UTF8字符串）
repositoryService.getModelEditorSourceExtra()  （获取PNG格式图像）

3.流程定义相关
repositoryService.getProcessDefinition(ProcessDefinitionId);  获取流程定义具体信息
repositoryService.activateProcessDefinitionById() 激活流程定义
repositoryService.suspendProcessDefinitionById()  挂起流程定义
repositoryService.deleteDeployment()  删除流程定义
repositoryService.getProcessDiagram()获取流程定义图片流
repositoryService.getResourceAsStream()获取流程定义xml流
repositoryService.getBpmnModel(pde.getId()) 获取bpmn对象（当前进行到的那个节点的流程图使用）

4.流程定义授权相关
repositoryService.getIdentityLinksForProcessDefinition() 流程定义授权列表
repositoryService.addCandidateStarterGroup()新增组流程授权
repositoryService.addCandidateStarterUser()新增用户流程授权
repositoryService.deleteCandidateStarterGroup() 删除组流程授权
repositoryService.deleteCandidateStarterUser()  删除用户流程授权
```

### 3.RuntimeService

处理正在运行的流程

```
runtimeService.createProcessInstanceBuilder().start() 发起流程
runtimeService.deleteProcessInstance() 删除正在运行的流程
runtimeService.suspendProcessInstanceById() 挂起流程定义
runtimeService.activateProcessInstanceById() 激活流程实例
runtimeService.getVariables(processInstanceId); 获取表单中填写的值
runtimeService.getActiveActivityIds(processInstanceId)获取以进行的流程图节点 （当前进行到的那个节点的流程图使用）
runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endId).changeState(); 终止流程
```

### 4.HistoryService

在用户发起审批后，会生成流程实例。historyService为处理流程实例的api，但是其中包括了已完成的和未完成的流程实例；
如果是处理正在运行的流程实例，请使用runtimeService；

暴露Flowable引擎收集的所有历史数据。当执行流程时，引擎会保存许多数据（可配置），例如流程实例启动时间、谁在执行哪个任务、完成任务花费的事件、每个流程实例的执行路径，等等。这个服务主要提供查询这些数据的能力

```
historyService.createHistoricProcessInstanceQuery().list() 查询流程实例列表（历史流程,包括未完成的）
historyService.createHistoricProcessInstanceQuery().list().foreach().getValue() 可以获取历史中表单的信息
historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult(); 根绝id查询流程实例
historyService.deleteHistoricProcessInstance() 删除历史流程
historyService.deleteHistoricTaskInstance(taskid); 删除任务实例
historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list()  流程实例节点列表 （当前进行到的那个节点的流程图使用）
    
flowable 有api查看act_hi_varinst里面的数据吗
HistoricVariableInstanceQuery query = historyService.createHistoricVariableInstanceQuery().processInstanceId(instance.getId());
                HistoricVariableInstance operate = query.variableName("operate").singleResult();
                if (operate.getValue().toString().equals("1")) { 　　　　　　　　　　　　// 表示流程同意　　　　　　　　　　}
```

### 5.TaskService

对流程实例的各个节点的审批处理

流转的节点审批

```
taskService.createTaskQuery().list() 待办任务列表
taskService.createTaskQuery().taskId(taskId).singleResult();  待办任务详情
taskService.saveTask(task); 修改任务
taskService.setAssignee() 设置审批人
taskService.addComment() 设置审批备注
taskService.complete() 完成当前审批
taskService.getProcessInstanceComments(processInstanceId); 查看任务详情（也就是都经过哪些人的审批，意见是什么）
taskService.delegateTask(taskId, delegater); 委派任务
taskService.claim(taskId, userId);认领任务
taskService.unclaim(taskId); 取消认领
taskService.complete(taskId, completeVariables); 完成任务

任务授权
taskService.addGroupIdentityLink()新增组任务授权
taskService.addUserIdentityLink() 新增人员任务授权
taskService.deleteGroupIdentityLink() 删除组任务授权
taskService.deleteUserIdentityLink() 删除人员任务授权
```

### 6.ManagementService

```
主要是执行自定义命令

managementService.executeCommand(new classA())  执行classA的内部方法 

在自定义的方法中可以使用以下方法获取repositoryService

ProcessEngineConfiguration processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

也可以获得流程定义方法集合

ProcessEngineConfigurationImpl processEngineConfiguration =  CommandContextUtil.getProcessEngineConfiguration(commandContext);
ProcessDefinitionEntityManager processDefinitionEntityManager = processEngineConfiguration.getProcessDefinitionEntityManager();
如 findById/findLatestProcessDefinitionByKey/findLatestProcessDefinitionByKeyAndTenantId 等。
```

### 7.IdentityService

用于身份信息获取和保存，这里主要是获取身份信息

用于管理（创建，更新，删除，查询……）组与用户。请注意，Flowable实际上在运行时并不做任何用户检查。

例如任务可以分派给任何用户，而引擎并不会验证系统中是否存在该用户。这是因为Flowable有时要与LDAP、Active Directory等服务结合使用

```
identityService.createUserQuery().userId(userId).singleResult();  获取审批用户的具体信息
identityService.createGroupQuery().groupId(groupId).singleResult(); 获取审批组的具体信息
```

### 8.DynamicBpmnService

可用于修改流程定义中的部分内容，而不需要重新部署它。例如可以修改流程定义中一个用户任务的办理人设置，或者修改一个服务任务中的类名。