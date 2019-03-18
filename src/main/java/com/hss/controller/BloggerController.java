package com.hss.controller;

import com.hss.entity.Blogger;
import com.hss.service.BloggerService;
import com.hss.util.CryptographyUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/blogger")
public class BloggerController {


    @Resource
    private BloggerService bloggerSesrvice;

    @RequestMapping("/login")
    public ModelAndView login(Blogger blogger, HttpServletRequest request) throws IOException
    {
        //获取当前实体
        //modelAndView跳转到其他页面需要redirect:
        Subject subject=SecurityUtils.getSubject();//获取当前登录的用户
        UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(),"hss"));
        ModelAndView modelAndView=new ModelAndView();
        try {
            subject.login(token);//登录验证
            modelAndView.addObject("blogger",blogger);
            modelAndView.setViewName("/admin/main");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("blogger",blogger);//进行回显
            request.setAttribute("errorInfo","用户名或者密码错误");
            modelAndView.setViewName("login");
        }
        return modelAndView;

    }

    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe() throws Exception
    {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("mainPage","foreground/blogger/info.jsp");
        modelAndView.addObject("pageTitle","hss个人简介");
        modelAndView.setViewName("mainPage");
        return  modelAndView;
    }
}
