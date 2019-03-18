package com.hss.controller.admin;

import com.hss.entity.Blog;
import com.hss.entity.BlogType;
import com.hss.entity.Blogger;
import com.hss.entity.Link;
import com.hss.service.BlogService;
import com.hss.service.BlogTypeService;
import com.hss.service.BloggerService;
import com.hss.service.LinkService;
import com.hss.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {
    @Resource
    private BloggerService bloggerService;

    @Resource
    private LinkService linkService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private BlogService blogService;

    /**
     * Ë¢ÐÂÏµÍ³»º´æ
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ServletContext application= RequestContextUtils.getWebApplicationContext(request).getServletContext();

        Blogger blogger=bloggerService.find(null);
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);

        List<Link> linkList=linkService.find(null);
        application.setAttribute("linkList", linkList);

        List<BlogType> blogTypeCountList=blogTypeService.find(null);
        application.setAttribute("blogTypeList", blogTypeCountList);

        List<Blog> blogCountList=blogService.list(null);
        application.setAttribute("blogList", blogCountList);

        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
