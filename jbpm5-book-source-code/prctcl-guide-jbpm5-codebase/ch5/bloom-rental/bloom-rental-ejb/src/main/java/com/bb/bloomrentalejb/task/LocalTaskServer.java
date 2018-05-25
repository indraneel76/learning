package com.bb.bloomrentalejb.task;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.drools.SystemEventListenerFactory;

public class LocalTaskServer implements TaskServer {
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("jpa.jbpm.task");
	private static org.jbpm.task.service.TaskService _instance;

	public static org.jbpm.task.service.TaskService getService() {
		return startService();
	}

	private static org.jbpm.task.service.TaskService startService() {
		if (_instance == null) {
			_instance = new org.jbpm.task.service.TaskService(emf,
					SystemEventListenerFactory.getSystemEventListener());
		}
		return _instance;
	}

	@Override
	public void start() {
		_instance = startService();
	}

	@Override
	public void stop() {
		if (emf != null) {
			emf.close();
			emf = null;
		}
	}
}
