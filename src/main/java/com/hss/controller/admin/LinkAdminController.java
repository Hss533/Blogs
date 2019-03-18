package com.hss.controller.admin;

import com.hss.entity.Link;
import com.hss.service.LinkService;
import com.hss.util.ResponseUtil;
import net.sf.json.JSON;
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
 * 后台友情链接controller模块
 */

@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Resource
    private LinkService linkService;

    /**
     * 展示所有的链接
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page",required = false)String page,
                       @RequestParam(value = "rows",required = false)String rows,
                       HttpServletResponse response)
    throws Exception
    {
        Map<String,Object> map=new HashMap<>();
        if(page!=null&&rows!=null)
        {
            int pageInteger=Integer.valueOf(page);
            int rowsInteger=Integer.valueOf(rows);
            int start=rowsInteger*(pageInteger-1);
            int size=rowsInteger;
            map.put("start",start);
            map.put("size",size);
        }
        List<Link> linkList=linkService.find(map);
        JSONObject result=new JSONObject();
        JSONArray jsonArray= JSONArray.fromObject(linkList);
        result.put("rows",jsonArray);
        result.put("total",linkList.size());
        ResponseUtil.write(response,result);
        return null;
    }
    @RequestMapping("/add")
    public String list(Link link,
                       HttpServletResponse response)
            throws Exception
    {
        int total=0;
        if(link.getId()==null)
        {
            //添加
            total=linkService.add(link);
        }else {
            //修改
             total =linkService.update(link);
        }
        JSONObject resultJson=new JSONObject();
        if(total>0)
        {
            resultJson.put("success",true);
        }else {
            resultJson.put("success",false);
        }
        ResponseUtil.write(response,resultJson);
        return null;
    }
    @RequestMapping ("/delete")
    public String  delete(@RequestParam(value = "ids")String ids,HttpServletResponse response) throws  Exception
    {
        JSONObject result=new JSONObject();
        String[] string=ids.split(",");
        Integer[] integers=new Integer[string.length];
        for(int i=0;i<string.length;i++)
        {
            integers[i]=Integer.valueOf(string[i]);

        }
        for(int i=0;i<integers.length;i++)
        {
            int num=linkService.delete(integers[i]);
            if(num<=0)
            {
                System.out.println("删除失败");
                result.put("success",false);
                break;
            }
        }
        if(result.get("success")==null)
        {
            result.put("success",true);
        }
        ResponseUtil.write(response,result);
        return null;
    }
}
