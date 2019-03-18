package com.hss.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/image")
public class ImageController {

    /**
     * 上传照片
     */
    @RequestMapping("/upload")
    public void upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String  realPath=request.getSession().getServletContext().getRealPath("\\");
        String filename=file.getOriginalFilename();
        File dir=new File(realPath+"\\static\\image\\test\\",filename);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        file.transferTo(dir);//这个的用处应该是把文件写入硬盘

        String jsonStr="{\"errno\":\"0\",\"data\":\"\"}";
        PrintWriter out=null;
        out=response.getWriter();
        out.write(jsonStr);

        //return "redirect:/testzhaopian.jsp";
    }
}
