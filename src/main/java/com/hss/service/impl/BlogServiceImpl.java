package com.hss.service.impl;

import com.hss.dao.BlogDao;
import com.hss.dao.LikeDao;
import com.hss.entity.Blog;
import com.hss.entity.Like;
import com.hss.service.BlogService;
import com.hss.util.RredisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Resource
    private LikeDao likeDao;

    @Override
    public List<Blog> countList()
    {
        return blogDao.countList();
    }

    @Override
    public List<Blog> list(Map<String, Object> map) {

        return blogDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogDao.getTotal(map);
    }

    @Override
    public Blog findById(Integer id) {
        return blogDao.findById(id);
    }

    @Override
    public Integer update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public Blog getNextBlog(Map<String, Object> map) {
        return blogDao.getNextBlog(map);
    }

    @Override
    public Blog getPreBlog(Map<String, Object> map) {
        return blogDao.getPreBlog(map);
    }

    @Override
    public Integer addBlog(Blog blog) {
        return blogDao.addBlog(blog);
    }

    @Override
    public Integer deleteBlog(Integer id) {
        return blogDao.deleteBlog(id);
    }


    public Integer likeOrUnlike(Integer articleId,String Ip,Integer status)
    {
        Jedis jedis=RredisUtil.getJedisPool();
        try{
                if(status==0)
                {
                    //表示未点赞过
                    jedis.zadd("article"+articleId,1,Ip);
                }
                else
                {
                    //表示取消点赞
                   jedis.zadd("article"+articleId,0,Ip);
                }
            }catch (Exception e)
            {
                return 0;
            }finally
            {
                jedis.close();
            }
        return 1;
    }

    //没有点赞返回false 点了赞表示true
    public boolean checkLikeOrUnlike(Integer articleId,String Ip)
    {
        Jedis jedis=RredisUtil.getJedisPool();
        if(jedis!=null)
        if(jedis.zrank("article"+articleId,Ip)!=null)
        {
            if(jedis.zscore("article"+articleId,Ip)==0)
            {
                jedis.close();
                return false;
            }
            if (jedis.zscore("article"+articleId,Ip)==1)
            {
                jedis.close();
                return true;
            }
        }
        //redis中没有找到的话 去数据库中找
        Like like=likeDao.findByArtcileIdAndUserIp("article"+articleId,Ip);
        if(like==null)
        {
            return false;
        }
        else if(like.getStatus()==0){
            return false;
        }
        else return true;
    }

}
