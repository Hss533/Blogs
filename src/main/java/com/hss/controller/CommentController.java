package com.hss.controller;

import com.hss.entity.Blog;
import com.hss.entity.Comment;
import com.hss.service.BlogService;
import com.hss.service.CommentService;
import com.hss.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 对评论有增加和修改状态
 * 要不要添加一个
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    /**
     * 添加评论
     * @param comment
     * @param imageCode
     * @param session
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public String  add(Comment comment,
                           @RequestParam("imageCode") String imageCode,
                           HttpSession session,
                           HttpServletResponse response,
                           HttpServletRequest request)
            throws Exception
    {

        int  resultNumber=0;
        //获取验证码,验证码的信息是保存在session中的
        String  sRand=(String)session.getAttribute("sRand");
        JSONObject result=new JSONObject();
        //验证码通过
        if(!imageCode.equals(sRand))
        {
            result.put("success",false);
            result.put("errorInfo","验证码错误");

        }
        else
        {
            Blog blog=new Blog();
            String userIp=request.getRemoteAddr();
            comment.setUserIp(userIp);
            if(comment.getId()==null)
            {
                //添加评论
                resultNumber=commentService.add(comment);
                //修改博客的评论数量
                blog=blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.update(blog);
            }
            else
            {

            }
        }
        if(resultNumber>0)
        {
            //添加评论成功
            result.put("success",true);
        }
        else{
            //添加品论失败
            result.put("success",false);
        }
        ResponseUtil.write(response,result);
        return null;
    }


}

