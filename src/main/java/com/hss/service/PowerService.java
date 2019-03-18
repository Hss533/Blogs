package com.hss.service;

import com.hss.entity.Power;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

import java.util.Map;

public interface PowerService {
    /**
     * 查找所有的权限
     * @return
     */
     List<Power> findAll(Map<String,Object> map);

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
     Power findById(Integer id);
}
