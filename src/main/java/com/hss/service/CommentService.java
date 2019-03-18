package com.hss.service;

import com.hss.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> list(Map<String, Object> map);
    Integer add(Comment comment);
    //后台
    List<Comment> listAdmin(Map<String ,Object> map);
    //修改comment
    Integer updateComment(Comment comment);
    Long getTotal(Map<String,Object> map);
    Integer deleteComment(Integer id);
}
