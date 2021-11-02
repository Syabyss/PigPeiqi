package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteService {

    public boolean isFavorite(String rid,int uid);

    void add(String rid, int uid);

    PageBean<Route> pageFavoriteRank(int currentPage, int pageSize, String rname, int first, int last);

    List<Route> hotQuery(int top);
}
