package com.mkyong.ws;

import javax.jws.WebService;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;
//Service Implementation
@WebService(endpointInterface = "com.mkyong.ws.Transform")
public class TransformImpl implements Transform{

	@Override
	public void transform(String[] args){
		String stylesheetPathname = "style.xsl";
		String inputPathname = "input.xml";
		String outputPathname = "output.xml";
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Source stylesheetSource = new StreamSource(new File(stylesheetPathname).getAbsolutePath());
		Transformer transformer = null;
		try {
			transformer = factory.newTransformer(stylesheetSource);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Source inputSource = new StreamSource(new File(inputPathname).getAbsoluteFile());
		Result outputResult = new StreamResult(new File(outputPathname).getAbsoluteFile());
		try {
			transformer.transform(inputSource, outputResult);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All Done !");
	}

}
