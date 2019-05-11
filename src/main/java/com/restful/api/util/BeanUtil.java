package com.restful.api.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.api.po.InBoundBean;


public class BeanUtil 
{
	ObjectMapper util ;
	public BeanUtil ()
	{
		if( util == null)
			util  = new ObjectMapper();
	}
	
	public static void main ( String args[])
	{
		InBoundBean bean = new InBoundBean();
		
		Map<String,String> obj4 = new HashMap<>();
		
		obj4.put("key", "4");
		bean.setObj1("1");
		bean.setObj2(2);
		bean.setObj3(3D);
		bean.setObj4(obj4);
		
		BeanUtil ut = new BeanUtil();
		
		Map<String,Object> getMap = ut.transToMap(bean);
		Map<String,Object> ret = new HashMap();
		for( String key : getMap.keySet())
		{
			ret.put(key.toUpperCase(), getMap.get(key));
//			System.out.println(" key "+ key + " v " + ret.get(key));
		}
		
		for( String key : ret.keySet())
		{
			System.out.println(" key "+ key + " v " + ret.get(key));
		}
		
	}
	
	public static Map<String,Object> transToMap ( Object obj )
	{
		BeanUtil util = new BeanUtil();
		System.out.println(" obj " + obj );
		System.out.println(" map " + Map.class);
		Map<String,Object> getMap = util.util.convertValue(obj, Map.class);
		
		Map<String,Object> ret = new HashMap();
		
		for( String key : getMap.keySet())
		{
			ret.put(key.toUpperCase(), getMap.get(key));
		}

		return ret ;
	}
}
