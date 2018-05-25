package com.bb.cust.bloomrentalejb.task.ext;
 
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.Command;
import org.jbpm.task.service.CommandName;
import org.jbpm.task.service.Operation;
import org.jbpm.task.service.SessionWriter;
import org.jbpm.task.service.TaskServerHandler;
import org.jbpm.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.cust.bloomrentalejb.task.BloomTaskServer;

public class BloomJMSHTService
{
    private static Logger log = LoggerFactory.getLogger(BloomJMSHTService.class);
 
    private static final SessionWriter sessionWriter = new BloomTaskSessionWriter();
    
    public static void process(Command command)
    {
    	try
        {
        	TaskService taskService = BloomTaskServer.getService();
        	
            TaskServerHandler serverHandler = new TaskServerHandler(
            		taskService, SystemEventListenerFactory.getSystemEventListener());
            
            serverHandler.messageReceived(sessionWriter, command);
            
            postProcess(command);
        }
        catch (Exception e)
        {
            log.error("Error while handling the Task Server Command", e);
        }
    }
    
    private static void postProcess(Command command) {
    	if (CommandName.OperationRequest.equals(command.getName())) {
    		List<Object> args = command.getArguments();
    		if (args != null && args.size() > 0) {
    			if (Operation.Complete.equals(args.get(0))) {
    				try {
    					Context context = new InitialContext();
    					BloomTaskCompletedHandlerLocal completedHandler = (BloomTaskCompletedHandlerLocal) 
    							context.lookup("java:module/BloomTaskCompletedHandler");
    					
    					completedHandler.execute(command);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    }
 
}
 
