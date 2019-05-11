package com.restful.api.common;

public class Constants 
{
	//取至小數點後幾位
	public static final Integer CURRY_COUNT = 2 ;
	
	public static final String I18N_SUPPORT = "supportLocale" ;
	// 一定要 英文開到 後面接數字 0-9  總 長度  5~15 位 
	public static final String CUSTOMERID_REGX = "[a-zA-Z0-9][0-9]{5,15}";
//	public static final String TIMEZONE_REGX = "[+-][0-9]{5}" ;
	public static final String TIMEZONE_REGX = "[-?0-9]{4}|[-?0-9]{5}|[+?0-9]{4}|[+?0-9]{5}" ;
	
	public static String [] getSupportLocale()
	{
		String [] ret = null ; 
		
		String supportLocale = "zh-cn,zh-tw,en,zh-TW,zh-CN" ;
		
		ret = supportLocale.split(",") ;
		return ret ;
	}
}
