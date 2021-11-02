package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.VueDao;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class VueDaoImpl implements VueDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findUser() {

        String sql=" select * from tab_user ";
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public List<Seller> findSeller() {

        String sql=" select * from tab_seller ";
        return template.query(sql, new BeanPropertyRowMapper<Seller>(Seller.class));
    }

    @Override
    public List<Route> findRoute() {

        String sql=" SELECT * FROM tab_route ";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<RouteImg> findRouteImage(int rrid) {

        String sql=" SELECT * FROM tab_route_img WHERE rid = "+rrid ;
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class));
    }

    @Override
    public void setRoute(int rid, String rname, double price, String routeIntroduce) {
        String sql="UPDATE tab_route SET rname= ? ,price= ? ,routeIntroduce= ? WHERE rid="+rid;
        template.update(sql,rname,price,routeIntroduce);
    }

    @Override
    public void setUser(int uid, String name, Date birthday, String telephone, String email) {
        String sql="UPDATE tab_user SET name= ? ,birthday= ? ,telephone= ? ,email= ? WHERE uid="+uid;
        template.update(sql,name,birthday,telephone,email);
    }

    @Override
    public void setSeller(int sid, String sname, String conphone, String address) {
        String sql="UPDATE tab_seller SET sname= ? ,consphone= ? ,address= ?  WHERE sid="+sid;
        template.update(sql,sname,conphone,address);
    }

    @Override
    public void newSeller(String sname, String conphone, String address) {

        String sql1="SELECT COUNT(*) FROM tab_seller ";
        int sid=template.queryForObject(sql1,Integer.class)+1;
        String sql="INSERT INTO tab_seller SET sid= ? ,sname= ? ,consphone= ? ,address= ? ";
        template.update(sql,sid,sname,conphone,address);
    }

    @Override
    public List<Message> getMessage() {
        String sql=" select * from tab_message ";
        return template.query(sql, new BeanPropertyRowMapper<Message>(Message.class));
    }
}
