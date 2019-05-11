package com.restful.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class BaseServlet extends HttpServlet
{
	static final String ENCODING = "UTF-8";
    /**
	 * 
	 */
	private static final long serialVersionUID = 8137830935958957653L;

	String contextPath = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	getContextPath();
    }
    
    public String getContextPath()
    {
    	if (contextPath == null)
    	{
    		contextPath = getServletContext().getContextPath();
    	}
    	return contextPath;
    }
    
    Hashtable<String, String> getParameterMap(HttpServletRequest request)
    {
    	Gson gson = new Gson();
    	//將Request的參數全部放到userInput裡。
    	Hashtable<String, String> userInput = new Hashtable<String, String>();    	
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements())  
		{
			String paramName = (String) paramNames.nextElement();
			//System.out.println("paramName=" + paramName);
			//對於多選的網頁元件，例如 checkbox 需再進一步檢查多選狀態
			String[] paramValues =request.getParameterValues(paramName);
			if (paramValues.length == 1) 
			{
				//System.out.println(paramName + " " + paramValues[0]);
				
				//20150511 andy for html encodeURIComponent
				paramValues[0] = checkJsonEncode(paramValues[0]);
				
				userInput.put(paramName, paramValues[0]);
			} 
			else 
			{
				//多選元件的值以 arraylist 轉成 json 字串後放入 hashmap 中
				List<String> list = new ArrayList<String>();
				for(int i=0; i < paramValues.length; i++) 
				{
					//20150511 andy for html encodeURIComponent
					paramValues[i] = checkJsonEncode(paramValues[i]);
					
					list.add(paramValues[i]);
					//System.out.println("多選 " + paramValues[i]);
	            }
				String values = gson.toJson(list);
				userInput.put(paramName, values);
				//System.out.println(paramName + " " + values);
			}
		}		

		//andy 20160402 for post json data
		try
		{
			//由 post data 中取得的資料為 key=value，必需加以處理
			BufferedReader reader = request.getReader();
			String line;
		    while ((line = reader.readLine()) != null)
		    {
		    	String [] data = line.split("=");
		    	if (data.length > 1)
		    	{
			    	userInput.put(data[0], data[1]);		    		
		    	}
		    }
			
		}
		catch(Exception e)
		{
			
		}
		
		
		return userInput;
		
    }//getParameterMap
    
    
    public String checkJsonEncode(String s)
    {
    	//試著轉 JSON {}
    	if (s.startsWith("%7B%22") && s.endsWith("%22%7D"))
    	{
    		try 
    		{
				s = URLDecoder.decode(s, "UTF-8");
			} 
    		catch (UnsupportedEncodingException e) 
    		{
				e.printStackTrace();
			}	
    		//System.out.println("datas====" + s);
    		
    	}
    	
    	return s;
    }
    
    
}
