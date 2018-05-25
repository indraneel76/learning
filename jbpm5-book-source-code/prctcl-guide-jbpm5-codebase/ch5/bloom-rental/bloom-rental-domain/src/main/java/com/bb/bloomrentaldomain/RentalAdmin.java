package com.bb.bloomrentaldomain;

import java.io.Serializable;

public class RentalAdmin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String reviewUserid;
	private boolean approvedByreviewer;
	private String reviewerComments;

	public String getReviewUserid() {
		return reviewUserid;
	}

	public void setReviewUserid(String reviewUserid) {
		this.reviewUserid = reviewUserid;
	}

	public boolean isApprovedByreviewer() {
		return approvedByreviewer;
	}

	public void setApprovedByreviewer(boolean approvedByreviewer) {
		this.approvedByreviewer = approvedByreviewer;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

}
