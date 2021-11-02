package cn.itcast.travel.service.impl;

import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.MyFavorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();
    private RouteDao routeDao=new RouteDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    //注册用户
    @Override
    public boolean regist(User user) {
        User u = userDao.findByUserName(user.getUsername());
        //判断u是否为null
        if(u!=null)
            return false;

        //保存用户信息、设置状态、激活码
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        //发送邮件
        String content="<a href='http://localhost:80/travel/user/active?code="+user.getCode()+"'>点击激活[佩奇旅游]</a>";
        MailUtils.sendMail(user.getEmail(),content,"佩奇旅游——激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //根据激活码查询用户
        User user=userDao.findByUserCode(code);
        //调用dao的修改激活状态的方法
        if (user!=null) {
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }
    //登录
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public PageBean<Route> favorPageQuery(int uid, int currentPage, int pageSize) {
        // 创建PageBean<Route>对象
        PageBean<Route> routePageBean = new PageBean<>();
        // 查询总记录数totalCount
        int totalCount = favoriteDao.findCountByUid(uid);
        if (totalCount == 0) {// 没有查到收藏记录
            return routePageBean;
        }
        // 计算起始记录数start，计算总页数totalPage
        int start = (currentPage - 1) * pageSize;
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
        // 分页查询rid列表（封装在MyFavorite类中）
        List<MyFavorite> pageFavoriteList = favoriteDao.findByUidAndPage(uid, start, pageSize);
        // 创新一个空的List<Route>集合
        List<Route> routeList = new ArrayList<>();
        // 遍历pageFavoriteList组装routeList
        for (MyFavorite myFavorite : pageFavoriteList) {
            // 根据其rid属性利用routeDao查route对象
            Route route = routeDao.findOne(myFavorite.getRid());
            // 向routeList中追加route属性
            routeList.add(route);
        }
        // 并组装PageBean<Route>对象
        routePageBean.setCurrentPage(currentPage);     // 设置当前页码
        routePageBean.setPageSize(pageSize);           // 设置每页显示条数
        routePageBean.setTotalPage(totalPage);         // 设置总页数
        routePageBean.setTotalCount(totalCount);       // 查询并设置总记录数
        routePageBean.setList(routeList);
        return routePageBean;
    }


}
