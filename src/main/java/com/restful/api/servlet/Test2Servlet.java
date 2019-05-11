package com.restful.api.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;

@WebServlet("/GW1")
public class Test2Servlet extends BaseRestServlet {

	@Override
	void getUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Map<String, Object> doAction(Map<String, String> userInput, Map<String, String> decodeUserInput) {
		System.out.println(" GW 1 Start ." );
		for( String key : userInput.keySet())
		{
			System.out.println("key " + key + " value " + userInput.get(key));
		}
		
		System.out.println("-----------------------------");
		
		for( String key : decodeUserInput.keySet())
		{
			System.out.println("key " + key + " value " + decodeUserInput.get(key));
		}
		
		return null;
	}

}
