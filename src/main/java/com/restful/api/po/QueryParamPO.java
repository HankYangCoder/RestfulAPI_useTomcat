package com.restful.api.po;

import java.util.HashMap;
import java.util.Map;

/**
 * 儲存 QueryParam 
 * @author Hank
 */
public class QueryParamPO 
{
	
	private Map<String,Object> QueryParam ;
	
	
	public QueryParamPO(){}
	
	public Map<String, Object> getQueryParam() {
		return QueryParam;
	}
	
	public Object getQueryParam(String key )
	{
		return getQueryParam().get(key);
	}
	
	public void setQueryParam( String key , String val)
	{
		if( QueryParam == null )
			QueryParam = new HashMap<String,Object>();
		
		this.QueryParam.put(key, val);
	}
	
	public boolean QueryParamIsNotNull()
	{
		boolean ret = true ; 
		
		if( QueryParam == null )
		{
			ret = false  ;
		}
		// TODO
//		else if( StrUtil.isEmpty(getValbyIP()) || StrUtil.isEmpty(getValbyMask()) || StrUtil.isEmpty(getValbyRouter()) || StrUtil.isEmpty(getValbyDNS())  )
//		{
//			ret = false ; 
//		}
		
		
		return ret ; 
	}
}	
