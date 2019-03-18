package com.hss.init;
import com.hss.entity.Blog;
import com.hss.entity.BlogType;
import com.hss.entity.Blogger;
import com.hss.entity.Link;
import com.hss.service.BlogService;
import com.hss.service.BlogTypeService;
import com.hss.service.BloggerService;
import com.hss.service.LinkService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;


/**
 * 用DefaultListableBeanFactory实现的容器实例化对象时不会调用ApplicationContextAware方法
 改用ClassPathXmlApplicationContext实现的容器则没问题。
 */
/**
 * 在启动项目的时候进行加载
 */
@Component
public class InitComponent  implements ServletContextListener,ApplicationContextAware
{
    private static ApplicationContext applicationCon;//= new ClassPathXmlApplicationContext("applicationContext.xml");;

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {

        //初始化的时候
        ServletContext application = sce.getServletContext();
        System.out.println(applicationCon);
        //相当于是设置svriece bean
        BloggerService bloggerService=(BloggerService)applicationCon.getBean("bloggerService");
        Blogger blogger=bloggerService.find(null);
        blogger.setPassword(null);
        application.setAttribute("blogger",blogger);

        LinkService linkService=(LinkService) applicationCon.getBean("linkService");
        List<Link> linkList=linkService.find(null);
        application.setAttribute("linkList", linkList);

        BlogService blogService=(BlogService)applicationCon.getBean("blogService");
        List<Blog> blogList=blogService.countList();
        application.setAttribute("dateList",blogList);

        BlogTypeService blogTypeService=(BlogTypeService)applicationCon.getBean("blogTypeService");
        List<BlogType> blogTypeList=blogTypeService.countList();
        application.setAttribute("blogTypeList",blogTypeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationCon = applicationContext;
    }

}
