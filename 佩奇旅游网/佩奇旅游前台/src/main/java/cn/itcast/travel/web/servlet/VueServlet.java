package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.VueService;
import cn.itcast.travel.service.impl.VueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet("/VueServlet/*")
public class VueServlet extends BaseServlet {

    private VueService vue=new VueServiceImpl();

    public void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> users = vue.findUUser();
        writeValue(users, response);
    }

    public void setUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> userMap = request.getParameterMap();
        String uid=new String(userMap.get("uid")[0].getBytes("iso-8859-1"),"UTF-8");
        String name=new String(userMap.get("name")[0].getBytes("iso-8859-1"),"UTF-8");
        String birthday=new String(userMap.get("birthday")[0].getBytes("iso-8859-1"),"UTF-8");
        String telephone=new String(userMap.get("telephone")[0].getBytes("iso-8859-1"),"UTF-8");
        String email=new String(userMap.get("email")[0].getBytes("iso-8859-1"),"UTF-8");

        vue.setUser(uid,name,birthday,telephone,email);
    }

    public void getSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Seller> sellers = vue.findSeller();
        writeValue(sellers, response);
    }

    public void setSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> userMap = request.getParameterMap();
        String sid=new String(userMap.get("sid")[0].getBytes("iso-8859-1"),"UTF-8");
        String sname=new String(userMap.get("sname")[0].getBytes("iso-8859-1"),"UTF-8");
        String consphone=new String(userMap.get("consphone")[0].getBytes("iso-8859-1"),"UTF-8");
        String address=new String(userMap.get("address")[0].getBytes("iso-8859-1"),"UTF-8");

        vue.setSeller(sid,sname,consphone,address);
    }

    public void newSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> userMap = request.getParameterMap();
        String sname=new String(userMap.get("name")[0].getBytes("iso-8859-1"),"UTF-8");
        String consphone=new String(userMap.get("desc")[0].getBytes("iso-8859-1"),"UTF-8");
        String address=new String(userMap.get("address")[0].getBytes("iso-8859-1"),"UTF-8");
        String date=new String(userMap.get("date1")[0].getBytes("iso-8859-1"),"UTF-8");

        vue.newSeller(sname,consphone,address);
    }

    public void getRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Route> routes = vue.findRoute();
        writeValue(routes, response);
    }

    public void setRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> map = request.getParameterMap();
        String rid=new String(map.get("rid")[0].getBytes("iso-8859-1"),"UTF-8");
        String rname=new String(map.get("rname")[0].getBytes("iso-8859-1"),"UTF-8");
        String price=new String(map.get("price")[0].getBytes("iso-8859-1"),"UTF-8");
        String routeIntroduce=new String(map.get("routeIntroduce")[0].getBytes("iso-8859-1"),"UTF-8");

        vue.setRoute(rid,rname,price,routeIntroduce);
    }

    public void newRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> map = request.getParameterMap();
        String rname=new String(map.get("rname")[0].getBytes("iso-8859-1"),"UTF-8");
        String price=new String(map.get("price")[0].getBytes("iso-8859-1"),"UTF-8");
        String routeIntroduce=new String(map.get("routeIntroduce")[0].getBytes("iso-8859-1"),"UTF-8");

    }

    public void getImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rrid = request.getParameter("rid");
        response.setContentType("application/json;charset=utf-8");
        List<RouteImg> routes = vue.findRouteImage(rrid);
        writeValue(routes, response);
    }

    public void getMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Message> messages=vue.getMessage();
        writeValue(messages, response);
    }

}
