package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.dao.RouteDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {

        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder s=new StringBuilder(sql);

        List params=new ArrayList();

        if (cid!=0){
            s.append(" and cid=? ");
            params.add(cid);
        }
        if (rname!=null && rname.length()>0){
            s.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql=s.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql="select * from tab_route where 1=1 ";
        StringBuilder s=new StringBuilder(sql);

        List params=new ArrayList();

        if (cid!=0){
            s.append(" and cid=? ");
            params.add(cid);
        }
        if (rname!=null && rname.length()>0){
            s.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        s.append(" limit ? , ? ");
        sql=s.toString();
        params.add(start);
        params.add(pageSize);

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid=? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<Route> findByCid(int cid) {
        String sql = "select * from tab_route where cid = ? order by rdate desc limit 0 , 6";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid);
    }

    @Override
    public List<Route> findByTheme() {
        String sql = "SELECT * FROM tab_route WHERE isThemeTour = '1' ORDER BY rdate DESC LIMIT 0 , 4";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<Route> findRouteByDate() {
        String sql = "select rid,rdate from tab_route order by rdate desc limit 0 , 4";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public Route findByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }
}
