package com.bb.cust.bloomrentalejb.task;

import java.util.List;

import javax.ejb.Local;

import com.bb.cust.bloomrentaldomain.RentalAdmin;
import com.bb.cust.bloomrentaldomain.RentalTaskData;

@Local
public interface TaskFacadeLocal {
	public List<RentalTaskData> getAssignedTasks(String userId);

	public void completeTask(long taskId, String userId, RentalAdmin admin);
}
