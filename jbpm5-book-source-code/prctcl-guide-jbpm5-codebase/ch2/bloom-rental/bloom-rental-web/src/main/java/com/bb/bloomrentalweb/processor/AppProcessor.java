package com.bb.bloomrentalweb.processor;

import javax.naming.Context;
import javax.naming.InitialContext;
import com.bb.bloomrentalejb.process.ProcessFacadeLocal;

public class AppProcessor {
	public void processApp() {
		try {
			Context context = new InitialContext();
			ProcessFacadeLocal processFacade = (ProcessFacadeLocal)

			context.lookup("java:module/ProcessFacade");
			processFacade.startProcess("com.bb.helloworld");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
