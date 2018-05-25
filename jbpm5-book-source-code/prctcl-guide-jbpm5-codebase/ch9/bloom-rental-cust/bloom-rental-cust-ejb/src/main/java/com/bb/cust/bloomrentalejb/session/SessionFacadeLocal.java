package com.bb.cust.bloomrentalejb.session;

import javax.ejb.Local;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;

@Local
public interface SessionFacadeLocal {
	public StatefulKnowledgeSession createSession(KnowledgeBase kbase);

	public StatefulKnowledgeSession loadSession(KnowledgeBase kbase,
			int sessionId);
}
