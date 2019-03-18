package com.hss.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class CryptographyUtil {

	

	public static String md5(String str,String salt)
	{
		return new Md5Hash(str,salt).toString();
	}

	@Test
	public void testMD5()
	{
		String password="Betterhss/.,";
		System.out.println(CryptographyUtil.md5(password,"hss"));
	}
}
