package com.bb.cust.bloomrentalejb.external;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.cust.bloomrentaldomain.RentalAdmin;

public class LetterDispatchService implements org.drools.process.instance.WorkItemHandler {
	private static Logger log = LoggerFactory
			.getLogger(LetterDispatchService.class);

	
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		log.info("Calling from LetterDispatchService...");
		RentalAdmin admin = (RentalAdmin)workItem.getParameter("param");

		log.info("The reviewer has " + (admin.isApprovedByreviewer()?"approved":"rejected") + " the task with the following comments:");
		log.info(admin.getReviewerComments());
		log.info("The Super user has " + (admin.isApprovedBySuper()?"approved":"rejected") + " the task with the following comments:");
		log.info(admin.getSuperComments());
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("Result", null); //use this to pass data back to the process context
		manager.completeWorkItem(workItem.getId(), results);
		}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}
}
