package com.bb.cust.bloomrentalweb.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.bb.cust.bloomrentalejb.task.LocalTaskServer;
import com.bb.cust.bloomrentalejb.task.TaskServer;

@WebListener
public class WebContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		TaskServer taskServer = new LocalTaskServer();
		try {
			taskServer.stop();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Human Task Server could not be started"
					+ "- Application will not function properly");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		TaskServer taskServer = new LocalTaskServer();
		try {
			taskServer.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Human Task Server could not be started"
					+ "- Application will not function properly");
		}
	}
}
