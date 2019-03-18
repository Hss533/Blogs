package com.hss.controller;

import com.hss.entity.BlogType;
import com.hss.service.BlogTypeService;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blogType")
public class BlogTypeController
{
    @Resource
    BlogTypeService blogTypeService;
    @RequestMapping("/show")
    public ModelAndView showTypes()
    {
        ModelAndView modelAndView=new ModelAndView();
        List<BlogType> blogTypeList=blogTypeService.find(null);
        modelAndView.addObject("blogTypeList",blogTypeList);

        return modelAndView;
    }






}
