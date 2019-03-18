package com.hss.dao;

import com.hss.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogDao {

    /**
     *
     * @return
     */
    List<Blog> countList();

    /**
     * 根据条件查询博客
     * @param map
     * @return
     */
    List<Blog> list(Map<String,Object> map);

    /**
     * 获取博客的总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     * 根据id查找实体
     * @param id
     * @return
     */
    Blog findById(Integer id);

    /**
     * 更新博客信息
     * @param blog
     * @return
     */
    Integer update(Blog blog);

    /**
     * 根据条件查询下一个博客和上一个博客
     * @param map
     * @return
     */
    Blog getNextBlog(Map<String,Object> map);
    Blog getPreBlog(Map<String,Object> map);

    /**
     * 添加博客
     * @param blog
     * @return
     */
    Integer addBlog(Blog blog);

    /**
     * 根据id删除博客
     * @param id
     * @return
     */
    Integer deleteBlog(Integer id);

}
