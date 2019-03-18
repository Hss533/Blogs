package com.hss.service.impl;

import com.hss.dao.BloggerDao;
import com.hss.entity.Blogger;
import com.hss.service.BloggerService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService
{
    @Resource
    private BloggerDao bloggerDao;

    @Override
    public Blogger find(Map map) {

        return bloggerDao.find(map);
    }

    @Override
    public Integer update(Blogger blogger) {
        return bloggerDao.update(blogger);
    }
}
