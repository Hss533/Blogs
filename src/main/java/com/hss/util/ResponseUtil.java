package com.hss.util;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {

    /**
     * 返回json流页面 进行接收
     * @param response
     * @param o
     * @throws Exception
     */

    public static void write(HttpServletResponse response,Object o) throws  Exception
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
