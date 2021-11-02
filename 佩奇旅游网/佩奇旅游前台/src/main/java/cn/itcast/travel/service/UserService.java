package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.domain.PageBean;

//注册用户
public interface UserService {
    boolean regist(User user);

    boolean active(String code);

    User login(User user);

    public PageBean<Route> favorPageQuery(int uid, int currentPage, int pageSize) ;
}
