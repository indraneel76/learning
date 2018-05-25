package com.bb.cust.bloomrentalweb.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bb.cust.bloomrentaldomain.ProcessAuditDetail;
import com.bb.cust.bloomrentalejb.console.ConsoleFacadeLocal;

public class ConsoleProcessor {
	private static Logger log = LoggerFactory.getLogger(ConsoleProcessor.class);

	public void getLog(HttpServletRequest request) {
		String role = request.getParameter("role");
		request.setAttribute("role", role);
		try {
			Context context = new InitialContext();
			ConsoleFacadeLocal consoleFacade = (ConsoleFacadeLocal) context
					.lookup("java:module/ConsoleFacade");
			ProcessAuditDetail criteria = new ProcessAuditDetail();
			criteria.setProcessName(request.getParameter("processName"));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date stDate = df.parse(request.getParameter("startDate"));
			Date endDate = df.parse(request.getParameter("endDate"));
			criteria.setStartDate(stDate);
			criteria.setEndDate(endDate);
			List<ProcessAuditDetail> list = consoleFacade
					.getProcessAuditDetail(criteria);
			request.setAttribute("processAuditList", list);
		} catch (Exception e) {
			log.error("Exception while getting tasks", e);
		}
	}
}
