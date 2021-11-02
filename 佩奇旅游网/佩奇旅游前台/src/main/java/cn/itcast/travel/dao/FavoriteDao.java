package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.MyFavorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid, int uid);

    public int findCountByRid(int rid);

    public void add(int rid ,int uid);

    public int findCountByUid(int uid) ;

    public List<MyFavorite> findByUidAndPage(int uid, int start, int pageSize) ;

    public List<Route> findRids();

    public Route findByRid(int rid);

    List<Route> findRouteByRangePage(int start, int pageSize, String rname, int first, int last);

    int findCountGroupByRid(int rid);

    int findCountByRangeWithOutPage(String rname, int first, int last);

    List<Favorite> findTopFavorite(int top);

}