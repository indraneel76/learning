package com.bb.bloomrentalejb.process;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.bloomrentalejb.session.SessionFacadeLocal;
import com.bb.bloomrentalejb.task.HumanTaskHelper;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessFacade implements ProcessFacadeLocal {
	private static Logger log = LoggerFactory.getLogger(ProcessFacade.class);
	@PersistenceUnit(unitName = "jpa.jbpm")
	private EntityManagerFactory emf;

	@EJB(lookup = "java:module/SessionFacade")
	private SessionFacadeLocal sessionFacade;

	@Override
	public void startProcess(String processId, Map<String, Object> processData,
			List<Object> facts) {
		log.info("startProcess called for" + processId);
		startBPM(processId, processData, facts);
	}

	public void startBPM(String processName, Map<String, Object> processData,
			List<Object> facts) {
		UserTransaction ut = null;
		ProcessInstance processInstance = null;
		StatefulKnowledgeSession ksession = null;
		try {
			Context ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
			ut.begin();
			ksession = sessionFacade.createSession(KnowledgebaseBuilder.kbase);
			ksession.getWorkItemManager().registerWorkItemHandler(
					"Service Task", new ServiceTaskHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
					HumanTaskHelper.getLocalWorkItemHandler(ksession));
			for (Object fact : facts) {
				ksession.insert(fact);
			}
			processInstance = ksession.startProcess(processName, processData);
			ksession.fireAllRules();
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (processInstance != null
						&& (processInstance.getState() == ProcessInstance.STATE_COMPLETED)) {
					ksession.dispose();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
