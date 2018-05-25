package com.bb.cust.bloomrentalejb.task;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.SystemEventListenerFactory;

public class BloomTaskServer implements TaskServer {
	public static EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("jpa.jbpm.task");
	
	private static boolean started = false;
	
	private static org.jbpm.task.service.TaskService _instance;
	
	public static org.jbpm.task.service.TaskService getService() throws Exception {
		if (_instance == null ) {
			startService();
		}
		return _instance;
	}
	
	private static org.jbpm.task.service.TaskService startService() throws Exception {
		if (_instance == null) {
			_instance = new org.jbpm.task.service.TaskService(emf, 
					SystemEventListenerFactory.getSystemEventListener()); 
			started = true;
		}
		return _instance;
	}
	
	@Override
	public void start() throws Exception {
		_instance = startService();
	}

	@Override
	public void stop() throws Exception {
		if (!started) {
			throw new Exception("Illegal call to stop -TaskServer not started yet...");
		}
		if (emf != null) {
			emf.close();
			emf = null;
			started = false;
		}
	}
}
