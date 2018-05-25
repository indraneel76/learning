package com.bb.cust.bloomrentalejb.external;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.jobs.ee.ejb.EJB3InvokerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BloomTimerService implements WorkItemHandler {
	private static Logger log = LoggerFactory
			.getLogger(BloomTimerService.class);

	@SuppressWarnings("rawtypes")
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		log.info("Calling from QuartzTimerService...");

		try {

			Integer sessionId = (Integer) workItem
					.getParameter("paramSessionId");

			Scheduler scheduler = new StdSchedulerFactory().getScheduler();

			Date currTime = new Date();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(EJB3InvokerJob.EJB_JNDI_NAME_KEY,
					"java:global/bloom-rental-cust-ear-1.0-SNAPSHOT/bloom-rental-cust-ejb-1.0-SNAPSHOT/BloomQuartzJob");
			map.put(EJB3InvokerJob.EJB_METHOD_KEY, "execute");

			Map<String, Object> argMap = new HashMap<String, Object>();
			argMap.put("sessionId", sessionId);
			argMap.put("workItemId", workItem.getId());

			Object[] args = new Object[] { argMap };
			map.put(EJB3InvokerJob.EJB_ARGS_KEY, args);

			Class[] types = new Class[] { Map.class };
			map.put(EJB3InvokerJob.EJB_ARG_TYPES_KEY, types);
			JobDataMap jdmap = new JobDataMap(map);

			JobDetail job = newJob(EJB3InvokerJob.class)
					.withIdentity("bloom-job-" + currTime,
							"bloom-group-" + currTime).usingJobData(jdmap)
					.build();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 10);

			// in production, you want to set cal to 9 AM ‘tomorrow’

			Trigger trigger = newTrigger()
					.withIdentity("bloom-trigger" + currTime,
							"bloom-group" + currTime).startAt(cal.getTime())
					.build();

			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			log.error("Error while scheduling Quartz job", e);
		}
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
