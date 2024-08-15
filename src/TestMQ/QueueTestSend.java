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

import java.util.HashMap;
import org.apache.activemq.transport.stomp.StompConnection;

/**
 * Use for Simple self test of ActiveMQ for POS integration.
 * @author Redhuan D. Oon http://www.red1.org/adempiere
 * @param set to jenkins.idempiere.com test service
 * @throws Exception for ActiveMQ Stomp connection
 * commented lines are for not ACKnowledging the queue so it remains
 *  for further testing of the same queue.
 */ 

public class QueueTestSend {
	private static final String PERSISTENT = "persistent";

	/**
	 * Use for Simple self test of ActiveMQ for POS integration.
	 * @author http://activemq.apache.org/
	 * @contributor Redhuan D. Oon http://www.red1.org/adempiere
	 * @param set to jenkins.idempiere.com test service
	 * @throws Exception for ActiveMQ Stomp connection
	 * Test sending has actual sample XML strings preset to live jenkins.idempiere.com service
	 */ 
	
	public static void main(String[] args) throws Exception {
		StompConnection connection = new StompConnection();
		connection.open("localhost",61613);
		connection.connect("","");
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(PERSISTENT, "true");
		connection.begin("tx1");
		connection.send("/queue/Products","<?xml version=\"1.0\" ?><entityDetail><type>openbravoPOS</type><detail><DocType>M_Product</DocType><ProductName>Hoe</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>138</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>15</PriceList><PriceLimit>12</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Rake-Bamboo</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>139</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>9</PriceList><PriceLimit>7.2</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Rake-Metal</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>140</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>12</PriceList><PriceLimit>9.6</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Weeder</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>141</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>3</PriceList><PriceLimit>2.4</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Transplanter</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>142</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>3.5</PriceList><PriceLimit>2.8</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Seeder</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>143</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>30</PriceList><PriceLimit>24</PriceLimit></detail><detail><DocType>M_Product</DocType><ProductName>Tiller</ProductName><M_Product_Category_ID>107</M_Product_Category_ID><CategoryName>Tools</CategoryName><M_Product_ID>144</M_Product_ID><C_TaxCategory_ID>107</C_TaxCategory_ID><TaxName>Standard</TaxName><UPC></UPC><PriceList>35</PriceList><PriceLimit>28</PriceLimit></detail></entityDetail>", "tx1", hm);
		connection.send("/queue/Customers", "<?xml version=\"1.0\" ?><entityDetail><type>openbravoPOS</type><detail><DocType>C_BPartner</DocType><CustomerName>Joe Block</CustomerName><Value>JoeBlock</Value><Description></Description><C_BPartner_ID>118</C_BPartner_ID></detail><detail><DocType>C_BPartner</DocType><CustomerName>C&amp;W Construction</CustomerName><Value>C&amp;W</Value><Description></Description><C_BPartner_ID>117</C_BPartner_ID></detail><detail><DocType>C_BPartner</DocType><CustomerName>Standard</CustomerName><Value>Standard</Value><Description>Walk-In Customer</Description><C_BPartner_ID>112</C_BPartner_ID></detail></entityDetail>", "tx1", hm);
		connection.commit("tx1");				
		connection.disconnect();
		}

}
