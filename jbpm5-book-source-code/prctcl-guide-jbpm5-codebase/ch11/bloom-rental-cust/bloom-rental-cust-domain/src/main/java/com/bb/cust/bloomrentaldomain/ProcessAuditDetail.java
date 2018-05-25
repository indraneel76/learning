package com.bb.cust.bloomrentaldomain;

import java.util.Date;
import java.util.List;

public class ProcessAuditDetail {
	private String processName;
	private long processInstanceId;
	private Date startDate;
	private Date endDate;
	private List<NodeAuditDetail> nodeAuditDetail;
	private RentalApp app;

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<NodeAuditDetail> getNodeAuditDetail() {
		return nodeAuditDetail;
	}

	public void setNodeAuditDetail(List<NodeAuditDetail> nodeAuditDetail) {
		this.nodeAuditDetail = nodeAuditDetail;
	}

	public RentalApp getApp() {
		return app;
	}

	public void setApp(RentalApp app) {
		this.app = app;
	}

}
