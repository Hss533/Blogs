package com.hss.controller;

import com.hss.entity.Blog;
import com.hss.entity.Comment;
import com.hss.lucene.BlogIndex;
import com.hss.service.BlogService;
import com.hss.service.CommentService;
import com.hss.util.ResponseUtil;
import com.hss.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客Controller层
 */
@Controller
@RequestMapping("/blog")
public class BlogController {


    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;



    private BlogIndex blogIndex=new BlogIndex();

    /**
     * 请求博客的详细信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView detials(@PathVariable("id")Integer id,
                                @RequestParam(value = "typeId",required=false)String typeId,
                                @RequestParam(value = "releaseDateStr",required=false)String releaseDateStr,
                                HttpServletRequest request) throws  Exception
    {


            ModelAndView modelAndView=new ModelAndView();
            Blog blog=blogService.findById(id);

            /*博客关键字*/
            List<String> keyWordList=new ArrayList();
            if(blog.getKeyWord()!=null&& blog.getKeyWord()!="")
            {
                for (int i = 0; i < blog.getKeyWord().split(" ").length; i++)
                {
                    keyWordList.add(blog.getKeyWord().split(" ")[i]);
                }
                keyWordList = StringUtil.filterWhite(keyWordList);
            }
            if(keyWordList.size()==0)
                keyWordList.add("暂无关键字");

            /*博客点击量增加1*/
            blog.setClickHit(blog.getClickHit()+1);//凡是访问一次这个 访问量就增加1
            blogService.update(blog);

            /* 下一篇 */
            Blog nextBlog;
            Map<String,Object> mapNext=new HashMap<>();
            mapNext.put("id",id);
            mapNext.put("releaseDateStr",releaseDateStr);
            mapNext.put("typeId",typeId);

            nextBlog=blogService.getNextBlog(mapNext);

            if(nextBlog==null)
            {
                nextBlog=new Blog();
                nextBlog.setTitle("已经是最后一篇");
            }

            /*上一篇*/
            Blog preBlog;
            Map<String,Object> mapPre=new HashMap<>();
            mapPre.put("id",id);
            mapPre.put("releaseDateStr",releaseDateStr);
            mapPre.put("typeId",typeId);

            preBlog=blogService.getPreBlog(mapPre);

            if(preBlog==null)
            {
                preBlog=new Blog();
                preBlog.setTitle("已经是第一篇");

            }

            /*博客评论*/
            Map<String,Object> mapComment=new HashMap<>();
            mapComment.put("blogId",blog.getId());
            List<Comment> commentList=commentService.list(mapComment);

            if(typeId!=null)
            {
                //根据typeId进行搜索得到的
                modelAndView.addObject("typeId",typeId);

            }
            if(releaseDateStr!=null)
            {
                //根据发布日期搜索得到的
                modelAndView.addObject("releaseDate",releaseDateStr);
            }

            boolean like=blogService.checkLikeOrUnlike(id,request.getRemoteAddr());
            if(like==false)//like是true 表示没有点赞
            {
                modelAndView.addObject("like","like");

            }
            else modelAndView.addObject("like","unlike");

            modelAndView.addObject("blog",blog);
            modelAndView.addObject("mainPage","foreground/blog/view.jsp");
            modelAndView.addObject("pageTitle",blog.getTitle());
            modelAndView.addObject("keyWordList",keyWordList);
            modelAndView.addObject("nextBlog",nextBlog);
            modelAndView.addObject("preBlog",preBlog);
            modelAndView.addObject("commentList",commentList);
            modelAndView.addObject("commentListLength",commentList.size());
            modelAndView.setViewName("mainTemp");

            return  modelAndView;
    }
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value = "q",required = false)String q,
                               @RequestParam(value = "page",required = false)String  page,
                               HttpServletRequest request)throws Exception
    {
        int size=10;
        if(page==null||page=="")
            page="1";
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pageTitle","搜索'"+q+"'--hss个人博客");
        modelAndView.addObject("mainPage","/foreground/blog/result.jsp");
        List<Blog> blogList=blogIndex.searchBlog(q);
        Integer index=blogList.size()<=(Integer.valueOf(page)*10)?blogList.size():(Integer.valueOf(page)*10);
        modelAndView.addObject("blogList",blogList.subList((Integer.valueOf(page)-1)*size,index));
        modelAndView.addObject("q",q);
        modelAndView.addObject("pageCode",genUpAndDownPageCode(Integer.parseInt(page),blogList.size(),q,size,request.getServletContext().getContextPath()));
        modelAndView.addObject("resultTotal",blogList.size());
        modelAndView.setViewName("mainTemp");
        return  modelAndView;
    }

    private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext)
    {
        long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode=new StringBuffer();
        if(totalPage==0){
            return "";
        }else{
            pageCode.append("<nav>");
            pageCode.append("<ul class='pager'>");
            if(page>1){
                pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>ÉÏÒ»Ò³</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='#'>ÉÏÒ»Ò³</a></li>");
            }
            if(page<totalPage){
                pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>ÏÂÒ»Ò³</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='#'>ÏÂÒ»Ò³</a></li>");
            }
            pageCode.append("</ul>");
            pageCode.append("</nav>");
        }
        return pageCode.toString();
    }

    @RequestMapping("/likeOrUnlike.do")
    public String like(@RequestParam(value = "id",required = false)Integer id,
                       @RequestParam(value = "status",required = false)Integer status,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        //status=0 说明没有点赞
        request.getRemoteAddr();
        int count=blogService.likeOrUnlike(id,request.getRemoteAddr(),status);
        JSONObject result=new JSONObject();
        String str;
        if(count==1)
        {
            result.put("success",true);
            if(status==0)
            {
               str="点赞成功";
               result.put("successInfo",str);
            }
            else
            {
                str="取消点赞成功";
                result.put("successInfo",str);
            }
        }
        else
        {
            str="操作失败";
            result.put("errorInfo",str);
        }
        ResponseUtil.write(response,result);
        return null;
    }

}
