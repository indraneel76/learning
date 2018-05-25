package com.bb.cust.bloomrentalejb.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jbpm.process.audit.ProcessInstanceLog;
import com.bb.cust.bloomrentaldomain.NodeAuditDetail;
import com.bb.cust.bloomrentaldomain.ProcessAuditDetail;

@Stateless
public class ConsoleFacade implements ConsoleFacadeLocal {
	@PersistenceContext(unitName = "jpa.jbpm")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessAuditDetail> getProcessAuditDetail(
			ProcessAuditDetail criteria) {
		List<ProcessAuditDetail> result = new ArrayList<ProcessAuditDetail>();
		String psql = "from ProcessInstanceLog where processId=:processName "
				+ "and start_date>=:startDate and end_date<=:endDate";
		Query query = em.createQuery(psql);
		query.setParameter("processName", criteria.getProcessName());
		query.setParameter("startDate", criteria.getStartDate());
		query.setParameter("endDate", criteria.getEndDate());
		List<ProcessInstanceLog> list = query.getResultList();
		for (ProcessInstanceLog log : list) {
			ProcessAuditDetail detail = new ProcessAuditDetail();
			detail.setProcessName(log.getProcessId());
			detail.setStartDate(log.getStart());
			detail.setEndDate(log.getEnd());
			detail.setProcessInstanceId(log.getProcessInstanceId());
			detail.setNodeAuditDetail(getNodeDetail(log.getProcessInstanceId()));
			result.add(detail);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<NodeAuditDetail> getNodeDetail(long processInstanceId) {
		List<NodeAuditDetail> result = new ArrayList<NodeAuditDetail>();
		String psql = "select nodeName, min(log_date), max(log_date) from NodeInstanceLog "
				+ "where processInstanceId= :processInstanceId "
				+ "group by processInstanceId, nodeName order by nodeInstanceId";
		Query query = em.createNativeQuery(psql);
		query.setParameter("processInstanceId", processInstanceId);
		List<Object[]> list = query.getResultList();
		for (Object[] log : list) {
			NodeAuditDetail detail = new NodeAuditDetail();
			detail.setNodeName((String) log[0]);
			detail.setStartDate((Date) log[1]);
			detail.setEndDate((Date) log[2]);
			result.add(detail);
		}
		return result;
	}
}
