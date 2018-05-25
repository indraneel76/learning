package com.bb.cust.bloomrentaldomain;

import java.io.Serializable;

public class RentalApp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String ssn;
	private int creditScore;
	private boolean approvedByRules = false;
	private boolean factUpdated = false;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public boolean isApprovedByRules() {
		return approvedByRules;
	}

	public void setApprovedByRules(boolean approvedByRules) {
		this.approvedByRules = approvedByRules;
	}

	public boolean isFactUpdated() {
		return factUpdated;
	}

	public void setFactUpdated(boolean factUpdated) {
		this.factUpdated = factUpdated;
	}

}
