/******************************************************************************
 * Product: iDempiere - OSGi sub-project of ADempiere 			              *
 * Copyright (C) ALL GPL FOSS PROJECTS where taken				              *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package TestMQ;

import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
public class QueueTestReceive {

	/**
	 * Use for Simple self test of ActiveMQ for POS integration.
	 * @author http://activemq.apache.org/
	 * @contributor Redhuan D. Oon http://www.red1.org/adempiere
	 * @param set to jenkins.idempiere.com test service
	 * @throws Exception for ActiveMQ Stomp connection
	 * commented lines are for not ACKnowledging the queue so it remains
	 *  for further testing of the same queue.
	 */ 
	 
	public static void main(String[] args) throws Exception {
 		StompConnection connection = new StompConnection();
		connection.open("jenkins.idempiere.com",61613);
		connection.connect("","");
		connection.subscribe("/queue/Products", Subscribe.AckModeValues.CLIENT);
		connection.begin("tx1");

		StompFrame message = connection.receive();
		System.out.println(message.getBody());
//	 	connection.ack(message, "tx1");
		connection.commit("tx1");	
		
		connection.subscribe("/queue/Customers", Subscribe.AckModeValues.CLIENT);
		connection.begin("tx2");
		message = connection.receive();
		System.out.println(message.getBody());
//		connection.ack(message, "tx2");
		connection.commit("tx2");
		connection.disconnect();

		}
	
  
}
