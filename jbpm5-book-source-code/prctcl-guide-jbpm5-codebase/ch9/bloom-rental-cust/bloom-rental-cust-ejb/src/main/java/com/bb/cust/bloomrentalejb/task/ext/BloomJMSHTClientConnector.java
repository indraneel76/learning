package com.bb.cust.bloomrentalejb.task.ext;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jbpm.task.service.BaseHandler;
import org.jbpm.task.service.Command;
import org.jbpm.task.service.TaskClientConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BloomJMSHTClientConnector implements TaskClientConnector {
	private static Logger log = LoggerFactory
			.getLogger(BloomJMSHTClientConnector.class);

	protected AtomicInteger counter;
	protected final String name;
	protected final BloomJMSHTClientHandler clientHandler;

	public BloomJMSHTClientConnector(String name,
			BloomJMSHTClientHandler clientHandler) {
		if (name == null) {
			throw new IllegalArgumentException("Name can not be null");
		}
		this.name = name;
		this.clientHandler = clientHandler;
		counter = new AtomicInteger();
	}

	@Override
	public boolean connect() {
		return true;
	}

	@Override
	public boolean connect(String address, int port) {
		return true;
	}

	@Override
	public void disconnect() throws Exception {
	}

	@Override
	public void write(Object message) {
		if (message instanceof Command) {
			try {
				sendMessage(message);
			} catch (Exception e) {
				log.error("Error while sending JMS message", e);
			}
		}
	}

	public void sendMessage(Object message) throws Exception {
		String destinationName = "queue/humanTaskQueue";
		Context ic = null;
		ConnectionFactory cf = null;
		Connection connection = null;

		try {
			ic = new InitialContext();

			cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
			Queue queue = (Queue) ic.lookup(destinationName);

			connection = cf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(queue);

			connection.start();

			ObjectMessage objMessage = session.createObjectMessage((Serializable)message);
			publisher.send(objMessage);

			log.info("Message sent to the Human Task Queue...");
		} catch (Exception e) {
			log.error("Error while sending JMS message", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public AtomicInteger getCounter() {
		return counter;
	}

	@Override
	public BaseHandler getHandler() {
		return clientHandler;
	}

}
