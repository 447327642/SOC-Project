package com.mkyong.client;

import com.mkyong.ws.Transform;
import com.mkyong.ws.TransformImplService;
import net.java.dev.jaxb.array.*;
public class TransformClinet{
	
	public static void main(String[] args) {
	   
		TransformImplService helloService = new TransformImplService();
		Transform hello = helloService.getTransformImplPort();
		
		StringArray test = new StringArray();
		
		hello.transform(test);
		
    }

}