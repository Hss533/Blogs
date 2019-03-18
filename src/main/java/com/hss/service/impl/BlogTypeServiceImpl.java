package com.hss.service.impl;

import com.hss.dao.BlogTypeDao;
import com.hss.entity.BlogType;
import com.hss.service.BlogTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import  java.util.List;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService
{
    @Resource
    BlogTypeDao blogTypeDao;
    @Override
    public List<BlogType> find(Map<String, Object> map) {
        return blogTypeDao.find(map);
    }

    @Override
    public BlogType getById(Integer id) {
        return blogTypeDao.getById(id);
    }

    @Override
    public List<BlogType> countList() {
        return blogTypeDao.countList();
    }

    @Override
    public Long getTotal() {
        return blogTypeDao.getTotal();
    }

    @Override
    public Integer add(BlogType blogType) {
        return blogTypeDao.add(blogType);
    }

    @Override
    public Integer update(BlogType blogType) {
        return blogTypeDao.update(blogType);
    }

    @Override
    public Integer delete(Integer id) {
        return blogTypeDao.delete(id);
    }
}
