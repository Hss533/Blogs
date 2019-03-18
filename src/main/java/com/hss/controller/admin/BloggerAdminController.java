package com.hss.controller.admin;


import com.hss.entity.Blogger;
import com.hss.service.BlogService;
import com.hss.service.BloggerService;
import com.hss.util.CryptographyUtil;
import com.hss.util.DateUtil;
import com.hss.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 加了admin的要进行身份认证
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

    @Resource
    private BloggerService bloggerService;
    /**
     * 查询博主信息
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/find")
    public String find (HttpServletResponse response) throws  Exception
    {
        Blogger blogger=bloggerService.find(null);
        JSONObject jsonObject=JSONObject.fromObject(blogger);
        ResponseUtil.write(response,jsonObject);
        return null;
    }
    /**
     *
     * @param imageFile
     * @param blogger
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(@RequestParam("imageFile") MultipartFile imageFile,
                       Blogger blogger,
                       HttpServletRequest request,
                       HttpServletResponse response)throws Exception
    {
        if(!imageFile.isEmpty()){
            String filePath=request.getServletContext().getRealPath("/");
            String imageName= DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal=bloggerService.update(blogger);
        StringBuffer result=new StringBuffer();
        if(resultTotal>0){
            result.append("<script language='javascript'>alert('修改成功');</script>");
        }else{
            result.append("<script language='javascript'>alert('修改失败');</script>");
        }
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/modifyPassword")
    public String modifyPassword(@RequestParam(value = "account")String userName,
                                 @RequestParam(value = "oldPassword")String oldPassword,
                                 @RequestParam(value = "newPassword")String newPassword,
                                 HttpServletResponse response) throws  Exception
    {
        JSONObject result=new JSONObject();
        Map<String ,Object> map=new HashMap();
        map.put("userName",userName);
        Blogger blogger=bloggerService.find(map);
        String old=CryptographyUtil.md5(oldPassword,"hss");
        if(!old.equals(blogger.getPassword()))
        {
            result.put("success",false);
        }
        else {
            blogger.setPassword(CryptographyUtil.md5(newPassword, "hss"));
            int count = bloggerService.update(blogger);
            if(count>0)
            {
                result.put("success",true);
            }
            else {
                result.put("success",false);

            }
        }
        ResponseUtil.write(response,result);
        return  null;
    }
    @RequestMapping("/logout")
    public String logout() throws  Exception
    {
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
