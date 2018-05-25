package com.bb.cust.bloomrentalejb.process;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface ProcessFacadeLocal {

	public void startProcess(String processId, Map<String, Object> processData,
			List<Object> facts);

}
