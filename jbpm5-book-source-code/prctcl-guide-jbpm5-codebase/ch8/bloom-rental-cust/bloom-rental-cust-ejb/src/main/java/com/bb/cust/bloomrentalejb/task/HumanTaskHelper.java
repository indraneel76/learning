package com.bb.cust.bloomrentalejb.task;

import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.process.WorkItemHandler;
import org.jbpm.process.workitem.wsht.LocalHTWorkItemHandler;
import org.jbpm.task.service.local.LocalTaskService;

public class HumanTaskHelper {
	private static org.jbpm.task.service.TaskService service;
	private static LocalTaskService localTaskService;
	static {
		try {
			service = LocalTaskServer.getService();
			localTaskService = new LocalTaskService(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WorkItemHandler getLocalWorkItemHandler(
			KnowledgeRuntime session) throws Exception {
		return new LocalHTWorkItemHandler(localTaskService, session);
	}

	public static LocalTaskService getLocalTaskService() throws Exception {
		return localTaskService;
	}
}
