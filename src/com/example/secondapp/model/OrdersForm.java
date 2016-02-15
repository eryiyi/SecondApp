package com.example.secondapp.model;

import java.io.Serializable;
import java.util.Map;

/**
 * author: ${zhanghailong}
 * Date: 2015/3/27
 * Time: 14:59
 * 类的功能、说明写在此处.
 */
public class OrdersForm implements Serializable {
    private Map<String,String> list;

    public Map<String, String> getList() {
        return list;
    }

    public void setList(Map<String, String> list) {
        this.list = list;
    }
}
