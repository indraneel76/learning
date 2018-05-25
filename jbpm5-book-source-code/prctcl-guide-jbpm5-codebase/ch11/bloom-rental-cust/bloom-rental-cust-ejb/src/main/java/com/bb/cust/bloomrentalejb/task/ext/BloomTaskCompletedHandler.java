package com.bb.cust.bloomrentalejb.task.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.task.Content;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.service.Command;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.cust.bloomrentalejb.external.BloomTimerService;
import com.bb.cust.bloomrentalejb.external.LetterDispatchService;
import com.bb.cust.bloomrentalejb.process.KnowledgebaseBuilder;
import com.bb.cust.bloomrentalejb.session.SessionFacadeLocal;
import com.bb.cust.bloomrentalejb.task.HumanTaskHelper;
import com.bb.cust.bloomrentalejb.task.TaskFacade;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BloomTaskCompletedHandler implements BloomTaskCompletedHandlerLocal{
	private static Logger log = LoggerFactory.getLogger(TaskFacade.class);
	
	@PersistenceContext(unitName="jpa.jbpm.task")
	private EntityManager em;
	
	@EJB(lookup="java:module/SessionFacade")
	private SessionFacadeLocal sessionFacade;	
	
	public void execute(Command command) throws Exception {
		List<Object> args = command.getArguments();
		
		UserTransaction ut = null;
		StatefulKnowledgeSession ksession = null;
		JPAWorkingMemoryDbLogger logger = null;
		
		if (args == null || args.size() <= 0) {
			return;
		}
        long taskId = (Long) args.get(1);
        
        try {
			Context ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
			ut.begin();

            Task task = em.find(Task.class, taskId);
            long workItemId = task.getTaskData().getWorkItemId();        	
            ksession = prepareSession(task);
            logger = new JPAWorkingMemoryDbLogger(ksession);
	        if (task.getTaskData().getStatus() == Status.Completed) {
	            String userId = task.getTaskData().getActualOwner().getId();
	            Map<String, Object> results = new HashMap<String, Object>();
	            results.put("ActorId", userId);
	            long contentId = task.getTaskData().getOutputContentId();
	            if (contentId != -1) {
	                Content content = em.find(Content.class, contentId);
	                Object result = ContentMarshallerHelper.unmarshall(content.getContent(), ksession.getEnvironment(), 
	                		null);
	                results.put("Result", result);
	                if (result instanceof Map) {
	                    Map<?, ?> map = (Map<?, ?>) result;
	                    for (Map.Entry<?, ?> entry : map.entrySet()) {
	                        if (entry.getKey() instanceof String) {
	                            results.put((String) entry.getKey(), entry.getValue());
	                        }
	                    }
	                }
	                ksession.getWorkItemManager().completeWorkItem(task.getTaskData().getWorkItemId(), results);
	            } else {
	            	ksession.getWorkItemManager().completeWorkItem(workItemId, results);
	            }
	            log.info("Completed the workitem...");
	        } else {
	        	ksession.getWorkItemManager().abortWorkItem(workItemId);
	        }
        } catch(Exception e) {
        	
        } finally {
			try {
				ut.commit();
				ksession.dispose();
				logger.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    private StatefulKnowledgeSession prepareSession(Task task) throws Exception {
		StatefulKnowledgeSession ksession = 
				sessionFacade.loadSession(KnowledgebaseBuilder.kbase, 
						task.getTaskData().getProcessSessionId());
		
		ksession.getWorkItemManager().registerWorkItemHandler("Service Task", 
				new ServiceTaskHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", 
				HumanTaskHelper.getBloomJMSWorkItemHandler(ksession));			
		ksession.getWorkItemManager().registerWorkItemHandler("LetterDispatchService", 
				new LetterDispatchService());
		ksession.getWorkItemManager().registerWorkItemHandler("BloomTimerService", new BloomTimerService());
		
		return ksession;
    }


}
