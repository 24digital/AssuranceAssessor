package Fare;

import java.io.*;
import java.net.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.*;

import Entities.Test;
import Entities.Zones;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class State {
private String state;
    public State(String state) {
        try {
            this.state = state;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Zones toZones() throws Exception
	{
		Zones zones = new Zones();
		zones.setZone(this.convertStateToZone(this.state));
				return zones;
	}

    public boolean stateExist(String statecode) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String serviceAddress = "http://localhost:8080/exist/rest/db/simplefares"+
                "?query=string(//zone[state[@code='"+statecode+"']]";

        URL existREstURL = new URL(serviceAddress);

        HttpURLConnection connection = (HttpURLConnection)existREstURL.openConnection();

        connection.setRequestMethod( "GET" );

        connection.connect();

        // grab the input stream associated with the content
        InputStream in = connection.getInputStream();

        StringBuffer sb = new StringBuffer();

        // establish an inputStreamReader and tell it the data is in UTF-8
        Reader reader = new InputStreamReader(in, "UTF-8");
        int c;

        //read a character at a time, appending into the StringBuffer
        while ((c = reader.read()) != -1) sb.append((char) c);

        connection.disconnect();

        // convert the returned data into a DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder parser = factory.newDocumentBuilder();

        InputSource inputSource = new InputSource( new StringReader( sb.toString() ) );
        Document xmldoc = parser.parse( inputSource );

        XPath xpath = XPathFactory.newInstance().newXPath();
        String namespaceURI = "http://exist.sourceforge.net/NS/exist";
        xpath.setNamespaceContext( new Test( "exist", namespaceURI ) );



        if((String)xpath.evaluate("//exist:value/text()",xmldoc,XPathConstants.STRING) == " ")
        {
            return false;
        }
        return true;

    }
	public String convertStateToZone( String stateCode ) throws Exception
	{
		String zone = null;
		
		stateCode = stateCode.toUpperCase();

       if(!stateExist(stateCode)){
           return "Invalid State Code";
       }

		String serviceAddress = "http://localhost:8080/exist/rest/db/simplefares" +
			"?_query=string(//zone[state[@code='" + stateCode + "']]/@id)"; 
		URL eXistRestURL = new URL(serviceAddress);
		HttpURLConnection connection = (HttpURLConnection)eXistRestURL.openConnection();
		
		connection.setRequestMethod( "GET" );
		
		connection.connect();
		
		// grab the input stream associated with the content
		InputStream in = connection.getInputStream();
		
		StringBuffer sb = new StringBuffer();
		
		// establish an inputStreamReader and tell it the data is in UTF-8
	    Reader reader = new InputStreamReader(in, "UTF-8");
	    int c;
	    
	    //read a character at a time, appending into the StringBuffer
	    while ((c = reader.read()) != -1) sb.append((char) c);
	    
		connection.disconnect();

	    // convert the returned data into a DOM
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder parser = factory.newDocumentBuilder();

		InputSource inputSource = new InputSource( new StringReader( sb.toString() ) );
	    Document xmldoc = parser.parse( inputSource ); 
	    
		XPath xpath = XPathFactory.newInstance().newXPath(); 
		String namespaceURI = "http://exist.sourceforge.net/NS/exist";
		xpath.setNamespaceContext( new Test( "exist", namespaceURI ) );
        System.out.println((String)xpath.evaluate("//exist/node()",xmldoc,XPathConstants.STRING));
	    // query out the zone...
	    zone = (String)xpath.evaluate( "//exist:value/text()", xmldoc, XPathConstants.STRING);
		
	    return zone;
	}


}
