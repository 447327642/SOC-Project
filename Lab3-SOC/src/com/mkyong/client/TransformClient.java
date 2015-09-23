package com.mkyong.client;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import com.mkyong.ws.Transform;

public class TransformClient{
	
	public static void main(String[] args) throws Exception {
	   
	URL url = new URL("http://localhost:9999/ws/transform?wsdl");
	
        QName qname = new QName("http://ws.mkyong.com/", "TransformImplService");

        Service service = Service.create(url, qname);

        Transform hello = service.getPort(Transform.class);
        
        
        hello.transform(args);

    }

}