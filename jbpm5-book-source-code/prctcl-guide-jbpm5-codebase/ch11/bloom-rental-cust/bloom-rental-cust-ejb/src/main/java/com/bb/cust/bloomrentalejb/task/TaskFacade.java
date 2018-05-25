package com.bb.cust.bloomrentalejb.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.task.Content;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.cust.bloomrentaldomain.RentalAdmin;
import com.bb.cust.bloomrentaldomain.RentalApp;
import com.bb.cust.bloomrentaldomain.RentalTaskData;
import com.bb.cust.bloomrentalejb.process.KnowledgebaseBuilder;
import com.bb.cust.bloomrentalejb.session.SessionFacadeLocal;


/**
 * Session Bean implementation class ProcessFacade
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TaskFacade implements TaskFacadeLocal {

	private static Logger log = LoggerFactory.getLogger(TaskFacade.class);
	
	@EJB(lookup="java:module/SessionFacade")
	private SessionFacadeLocal sessionFacade;
	
	@PersistenceContext(unitName="jpa.jbpm.task")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<RentalTaskData> getAssignedTasks(String userId) {
		List<RentalTaskData> data = new ArrayList<RentalTaskData>();
		
		try {
			List<Status> status = new ArrayList<Status>();
			String language = "en-UK";
			
			//retrieve only Reserved Tasks
			status.add(Status.Reserved);
			
	        Query query = em.createNamedQuery("TasksOwnedWithParticularStatus");
	        query.setParameter("userId", userId);
	        query.setParameter("language", language);
	        query.setParameter("status", status);
	        
	        List<TaskSummary> res = (List<TaskSummary>)query.getResultList();
	        
	        RentalAdmin admin = null;
			RentalApp app = null;
			
			for (Object each : res) {
				TaskSummary t = (TaskSummary) each;
				Task task = em.find(Task.class, t.getId());
				Content content = em.find(Content.class, task.getTaskData().getDocumentContentId());
		        
				if (content != null) {
					Map<String, Object> contentData = (Map<String, Object>) 
			        		ContentMarshallerHelper.unmarshall(content.getContent(), null);
			        
			        admin = (RentalAdmin) contentData.get("inAdmin");
					app = (RentalApp) getProcessData(task);
				}
				
				data.add(buildRentalTaskData(t, app, admin));
			}
		} catch (Exception e) {
			log.error("Exception in getting Assigned Tasks", e);
		}
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public void completeTask(long taskId, String userId, RentalAdmin param) {
		try {
			Task task = em.find(Task.class, taskId);
			RentalAdmin admin = null;
			
			if (task.getTaskData().getDocumentContentId() > 0) {
				Content content = em.find(Content.class, task.getTaskData().getDocumentContentId());
		        
				Map<String, Object> contentData = (Map<String, Object>) 
		        		ContentMarshallerHelper.unmarshall(content.getContent(), null);
		        
		        admin = (RentalAdmin) contentData.get("inAdmin");
		        
		        if (userId.equals("Reviewer")) {
		        	admin.setApprovedByreviewer(param.isApprovedByreviewer());
		        	admin.setReviewerComments(param.getReviewerComments());
		        } else if (userId.equals("Super")) {
		        	admin.setApprovedBySuper(param.isApprovedBySuper());
		        	admin.setSuperComments(param.getSuperComments());
		        }
			}
	        Map<String, Object> results = new HashMap<String, Object>();
	        results.put("outAdmin", admin);
			
	        org.jbpm.task.TaskService taskService = HumanTaskHelper.getBloomTaskService();
	        
	        taskService.start(taskId, userId);
	        taskService.completeWithResults(taskId, userId, results);
		} catch (Exception e) {
			log.error("Exception in getting completing task", e);
		} 
	}	
	
	private Object getProcessData(Task task) {
		StatefulKnowledgeSession ksession = 
				sessionFacade.loadSession(KnowledgebaseBuilder.kbase, 
						task.getTaskData().getProcessSessionId());
		
		ProcessInstance pi = ((org.drools.runtime.process.ProcessRuntime)ksession).
				getProcessInstance(task.getTaskData().getProcessInstanceId());
		
		WorkflowProcessInstance wf = (WorkflowProcessInstance)pi;
		
		Object obj = wf.getVariable("app");
		
		return obj;
	}
	
	private RentalTaskData buildRentalTaskData(TaskSummary task, RentalApp app, RentalAdmin admin) {
		RentalTaskData data = new RentalTaskData();
		data.setApp(app);
		data.setAdmin(admin);
		data.setTaskId(task.getId());
		data.setTaskName(task.getName());
		log.info("RentalTaskData:" + data);
		return data;
	}
}
