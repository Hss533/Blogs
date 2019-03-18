package com.hss.controller.admin;

import com.hss.entity.Blog;
import com.hss.entity.BlogType;
import com.hss.entity.PageBean;
import com.hss.service.BlogService;
import com.hss.service.BlogTypeService;
import com.hss.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
@RequestMapping("/admin/blogType/")
public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;
    @Resource
    private BlogService blogService;
    /**
     * 添加博客
     * @param response
     * @param blogType
     * @throws Exception
     */
    @RequestMapping("/add")
    public void add(HttpServletResponse response,BlogType blogType)throws Exception
    {

        Integer result=null;
        //这个是进行添加
        if(blogType!=null&&blogType.getId()==null)
        {
            result=blogTypeService.add(blogType);

        }
        //这个是进行修改
        if(blogType!=null &&blogType.getId()!=null)
        {
            result=blogTypeService.update(blogType);
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

    /**
     * 将博客类别列出
     * @param page
     * @param rows
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list")
    public void list(@RequestParam(value = "page",required = false)String page,
                     @RequestParam(value = "rows",required = false)String rows,
                     HttpServletResponse response)
            throws Exception
    {
        //第几页 每页记录数
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        List<BlogType> blogTypeList=blogTypeService.find(map);
        Long total=blogTypeService.getTotal();
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(blogTypeList);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);

    }

    /**
     * 删除博客类别
     * 再删除博客类别的时候要判别之下有没有博客
     * 如果有的话就不能进行删除
     * @param ids
     * @param response
     * @throws Exception
     */
    @RequestMapping("/delete")
    public  void delete(@RequestParam(value = "ids" ,required = false)String ids,HttpServletResponse response)throws  Exception
    {

        String[] idStrings=ids.split(",");
        JSONObject result=new JSONObject();
        for(int i=0;i<idStrings.length;i++)
        {
            Map<String,Object> map=new HashMap<>();
            map.put("typeId",Integer.parseInt(idStrings[i]));
            List<Blog> list=blogService.list(map);
            if(list.size()==0)
            {
                int resultNumber=blogTypeService.delete(Integer.parseInt(idStrings[i]));
                if(resultNumber>0)
                {
                    result.put("success",true);
                }
                else
                    result.put("success",false);
            }
            else
            {
                result.put("success",false);
                break;
            }

        }
        ResponseUtil.write(response,result);

    }

}
