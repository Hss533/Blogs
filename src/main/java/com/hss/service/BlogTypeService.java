package com.hss.service;

import com.hss.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogTypeService {
    List<BlogType> find(Map<String ,Object> map);
    BlogType getById(Integer id);
    List<BlogType> countList();
    Long getTotal();
    Integer add(BlogType blogType);
    Integer update(BlogType blogType);
    Integer delete(Integer id);
}
