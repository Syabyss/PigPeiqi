package cn.itcast.travel.dao;

import cn.itcast.travel.domain.*;

import java.util.Date;
import java.util.List;

public interface VueDao {

    public List<User> findUser();
    public List<Seller> findSeller();
    public List<Route> findRoute();
    public List<RouteImg> findRouteImage(int rrid);
    public void setRoute(int rid,String rname,double price,String routeIntroduce);
    public void setUser(int uid, String name, Date birthday, String telephone, String email);
    public void setSeller(int sid,String sname,String conphone,String address);
    public void newSeller(String sname,String conphone,String address);
    public List<Message> getMessage();
}
