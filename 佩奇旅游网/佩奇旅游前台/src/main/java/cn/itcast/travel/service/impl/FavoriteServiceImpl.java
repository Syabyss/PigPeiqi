package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        //如果这个对象有值则为true，反之为false
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> pageFavoriteRank(int currentPage, int pageSize, String rname, int first, int last){
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //开始索引
        int start = (currentPage - 1) * pageSize;
        List<Route> routeList;
        //遍历list
        //根据rid,寻找符合条件的 route
        routeList = favoriteDao.findRouteByRangePage(start, pageSize, rname, first, last);

        for (Route r : routeList) {
            int count = favoriteDao.findCountGroupByRid(r.getRid());
            r.setCount(count);
        }
        //}
        //这个写法是错误!!int totalCount = routeList.size();
        int totalCount = favoriteDao.findCountByRangeWithOutPage(rname, first, last);
        pb.setTotalCount(totalCount);
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pb.setTotalPage(totalPage);
        pb.setList(routeList);
        return pb;
    }

    @Override
    public List<Route> hotQuery(int top) {

        List<Route> routeList = new ArrayList<>();
        Route route;
        //找到排名前几的路线
        List<Favorite> favoriteList = favoriteDao.findTopFavorite(top);

        for (Favorite favorite : favoriteList) {
            route = routeDao.findByRid(favorite.getRid());
            routeList.add(route);
        }
        return routeList;
    }
}
