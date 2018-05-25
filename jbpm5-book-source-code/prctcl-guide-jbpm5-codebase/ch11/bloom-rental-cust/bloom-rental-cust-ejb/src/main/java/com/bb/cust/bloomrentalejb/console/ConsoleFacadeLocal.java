package com.bb.cust.bloomrentalejb.console;

import java.util.List;
import javax.ejb.Local;
import com.bb.cust.bloomrentaldomain.ProcessAuditDetail;

@Local
public interface ConsoleFacadeLocal {
	public List<ProcessAuditDetail> getProcessAuditDetail(
			ProcessAuditDetail criteria);
}
