package com.example.secondapp.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Order implements Serializable{
    private String ordersn;
    private String pay_money;
    private String pay_way;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public Order(String pay_way, String pay_money, String ordersn) {
        this.pay_way = pay_way;
        this.pay_money = pay_money;
        this.ordersn = ordersn;
    }
}
