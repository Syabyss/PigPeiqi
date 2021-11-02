package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao=new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {


        List<Category>cs=null;

        //判断集合是否为空
            //如果为空，从数据库查询，再将数据存入redis
            cs= categoryDao.findAll();
            for (int i=0;i<cs.size();i++){


                System.out.println("从mysql查询");
            }
        return cs;
    }
}
