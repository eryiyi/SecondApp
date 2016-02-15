package com.example.secondapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/15.
 * //		"": "20151115131835685185",
 //		"": null,
 //		"": "樱桃",
 //		"": "/Uploads/2015-11-13/5645f0b364021.jpg",
 //		"": "154",
 //		"": "0.00",
 //		"": "2号水果店",
 //		"": "0",
 //		"": "0",
 //		"": "9",
 //		"": "60.00",
 //		"": "220",
 //		"": "60.00",
 //		"": 22,
 //		"": "3"
 */
public class OrderGoods implements Serializable {
    private String goods_no;
    private String product_id;
    private String product_name;
    private String img;
    private String order_id;
    private String goods_weight;
    private String shop_name;
    private String is_checkout;
    private String is_send;
    private String delivery_id;
    private String real_price;
    private String id;
    private String goods_price;
    private String goods_id;
    private String goods_nums;

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getIs_checkout() {
        return is_checkout;
    }

    public void setIs_checkout(String is_checkout) {
        this.is_checkout = is_checkout;
    }

    public String getIs_send() {
        return is_send;
    }

    public void setIs_send(String is_send) {
        this.is_send = is_send;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getReal_price() {
        return real_price;
    }

    public void setReal_price(String real_price) {
        this.real_price = real_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_nums() {
        return goods_nums;
    }

    public void setGoods_nums(String goods_nums) {
        this.goods_nums = goods_nums;
    }
}
