package Fare;

import Entities.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marion on 9/10/2014.
 */
public class AirFare {

 public static   Float calculate(String arrival, String departure) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        String address = "http://localhost:8080/exist/rest/db/simplefares?_query=string(//rule" +
                "[antecedent[contains(string(@to),'"+arrival+"')%20and%20contains(string(@from),'"+ departure +"')]]/consequent/string(@fare))";
        URL eXistRestURL = new URL(address);
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
        System.out.println((String)xpath.evaluate("//exist/node()",xmldoc, XPathConstants.STRING));
        // query out the zone...
    String temp =    (String)xpath.evaluate( "//exist:value/text()", xmldoc, XPathConstants.STRING);

return Float.parseFloat(temp);
            }


}
