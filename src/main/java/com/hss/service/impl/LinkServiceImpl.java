package com.hss.service.impl;

import com.hss.dao.LinkDao;
import com.hss.entity.Link;
import com.hss.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 友情链接service实现类
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkDao linkDao;
    @Override
    public List<Link> find(Map<String, Object> map) {
        return linkDao.find(map) ;
    }

    @Override
    public int add(Link link) {
        return linkDao.add(link);
    }

    @Override
    public int update(Link link) {
        return linkDao.update(link);
    }

    @Override
    public int delete(Integer id) {
        return linkDao.delete(id);
    }
}
