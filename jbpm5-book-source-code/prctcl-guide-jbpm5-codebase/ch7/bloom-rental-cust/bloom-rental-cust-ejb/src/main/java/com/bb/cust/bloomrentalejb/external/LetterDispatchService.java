package com.bb.cust.bloomrentalejb.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.cust.bloomrentaldomain.RentalAdmin;

public class LetterDispatchService {
	private static Logger log = LoggerFactory
			.getLogger(LetterDispatchService.class);

	public void sendLetter(Object param) {
		log.info("Calling from LetterDispatchService...");

		RentalAdmin admin = (RentalAdmin) param;

		log.info("The reviewer has "
				+ (admin.isApprovedByreviewer() ? "approved" : "rejected")
				+ " the task with the following comments:");
		log.info(admin.getReviewerComments());

		if (admin.getSuperComments() != null) {
			log.info("The Super user has "
					+ (admin.isApprovedBySuper() ? "approved" : "rejected")
					+ " the task with the following comments:");
			log.info(admin.getSuperComments());
		}
	}
}
