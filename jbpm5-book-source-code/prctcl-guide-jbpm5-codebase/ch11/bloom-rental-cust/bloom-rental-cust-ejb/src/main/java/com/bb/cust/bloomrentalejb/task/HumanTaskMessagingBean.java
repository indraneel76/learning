package com.bb.cust.bloomrentalejb.task;

import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.ejb.ActivationConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jbpm.task.service.Command;

import com.bb.cust.bloomrentalejb.task.ext.BloomJMSHTService;

@MessageDriven(name = "HumanTaskMessagingBean", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/humanTaskQueue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
@TransactionManagement(TransactionManagementType.BEAN)
public class HumanTaskMessagingBean implements MessageListener{

	private static Logger log = LoggerFactory.getLogger(HumanTaskMessagingBean.class);
	
	@Override
	public void onMessage(Message message) {
		log.info("Message received in MDB...");
		
		Command command;
		try {
			ObjectMessage msg = (ObjectMessage)message;
			command = (Command) msg.getObject();
			BloomJMSHTService.process(command);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
