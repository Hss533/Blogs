package com.hss.dao;

import com.hss.entity.Blog;
import com.hss.entity.Blogger;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Map;

public interface BloggerDao
{

    Blogger find(Map map);
    Integer delete(Integer id);
    Integer update(Blogger blogger);
    Integer add(Blogger blogger);
}
