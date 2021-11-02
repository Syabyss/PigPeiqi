package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.VueDao;
import cn.itcast.travel.dao.impl.VueDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.VueService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VueServiceImpl implements VueService {

    private VueDao vueDao=new VueDaoImpl();

    @Override
    public List<User> findUUser() {
        List<User> users= vueDao.findUser();
        return users;
    }

    @Override
    public List<Seller> findSeller() {
        List<Seller> seller= vueDao.findSeller();
        return seller;
    }

    @Override
    public List<Route> findRoute() {
        List<Route> route= vueDao.findRoute();
        return route;
    }

    @Override
    public List<RouteImg> findRouteImage(String rrid) {
        List<RouteImg> routeImge= vueDao.findRouteImage(Integer.parseInt(rrid));
        System.out.println(rrid);
        return routeImge;
    }

    @Override
    public void setRoute(String rid, String rname,String price, String routeIntroduce) {
        int rrid=Integer.parseInt(rid);
        double pprice=Double.parseDouble(price);
        vueDao.setRoute(rrid,rname,pprice,routeIntroduce);
    }

    @Override
    public void setUser(String uid, String name, String birthday, String telephone, String email) {
        int uuid=Integer.parseInt(uid);
        try {
            Date bbirthday = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            vueDao.setUser(uuid,name,bbirthday,telephone,email);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSeller(String sid, String sname, String conphone, String address) {
        int ssid=Integer.parseInt(sid);
        vueDao.setSeller(ssid,sname,conphone,address);
    }

    @Override
    public void newSeller(String sname, String conphone, String address) {
        vueDao.newSeller(sname,conphone,address);
    }

    @Override
    public List<Message> getMessage() {
        List<Message> messages= vueDao.getMessage();
        return messages;
    }
}
