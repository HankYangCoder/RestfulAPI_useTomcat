package com.restful.api.util;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.restful.api.common.Constants;
import com.restful.api.common.RestfulAPIResult;
import com.restful.api.common.ServiceException;


public class RestfulAPICommonUtil 
{
	private final static Logger log = Logger.getLogger(RestfulAPICommonUtil.class.getName());

	/**
	 * 檢核傳入參數 
	 * @param sec
	 * @param str
	 * @return
	 */
	public static Map<String,Object> CheckParam( String sec , String ... str)
	{
		String error_msg = "" ; 
		Map<String,Object> ret = new HashMap();
	
		//使用自己的checkString
//		error_msg = StrUtil.checkString(sec );
		error_msg = checkString(sec );
		
		if(!"".equals(error_msg))
		{
			ret.put("SEC_CODE", "EMPTY");
			error_msg = "" ; 
		}
		
		//使用自己的checkString
//		error_msg = StrUtil.checkString(str );
		error_msg = checkString(str );
		if(!"".equals(error_msg))
		{
			ret.put("PARAM_CODE", "EMPTY");
			error_msg = "" ; 
		}
		
		return ret ; 
	}
	
	/**
	 * 檢查 RestfulAPI 參數 
	 * 是否有問題
	 * 一但有錯誤 會直接回傳 並告知是什麼問題 
	 * <pre>
	 * e.g 
	 *	 RestfulAPICommonUtil.CheckParam(retMap, sec, token, cid , accountid);
	 *</pre>
	 * @param retMap 回傳 map 
	 * @param sec    驗證碼
	 * @param token  sso 登入  token
	 * @param str    其它參數
	 */
	public static void CheckParam( Map<String,Object> retMap , String sec , String token  , String ... str )
	{
		String error_msg = "" ;
		
		// 檢查 sec token 是否為空白 
		error_msg = StrUtil.checkString(sec , token );
		error_msg = checkString(sec , token );
		System.out.println("error ["+error_msg+"]");
		if( !StrUtil.isEmpty(error_msg))
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
			retMap.put("return_value", "SEC OR Token Param have Empty  ") ;
			return ;
		}
		
		//檢查 除了 token 跟 sec 外的參數
		error_msg = StrUtil.checkString(str );
//		error_msg = checkString(str);
		
		if( !error_msg.isEmpty())
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
			retMap.put("return_value", "Param2 have Empty  ") ;
			return ;
		}
		
		
		String retCode = retMap.get("StatusCode").toString();
		System.out.println("retcode " + retCode );
		// 檢查 回傳是否 為 0000 
		if( !RestfulAPIResult.RA_SUCCESS_CODE.equals(retCode))
		{
			retMap.put("return_code", RestfulAPIResult.RA_UNKNOWN_CODE) ;
			retMap.put("return_value", "SSO Validation Error ") ;
//			System.out.println(" ret Map " + retMap );
			return ;
		}
		
		System.out.println(" REST ful API Check and Test Login IS SUCCESS!!! ");
	}
	
	public static String checkString(String ...str)
	{
		for( String x : str)
		{
			if( StrUtil.isEmpty(x))
			{
				return "String is empty " + x ; 
			}
		}
		return "" ;
	}
	
	/**
	 * 檢查 RestfulAPI 參數 
	 * 是否有問題
	 * 一但有錯誤 會直接回傳 並告知是什麼問題 
	 * <pre>
	 * e.g 
	 *	 RestfulAPICommonUtil.CheckParam(retMap, sec, token, cid , accountid);
	 *</pre>
	 * @param retMap 回傳 map 
	 * @param sec    驗證碼
	 * @param token  sso 登入  token
	 * @param str    其它參數
	 */
	public Map<String,Object> CheckParam2 ( Map<String,Object> retMap , String sec , String token  , String ... str )
	{
		String error_msg = "" ;
		
		log.debug("go into CheckParam2");
		log.debug("CheckParam2 sec="+sec);
		log.debug("CheckParam2 token="+token);
		for(String x : str)
		{
			log.debug("CheckParam2 str="+x);
		}
	
		// 檢查 sec token 是否為空白 
		error_msg = StrUtil.checkString(sec , token );
//		error_msg = checkString(sec , token );
		System.out.println("error ["+error_msg+"]");
		if( !StrUtil.isEmpty(error_msg))
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
			retMap.put("return_value", "SEC OR Token Param have Empty  ") ;
			return retMap ;
		}
		
		//檢查 除了 token 跟 sec 外的參數
		error_msg = StrUtil.checkString(str );
//		error_msg = checkString(str);
		
		if( !error_msg.isEmpty())
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
			retMap.put("return_value", "Param2 have Empty  ") ;
			return retMap ;
		}
		
		String retCode = retMap.get("StatusCode").toString();
		System.out.println("retcode " + retCode );
		// 檢查 回傳是否 為 0000 
		if( !RestfulAPIResult.RA_SUCCESS_CODE.equals(retCode))
		{
			retMap.put("return_code", RestfulAPIResult.RA_UNKNOWN_CODE) ;
			retMap.put("return_value", "SSO Validation Error ") ;
			System.out.println(" ret Map " + retMap );
			return retMap ;
		}
		
		System.out.println(" REST ful API Check and Test Login IS SUCCESS!!! ");
		log.debug("end CheckParam2");
		retMap = new HashMap<String,Object>();
		return retMap ; 
	}
	
	/**
	 * 檢查gw上行電文傳送過來的每個參數
	 * @param str
	 * @return
	 */
	public static Map<String,Object> CheckParam_gw( Map<String, Object> retMap, String sec, String ... str)
	{

		log.debug("go into CheckParam_gw");
		String error_msg = "";
		log.debug("CheckParam_gw sec="+sec);
		for(String x : str)
		{
			log.debug("CheckParam_gw str="+x);
		}
		
		// 檢查 sec 是否為空白 
		error_msg = StrUtil.checkString(sec);
		System.out.println("error ["+error_msg+"]");
		if( !StrUtil.isEmpty(error_msg))
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_DATA_EMPTY_CODE) ;
			retMap.put("return_value", "Sec is Empty  ") ;
			return retMap ;
		}
		
		//檢查 除了 sec 外的參數
		error_msg = StrUtil.checkString(str );
		if( !error_msg.isEmpty())
		{
			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_DATA_EMPTY_CODE) ;
			retMap.put("return_value", "Param is empty  ") ;
			return retMap ;
		}
		
		System.out.println(" Restful API Check SUCCESS!!! ");
		log.debug("end CheckParam_gw");
		retMap = new HashMap<String,Object>();
		return retMap ; 
		
	}
	
	/**
	 * 日期切割檢查
	 * @param timezone
	 * @return
	 * @throws ServiceException 
	 */
	public Integer timezoneSplit(String timezone) throws ServiceException
	{
		Integer retInt = 0 ;
		timezone = timezone.replaceAll(":", "").trim();
		if( !timezone.matches(Constants.TIMEZONE_REGX))
		{
			throw new ServiceException("時間格式錯誤");
		}
		
		// 時區 正負號 
		String tag = "" ;
		// 時區 小時
		Integer hour = 0 ;
		// 時區 分
		Integer min = 0 ;
		
		tag = timezone.substring(0, 1);
		
		hour = Integer.valueOf(timezone.substring(1, 3)) ; 
		
		min = Integer.valueOf(timezone.substring(3));
		
		retInt = (hour*3600 ) + ( min * 60 ) ;
			
		String tmp = tag + String.valueOf(retInt) ;
		
		retInt = Integer.valueOf(tmp);
		
		return retInt ;
	}
	
	/**
	 * 檢核語系 
	 * @param lang
	 * @return
	 */
	public boolean localeCheck(String lang)
	{
		boolean ret = false  ; 
		
		lang = lang.toLowerCase();
		
		for( String x : Constants.getSupportLocale())
		{
			if( x.equalsIgnoreCase(lang))
			{
				return true ;
			}
		}
		
		return ret ; 
	}
	
	public static String langFormatChange(String lang)
	{
		if( lang.indexOf("zh") >= 0)
			lang = lang.substring(0, lang.indexOf("-")).toLowerCase() + "-" + lang.substring(lang.indexOf("-")+1).toUpperCase() ;
		
		return lang ; 
	}
	
	public Map<String,Object> dateTypeAndSelectedDateCheck(Map<String,Object> retMap, String dateType, String selectedDate)
	{
		
		if("1".equalsIgnoreCase(dateType))
		{
			//日期類型1表示選擇一日
			//ex:2018-02-05
			//年的開頭為2的四位數字，
			//中間加"-"，
			//後面放月份、十位數只能0-1個位數只能0-9，
			//再用"-"連接日期，
			//十位數只能0-1個位數只能0-9
			if(!selectedDate.matches("2[0-9]{3}-[0-1][0-9]-[0-3][0-9]"))
			{
				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05-20 Your Input ["+selectedDate+"]") ;
				return retMap ;
			}
			//炫泡驗證
//			if(!selectedDate.matches("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"
//					+ "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"))
//			{
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
//				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05-20 Your Input ["+selectedDate+"]") ;
//				return retMap ;
//			}
		}
		else if("2".equalsIgnoreCase(dateType))
		{
			//日期類型2表示選擇一周
			//2018-02-05~2018-02-11
			//年的開頭為2的四位數字，
			//中間加"-"，
			//後面放月份、十位數只能0-1個位數只能0-9，
			//再用"-"連接日期，
			//十位數只能0-1個位數只能0-9
			//起始跟結束日期格式一樣，透過"~"連接
			if(!selectedDate.matches("2[0-9]{3}-[0-1][0-9]-[0-3][0-9]~2[0-9]{3}-[0-1][0-9]-[0-3][0-9]"))
			{
				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05-21~2018-05-29 Your Input ["+selectedDate+"]") ;
				return retMap ;
			}
			//炫泡驗證
//			if(!selectedDate.matches("((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"
//					+ "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))~"
//					+ "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"
//					+ "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))"))
//			{
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
//				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05-21~2018-05-29 Your Input ["+selectedDate+"]") ;
//				return retMap ;
//			}
		}
		else if("3".equalsIgnoreCase(dateType))
		{
			//日期類型3表示選擇一月
			//2018-02
			//年的開頭為2的四位數字，中間加"-"，後面放月份、十位數只能0-1個位數只能0-9
			if(!selectedDate.matches("2[0-9]{3}-[0-1][0-9]"))
			{
				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05 Your Input ["+selectedDate+"]") ;
				return retMap ;
			}
			//炫泡驗證
//			if(!selectedDate.matches("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])"))
//			{
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
//				retMap.put("return_value", "selectedDate Format Error  e.g 2018-05 Your Input ["+selectedDate+"]") ;
//				return retMap ;
//			}		
					
		}
		else if("4".equalsIgnoreCase(dateType))
		{
			//日期類型4表示選擇一年
			//2018
			//開頭必須為2的四位數字
			if(!selectedDate.matches("2[0-9]{3}"))
			{
				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
				retMap.put("return_value", "selectedDate Format Error  e.g 2018 Your Input ["+selectedDate+"]") ;
				return retMap ;
			}
			//炫泡驗證
//			if(!selectedDate.matches("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"))
//			{
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_INPUT_ERROR_CODE) ;
//				retMap.put("return_value", "selectedDate Format Error  e.g 2018 Your Input ["+selectedDate+"]") ;
//				return retMap ;
//			}
			
		}
		
		retMap = new HashMap<String,Object>();
		return retMap ; 
	}
	
	
	public static void main( String a [])
	{
		RestfulAPICommonUtil rautil = new RestfulAPICommonUtil();
		
		try {
			System.out.println(rautil.timezoneSplit(" 08:00"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Translator.getLocale("Zh-eW") ;
		
//		System.out.println(" here >>> " + Translator.getWord("zh-TW" , "alarm_desc"));
		
//		System.out.println(rautil.langFormatChange("zh-tw"));
	}

}
