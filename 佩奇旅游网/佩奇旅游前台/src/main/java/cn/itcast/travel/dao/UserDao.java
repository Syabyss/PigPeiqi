package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    //根据用户名查询用户信息
    public User findByUserName(String name);

    public void save(User user);

    User findByUserCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);

}
