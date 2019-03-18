package com.hss.service.impl;

import com.hss.dao.CommentDao;
import com.hss.entity.Comment;
import com.hss.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl  implements CommentService{

    @Resource
    private CommentDao commentDao;

    @Override
    public List<Comment> list(Map<String, Object> map)
    {
        return commentDao.list(map);
    }

    @Override
    public Integer add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public List<Comment> listAdmin(Map<String, Object> map) {
        return commentDao.listAdmin(map);
    }

    @Override
    public Integer updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer deleteComment(Integer id) {
        return commentDao.deleteComment(id);
    }
}
