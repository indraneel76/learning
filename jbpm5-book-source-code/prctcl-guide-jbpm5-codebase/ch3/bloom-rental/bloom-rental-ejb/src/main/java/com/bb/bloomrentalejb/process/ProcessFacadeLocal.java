package com.bb.bloomrentalejb.process;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface ProcessFacadeLocal {

	public void startProcess(String processId, Map<String, Object> processData);

}
