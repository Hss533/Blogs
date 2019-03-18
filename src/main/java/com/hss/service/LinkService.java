package com.hss.service;

import com.hss.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkService
{

     List<Link> find(Map<String,Object> map);
     int add(Link link);
     int update (Link link);

     int delete(Integer id);
}
