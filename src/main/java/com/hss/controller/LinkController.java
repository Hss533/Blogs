package com.hss.controller;

import com.hss.entity.Link;
import com.hss.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    /**
     * 友情链接列表
     *

     * @return
     */
    @RequestMapping("/show")
    public ModelAndView showUrl() throws IOException{

        ModelAndView modelAndView=new ModelAndView();
        List<Link> linkList=linkService.find(null);
        modelAndView.addObject("linkList",linkList);
        modelAndView.setViewName("TestshowLink");
        return modelAndView;
    }
}
