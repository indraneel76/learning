package com.bb.cust.bloomrentaldomain;

import java.util.Date;

public class NodeAuditDetail {
	private String nodeName;
	private int nodeInstanceId;
	private Date startDate;
	private Date endDate;

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(int nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
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

}
