package com.restful.api.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.restful.api.common.RestfulAPIResult;
import com.restful.api.po.InBoundBean;
import com.restful.api.po.OutBoundBean;
import com.restful.api.po.QueryParamPO;
import com.restful.api.util.BeanUtil;
import com.restful.api.util.PropUtil;
import com.restful.api.util.RestfulAPICommonUtil;
import com.restful.api.util.StrUtil;

/**
 * <pre>
 * 
 * 若要 copy 至其它地方 亦可 但要實作的東西會較多 並要修改 inbound & outbound 等
 * invoke_processData input InBoundBean & userOP
 * 
 * error handel 
 * invoke_QueryError  input 現在狀態的 OutBoundBean 轉型為 Map 傳入  
 * invoke_InBoundINITError
 * invoke_ParamCheckError
 * invoke_ProcessError
 * invoke_DefaultError
 * 	 * 作業流程 
	 * 1. 進行 參數檢核   
	 *    A. 參數全為必填故 若有空白則發生錯誤 
	 *    B. SEC 驗證失敗 不會回傳失敗  留正 實作方法進行後續通知
	 * 2. 進行 invoke_NecessaryWork 
	 *    A. 進行必要的工作
	 * 3. 進行 invoke_processData
	 *    A. 進行回傳的資料處理項目 
	 * 4. 進行 實作 ErrorHandel 
	 *    A. invoke_QueryError 代表查詢資料為空白  
	 *    B. invoke_InBoundINITError 代表 InBound Bean 元件初始化失敗 
	 *    C. invoke_ParamCheckError  代表 GW 上行內容參數有空白 或  NULL 請參考流程 1 
	 *    D. invoke_ProcessError     代表 進行 invoke_processData 時 發生 Exception 
	 *    E. invoke_DefaultError     預設的錯誤
 * </pre>
 * @author Hank
 *
 */
abstract class BaseApiRest extends ApiRest 
{	
	private final static Logger log = Logger.getLogger(BaseApiRest.class.getName());
	
	Gson gson ; 
	InBoundBean inBound ; 
	OutBoundBean outBound ;
	QueryParamPO qpPo ;
	PropUtil prop ;
	
	public static enum ErrorActionType { InBoundINITError , QueryError , ProcessError , ParamCheckError , DefaultError , CheckSECError , NecessaryWorkError} ;
	
	public BaseApiRest()
	{
		gson = new Gson();
		prop = new PropUtil();
	}
	
	/**
	 * 調用 資料處理 此為必要實作 
	 * @param inBound
	 * @param userOP
	 * @return
	 */
	protected abstract Map<String,Object> invoke_processData(InBoundBean inBound , String userOP);
	/**
	 * 調用 必要處理流程工作
	 * @param inBound
	 * @param retMap
	 */
	protected abstract void invoke_NecessaryWork(InBoundBean inBound , Map<String, Object> retMap ) ;
	
	protected void invoke_NecessaryWorkError(Map<String,Object> ret )
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_NecessaryWorkError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_NecessaryWorkError ["+ret+"]");
	}
	
	/**
	 * Error Handling Functon
	 * 安全加密錯誤
	 * @param ret
	 */
	protected void invoke_CheckSECError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_CheckSECError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_CheckSECError ["+ret+"]");
	}
	/**
	 * Error Handling Functon
	 * 查詢錯誤
	 * @param ret
	 */
	protected void invoke_QueryError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_QueryError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_QueryError ["+ret+"]");
	}
	/**
	 * Error Handling Functon
	 * 輸入項 初始化錯誤
	 * @param ret
	 */
	protected void invoke_InBoundINITError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_InBoundINITError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_InBoundINITError ["+ret+"]");
	}
	/**
	 * Error Handling Functon
	 * 參數檢查錯誤
	 * @param ret
	 */
	protected void invoke_ParamCheckError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_ParamCheckError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_ParamCheckError ["+ret+"]");
	}
	/**
	 * Error Handling Functon
	 * 調用 資料錯誤 錯誤
	 * @param ret
	 */
	protected void invoke_ProcessError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_ProcessError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_ProcessError ["+ret+"]");
	}
	
	/**
	 * Error Handling Functon
	 * 預設錯誤 
	 * @param ret
	 */
	protected void invoke_DefaultError(Map<String,Object> ret)
	{
		// TODO Auto-generated method stub
		log.debug(" Error Handel invoke_DefaultError [GWOTANetworkAgentRest]");
		log.debug(" Error Handel invoke_DefaultError ["+ret+"]");
	}
//	protected abstract Map<String,Object> invoke_buildOutData(OutBondBean outBound , String userOP);
	protected abstract String getUserOP();
	
	/**
	 * Do Action Function For Test 
	 * @param func
	 * @return
	 */
	public Response doAction(String func)
	{
		String ret = "";
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("RETURN", "get Func "+func);
		ret = gson.toJson(retMap);
		return Response.ok(ret).build();
	}
	
	/**
	 * TODO 傳入 Val 
	 * Do Action Function 
	 * @return
	 */
	public Response doAction (
			
			) 
	{
		setParam();
		return doAction();
	}
	
	
	
	/**
	 * 作業流程 
	 * 1. 進行 參數檢核   
	 *    A. 參數全為必填故 若有空白則發生錯誤 
	 *    B. SEC 驗證失敗 不會回傳失敗  留正 實作方法進行後續通知
	 * 2. 進行 invoke_NecessaryWork 
	 *    A. 進行必要的工作
	 * 3. 進行 invoke_processData
	 *    A. 進行回傳的資料處理項目 
	 * 4. 進行 實作 ErrorHandel 
	 *    A. invoke_QueryError 代表查詢資料為空白  
	 *    B. invoke_InBoundINITError 代表 InBound Bean 元件初始化失敗 
	 *    C. invoke_ParamCheckError  代表 GW 上行內容參數有空白 或  NULL 請參考流程 1 
	 *    D. invoke_ProcessError     代表 進行 invoke_processData 時 發生 Exception 
	 *    E. invoke_DefaultError     預設的錯誤
	 * @param trandatetime
	 * @param status
	 * @param gw_tar_no
	 * @param gw_ini_no
	 * @param sec
	 * @param gwsn
	 * @return
	 */
	public Response doAction(
			String trandatetime, String status , String id
			)
	{
		log.debug("do Active start. ID [" + id +"] status ["+status+"] " );
//		System.out.println("do Active start. GWSN [" + gwsn +"] status ["+status+"] " );
		
		String ret = "";
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = RestfulAPICommonUtil.CheckParam( id, trandatetime, status );
		
		if( !retMap.isEmpty())
		{
			if( retMap.get("PARAM_CODE")!=null)
			{
				retMap = buildErrorHandel(retMap, ErrorActionType.ParamCheckError );
				ret = gson.toJson(retMap);
//				log.debug("end path:/app/cusbasemachine/query/status");
				log.debug("ret ="+ret);
				return Response.ok(ret).build();
			}
			
			if( retMap.get("SEC_CODE")!=null)
			{
				retMap = buildErrorHandel(retMap, ErrorActionType.CheckSECError);
				ret = gson.toJson(retMap);
//				log.debug("end path:/app/cusbasemachine/query/status");
				log.debug("ret ="+ret);
				return Response.ok(ret).build();
			}
			
		}
		
		try{
			if( inBound == null )
			{
				// TODO
				inBound = new InBoundBean();
			}
		}
		catch ( Exception ex )
		{
			retMap.put("INIT", "Input Error");
			retMap = buildErrorHandel(retMap, ErrorActionType.InBoundINITError);
			ret = gson.toJson(retMap);
//				log.debug("end path:/app/cusbasemachine/query/status");
			log.debug("ret ="+ret);
			return Response.ok(ret).build();
		}
		

			System.out.println(" inBOund " + inBound);
			
		try{
			
			invoke_NecessaryWork(inBound, retMap);
			
		}
		catch ( Exception ex )
		{
			retMap.put("INIT", "Input Error");
//			retMap.put("return_code", RestfulAPIResult.RA_PARAM2_DATA_EMPTY_CODE) ;
//			retMap.put("return_value", "Query Is Not Data" ) ;
			retMap = buildErrorHandel(retMap, ErrorActionType.NecessaryWorkError);
			ret = gson.toJson(retMap);
//			log.debug("end path:/app/cusbasemachine/query/status");
			log.debug("ret ="+ret);
			return Response.ok(ret).build();
		}
			
		try 
		{
			retMap = invoke_processData(inBound , getUserOP());
		
			if( retMap == null || retMap.isEmpty())
			{
				retMap.put("INIT", "Input Error");
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_DATA_EMPTY_CODE) ;
//				retMap.put("return_value", "Query Is Not Data" ) ;
				retMap = buildErrorHandel(retMap, ErrorActionType.QueryError );
				ret = gson.toJson(retMap);
//				log.debug("end path:/app/cusbasemachine/query/status");
				log.debug("ret ="+ret);
				return Response.ok(ret).build();
			}
		} 
		catch ( Exception ex )
		{
//				retMap.put("return_code", RestfulAPIResult.RA_PARAM2_DATA_EMPTY_CODE) ;
//				retMap.put("return_value", "Query Is Not Data" ) ;
				retMap = buildErrorHandel(retMap, ErrorActionType.ProcessError);
				ret = gson.toJson(retMap);
//				log.debug("end path:/app/cusbasemachine/query/status");
				log.debug("ret ="+ret);
				return Response.ok(ret).build();
		}
		
		ret = gson.toJson(retMap) ;
		log.debug("do Active End. ID [" + id +"] status ["+status+"]  Ret Data [" + retMap +"] ");
		return Response.ok(ret).build();
	}

	protected void setQueryParamPO(String key , String val  ) 
	{
		if( StrUtil.isNotEmpty(val))
		{
			
			if( null == qpPo)
			{
				qpPo = new QueryParamPO();
			}
			qpPo.setQueryParam(key, val);
		}
	}
	
	protected Map<String,Object> buildErrorHandel(Map<String,Object> input , ErrorActionType excpetion)
	{
//		for( String x : input.keySet())
//		{
//			System.out.println(" key " + x + " v " + input.get(x));
//		}
		
		//TODO start
		String statuscode = "R0000" ;
		String statusdesb = "SUCCESS" ;
		
		statuscode = checkErrorMesg( excpetion).get("K") ;
		statusdesb = checkErrorMesg( excpetion).get("V") ;
		
		// TODO
		if( outBound == null )
		{
			outBound = new OutBoundBean();
		}
		
		Map<String,Object> ret = BeanUtil.transToMap(outBound) ;
		
		switch( excpetion )
		{
			case QueryError :
				invoke_QueryError(ret);
				break ;
			case InBoundINITError :
				invoke_InBoundINITError(ret);
				break ;
			case ParamCheckError :
				invoke_ParamCheckError(ret);
				break ;
			case ProcessError :
				invoke_ProcessError(ret);
				break ;
			case CheckSECError : 
				invoke_CheckSECError(ret);
			case NecessaryWorkError :
				invoke_NecessaryWorkError(ret);
				break ; 
			default :
				invoke_DefaultError(ret);
				break ;
		}
		
		//TODO  build Error Return Param & Val
		
		return ret ; 
	}
	
	public Map<String,String> checkErrorMesg(ErrorActionType excpetion)
	{
		Map<String,String> retdesc = new HashMap() ; 
		
		switch( excpetion )
		{
			case QueryError :
				retdesc.put("K", RestfulAPIResult.OTA_QUERY_CODE);
				retdesc.put("V" , "QueryError");
				break ;
			case InBoundINITError :
				retdesc.put("K", RestfulAPIResult.OTA_INIT_CODE );
				retdesc.put("V" , "InitError");
				break ;
			case ParamCheckError :
				retdesc.put("K", RestfulAPIResult.OTA_PARAMCHECK_CODE);
				retdesc.put("V" ,"ParamCheckError");
				break ;
			case ProcessError :
				retdesc.put("K", RestfulAPIResult.OTA_PROCESS_CODE);
				retdesc.put("V" , "ProcessError");
				break ;
			case CheckSECError :
				retdesc.put("K", RestfulAPIResult.OTA_SECCHECK_CODE);
				retdesc.put("V" ,"SECCheckError" );
				break ;
			case NecessaryWorkError :
				retdesc.put("K", RestfulAPIResult.OTA_NECSSARY_CODE);
				retdesc.put("V" , "NecessaryWorkError");
				break ;
			default :
				retdesc.put("K", RestfulAPIResult.OTA_DEFAULT_CODE);
				retdesc.put("V" , "DefaultError");
				break ;
		}
		
		return retdesc ; 
	}
	
	public String getUUIDKey()
	{
		String ret = "" ; 
		
		ret = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		
		return ret ; 
	}
	
	/**
	 * 實作 Query Param Val
	 */
	protected void setParam() 
	{
	}
}
