package com.bb.cust.bloomrentalejb.task;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.process.WorkItemHandler;
import org.jbpm.task.service.TaskClient;

import com.bb.cust.bloomrentalejb.task.ext.BloomJMSHTClientConnector;
import com.bb.cust.bloomrentalejb.task.ext.BloomJMSHTClientHandler;
import com.bb.cust.bloomrentalejb.task.ext.BloomJMSHTWorkItemHandler;
import com.bb.cust.bloomrentalejb.task.ext.BloomTaskServiceWrapper;

public class HumanTaskHelper {
	public static WorkItemHandler getBloomJMSWorkItemHandler(KnowledgeRuntime session) throws Exception {
		return new BloomJMSHTWorkItemHandler(session);
	}	
	
	public static org.jbpm.task.TaskService getBloomTaskService() {
    	BloomJMSHTClientHandler clientHandler = new BloomJMSHTClientHandler
    			(SystemEventListenerFactory.getSystemEventListener());
    	
    	BloomJMSHTClientConnector connector = new BloomJMSHTClientConnector
    			("BloomJMSHTClientConnector", clientHandler);
    	
    	return new BloomTaskServiceWrapper(new TaskClient(connector));
	}
}