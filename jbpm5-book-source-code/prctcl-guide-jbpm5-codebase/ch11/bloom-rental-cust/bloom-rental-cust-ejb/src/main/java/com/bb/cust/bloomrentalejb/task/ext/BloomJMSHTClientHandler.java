package com.bb.cust.bloomrentalejb.task.ext;

import java.util.HashMap;
import java.util.Map;
 
import org.drools.SystemEventListener;
import org.drools.task.service.ResponseHandler;
import org.jbpm.task.service.BaseClientHandler;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskClientHandler;


public class BloomJMSHTClientHandler extends BaseClientHandler{
	
    protected Map<Integer, ResponseHandler> responseHandlers;
 
    private TaskClientHandler handler;
 
    public BloomJMSHTClientHandler(SystemEventListener systemEventListener)
    {
        responseHandlers = new HashMap<Integer, ResponseHandler>();
        this.handler = new TaskClientHandler(responseHandlers,
                systemEventListener);
    }
 
    @Override
    public void addResponseHandler(int id, ResponseHandler responseHandler)
    {
        responseHandlers.put(id, responseHandler);
    }
 
    public TaskClient getClient()
    {
        return handler.getClient();
    }
 
    public void setClient(TaskClient client)
    {
        handler.setClient(client);
    }
 
    public void exceptionCaught(Throwable cause) throws Exception
    {
        handler.exceptionCaught(null, cause);
    }
 
    public void messageReceived(Object message) throws Exception
    {
        handler.messageReceived(null, message);
    }
}
 
