package com.hss.service;

import com.hss.entity.Blog;
import com.hss.entity.Blogger;

import java.util.Map;


public interface BloggerService {
    /**
     * 登录
     * @param
     * @return
     */
     Blogger find(Map map);
     Integer update(Blogger blogger);
}
