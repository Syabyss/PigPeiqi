package cn.itcast.travel.domain;

import java.io.Serializable;

public class Message implements Serializable {
    private int mid;
    private String message;
    private String date;
    private String status;

    public Message(){}

    public Message(int mid, String message, String date, String status) {
        this.mid = mid;
        this.message = message;
        this.date = date;
        this.status = status;
    }
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
