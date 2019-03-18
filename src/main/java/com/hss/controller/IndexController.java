package com.hss.controller;

import com.hss.entity.Blog;
import com.hss.entity.PageBean;
import com.hss.service.BlogService;
import com.hss.util.PageUtil;
import com.hss.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页controller
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Resource
    private BlogService blogService;

    @RequestMapping("/index")
    public ModelAndView index(
            @RequestParam(value = "typeId",required = false)String typeId,
            @RequestParam(value = "releaseDateStr",required = false)String releaseDateStr,
            @RequestParam(value = "page",required = false)String page,
            HttpServletRequest request) throws  Exception
    {
        ModelAndView modelAndView=new ModelAndView();
        //如果不选的话，默认主页是1，pageSize=10
        if(StringUtil.isEmpty(page))
        {
            page="1";
        }
        PageBean pageBean=new PageBean(Integer.parseInt(page),10);
        Map<String,Object> map=new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("typeId",typeId);
        map.put("releaseDateStr",releaseDateStr);
        List<Blog> blogList=blogService.list(map);
        //进行图片的删选工作，删选出三张图片jsoup
        for(Blog blog:blogList)
        {
            List<String> imageList=blog.getImageList();
            String blogInfo=blog.getContent();
            Document document= Jsoup.parse(blogInfo);
            Elements jpgs=document.select("img[src$=.jpg]");
            for(int i=0;i<jpgs.size();i++)
            {
                Element jpg=jpgs.get(i);
                imageList.add(jpg.toString());
                if(i==2)
                    break;
            }

        }
        StringBuffer param=new StringBuffer();
        StringBuffer params=new StringBuffer();
        if(StringUtil.isNotEmpty(typeId))
        {
            param.append("typeId"+typeId);
            params.append("typeId="+typeId);
        }
        if(StringUtil.isNotEmpty(releaseDateStr))
        {
            param.append("releaseDateStr"+releaseDateStr);
            params.append("releaseDateStr="+releaseDateStr);
        }
        modelAndView.addObject("blogList",blogList);//这个就相当于request.setAttribute()
        modelAndView.addObject("mainPage","foreground/blog/list.jsp");
        modelAndView.addObject("pageTitle","个人博客系统--hss");
        modelAndView.addObject("tiny_title","博客列表");
        if("".equals(params.toString()))
        {
            modelAndView.addObject("params","");
        }
        else
        {
            modelAndView.addObject("params","?"+params.toString());
        }
        modelAndView.addObject("pageCode", PageUtil.genPageination(request.getContextPath()+"/index.html",blogService.getTotal(map),Integer.parseInt(page),10,param.toString()));
        modelAndView.setViewName("mainTemp");//这个是跳转到blog/list.jsp
        return modelAndView;
    }
}
