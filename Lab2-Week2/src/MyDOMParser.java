
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyDOMParser {

  public static ArrayList<Article> MyParser() throws Exception {
	//Get the DOM Builder Factory
	    DocumentBuilderFactory factory = 
	        DocumentBuilderFactory.newInstance();

	    //Get the DOM Builder
	    DocumentBuilder builder = factory.newDocumentBuilder();

	    //Load and Parse the XML document
	    //document contains the complete XML as a Tree.
	    Document document = 
	      builder.parse(
	    		 new FileInputStream("/Users/gongbailiang/Documents/workspace/Lab2-Week2/mydblp.xml"));
    ArrayList<Article> empList = new ArrayList<>();

    //Iterating through the nodes and extracting the data.
    NodeList nodeList = document.getDocumentElement().getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i++) {

      //We have encountered an <employee> tag.
      Node node = nodeList.item(i);
      if (node instanceof Element) {
        Article emp = new Article();
        emp.mdate = node.getAttributes().
            getNamedItem("mdate").getNodeValue();
        
        emp.key = node.getAttributes().
                getNamedItem("key").getNodeValue();
       
        NodeList childNodes = node.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
          Node cNode = childNodes.item(j);

          //Identifying the child tag of employee encountered. 
          if (cNode instanceof Element) {
            String content = cNode.getLastChild().
                getTextContent().trim();
            switch (cNode.getNodeName()) {
              case "author":
                emp.author.add(content);
                break;
              case "title":
                emp.title = content;
                break;
              case "pages":
                emp.pages = content;
                break;
              case "year":
                emp.year = content;
                break;
              case "volume":
                emp.volume = content;
                break;
              case "journal":
                emp.journal = content;
                break;
              case "number":
                  emp.number = content;
                  break;
              case "url":
                  emp.url = content;
                  break;
              case "ee":
                  emp.ee = content;
                  break;
            }
          }
        }
        empList.add(emp);
      }

    }
    
    return empList;
  }
}

