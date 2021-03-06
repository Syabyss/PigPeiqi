package cn.itcast.travel.domain;

import java.io.Serializable;


public class Favorite implements Serializable {

    private Route route;                                        //旅游线路对象
    private String date;                                        //收藏时间
    private User user;                                          //所属用户
    private int rid;


    public Favorite() {

    }

    public Favorite(Route route, String date, User user) {
            this.route = route;
            this.date = date;
            this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
