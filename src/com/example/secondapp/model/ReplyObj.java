package com.example.secondapp.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/14.
 * "id": "2",
 "uid": "19",
 "product_id": "10",
 "order_id": "6",
 "content": "0.0",
 "grade": "5",
 "dateline": "1435542085"
 */
public class ReplyObj implements Serializable{
    private String id;
    private String uid;
    private String product_id;
    private String order_id;
    private String content;
    private String grade;
    private String dateline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
