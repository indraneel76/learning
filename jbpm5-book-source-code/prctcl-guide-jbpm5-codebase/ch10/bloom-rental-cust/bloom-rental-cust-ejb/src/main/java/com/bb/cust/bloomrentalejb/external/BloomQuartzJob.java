package com.bb.cust.bloomrentalejb.external;

import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bb.cust.bloomrentalejb.process.KnowledgebaseBuilder;
import com.bb.cust.bloomrentalejb.session.SessionFacadeLocal;
import com.bb.cust.bloomrentalejb.task.HumanTaskHelper;
import com.bb.cust.bloomrentalejb.external.BloomTimerService;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BloomQuartzJob implements BloomQuartzJobLocal {
	private static Logger log = LoggerFactory.getLogger(BloomQuartzJob.class);

	@EJB(lookup = "java:module/SessionFacade")
	private SessionFacadeLocal sessionFacade;

	@Override
	public void execute(Map<String, Object> map) {
		UserTransaction ut = null;
		StatefulKnowledgeSession session = null;
		log.info("Executing BloomQuartzJob...");

		try {
			Context ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
			ut.begin();
			int sessionId = (Integer) map.get("sessionId");
			long workItemId = (Long) map.get("workItemId");
			session = prepareSession(sessionId);
			session.getWorkItemManager().completeWorkItem(workItemId, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ut.commit();
				session.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private StatefulKnowledgeSession prepareSession(int sessionId)
			throws Exception {
		StatefulKnowledgeSession ksession = sessionFacade.loadSession(
				KnowledgebaseBuilder.kbase, sessionId);
		ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
				new ServiceTaskHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
				HumanTaskHelper.getBloomJMSWorkItemHandler(ksession));
		ksession.getWorkItemManager().registerWorkItemHandler(
				"LetterDispatchService", new LetterDispatchService());
		ksession.getWorkItemManager().registerWorkItemHandler(
				"BloomTimerService", new BloomTimerService());

		return ksession;
	}
}
