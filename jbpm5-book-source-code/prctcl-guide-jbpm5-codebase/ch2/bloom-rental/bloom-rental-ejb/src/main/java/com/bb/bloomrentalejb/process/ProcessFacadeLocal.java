package com.bb.bloomrentalejb.process;

import javax.ejb.Local;

@Local
public interface ProcessFacadeLocal {

	public void startProcess(String processId);
}
