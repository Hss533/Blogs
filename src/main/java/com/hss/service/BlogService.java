package com.hss.service;

import com.hss.entity.Blog;
import com.mysql.jdbc.BlobFromLocator;

import java.util.List;
import java.util.Map;

public interface BlogService
{

    List<Blog> countList();//将博客根据日期进行分类，并得出每个分类的个数和名称
    /**
     *   所得出的表
     *   2018年11月	20
         2018年09月	2
         2018年08月	1
     */
    List<Blog> list(Map<String,Object> map);//根据条件查询博客
    Long getTotal(Map<String,Object> map);//获取博客总数
    Blog findById(Integer id);//根据id查询博客
    Integer update(Blog blog);//更新博客
    Blog getNextBlog(Map<String,Object> map);//寻找下一篇博客
    Blog getPreBlog(Map<String,Object> map);//寻找上一篇博客
    Integer addBlog(Blog blog);//添加新的博客
    Integer deleteBlog(Integer id);//根据博客id删除博客

    boolean checkLikeOrUnlike(Integer articleId,String Ip);//查看博客是否点赞
    Integer likeOrUnlike(Integer articleId,String Ip,Integer status);
}
