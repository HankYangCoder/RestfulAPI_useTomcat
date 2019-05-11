package com.restful.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 
{
	
	public static byte [] md5 (String data, String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytesOfMessage = data.getBytes(encoding);
		byte [] ret = md.digest(bytesOfMessage);
		return ret;
	}
	
	public static byte[] md5 ( byte[] bytes ) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
//		byte[] bytesOfMessage = data.getBytes(encoding);
		byte [] ret = md.digest(bytes);
		return ret;
	}
	
	public static byte[] md5(File file ) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		
//		byte[] bytesOfMessage = file.getBytes(encoding);
		byte [] bytesOfMessage = convertFiletoBytes(file) ;
		
		byte [] ret = md.digest(bytesOfMessage);
		return ret;
	}
	
	public static byte[] convertFiletoBytes( File file ) throws IOException
	{
//		File file = new File("/temp/abc.txt");
		//init array with file length
		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray); //read file into bytes[]
		fis.close();

		return bytesArray;
	}
	
	public static void main(String [] args) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte [] a = md5("1111111", "UTF-8");
		System.out.println(HexUtility.bytesToHex(a));
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedText = encoder.encodeToString(a);
		System.out.println(encodedText);
		
	}
}
