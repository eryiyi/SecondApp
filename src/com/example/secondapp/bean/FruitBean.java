package com.example.secondapp.bean;

import com.example.secondapp.model.ReplyObj;

import java.io.Serializable;
import java.util.List;

public class FruitBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public int product_id;
	public String product_name;
	public String community_id;
	public String shop_id;
	public String type_id;
	public String product_pic;
	public String product_pic1;
	public String product_pic2;
	public String product_pic3;
	public String buy_numbers;
	public String is_discount;
	public String is_tuangou;
	public String price_tuangou;
	public String price;
	public String info;
	public String discount;
	public String tuangou_numbers;
	public String unit;
	public String dateline;
	public String is_open;
	public String is_num;
	public String delivery_type;
	public String pay_type;
	public String product_audit;
	public String type_name;
	public String uid;
	public String type_logo;
	public String example;
	public String countNum;

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	private List<ReplyObj> reply;

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public List<ReplyObj> getReply() {
		return reply;
	}

	public void setReply(List<ReplyObj> reply) {
		this.reply = reply;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(String community_id) {
		this.community_id = community_id;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getProduct_pic() {
		return product_pic;
	}

	public void setProduct_pic(String product_pic) {
		this.product_pic = product_pic;
	}

	public String getProduct_pic1() {
		return product_pic1;
	}

	public void setProduct_pic1(String product_pic1) {
		this.product_pic1 = product_pic1;
	}

	public String getProduct_pic2() {
		return product_pic2;
	}

	public void setProduct_pic2(String product_pic2) {
		this.product_pic2 = product_pic2;
	}

	public String getProduct_pic3() {
		return product_pic3;
	}

	public void setProduct_pic3(String product_pic3) {
		this.product_pic3 = product_pic3;
	}

	public String getBuy_numbers() {
		return buy_numbers;
	}

	public void setBuy_numbers(String buy_numbers) {
		this.buy_numbers = buy_numbers;
	}

	public String getIs_discount() {
		return is_discount;
	}

	public void setIs_discount(String is_discount) {
		this.is_discount = is_discount;
	}

	public String getIs_tuangou() {
		return is_tuangou;
	}

	public void setIs_tuangou(String is_tuangou) {
		this.is_tuangou = is_tuangou;
	}

	public String getPrice_tuangou() {
		return price_tuangou;
	}

	public void setPrice_tuangou(String price_tuangou) {
		this.price_tuangou = price_tuangou;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getTuangou_numbers() {
		return tuangou_numbers;
	}

	public void setTuangou_numbers(String tuangou_numbers) {
		this.tuangou_numbers = tuangou_numbers;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getIs_open() {
		return is_open;
	}

	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}

	public String getIs_num() {
		return is_num;
	}

	public void setIs_num(String is_num) {
		this.is_num = is_num;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getProduct_audit() {
		return product_audit;
	}

	public void setProduct_audit(String product_audit) {
		this.product_audit = product_audit;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType_logo() {
		return type_logo;
	}

	public void setType_logo(String type_logo) {
		this.type_logo = type_logo;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}
}
