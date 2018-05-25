package com.bb.cust.bloomrentalejb.task.ext;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.KnowledgeRuntime;
import org.jbpm.process.workitem.wsht.GenericHTWorkItemHandler;
import org.jbpm.task.service.TaskClient;

public class BloomJMSHTWorkItemHandler extends GenericHTWorkItemHandler {

    public BloomJMSHTWorkItemHandler(KnowledgeRuntime session) {
        super(session);
        init();
    }
    
    private void init() {
    	BloomJMSHTClientHandler clientHandler = new BloomJMSHTClientHandler
    			(SystemEventListenerFactory.getSystemEventListener());
    	
    	BloomJMSHTClientConnector connector = new BloomJMSHTClientConnector
    			("BloomJMSHTClientConnector", clientHandler);
    	
    	setClient(new BloomTaskServiceWrapper(new TaskClient(connector)));
    }
    
    @Override
    public void connect() {
        if (getClient() == null) {
            throw new IllegalStateException("You must set the Task Service Client to the work item to work");
        }
    }    
}
