package com.bb.cust.bloomrentalejb.session;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bb.cust.bloomrentalejb.process.KnowledgebaseBuilder;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class SessionFacade implements SessionFacadeLocal {
	private static Logger log = LoggerFactory.getLogger(SessionFacade.class);
	@PersistenceUnit(unitName = "jpa.jbpm")
	private EntityManagerFactory emf;

	@Override
	public StatefulKnowledgeSession createSession(KnowledgeBase kbase) {
		log.info("Creating jbpm session..");
		return JPAKnowledgeService.newStatefulKnowledgeSession(
				KnowledgebaseBuilder.kbase, null, createEnvironment());
	}

	@Override
	public StatefulKnowledgeSession loadSession(KnowledgeBase kbase,
			int sessionId) {
		log.info("Loading jbpm session with id" + sessionId);
		return JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId,
				kbase, null, createEnvironment());
	}

	private Environment createEnvironment() {
		Environment environment = KnowledgeBaseFactory.newEnvironment();
		environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		return environment;
	}
}
