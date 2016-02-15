package com.example.secondapp.data;

import com.example.secondapp.bean.FruitBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class FruitBeanData {
    private String code;
    private String msg;
    private List<FruitBean> data;

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

    public List<FruitBean> getData() {
        return data;
    }

    public void setData(List<FruitBean> data) {
        this.data = data;
    }
}
