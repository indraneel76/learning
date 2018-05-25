package com.bb.bloomrentalweb.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.bloomrentaldomain.RentalAdmin;
import com.bb.bloomrentaldomain.RentalApp;
import com.bb.bloomrentalejb.process.ProcessFacadeLocal;

public class AppProcessor {
	private static Logger log = LoggerFactory.getLogger(AppProcessor.class);

	public void processApp(HttpServletRequest request) {
		try {
			Context context = new InitialContext();
			ProcessFacadeLocal processFacade = (ProcessFacadeLocal) context
					.lookup("java:module/ProcessFacade");
			RentalApp app = buildProcessData(request);
			Map<String, Object> processData = new HashMap<String, Object>();
			processData.put("app", app);
			processData.put("admin", buildRentalAdmin());
			List<Object> facts = new ArrayList<Object>();
			facts.add(app);
			processFacade.startProcess("com.bb.bloomrentalapp", processData,
					facts);
			log.info("Credit Score:" + app.getCreditScore());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RentalApp buildProcessData(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String ssn = request.getParameter("ssn");
		RentalApp app = new RentalApp();
		app.setFirstName(firstName);
		app.setLastName(lastName);
		app.setSsn(ssn);
		return app;
	}

	private RentalAdmin buildRentalAdmin() {
		RentalAdmin admin = new RentalAdmin();
		admin.setReviewUserid("Reviewer");
		admin.setSuperUserid("Super");
		return admin;
	}

}
