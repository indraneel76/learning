package com.bb.bloomrentalweb.processor;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import com.bb.bloomrentaldomain.RentalTaskData;
import com.bb.bloomrentalejb.task.TaskFacadeLocal;

public class AdminProcessor {

	public void getTasksForReviewer(HttpServletRequest request) {
		try {
			Context context = new InitialContext();
			TaskFacadeLocal taskFacade = (TaskFacadeLocal) context
					.lookup("java:module/TaskFacade");
			List<RentalTaskData> list = taskFacade.getAssignedTasks("Reviewer");
			request.setAttribute("rentalTaskDataList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void completeTask(HttpServletRequest request) {
		try {
			String taskIdStr = request.getParameter("taskId");
			String comments = request.getParameter("comments");
			String approve = request.getParameter("approve");
			boolean approved = (approve != null);
			Context context = new InitialContext();
			TaskFacadeLocal taskFacade = (TaskFacadeLocal) context
					.lookup("java:module/TaskFacade");
			taskFacade
					.completeTask(Long.valueOf(taskIdStr), approved, comments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
