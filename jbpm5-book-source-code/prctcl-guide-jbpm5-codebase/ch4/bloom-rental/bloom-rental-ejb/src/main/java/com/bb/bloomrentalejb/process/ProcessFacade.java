package com.bb.bloomrentalejb.process;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import org.drools.KnowledgeBaseFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessFacade implements ProcessFacadeLocal {
	private static Logger log = LoggerFactory.getLogger(ProcessFacade.class);
	@PersistenceUnit(unitName = "jpa.jbpm")
	private EntityManagerFactory emf;

	@Override
	public void startProcess(String processId, Map<String, Object> processData,
			List<Object> facts) {
		log.info("startProcess called for" + processId);
		startBPM(processId, processData, facts);
	}

	public void startBPM(String processId, Map<String, Object> processData,
			List<Object> facts) {
		UserTransaction ut = null;
		StatefulKnowledgeSession ksession = null;
		try {
			Context ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
			ut.begin();
			ksession = JPAKnowledgeService.newStatefulKnowledgeSession(
					KnowledgebaseBuilder.kbase, null, createEnvironment());
			ksession.getWorkItemManager().registerWorkItemHandler(
					"Service Task", new ServiceTaskHandler());
			for (Object fact : facts) {
				ksession.insert(fact);
			}
			ksession.startProcess(processId, processData);
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
				ksession.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Environment createEnvironment() {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		return environment;
	}
}
