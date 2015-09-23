package com.mkyong.endpoint;

import javax.xml.ws.Endpoint;
import com.mkyong.ws.TransformImpl;

//Endpoint publisher
public class TransformPublisher{
	
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:9999/ws/transform", new TransformImpl());
    }

}