package com.signinner.action;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Main {

	public static void main(String[] args) throws NamingException, JMSException{
		Hashtable<Object,String> h=new Hashtable<Object, String>();
		h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		h.put(Context.PROVIDER_URL, "t3://localhost:7001");
		InitialContext ic=new InitialContext(h);
		QueueConnectionFactory fac=(QueueConnectionFactory) ic.lookup("jms/ConnectionFactory");
		Queue queue=(Queue) ic.lookup("jms/queue");
		QueueConnection conn=fac.createQueueConnection();
		QueueSession session=conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueSender send=session.createSender(queue);
		TextMessage m=session.createTextMessage("hello");
		send.send(m);
	}
}
