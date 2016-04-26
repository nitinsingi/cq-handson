package com.cqhandson;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {

			   boolean bFirstName = false;
		   boolean bLastName = false;
		   boolean bNickName = false;
		   boolean bExp = false;
		   String getEmployeeData="";

		   @Override
		   public void startElement(String uri, 
		      String localName, String qName, Attributes attributes)
		         throws SAXException {
		      if (qName.equalsIgnoreCase("employee")) {
		         String empid = attributes.getValue("empid");
		         getEmployeeData = getEmployeeData + ("Emp No : " + empid)+ "\n";
		      } else if (qName.equalsIgnoreCase("firstname")) {
		         bFirstName = true;
		      } else if (qName.equalsIgnoreCase("lastname")) {
		         bLastName = true;
		      } else if (qName.equalsIgnoreCase("nickname")) {
		         bNickName = true;
		      }
		      else if (qName.equalsIgnoreCase("experience")) {
		    	  bExp = true;
		      }
		   }

		   @Override
		   public void endElement(String uri, 
		      String localName, String qName) throws SAXException {
		      if (qName.equalsIgnoreCase("employee")) {
		    	  getEmployeeData = getEmployeeData + ("End Element :" + qName)+ "\n";
		      }
		   }

		   @Override
		   public void characters(char ch[], 
		      int start, int length) throws SAXException {
		      if (bFirstName) {
		    	  getEmployeeData = getEmployeeData + ("First Name: " 
		         + new String(ch, start, length))+ "\n";
		         bFirstName = false;
		      } else if (bLastName) {
		    	  getEmployeeData = getEmployeeData + ("Last Name: " 
		         + new String(ch, start, length))+ "\n";
		         bLastName = false;
		      } else if (bNickName) {
		    	  getEmployeeData = getEmployeeData + ("Nick Name: " 
		         + new String(ch, start, length))+ "\n";
		         bNickName = false;
		      } else if (bExp) {
		    	  getEmployeeData = getEmployeeData + ("Experience: " 
		         + new String(ch, start, length)) + "\n";
		         bExp = false;
		      }
		   }
		   
		   public String returnData(){
			   return getEmployeeData;
		   }
}

