package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.domain.PageBean;
import com.alibaba.druid.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    //分页查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");
        //String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        String rname = null;
        //用户输入的查询rname
        String rnameStr = request.getParameter("rname");

        if(rnameStr!=null&&rnameStr.length()>0&&!"null".equals(rnameStr)) {
            rname = new String(rnameStr.getBytes("iso8859-1"), "utf-8");
        }

        int cid = 0;
        //处理参数
        if(cidStr!=null&&cidStr.length()>0&& !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 1;
        //处理参数
        if(!StringUtils.isEmpty(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }
        int pageSize = 10;
        //处理参数
        //if(pageSizeStr!=null&&pageSizeStr.length()>0){
        //    pageSize = Integer.parseInt(pageSizeStr);
        //}

        //调用service查询pagebean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //将pagebean对象序列化未json返回
        writeValue(pb,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收id
        String rid = request.getParameter("rid");
        //调用service查询route对象
        Route route = routeService.findOne(rid);
        writeValue(route, response);
        //转回为json写回客户端
    }

    //判断是否收藏过
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rid = request.getParameter("rid");

        //获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }

        //调用favoriteservice查询是否收藏过
        boolean flag = favoriteService.isFavorite(rid, uid);

        //写回客户端
        writeValue(flag, response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取线路rid
        String rid = request.getParameter("rid");

        //获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if (user == null) {
            return;
        } else {
            uid = user.getUid();
        }

        //调用service添加
        favoriteService.add(rid,uid);
    }


    public void popularTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.popularTravel();
        writeValue(routes, response);
    }

    public void newestTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.newestTravel();
        writeValue(routes, response);
    }

    public void themeTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.themeTravel();
        writeValue(routes, response);
    }

    public void inTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.inTravel();
        writeValue(routes, response);
    }

    public void outTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.outTravel();
        writeValue(routes, response);
    }
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String rnameStr = request.getParameter("rname");
        String rname = null;
        if(rnameStr!=null){
            //中文乱码问题
            rname = new String(rnameStr.getBytes("iso8859-1"),"utf-8");
        }

        //获取当前页码数
        String currentPageStr = request.getParameter("currentPage");
        int currentPage = 1;
        if(!StringUtils.isEmpty(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }

        //获取价格范围
        int first = 0;

        String firstStr = request.getParameter("first");
        if(!StringUtils.isEmpty(firstStr)){
            first = Integer.parseInt(firstStr);
        }
        int last = 0;
        String lastStr = request.getParameter("last");
        if(!StringUtils.isEmpty(lastStr)){
            last = Integer.parseInt(lastStr);
        }
        //每页显示个数
        int pageSize = 4;

        PageBean<Route> pb = favoriteService.pageFavoriteRank(currentPage,pageSize,rname,first,last);

        writeValue(pb,response);
    }

    public void hotQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //显示的热门数量
            int top  = 5;
            List<Route> routeList = favoriteService.hotQuery(top);

            writeValue(routeList,response);

    }
}