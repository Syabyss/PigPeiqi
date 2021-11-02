package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUserName(String username) {
        User user=null;
        try {
            //定义sql语句
            String sql = "select * from tab_user where username=?";
            //执行sql语句
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (DataAccessException e){
        }
        return user;
    }

    @Override
    public void save(User user) {
        //定义sql语句
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)"
                +"values(?,?,?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getUsername(),
                            user.getPassword(),
                            user.getName(),
                            user.getBirthday(),
                            user.getSex(),
                            user.getTelephone(),
                            user.getEmail(),
                            user.getStatus(),
                            user.getCode());
        System.out.println("save");
    }

    @Override
    public User findByUserCode(String code) {
        User user=null;
        try {
            //定义sql语句
            String sql = "select * from tab_user where code=?";
            //执行sql语句
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (DataAccessException e){
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status='Y' where uid=?";
        template.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user=null;
        try {
            //定义sql语句
            String sql = "select * from tab_user where username=? and password=?";
            //执行sql语句
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        }catch (DataAccessException e){
        }
        return user;
    }
}
