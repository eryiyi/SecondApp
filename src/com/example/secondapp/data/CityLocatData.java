package com.example.secondapp.data;


import java.util.List;

import com.example.secondapp.model.SortModel;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CityLocatData {
    private String code;
    private String msg;
    private List<SortModel> data;

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

    public List<SortModel> getData() {
        return data;
    }

    public void setData(List<SortModel> data) {
        this.data = data;
    }
}
