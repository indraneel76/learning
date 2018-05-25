package com.bb.cust.bloomrentalejb.task.ext;

import org.jbpm.task.service.Command;

public interface BloomTaskCompletedHandlerLocal {

	public void execute(Command command) throws Exception;
}
