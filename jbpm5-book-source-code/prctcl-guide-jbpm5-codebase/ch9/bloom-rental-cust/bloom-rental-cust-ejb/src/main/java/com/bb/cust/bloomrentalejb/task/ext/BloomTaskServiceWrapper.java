package com.bb.cust.bloomrentalejb.task.ext;

import org.jbpm.process.workitem.wsht.BlockingAddTaskResponseHandler;
import org.jbpm.task.AsyncTaskService;
import org.jbpm.task.Task;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.SyncTaskServiceWrapper;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;

public class BloomTaskServiceWrapper extends SyncTaskServiceWrapper {
	private int timeout = 3000;
	private AsyncTaskService taskService;
	
	public BloomTaskServiceWrapper(AsyncTaskService taskService) {
		super(taskService);
		this.taskService = taskService;
	}
	
    public void addTask(Task task, ContentData content) {
        BlockingAddTaskResponseHandler responseHandler = new BlockingAddTaskResponseHandler();
        taskService.addTask(task, content, responseHandler);
    }
    
    public void start(long taskId, String userId) {
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        taskService.start(taskId, userId, responseHandler);
        responseHandler.waitTillDone(timeout);
    }
    
    public void complete(long taskId, String userId, ContentData outputData) {
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        taskService.complete(taskId, userId, outputData, responseHandler);
    }    

}
