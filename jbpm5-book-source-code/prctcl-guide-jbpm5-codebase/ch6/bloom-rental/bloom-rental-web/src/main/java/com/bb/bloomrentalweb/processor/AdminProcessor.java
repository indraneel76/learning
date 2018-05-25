package com.bb.bloomrentalweb.processor;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.bloomrentaldomain.RentalAdmin;
import com.bb.bloomrentaldomain.RentalTaskData;
import com.bb.bloomrentalejb.task.TaskFacadeLocal;

public class AdminProcessor {

	private static Logger log = LoggerFactory.getLogger(AdminProcessor.class);

	public void getTasks(HttpServletRequest request) {
		String role = request.getParameter("role");
		request.setAttribute("role", role);
		try {
			Context context = new InitialContext();
			TaskFacadeLocal taskFacade = (TaskFacadeLocal) context
					.lookup("java:module/TaskFacade");
			List<RentalTaskData> list = taskFacade.getAssignedTasks(role);
			request.setAttribute("rentalTaskDataList", list);
		} catch (Exception e) {
			log.error("Exception while getting tasks", e);
		}
	}

	public void completeTask(HttpServletRequest request) {
		try {
			String taskIdStr = request.getParameter("taskId");
			String role = request.getParameter("role");
			String approve = request.getParameter("approve");
			boolean approved = (approve != null);
			RentalAdmin admin = new RentalAdmin();
			if (role.equals("Reviewer")) {
				admin.setApprovedByreviewer(approved);
				admin.setReviewerComments(request.getParameter("revComments"));
			} else if (role.equals("Super")) {
				admin.setApprovedBySuper(approved);
				admin.setSuperComments(request.getParameter("superComments"));
			}
			Context context = new InitialContext();
			TaskFacadeLocal taskFacade = (TaskFacadeLocal) context
					.lookup("java:module/TaskFacade");
			taskFacade.completeTask(Long.valueOf(taskIdStr), role, admin);
		} catch (Exception e) {
			log.error("Exception while completing task", e);
		}
	}

}
