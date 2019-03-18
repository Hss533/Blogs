package com.hss.dao;

import com.hss.entity.Power;
import java.util.List;

import java.util.Map;

public interface PowerDao {
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
