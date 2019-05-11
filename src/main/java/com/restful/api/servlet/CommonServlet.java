package com.restful.api.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restful.api.util.StrUtil;


public class CommonServlet extends BaseServlet 
{
//	Session session = null;
	Map<String, String> userInput;
	Map<String, String> decodedUserInput;
	
	private static final long serialVersionUID = -1922919881366732414L;

	public CommonServlet()
	{
		super();
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	process(request, response);
    }

    
    protected void process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//    	getSession();
        response.setCharacterEncoding(ENCODING);
        userInput = getParameterMap(request);
        decodeMap();
    }
    
    /**
     * 另外產生經過  URLDecoder.decode 的 decodedUserInput
     */
    public void decodeMap()
    {
    	decodedUserInput = new HashMap<String, String>();
    	for(String key : userInput.keySet())
    	{
    		try
    		{
    			String input = userInput.get(key);
    			if (StrUtil.isEmpty(input))
    			{
    				decodedUserInput.put(key, input);
    			}
    			else
    			{
            		String s = URLDecoder.decode(input, "UTF-8");
    				decodedUserInput.put(key, s);    				
    			}
    			
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			System.out.println(e);
    		}
    	}
    }
    
    
//    public Session getSession()
//    {
//    	if (session == null)
//    	{
//    		session = SecurityUtils.getSubject().getSession();
//    	}
//    	return session;
//    }
//    
//    public void setSession(Session se)
//    {
//    	this.session = se;
//    }
    
    /**
     * 檢核使用者是否登入
     * <pre>
     * 事實上若使用者未登入，可能在 filter就被重導到登入頁。
     * </pre>
     * @return
     */
    public boolean isLogin()
    {
    	boolean ret = false;
    	
//    	Subject subject = SecurityUtils.getSubject();
    	
//    	if (!subject.isAuthenticated())
//    	{
//    		return ret;
//    	}
    	
    	//本系統目前必需要有 session
//    	Session se = subject.getSession(false);
//    	if (se == null)
//    	{
//    		return ret;
//    	}
//    	else
//    	{
//    		ret = true;
//    	}
    	
    	return ret;
    }//isLogin
	
	
}
