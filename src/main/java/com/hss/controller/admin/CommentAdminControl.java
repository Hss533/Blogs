package com.hss.controller.admin;

import com.hss.entity.Blog;
import com.hss.entity.Comment;
import com.hss.entity.PageBean;
import com.hss.service.BlogService;
import com.hss.service.CommentService;
import com.hss.util.DateJsonValueProcessor;
import com.hss.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现这三个功能
 * 1.将未审核的评论审核
 * 2.可以更改已经审核的评论的状态
 * 3.根据文章名称查找评论
 * 0 是未审核  1  是审核已经通过 -1是审核未通过
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminControl {


    @Resource
    public BlogService blogService;
    @Resource
    public CommentService commentService;

    /**
     *
     * @param q
     * @return
     * @throws Exception
     */
    public List<Comment> findComment(String q) throws  Exception
    {
        String title="%"+q+"%";
        Map hashMap=new HashMap();
        hashMap.put("title",title);
        List<Blog> blogList=blogService.list(hashMap);
        List<Comment> commentList=new ArrayList<>();
        for (Blog blog:blogList)
        {
            int id=blog.getId();
            Map<String,Object> map=new HashMap<>();
            map.put("id",id);
            List<Comment> comments=commentService.listAdmin(map);
            for(Comment comment : comments)
            {
                commentList.add(comment);
            }
        }

        return commentList;
    }

    @RequestMapping("/list")
    public String listCommentToDoCheck(
                                       @RequestParam(value="page",required=false)String page,
                                       @RequestParam(value="rows",required=false)String rows,
                                       @RequestParam(value = "state",required = false)String state,
                                       @RequestParam(value = "title",required = false) String title,
                                       HttpServletResponse response) throws  Exception
    {

        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        int start=pageBean.getStart();
        int size=pageBean.getPageSize();
        JSONObject result=new JSONObject();
        List<Comment> commentList;
        Map<String,Object> map=new HashMap<>();
        map.put("state",state);
        map.put("start",start);
        map.put("size",size);
        Long total=0l;
        if(title==null||title==""){
            total=commentService.getTotal(map);
            commentList=commentService.listAdmin(map);
        }
        else {
            List<Comment> temp=findComment(title);
            total=Long.valueOf(temp.size());
            commentList=temp.subList(start,temp.size()<=(start+size)?temp.size():(start+size));
        }
        JsonConfig jsonConfig=new JsonConfig();
        //将日期进行格式化
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray=JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return  null;
    }


    /**
     * 修改  进行审核
     * @param ids
     * @param state
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    public String updateComments(@RequestParam(value = "ids")String ids,
                                 @RequestParam(value = "state")Integer state,
                                 HttpServletResponse response) throws  Exception
    {
        String[] length=ids.split(",");
        Integer[] integer=new Integer[length.length];
        JSONObject jsonObject=new JSONObject();
        for(int i=0;i<length.length;i++)
        {
            integer[i]=Integer.valueOf(length[i]);
            Comment comment=new Comment();
            comment.setState(state);
            comment.setId(integer[i]);
            int num=commentService.updateComment(comment);
            if(num<0)
            {
                jsonObject.put("success",false);
                break;
            }
        }
        if(jsonObject.get("success")==null)
        {
            jsonObject.put("success",true);
        }
        ResponseUtil.write(response,jsonObject);
        return  null;
    }
    @RequestMapping("/delete")
    public String deleteComments(@RequestParam(value = "ids")String ids,
                                 HttpServletResponse response) throws  Exception
    {
        String[] length=ids.split(",");
        Integer[] integer=new Integer[length.length];
        JSONObject jsonObject=new JSONObject();
        for(int i=0;i<length.length;i++)
        {
            integer[i]=Integer.valueOf(length[i]);
            int num=commentService.deleteComment(integer[i]);
            if(num<0)
            {
                jsonObject.put("success",false);
                break;
            }
        }
        if(jsonObject.get("success")==null)
        {
            jsonObject.put("success",true);
        }
        ResponseUtil.write(response,jsonObject);
        return  null;
    }
}
