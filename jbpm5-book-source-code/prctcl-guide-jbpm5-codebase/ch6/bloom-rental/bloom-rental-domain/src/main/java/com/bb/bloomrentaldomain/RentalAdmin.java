package com.bb.bloomrentaldomain;

import java.io.Serializable;

public class RentalAdmin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String reviewUserid;
	private boolean approvedByreviewer;
	private String reviewerComments;
	private String superUserid;
	private boolean approvedBySuper;
	private String superComments;	


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

	public String getSuperUserid() {
		return superUserid;
	}

	public void setSuperUserid(String superUserid) {
		this.superUserid = superUserid;
	}

	public boolean isApprovedBySuper() {
		return approvedBySuper;
	}

	public void setApprovedBySuper(boolean approvedBySuper) {
		this.approvedBySuper = approvedBySuper;
	}

	public String getSuperComments() {
		return superComments;
	}

	public void setSuperComments(String superComments) {
		this.superComments = superComments;
	}

}
