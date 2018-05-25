package com.bb.bloomrentalejb.process;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessFacade implements ProcessFacadeLocal {
	@PersistenceUnit(unitName = "jpa.jbpm")
	private EntityManagerFactory emf;

	@Override
	public void startProcess(String processId) {
		System.out.println("startProcess called");
		System.out.println("Running process for" + processId);
		startBPM(processId);
		System.out.println("Process successfully started...");
	}

	public void startBPM(String processId) {
		UserTransaction ut = null;
		StatefulKnowledgeSession ksession = null;
		try {
			Context ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
			ut.begin();

			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory
					.newClassPathResource("com.bb.helloworld.bpmn2"),
					ResourceType.BPMN2);
			KnowledgeBase kbase = kbuilder.newKnowledgeBase();
			ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase,
					null, createEnvironment());
			ksession.startProcess(processId, null);
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
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private Environment createEnvironment() {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		return environment;
	}
}
