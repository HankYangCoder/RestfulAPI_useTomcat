package com.restful.api.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public abstract class BaseRestServlet extends CommonServlet 
{
	static Gson gson ; 
	
	public BaseRestServlet()
	{
		if( gson == null )
			gson = new Gson() ;
	}
	
	abstract void getUser();
	
	abstract Map<String,Object> doAction(Map<String,String> userInput , Map<String,String> decodeUserInput );
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String ret = "" ;
		Map<String,Object> retMap = new HashMap<String,Object>();
		// 呼叫父類別的方式
    	super.doPost(request, response);
    	
    	retMap = doAction(userInput , decodedUserInput);
		
		ret = gson.toJson(retMap);
		response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
		response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
    	PrintWriter out = response.getWriter();
		out.print(ret);  //請勿使用 println ，以防 client 端異常
		out.close();
	}
}
