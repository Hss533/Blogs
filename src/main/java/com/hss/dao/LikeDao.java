package com.hss.dao;

import com.hss.entity.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeDao
{

    int add(@Param("articleId") String articleId,@Param("Ip") String Ip);
    int update(@Param("articleId") String articleId,@Param("Ip") String Ip,@Param("status")Integer status);
    Like findByArtcileIdAndUserIp(@Param("articleId") String articleId,@Param("Ip") String Ip);
    List<Like> findByArtcileId(String articleId);

}
