package com.hss.controller.admin;

import com.hss.entity.Blog;
import com.hss.entity.PageBean;
import com.hss.lucene.BlogIndex;
import com.hss.service.BlogService;
import com.hss.util.DateJsonValueProcessor;
import com.hss.util.ResponseUtil;
import com.hss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台博客操作
 */
@Controller
@RequestMapping("/admin/blog/")
public class BlogAdminController {

    @Resource
    private BlogService blogService;

    private BlogIndex blogIndex=new BlogIndex();

    /**
     * 保存博客
     * @param response
     * @param blog
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public void save(HttpServletResponse response,Blog blog)throws Exception
    {

        Integer result=null;
        //这个是进行修改的
        if(blog!=null&&blog.getId()!=null)
        {
            result=blogService.update(blog);
            blogIndex.updateIndex(blog);
        }
        //这个是进行添加
        if(blog!=null&&blog.getId()==null)
        {
            result=blogService.addBlog(blog);
            blogIndex.addIndex(blog);
         }

        JSONObject resultJson=new JSONObject();

        if(result>0)
        {
            resultJson.put("success",true);
        }else {
            resultJson.put("success",false);
        }
        ResponseUtil.write(response,resultJson);

    }

    @RequestMapping("/findById")
    public String findById(@RequestParam(value = "id")String id,HttpServletResponse response) throws Exception
    {
        Blog blog=blogService.findById(Integer.valueOf(id));
        JSONObject resultJson=JSONObject.fromObject(blog);

        ResponseUtil.write(response,resultJson);
        return null;
    }
    /**
     * 根据每页记录数和第几页和查询条件来列出博客列表
     * @param page
     * @param rows
     * @param response
     * @param s_blog
     * @throws Exception
     */
    @RequestMapping("/list")
    public void list(@RequestParam(value = "page",required = false)String page,
                     @RequestParam(value = "rows",required = false)String rows,
                     HttpServletResponse response,
                     Blog s_blog)
            throws Exception
    {
        //第几页 每页记录数
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        List<Blog> blogList=blogService.list(map);
        Long total=blogService.getTotal(map);
        JSONObject result=new JSONObject();
        //将日期类的json转换成json类的
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);

    }
    @RequestMapping("/delete")
    public  void delete(@RequestParam(value = "ids" ,required = false)String ids,HttpServletResponse response)throws  Exception
    {

        int flag=0;
        System.out.println(ids);
        String[] idStrings=ids.split(",");
        for(int i=0;i<idStrings.length;i++)
        {
            int result=blogService.deleteBlog(Integer.parseInt(idStrings[i]));
            blogIndex.deleteIndex(idStrings[i]);
            if(result==0)
            {
                flag=1;
            }
        }
        JSONObject result=new JSONObject();

        if(flag==1)
        {
            result.put("success",false);
        }
        else result.put("success",true);
        ResponseUtil.write(response,result);

    }

}
