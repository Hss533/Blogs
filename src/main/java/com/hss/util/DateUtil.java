package com.hss.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类
 */
public class DateUtil {


	/**
	 * 将日期按照对应格式转换为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}

	/**
	 * 将字符串按照对应格式转换为日期
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
	
}
