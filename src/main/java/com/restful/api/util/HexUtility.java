package com.restful.api.util;

import java.io.File;

public class HexUtility {
	public static String bytesToHex(byte[] b) {
      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      StringBuffer buf = new StringBuffer();
      for (int j=0; j<b.length; j++) {
         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
         buf.append(hexDigit[b[j] & 0x0f]);
      }
      return buf.toString();
   }
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static String getMD5Code( File file )
	{
		byte [] ret = null ; 
		try {
			
			if( file.exists())
			{
				ret = MD5.md5(file);
				
			}
			
		} catch (Exception ex ) {
			ex.printStackTrace();
		}
		
//		return byteArray2Hex(ret);
		return HexUtility.bytesToHex(ret);
	}
}
