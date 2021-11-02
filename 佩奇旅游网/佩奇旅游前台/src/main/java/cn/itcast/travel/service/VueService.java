package cn.itcast.travel.service;
import cn.itcast.travel.domain.*;

import java.util.List;

public interface VueService {

    public List<User> findUUser();
    public List<Seller> findSeller();
    public List<Route> findRoute();
    public List<RouteImg> findRouteImage(String rrid);
    public void setRoute(String rid,String rname,String price,String routeIntroduce);
    public void setUser(String uid,String name,String birthday,String telephone,String email);
    public void setSeller(String sid,String sname,String conphone,String address);
    public void newSeller(String sname,String conphone,String address);
    public List<Message> getMessage();
}
