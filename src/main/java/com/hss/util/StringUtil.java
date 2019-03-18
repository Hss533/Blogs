package com.hss.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static boolean isEmpty(String string) {
        if ("".equals(string) || string == null) {
            return true;
        } else
            return false;
    }

    public static boolean isNotEmpty(String string) {
        if (!"".equals(string) && string != null) {
            return true;
        } else
            return false;
    }

    /**
     * 模糊查询
     * @param str
     * @return
     */
    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        } else {
            return null;
        }
    }

    /**
     * 过滤掉集合中的空格以及空字符串
     *
     * @param list
     * @return
     */
    public static List<String> filterWhite(List<String> list) {
        List<String> resultList = new ArrayList<>();
        for (String l : list) {
            if (isNotEmpty(l.trim())) {
                resultList.add(l);
            }
        }
        return resultList;
    }

    @Test
    public void test()
    {
        List keyWordList=new ArrayList();
        for(int i=0;i<" husah  huasd  asa ".split(" ").length;i++)
        {
            keyWordList.add(" husah  huasd  asa ".split(" ")[i]);
        }
        keyWordList= StringUtil.filterWhite(keyWordList);

        System.out.println(keyWordList.get(0));
        System.out.println(keyWordList.get(1));
        System.out.println(keyWordList.get(2));
        System.out.println(keyWordList.size());
    }
}
