package com.example.secondapp.data;

import com.example.secondapp.bean.MyOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class MyOrderBeanData {
    private String code;
    private String msg;
    private List<MyOrderBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MyOrderBean> getData() {
        return data;
    }

    public void setData(List<MyOrderBean> data) {
        this.data = data;
    }
}
