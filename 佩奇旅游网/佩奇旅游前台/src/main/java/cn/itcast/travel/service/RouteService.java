package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.PageBean;

import java.util.List;

public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    Route findOne(String rid);
    public List<Route> popularTravel();

    public List<Route> newestTravel();

    public List<Route> themeTravel();

    public List<Route> inTravel();

    public List<Route> outTravel();


}
