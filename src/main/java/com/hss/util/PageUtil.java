package com.hss.util;

public class PageUtil
{


    public static String genPageination(String targetUrl,long totalNum,int currentPage,int pageSize,String param)
    {

        System.out.println(param);
        //看一下后面需不需要连接符
        String encoding=new String("");
        if("".equals(param)||null==param)
            encoding="";
        else
            encoding="&";

        StringBuffer pageCode =new StringBuffer();
        long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;//总页数
        if(totalPage==0)
        {
            return "再找也没有啦~~~~";
        }
        else {

            //上一页
            if(currentPage>1)
            {
                //当前页不是第一页
                pageCode.append("<li><a href='"+targetUrl+"?page=1"+encoding+param+"'>首页</a></li>");
                pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+encoding+param+"'>上一页</a></li>");
            }
            else
            {
                //当前页是第一页
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+encoding+param+"'>首页</a></li>");
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+encoding+param+"'>上一页</a></li>");
            }
            //中间要显示的页数
            for(int i=currentPage-1;i<=currentPage+2;i++)
            {
                if(i<1||i>totalPage)
                {
                    continue;
                }
                if(i==currentPage)
                {
                    pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+encoding+param+"'>"+i+"</a></li>");
                }else{
                    pageCode.append("<li><a href='"+targetUrl+"?page="+i+encoding+param+"'>"+i+"</a></li>");
                }
            }
            //下一页
            if(currentPage<totalPage)
            {
                //当前页不是最后一页
                pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+encoding+param+"'>下一页</a></li>");
                pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+encoding+param+"'>尾页</a></li>");
            }
            else{
                //当前页是最后一页
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+encoding+param+"'>下一页</a></li>");
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+totalPage+encoding+param+"'>尾页</a></li>");
            }


        }
        return pageCode.toString();
    }

}
