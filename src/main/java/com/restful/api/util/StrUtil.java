package com.restful.api.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StrUtil 
{
	
	public static boolean isNotEmpty(Object o )
	{
		if( o == null || o.toString().isEmpty())
		{
			return false ; 
		}
		return true ;
	}
	
	public static boolean isEmpty(Object o )
	{
		return !isNotEmpty(o) ;
	}
	
	public static boolean isNotEmpty(String s)
	{
		if(s == null || s.isEmpty())
		{
			return false;
		}
		return true;
	}
	
	public static boolean isEmpty(String s)
	{
		return !isNotEmpty(s);
	}
	
	public static boolean isNumeric(String s)
	{
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(s, pos);
		return s.length() == pos.getIndex();
	}
	
	public static boolean isNubmer(String s)
	{
		s = s.trim() ;
		if( s.length() == 0)
			return false ; 
		
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(s, pos);
		return s.length() == pos.getIndex();
	}

	public static boolean isSignedNumeric(String s)
	{
		int idx = 0;
		if (s.startsWith("+") || s.startsWith("-"))
		{
			idx = 1;
		}
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(idx);
		formatter.parse(s, pos);
		return s.length() == pos.getIndex();
	}
	
	public static int zeroWhenEmpty(String s)
	{
		if (StrUtil.isEmpty(s))
		{
			return 0;
		}
		else 
		{
			return Integer.parseInt(s);
		}
	}
	
	/**
	 * 右補滿
	 * 當 newLength 的值小於 輸入字串長度時，回傳原有字串
	 * @param org  原有的字串
	 * @param pad  要補滿的字元(byte)
	 * @param newLength 長度
	 * @return 補滿的字串
	 */
	public static String padOnRight(String org, byte pad, int newLength)
	{	
		if (org.length() > newLength)
		{
			return org;
		}
		
		byte[] newArr = new byte[newLength];
		
		Arrays.fill( newArr, pad );
				
		byte[] orgByteArr = org.getBytes();
		System.arraycopy(orgByteArr, 0, newArr, 0, orgByteArr.length);
		
		return new String(newArr);
	}
	
	/* 
	 * 左填空白至newLength(資料靠右)
	 * @param org
	 * @param newLength
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static String addSpaceOnLeft(String org, int newLength){
		
		byte[] newArr = new byte[newLength];
		
		Arrays.fill( newArr, (byte)32 );
		
		byte[] orgByteArr = org.getBytes();
		System.arraycopy(orgByteArr, 0, newArr, newArr.length-orgByteArr.length, orgByteArr.length);
		
		return new String(newArr);
	}

	/**
	 * 左補零
	 * @param org
	 * @param newLength
	 * @return
	 */
	public static String padZeroLeft(String org, int newLength)
	{
		return padOnLeft(org, (byte) 0x30, newLength);
	}
	
	/**
	 * 左補滿
	 * 當 newLength 的值小於 輸入字串長度時，回傳原有字串
	 * @param org  原有的字串
	 * @param pad  要補滿的字元(byte)
	 * @param newLength 長度
	 * @return 補滿的字串
	 */
	public static String padOnLeft(String org, byte pad, int newLength)
	{
		if (org.length() > newLength)
		{
			return org;
		}
		
		byte[] newArr = new byte[newLength];
		
		Arrays.fill( newArr, pad );
		
		byte[] orgByteArr = org.getBytes();
		System.arraycopy(orgByteArr, 0, newArr, newArr.length-orgByteArr.length, orgByteArr.length);
		
		return new String(newArr);
	}
	
	/**
	 * 將輸入的整數轉換為左補 0 到指定位數的字串
	 * @param num		要處理的整數
	 * @param digits    要補足的總長度
	 * @return
	 */
	public static String padZero(int num, int digits)
	{
		String ret = "";
		
		//方法一
		//String pattern = "%0" + digits + "d";
		//ret = String.format(pattern, num);
		
		//方法二 效能比 方法一快三倍
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		String format = String.valueOf(zeros);
		DecimalFormat df = new DecimalFormat(format);
		ret = df.format(num);
		
		return ret;
	}
	
	/**
	 * 將 Float 資料轉為 字串型態
	 * 若是 Float 為 null，則輸出空字串
	 * @param f
	 * @return
	 */
	public static String nullToEmptyString(Float f)
	{
		Float floatValue = f;
		String strValue = ""; 
		if (floatValue != null) strValue = floatValue.toString(); 
		return strValue;
	}
	
	/**
	 * 將所有 Map 中 的 null value 轉成 empty string
	 * @param map
	 */
	public static void nullToEmptyString(Map<String, String> map)
	{
		Set<String> keySet = map.keySet();
		for(String key : keySet)
		{
			String value = map.get(key);
			if (value == null)
			{
				map.put(key, "");
			}
		}		
	}
	
	/**
	 * 將所有 Map 中 的 null value 轉成  string  0
	 * @param map
	 */
	public static void nullToZeroString(Map<String, String> map)
	{
		Set<String> keySet = map.keySet();
		for(String key : keySet)
		{
			String value = map.get(key);
			if (value == null)
			{
				map.put(key, "0");
			}
		}		
	}
	
	/**
	 * 將所有 Map 中 的 null value 轉成 empty string
	 * @param map
	 */
	public static Map<String,String> nullToEmptyStringObjTypeUse(Map<String,Object> map){
		Map<String,String> ret = new HashMap<String, String>();
		Set<String> keySet = map.keySet();
		for(String key : keySet)
		{
			String value = map.get(key)!=null ? map.get(key).toString() :"";
			ret.put(key, value);
		}	
		return ret ; 
	}
	
	/**
	 * 將所有 Map 中 的 null value 轉成 empty string
	 * @param map
	 */
	public static Map<String,Object> nullToZeroStringObjTypeUse(Map<String,Object> map){
		Map<String,Object> ret = new HashMap<String, Object>();
		Set<String> keySet = map.keySet();
		for(String key : keySet)
		{
			String value = map.get(key)!=null ? map.get(key).toString() :"0";
			ret.put(key, value);
		}	
		return ret ; 
	}

	/**
	 * 將所有 Map 中 的 null value 轉成 BigDecimal 0
	 * @param map
	 */
	public static Map<String,Object> nullToBigDecimalZero(Map<String,Object> map){
		Map<String,Object> ret = new HashMap<String, Object>();
		Set<String> keySet = map.keySet();
		for(String key : keySet)
		{
			Object v = map.get(key);
			if (v == null)				
			{
				BigDecimal b = new BigDecimal(0);
				ret.put(key, b);				
			}
			else
			{
				ret.put(key, v);	
			}
		}	
		return ret ; 
	}
	
	public static String nullToEmptyString(String str)
	{
		if (str == null) return "";
		return str;
	}
	
	public static String nullToZeroString(String str)
	{
		if (str == null) return "0";
		return str;
	}
	
	public static String emptyToZero(String str)
	{
		if (str == null || str.equals(""))
			return "0" ;
		
		return str ; 
	}
	
	public static Boolean isTrue(String str)
	{
		if (isEmpty(str)) return false;
		String s = str.toLowerCase();
		if (s.startsWith("y") ||
			s.startsWith("1") ||
			s.startsWith("t")
			)
		{
			return true;
		}
			
		return false;
	}
	
	public static String getUUID()
	{
		String ret = UUID.randomUUID().toString().replace("-", ""); 
		return ret;
	}
	
	public static String trimAllWhitespace(String str) 
	{
        if (isEmpty(str)) 
        {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) 
        {
            if (Character.isWhitespace(sb.charAt(index))) 
            {
                sb.deleteCharAt(index);
            }
            else 
            {
                index++;
            }
        }
        return sb.toString();
    }
	
	public static String getInputCarry(String str , int count)
	{
		String ret = "";
        double b = new BigDecimal(str).setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
        ret = String.valueOf(b);
		return ret ;
	}
	
	public static Double getInputCarryDouble(String str , int count)
	{
		String ret = "";
		double b = 0d ;
		try {
			b = new BigDecimal(str).setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
       // ret = String.valueOf(b);
		return b ;
	}
	
	public static String langChange(String lang)
	{
		String ret = "" ;
		if(lang.equals("zh-tw"))
			ret = "zh-TW";
		else if (lang.equals("zh-cn"))
			ret = "zh-CN" ;
		return ret;
	}
	
	public static String getSQLInContext(List<String> context , Map map , String param)
	{
		String ret = "";
		
		for( int x = 0 ; x < context.size() ; x++)
		{
//			System.out.println(x);
			map.put(param+x, context.get(x));
			ret = ret +":"+ param+x +",";
		}
		return ret.substring(0, ret.lastIndexOf(",")) ; 
	}
	
	public static String getSQLInContext( List<String> context )
	{
		String ret = "";
		
		for( int x = 0 ; x < context.size() ; x++)
		{
//			System.out.println(x);
//			map.put(param+x, context.get(x));
			ret += "'"+context.get(x) + "' , " ; 
		}
		return ret.substring(0, ret.lastIndexOf(",")) ; 
	}
	
	public static String amountFormat(double num)
	{
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String str = df.format(num);
		//System.out.println(str);
		return str;
	}
	
	/**
	 * gson string to Map
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> gsonStringToHashMap(String jsonStr)
	{
		Gson gson = new Gson();
		Map<String,Object> ret = new HashMap<String,Object>();
		ret = gson.fromJson(jsonStr, new TypeToken<HashMap<String, Object>>() {}.getType());
		return ret ;
	}
	
	/**
	 * gson string to Map
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,String> gsonStringToHashMap2(String jsonStr)
	{
		Gson gson = new Gson();
		Map<String,String> ret = new HashMap<String,String>();
		ret = gson.fromJson(jsonStr, new TypeToken<HashMap<String, String>>() {}.getType());
		return ret ;
	}
	/**
	 * gson String to ArrayList<Map<String,Object>>
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String,Object>> gsonStringToArrayList(String jsonStr)
	{
		Gson gson = new Gson();
		List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		ret = gson.fromJson(jsonStr, new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
		return ret ;
	}
	
	/**
	 * gson String to List<String>
	 * @param jsonStr
	 * @return
	 */
	public static List<String> gsonStringToArrayListString(String jsonStr)
	{
		Gson gson = new Gson();
		List<String> ret = new ArrayList<String>();
		ret = gson.fromJson(jsonStr, new TypeToken<ArrayList<String>>(){}.getType());
		return ret ;
	}
	
	public static String checkChangeToNumber( String val)
	{
		String ret = "" ; 
		try {
			
			String tmp = getInputCarry(String.valueOf(Double.parseDouble(val)) , 2 );
			
			ret = String.valueOf(tmp) ; 
		}
		catch ( Exception e )
		{
			ret = val ; 
		}
		
		return ret ; 
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
	
//	
	public static void main(String []args)
	{
//		System.out.println(StrUtil.getInputCarry("3.01232231", 2));
//		System.out.println(StrUtil.isNumeric("5"));
		
//		List<String> abc = new ArrayList<String>();
//		Map map =new HashMap();
//		String param = "bac";
//		abc.add("a");
//		abc.add("b");
//		abc.add("c");
//		abc.add("d");
//		
//		System.out.println(StrUtil.getSQLInContext(abc, map , param));
//		System.out.println(map.toString());
//		
//		amountFormat(1111111);
		String uuu = null;
		System.out.println(StrUtil.emptyToZero(uuu));
	}
}
