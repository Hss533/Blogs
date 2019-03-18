package com.hss.util;

// todo 因为这是一个个人博客，所以别人可以观看，在看的时候，可以进行点赞
// todo 记录点赞的时候，根据每个人的IP进行记录，点过赞了不能再次进行点赞 也可以取消点赞 使用什么存储结构在redis中进行存储呢？

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 只有一个表
 * 设置一个id  一个文章id
 * 然后一列是存储文章的点赞数量的
 * 还有一列是存储文章的点赞
 *
 *
 *
 */
public class RredisUtil
{

    private static  JedisPool jedisPool;
    public RredisUtil(String ip, int port)
    {
        jedisPool=new JedisPool(ip,port);//简单的配置
        System.out.println(jedisPool.getResource().ping());
    }
    public static Jedis getJedisPool()
    {
        Jedis jedis=jedisPool.getResource();
        return jedis;
    }
}
