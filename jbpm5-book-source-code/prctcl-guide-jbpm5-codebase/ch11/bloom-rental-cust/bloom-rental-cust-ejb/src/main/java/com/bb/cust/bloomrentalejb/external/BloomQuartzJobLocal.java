package com.bb.cust.bloomrentalejb.external;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface BloomQuartzJobLocal {
	
	public void execute(Map<String, Object> map);

}
