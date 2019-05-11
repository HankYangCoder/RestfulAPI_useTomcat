package com.restful.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class HttpUtil 
{
	private final static Logger log = Logger.getLogger(HttpUtil.class.getName());
	public static String ENCODING = "UTF-8";
	
	public HttpUtil()
	{
		boolean falg =  true ;
//		System.out.println(falg);
		if( falg )
			HttpUtil.disableSslVerification() ;
	}
	
	public static void disableSslVerification() {
		System.out.println(" SSL Verify All True Mode Open");
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        
	        SSLContext.setDefault(sc);
	        
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}	
	
	public static String httpGet(String url)
	{
		String ret = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try
		{
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() 
			{
	            @Override
	            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException 
	            {
	                int status = response.getStatusLine().getStatusCode();
	                log.debug("http status=" + status);
	                if (status >= 200 && status < 300) 
	                {
	                    HttpEntity entity = response.getEntity();
	                    return entity != null ? EntityUtils.toString(entity) : null;
	                } 
	                else 
	                {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }
	        };
	        ret = httpclient.execute(httpget, responseHandler);
	        log.debug("----------------------------------------");
	        log.debug(ret);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("httpGet exception=", e);
		}
		finally
		{
			try 
			{
				httpclient.close();
			} 
			catch (IOException e) 
			{
			}
		}
		return ret;
	}
	
	/**
     * 另外產生經過  URLDecoder.decode 的 decodedUserInput
    */
    public static Map<String, String> decodeMap(Map<String, String>  userInput)
    {
    	Map<String, String>  decodedUserInput = new HashMap<String, String>();
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
    	return decodedUserInput;
    }


    /**
     * 一般正常的 https get 功能
     * @param httpsUrl
     * @return
     */
    public static String httpsGet(String httpsUrl)
    {
    	String ret = null;
    	URL url = null;
    	HttpsURLConnection con = null;
    	InputStream ins = null;
    	InputStreamReader isr = null;
    	BufferedReader in = null;
    	
    	try
    	{
            url = new URL(httpsUrl);
            con = (HttpsURLConnection)url.openConnection();
            ins = con.getInputStream();
            isr = new InputStreamReader(ins);
            in = new BufferedReader(isr);
            String inputLine;

            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
            {
            	System.out.println(inputLine);
            	sb.append(inputLine);              
            }

            ret = sb.toString();
            
            in.close();    		
    	}
    	catch(Exception e)
    	{
    		
    	}
    	finally
    	{
    		if (in != null)
    		{
    			try
    			{
    				in.close();
    			}
    			catch(Exception e)
    			{
    				
    			}
    		}
    	} //finally
    	
    	return ret;
    }  //httpsGet
	
	static TrustManager[] getDummyTrustManagers()
	{
		TrustManager[] trustAllCerts = new TrustManager[] 
		{ 
				new X509TrustManager()
				{
					public java.security.cert.X509Certificate[] getAcceptedIssuers()
					{
						return null;
					}
					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
					{
					}
					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
					{
					}
				}
		};
		return trustAllCerts;
	}
    
	/**
	 * https Get Method 
	 * @param inputUrl
	 * @return
	 */
	public static String httpsGetX(String inputUrl)
	{
		return httpsCall(inputUrl, null, false, null);
	}
	
	/**
	 * https Post Method
	 * @param inputUrl
	 * @param postData
	 * @return
	 */
	public static String httpsPostX(String inputUrl, String postData)
	{
		return httpsCall(inputUrl, null, true, postData);		
	}
	
	/**
	 * http Post Method 
	 * @param inputUrl
	 * @param postData
	 * @return
	 */
	public static String httpPost(String inputUrl , String postData)
	{
		return httpCall(inputUrl, null, true, postData);	
	}
	
	/**
	 * http Get Method 
	 * @param inputUrl
	 * @param postData
	 * @return
	 */
	public static String httpGet(String inputUrl , String postData)
	{
		return httpCall(inputUrl, null, false, postData);	
	}
	
	/**
	 * 無條件信任 https 的方式
	 * @param inputUrl
	 * @param alg
	 * @return
	 */
	public static String httpsCall(String inputUrl, String alg, boolean isPost, String postData)
	{
		String ret = null;
		SSLContext sc = null;
		HttpsURLConnection urlConn = null;
		URL url = null; 
    	InputStream ins = null;
    	InputStreamReader isr = null;
    	BufferedReader in = null;
		
		if (StrUtil.isEmpty(alg))
		{
			alg = "TLS";
		}
		
		try
		{
			url = new URL(inputUrl);
			sc = SSLContext.getInstance("TLS");
			//設定空的 SSL key manager 及 trust manager
			//信任所有 server 與 憑證
			sc.init(null, getDummyTrustManagers(), new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			urlConn = (HttpsURLConnection) url.openConnection();

			//trust all host
            HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
            };
            urlConn.setHostnameVerifier(allHostsValid);            

            urlConn.setDoInput(true); 
            urlConn.setInstanceFollowRedirects(true);
			
			if (isPost)
			{
	            urlConn.setRequestMethod("POST"); 
	            urlConn.setRequestProperty("User-Agent", "GoService");
	            urlConn.setRequestProperty("Connection", "Keep-Alive");
	            urlConn.setRequestProperty("Host", url.getHost());
	            urlConn.setRequestProperty("Content-Length", String.valueOf(postData.getBytes(ENCODING).length));
	            urlConn.setDoOutput(true); 
	        	OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream(), ENCODING);
	        	out.write(postData);
	        	out.close();
			}
			
        	//當  Http URL Connection 非 200 時，會造成  IO exception
        	try
        	{
                ins = urlConn.getInputStream();
        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
        	catch(Exception e)
        	{
        		//取得錯誤訊息
        		log.debug("== GET ERROR STREAM ==");
        		log.debug("ContentLength=" + urlConn.getContentLength());
        		log.debug("ResponseCode=" + urlConn.getResponseCode());
        		log.debug("ResponseMessage=" + urlConn.getResponseMessage());
                ins = urlConn.getErrorStream();
        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
			
            in = new BufferedReader(isr);
            String inputLine;

            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
            {
//            	System.out.println(inputLine);
            	sb.append(inputLine);              
            }

            ret = sb.toString();
            
            in.close();    		

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
		
		return ret;
	}
	
	/**
	 * 無條件信任 https 的方式
	 * @param inputUrl
	 * @param alg
	 * @return
	 */
	public static String httpCall(String inputUrl, String alg, boolean isPost, String postData)
	{
		String ret = null;
		SSLContext sc = null;
//		HttpsURLConnection urlConn = null;
		HttpURLConnection urlConn = null ;
		URL url = null; 
    	InputStream ins = null;
    	InputStreamReader isr = null;
    	BufferedReader in = null;
		
//		if (StrUtil.isEmpty(alg))
//		{
//			alg = "TLS";
//		}
		
		try
		{
			url = new URL(inputUrl);
//			sc = SSLContext.getInstance("TLS");
			//設定空的 SSL key manager 及 trust manager
			//信任所有 server 與 憑證
//			sc.init(null, getDummyTrustManagers(), new java.security.SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			urlConn =  (HttpURLConnection) url.openConnection();

			//trust all host
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//				@Override
//				public boolean verify(String arg0, SSLSession arg1) {
//					return true;
//				}
//            };
//            urlConn.setHostnameVerifier(allHostsValid);            

            urlConn.setDoInput(true); 
            urlConn.setInstanceFollowRedirects(true);
			
			if (isPost)
			{
	            urlConn.setRequestMethod("POST"); 
	            urlConn.setRequestProperty("User-Agent", "GoService");
	            urlConn.setRequestProperty("Connection", "Keep-Alive");
	            urlConn.setRequestProperty("Host", url.getHost());
	            urlConn.setRequestProperty("Content-Length", String.valueOf(postData.getBytes(ENCODING).length));
	            urlConn.setDoOutput(true); 
	        	OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream(), ENCODING);
	        	out.write(postData);
	        	out.close();
			}
			
        	//當  Http URL Connection 非 200 時，會造成  IO exception
        	try
        	{
                ins = urlConn.getInputStream();
        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
        	catch(Exception e)
        	{
        		//取得錯誤訊息
        		log.debug("== GET ERROR STREAM ==");
        		log.debug("ContentLength=" + urlConn.getContentLength());
        		log.debug("ResponseCode=" + urlConn.getResponseCode());
        		log.debug("ResponseMessage=" + urlConn.getResponseMessage());
//                ins = urlConn.getErrorStream();
//        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
			
        	if( isr!=null )
        	{
	            in = new BufferedReader(isr);
	            String inputLine;
	
	            StringBuffer sb = new StringBuffer();
	            while ((inputLine = in.readLine()) != null)
	            {
	//            	System.out.println(inputLine);
	            	sb.append(inputLine);              
	            }
	
	            ret = sb.toString();
	            
	            in.close();    		
        	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
		
		return ret;
	}
	
	/**
	 * 無條件信任 https 的方式
	 * @param inputUrl
	 * @param alg
	 * @return
	 */
	public static String httpCallTest(String inputUrl, String alg, boolean isPost, String postData)
	{
		String ret = null;
		SSLContext sc = null;
//		HttpsURLConnection urlConn = null;
		HttpURLConnection urlConn = null ;
		URL url = null; 
    	InputStream ins = null;
    	InputStreamReader isr = null;
    	BufferedReader in = null;
		
//		if (StrUtil.isEmpty(alg))
//		{
//			alg = "TLS";
//		}
		
		try
		{
			url = new URL(inputUrl);
//			sc = SSLContext.getInstance("TLS");
			//設定空的 SSL key manager 及 trust manager
			//信任所有 server 與 憑證
//			sc.init(null, getDummyTrustManagers(), new java.security.SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			urlConn =  (HttpURLConnection) url.openConnection();

			//trust all host
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//				@Override
//				public boolean verify(String arg0, SSLSession arg1) {
//					return true;
//				}
//            };
//            urlConn.setHostnameVerifier(allHostsValid);            

            urlConn.setDoInput(true); 
            urlConn.setInstanceFollowRedirects(true);
			
			if (isPost)
			{
	            urlConn.setRequestMethod("POST"); 
	            urlConn.setRequestProperty("User-Agent", "GoService");
	            urlConn.setRequestProperty("Connection", "Keep-Alive");
	            urlConn.setRequestProperty("Host", url.getHost());
	            urlConn.setRequestProperty("Content-Length", String.valueOf(postData.getBytes(ENCODING).length));
	            urlConn.setDoOutput(true); 
	        	OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream(), ENCODING);
	        	out.write(postData);
	        	out.close();
			}
			
        	//當  Http URL Connection 非 200 時，會造成  IO exception
        	try
        	{
                ins = urlConn.getInputStream();
        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
        	catch(Exception e)
        	{
        		//取得錯誤訊息
        		log.debug("== GET ERROR STREAM ==");
        		log.debug("ContentLength=" + urlConn.getContentLength());
        		log.debug("ResponseCode=" + urlConn.getResponseCode());
        		log.debug("ResponseMessage=" + urlConn.getResponseMessage());
                ins = urlConn.getErrorStream();
        		isr = new java.io.InputStreamReader(ins, ENCODING);
        	}
        	
        	ret = urlConn.getResponseMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
		
		return ret;
	}
    
    
	public static void main(String [] args)
	{
		//String s = HttpUtil.httpGet("http://weather.yahooapis.com/forecastrss?p=80020&u=f");
		//String s = HttpUtil.httpGet("http://public.opencpu.org/ocpu/library/");
		String 	httpsUrl ="";
		String c = "" ;
//		String httpsUrl = "https://www.google.com.tw/?gws_rd=ssl";
//		String c = HttpUtil.httpsGet(httpsUrl);
//		System.out.println(c);
		/*
		httpsUrl = "http://127.0.0.1:8080/AuthService/rest/v01/accountFunc/Test/";
		c = HttpUtil.httpsGetX(httpsUrl);
		System.out.println("GetTest "+c);
		c = HttpUtil.httpGet(httpsUrl);
		System.out.println("GetTest "+c);
		
//		httpsUrl = "https://login.fusheng.com/AuthService/rest/v01/accountFunc/PostTest/token/cid/sec/";
		httpsUrl = "http://127.0.0.1:8080/AuthService/rest/v01/accountFunc/PostTest";
		Map<String,String> m = new HashMap<String,String>();
		m.put("token", "token");
		m.put("createID", "ID");
		m.put("sec", "sec");
		*/
//		httpsUrl = "http://127.0.0.1:8080//AuthService/rest/v01/accountFunc/PostTest/ccfd35b6-cf6d-48a4-8403-35ed98efa8a4/andy_cr/"+WebSecCommonUtil.genWebToken();
//		httpsUrl = "https://172.17.0.25/AuthService/rest/v01/accountFunc/QueryCompany/a31653f1-2f78-49df-86bd-1364d345568c/andy_cr/"+WebSecCommonUtil.genWebToken();
//		httpsUrl = "http://127.0.0.1:8080/AuthService/rest/v01/QueryAccount" ;
//		Map<String,String> m = new HashMap<String,String>();
//		m.put("company_id", "20871948") ;
//		m.put("account_id", "andy_cr");
//		m.put("system_id", "1");
		
//		String a = "createData="+  new Gson().toJson(m );
//		httpsUrl += a ;
//		System.out.println(">>>" + a );
		
//		System.out.println(httpsUrl);
		
//		c = HttpUtil.httpCall(httpsUrl, "", true, a );
//		String postParam = "createData="+  new Gson().toJson(m );
//		c = HttpUtil.httpsPostX(httpsUrl , postParam);
//		System.out.println("post "+c);
//		System.out.println("Post "+StrUtil.gsonStringToHashMap(c));
//		String retData = "" ;
//		String wsUrl = ulbo.getSSOUrl(Constants.SSO_WS_NAME);
//		Map<String,Object> ssoParam = spBo.findSSOParam(Constants.SSO_WS_NAME) ;
		
//		System.out.println(">><<<>>");
		
//		disableSslVerification();
		
//		IAuthServiceProxy svc = new IAuthServiceProxy();
//		svc.setEndpoint("https://172.17.0.25/AuthService/ws/sso");
//		svc.setEndpoint("https://login.fusheng.com/AuthService");
//		System.out.println(">>> url "+wsUrl);
//		try {
//			retData = svc.verifySSOTokenInValid("ff804f86-32ef-4944-9dde-45e5fffa8cd1");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		System.out.println(">>>>"+retData);
//		
//		System.out.println(">><<e<>>");
		
		/*
		//Dynamic web service call
	    String url = "https://172.17.0.25/AuthService/ws/sso?wsdl"; //"https://172.17.0.69/DWI/GS_WebService.asmx?WSDL"
	    String nameSpace = "http://sso.fusheng.com/"; //"http://tempuri.org/";
	    String serviceName = "AuthServiceService"; //"GS_WebService";
	    String portName = "AuthServicePort"; //"GS_WebServiceSoap";
	    URL wsdlURL = null;
		try {
			wsdlURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    QName serviceQName = new QName(nameSpace, serviceName);
	    QName portQName = new QName(nameSpace, portName);
	    Service service = Service.create(wsdlURL, serviceQName);
	    AuthServiceService ts = service.getPort(portQName ,AuthServiceService.class );
	    //GSWebServiceSoap port = 
	    //  (GSWebServiceSoap) service.getPort(portQName, GSWebServiceSoap.class);
	    
	    //String s = port.getDevice("DS4160075A").getMODEL();
	    //System.out.println(s);
		//System.setProperty("javax.net.ssl.trustStore",	"src/main/resources/myKeystore.jks");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	    
	    */
	    
		
		
		
//		com.fusheng.goservice.ws.gs.AuthServiceService svcws;
//		try {
//			svcws = new AuthServiceService(new URL("https://172.17.0.25/AuthService/ws/sso?wsdl"));
//			
//			com.fusheng.goservice.ws.gs.IAuthService soap = svcws.getAuthServicePort();
//			
//			retData = soap.verifySSOTokenInValid("010e23b7-b29a-42e9-9256-8cd4f65931e1");
//			System.out.println(">>> >>> "+retData);
//			
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		/*
		StringBuilder tStr = new StringBuilder();
		String tempStr = "20170101"; 
		tStr.append(tempStr.substring(0 , 4));
		tStr.append("-");
		tStr.append(tempStr.substring(4 , 6));
		tStr.append("-");
		tStr.append(tempStr.substring(6));
		String activeTime = tStr.toString() ;
		System.out.println(activeTime);
		*/
	}
	
	
}
