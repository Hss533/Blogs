package com.hss.dao;

import com.hss.entity.Blog;
import com.hss.entity.Comment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface CommentDao {

    /**
     * 查询出所有博客评论
     * @param map
     * @return
     */
    List<Comment> list(Map<String, Object> map);

    /**
     * 添加博客评论
     * @param comment
     * @return
     */
    Integer add(Comment comment);


    List<Comment> listAdmin(Map<String ,Object> map);

    Integer updateComment(Comment comment);
    Long getTotal(Map<String,Object> map);
    Integer deleteComment(Integer id);
}
