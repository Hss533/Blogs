package com.hss.dao;

import com.hss.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogTypeDao
{

    /**
     * 根据条件进行查找
     * @param map
     * @return
     */
    List<BlogType> find(Map<String ,Object> map);

    /**
     * 根据id进行查找
     * @param id
     * @return
     */
    BlogType getById(Integer id);

    /**
     *
     * @return
     */
    List<BlogType> countList();

    /**
     * 添加博客类别
     * @param blogType
     * @return
     */
    Integer add(BlogType blogType);

    /**
     * 更新博客类别
     * @param blogType
     * @return
     */
    Integer update(BlogType blogType);

    /**
     * 删除博客类别
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 得到所有的博客数量
     * @return
     */
    Long getTotal();
}
