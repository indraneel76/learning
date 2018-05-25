package com.bb.cust.bloomrentaldomain;

import java.io.Serializable;

public class RentalTaskData implements Serializable {

	private static final long serialVersionUID = 1L;
	private long taskId;
	private String taskName;
	private RentalApp app;
	private RentalAdmin admin;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public RentalApp getApp() {
		return app;
	}

	public void setApp(RentalApp app) {
		this.app = app;
	}

	public RentalAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(RentalAdmin admin) {
		this.admin = admin;
	}

}
