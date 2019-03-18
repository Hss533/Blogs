package com.hss.service.impl;

import com.hss.dao.PowerDao;
import com.hss.entity.Power;
import com.hss.service.PowerService;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

@Service("powerServiceImpl")
public class PowerServiceImpl implements PowerService{

    @Resource
    private PowerDao powerDao;

    @Override
    public Power findById(Integer id) {
        return powerDao.findById(id);
    }

    /**
     * 查找出所有的权限
     * @param map
     * @return
     */
    @Override
    public List<Power> findAll(Map<String, Object> map) {
        return powerDao.findAll(map);
    }
}
