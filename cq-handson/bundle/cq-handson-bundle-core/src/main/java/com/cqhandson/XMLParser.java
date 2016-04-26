package com.cqhandson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {
   
   
   public String domParserver(){
	   String displayData="";
	   try {	
	         File inputFile = new File("employee.xml");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         displayData= displayData + "Root element :" 
	            + doc.getDocumentElement().getNodeName()+ "\n";
	         NodeList nList = doc.getElementsByTagName("employee");
	         displayData= displayData + "----------------------------";
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            displayData= displayData + "\nCurrent Element :"  + "\n" 
	               + nNode.getNodeName();
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               displayData= displayData + "Student roll no : " 
	                  + eElement.getAttribute("rollno")  + "\n";
	               displayData= displayData +"First Name : " 
	                  + eElement
	                  .getElementsByTagName("firstname")
	                  .item(0)
	                  .getTextContent() + "\n";
	               displayData= displayData +"Last Name : " 
	               + eElement
	                  .getElementsByTagName("lastname")
	                  .item(0)
	                  .getTextContent() + "\n";
	               displayData= displayData +"Nick Name : " 
	               + eElement
	                  .getElementsByTagName("nickname")
	                  .item(0)
	                  .getTextContent() + "\n";
	               displayData= displayData +"Experience : " 
	               + eElement
	                  .getElementsByTagName("experience")
	                  .item(0)
	                  .getTextContent() + "\n";
	            }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   return displayData;
}
   public String SAXParser(){
	   String displaySaxData="";
	   try {
		   
	   File inputFile = new File("employee.xml");
	   SAXParserFactory factory = SAXParserFactory.newInstance();
	   SAXParser saxParser = factory.newSAXParser();
	   UserHandler userhandler = new UserHandler();
	   saxParser.parse(inputFile, userhandler);
	   displaySaxData= userhandler.returnData();
   	   } catch (Exception e) {
       e.printStackTrace();
       }
	   return displaySaxData;
   }
     
   	
   @SuppressWarnings("restriction")
public String STAXParser(){
	   boolean bFirstName = false;
	   boolean bLastName = false;
	   boolean bNickName = false;
	   boolean bExp = false;
	   String displayStaxData="";
	   
	   try{
	   XMLInputFactory factory = XMLInputFactory.newInstance();
       XMLEventReader eventReader =
       factory.createXMLEventReader(
          new FileReader("employee.xml"));

          while(eventReader.hasNext()){
             XMLEvent event = eventReader.nextEvent();
             switch(event.getEventType()){
                case XMLStreamConstants.START_ELEMENT:
                   StartElement startElement = event.asStartElement();
                   String qName = startElement.getName().getLocalPart();
                   if (qName.equalsIgnoreCase("student")) {
                	   displayStaxData = displayStaxData + ("Start Element : Employee") + "\n";
                      Iterator<Attribute> attributes = startElement.getAttributes();
                      String empid = attributes.next().getValue();
                      displayStaxData = displayStaxData +("Roll No : " + empid) + "\n";
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
                   break;
                case XMLStreamConstants.CHARACTERS:
                   Characters characters = event.asCharacters();
                   if(bFirstName){
                	   displayStaxData = displayStaxData +("First Name: " 
                      + characters.getData()) + "\n";
                      bFirstName = false;
                   }
                   if(bLastName){
                	   displayStaxData = displayStaxData +("Last Name: " 
                      + characters.getData()) + "\n";
                      bLastName = false;
                   }
                   if(bNickName){
                	   displayStaxData = displayStaxData +("Nick Name: " 
                      + characters.getData()) + "\n";
                      bNickName = false;
                   }
                   if(bExp){
                	   displayStaxData = displayStaxData +("Experience: " 
                      + characters.getData()) + "\n";
                      bExp = false;
                   }
                   break;
                case  XMLStreamConstants.END_ELEMENT:
                   EndElement endElement = event.asEndElement();
                   if(endElement.getName().getLocalPart().equalsIgnoreCase("employee")){
                	   displayStaxData = displayStaxData +("End Element : Employee") + "\n";
                      
                   }
                   break;
             }		    
          }
       } catch (Exception e) {
    	   e.printStackTrace();
       } 
	   return displayStaxData;
   }
   
}
