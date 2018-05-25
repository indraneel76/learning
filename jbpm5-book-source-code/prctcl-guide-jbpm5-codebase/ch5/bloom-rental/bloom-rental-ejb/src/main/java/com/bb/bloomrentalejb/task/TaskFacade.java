package com.bb.bloomrentalejb.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.task.Content;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.bloomrentaldomain.RentalAdmin;
import com.bb.bloomrentaldomain.RentalApp;
import com.bb.bloomrentaldomain.RentalTaskData;
import com.bb.bloomrentalejb.process.KnowledgebaseBuilder;
import com.bb.bloomrentalejb.session.SessionFacadeLocal;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskFacade implements TaskFacadeLocal {
	private static Logger log = LoggerFactory.getLogger(TaskFacade.class);
	@EJB(lookup = "java:module/SessionFacade")
	private SessionFacadeLocal sessionFacade;

	@Override
	public List<RentalTaskData> getAssignedTasks(String userId) {
		List<RentalTaskData> data = new ArrayList<RentalTaskData>();
		try {
			LocalTaskService service = HumanTaskHelper.getLocalTaskService();
			List<Status> status = new ArrayList<Status>();
			// retrieve only Reserved Tasks
			status.add(Status.Reserved);
			List<TaskSummary> res = service.getTasksOwned(userId, status,
					"en-UK");
			for (Object each : res) {
				TaskSummary t = (TaskSummary) each;
				Task task = service.getTask(t.getId());
				RentalApp app = (RentalApp) getProcessData(task);
				data.add(buildRentalTaskData(t, app));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	public void completeTask(long taskId, boolean taskApproved,
			String taskComments) {
		StatefulKnowledgeSession ksession = null;
		long processInstanceId = 0;
		try {
			LocalTaskService service = HumanTaskHelper.getLocalTaskService();
			Task task = service.getTask(taskId);
			processInstanceId = task.getTaskData().getProcessInstanceId();
			ksession = sessionFacade.loadSession(KnowledgebaseBuilder.kbase,
					task.getTaskData().getProcessSessionId());
			Content content = service.getContent(task.getTaskData()
					.getDocumentContentId());
			Map<String, Object> contentData = (Map<String, Object>) ContentMarshallerHelper
					.unmarshall(content.getContent(), null);
			RentalAdmin admin = (RentalAdmin) contentData.get("inAdmin");
			admin.setApprovedByreviewer(taskApproved);
			admin.setReviewerComments(taskComments);
			Map<String, Object> results = new HashMap<String, Object>();
			results.put("outAdmin", admin);
			service.start(taskId, "Reviewer");
			service.completeWithResults(taskId, "Reviewer", results);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isProcessCompleted(ksession, processInstanceId)) {
				ksession.dispose();
			}
		}
	}

	private boolean isProcessCompleted(StatefulKnowledgeSession ksession,
			long processInstanceId) {
		ProcessInstance pi = ((org.drools.runtime.process.ProcessRuntime) ksession)
				.getProcessInstance(processInstanceId);
		return (pi == null)
				|| (pi != null && pi.getState() == ProcessInstance.STATE_COMPLETED);
	}

	private Object getProcessData(Task task) {
		StatefulKnowledgeSession ksession = sessionFacade.loadSession(
				KnowledgebaseBuilder.kbase, task.getTaskData()
						.getProcessSessionId());
		ProcessInstance pi = ((org.drools.runtime.process.ProcessRuntime) ksession)
				.getProcessInstance(task.getTaskData().getProcessInstanceId());
		WorkflowProcessInstance wf = (WorkflowProcessInstance) pi;
		Object obj = wf.getVariable("app");
		return obj;
	}

	private RentalTaskData buildRentalTaskData(TaskSummary task, RentalApp app) {
		RentalTaskData data = new RentalTaskData();
		data.setApp(app);
		data.setTaskId(task.getId());
		data.setTaskName(task.getName());
		log.info("RentalTaskData:" + data);
		return data;
	}
}
