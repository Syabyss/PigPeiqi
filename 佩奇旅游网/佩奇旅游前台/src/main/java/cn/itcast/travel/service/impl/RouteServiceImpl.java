package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao=new RouteDaoImpl();
    private CategoryDao categoryDao=new CategoryDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        //封装pagebean
        PageBean<Route> pb=new PageBean<Route>();

        //设置当前页码
        pb.setCurrentPage(currentPage);

        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示数据集合
        int start=(currentPage-1)*pageSize;
        List<Route> list=routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数
        int totalPage=totalCount %pageSize==0? (totalCount/pageSize):(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);
        Seller seller=sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }

    @Override
    public List<Route> popularTravel() {
        List<Route> fav_list = favoriteDao.findRids();
        List<Route> routes_list = new ArrayList<>();

        for (int i = 0; i < fav_list.size(); i++) {
            Route route = favoriteDao.findByRid(fav_list.get(i).getRid());
            routes_list.add(route);
        }
        return routes_list;
    }

    @Override
    public List<Route> newestTravel() {
        List<Route> routes = routeDao.findRouteByDate();
        List<Route> routes_list = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {
            Route route = favoriteDao.findByRid(routes.get(i).getRid());
            routes_list.add(route);
        }
        return routes_list;
    }


    @Override
    public List<Route> themeTravel() {
        List<Route> routes = routeDao.findByTheme();
        List<Route> routes_list = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {
            Route route = favoriteDao.findByRid(routes.get(i).getRid());
            routes_list.add(route);
        }
        return routes_list;
    }

    @Override
    public List<Route> inTravel() {
        String cname = "国内游";
        Category category = categoryDao.findByName(cname);
        List<Route> routes = routeDao.findByCid(category.getCid());
        List<Route> routes_list = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {
            Route route = favoriteDao.findByRid(routes.get(i).getRid());
            routes_list.add(route);
        }
        return routes_list;
    }

    @Override
    public List<Route> outTravel() {
        String cname = "出境游";
        Category category = categoryDao.findByName(cname);
        List<Route> routes = routeDao.findByCid(category.getCid());
        List<Route> routesList = new ArrayList<>();

        for (Route value : routes) {
            Route route = favoriteDao.findByRid(value.getRid());
            routesList.add(route);
        }
        return routesList;
    }
}
