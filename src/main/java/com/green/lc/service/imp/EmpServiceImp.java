
/*
 * User:liveGreen
 * Date: 2019/5/29
 */

package com.green.lc.service.imp;

import com.green.lc.dao.EmpDAO;
import com.green.lc.entity.Emp;
import com.green.lc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 缓存相关的注解：
 * @Cacheable()  一般用于查询，没有数据改变时，例如查询等
 *
 * @CachePut 根据方法的请求参数对其结果进行换粗，和
 *      @Cacheable不同的是每次都会触发真实的方法调用，一般用于save方法上面
 *
 * @CacheEvict 针对方法配置，能够根据一定的条件对缓存进行清空，
 *      一般标注在delete、update方法上面，如果需要每次都进行
 *      清除缓存，则配置成@CacheEvict(allEntries=true)
 *
 *
 */

@Service
@CacheConfig(cacheNames = "emp")  //设置缓存的名称
public class EmpServiceImp implements EmpService {

    @Autowired
    private EmpDAO empDAO;

    @Override
    @Cacheable()  //一般用于查询，没有数据改变时
    public List<Emp> findAll() {
        return empDAO.findAll();
    }

    @Override
    @Cacheable()
    public Optional<Emp> findById(Long empId) {
        return empDAO.findById(empId);
    }


}
