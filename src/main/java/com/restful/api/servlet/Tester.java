package com.restful.api.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restful.api.util.HttpUtil;


@WebServlet("/Tester")
public class Tester extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7242764875174956652L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,String> userInput = HttpUtil.decodeMap(getParameterMap(request));
		String ret = "";
		String func = (String) userInput.get("f");
		
		if("abc".equalsIgnoreCase(func))
		{
			ret = userInput.get("output");
		}
		
		PrintWriter out = response.getWriter();
		out.print(ret);
		out.close();
	}
}
