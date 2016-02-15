package com.example.secondapp.bean;

import java.io.Serializable;
import java.util.List;
//		"": null,
//		"": "585.00",
//		"invoice_title": "",
//		"status": "1",
//		"prop": null,
//		"pay_time": "0",
//		"postcode": "",
//		"distribution": "10",
//		"discount": "0.00",
//		"country": "86",
//		"point": "0",
//		"exp": "0",
//		"insured": "0.00",
//		"address": "台卅人民路9876",
//		"if_insured": "0",
//		"create_time": "1447564715",
//		"goods_data": [
//		{

//		},
//

public class MyOrderBean implements Serializable{
	private String id;
	private String order_no;
	private String user_id;
	private String pay_type;
	private String distribution;
	private String status;
	private String accept_name;
	private String postcode;
	private String telephone;
	private String country;
	private String province;
	private String city;
	private String area;
	private String address;
	private String mobile;
	private String payable_amount;
	private String real_amount;
	private String payable_freight;
	private String real_freight;
	private String pay_time;
	private String send_time;
	private String create_time;
	private String completion_time;
	private String invoice;
	private String postscript;
	private String note;
	private String if_del;
	private String insured;
	private String if_insured;
	private String pay_fee;
	private String invoice_title;
	private String taxes;
	private String promotions;
	private String discount;
	private String order_amount;
	private String prop;
	private String accept_time;
	private String exp;
	private String point;
	private String type;
	private String trade_no;
	private String takeself;
	private String order_id;
	private String goods_id;
	private String img;
	private String product_id;
	private String goods_price;
	private String real_price;
	private String goods_nums;
	private String goods_weight;
	private String goods_array;
	private String is_send;
	private String is_checkout;
	private String delivery_id;

	private List<OrderGoods> goods_data;

	public List<OrderGoods> getGoods_data() {
		return goods_data;
	}

	public void setGoods_data(List<OrderGoods> goods_data) {
		this.goods_data = goods_data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccept_name() {
		return accept_name;
	}

	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPayable_amount() {
		return payable_amount;
	}

	public void setPayable_amount(String payable_amount) {
		this.payable_amount = payable_amount;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

	public String getPayable_freight() {
		return payable_freight;
	}

	public void setPayable_freight(String payable_freight) {
		this.payable_freight = payable_freight;
	}

	public String getReal_freight() {
		return real_freight;
	}

	public void setReal_freight(String real_freight) {
		this.real_freight = real_freight;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCompletion_time() {
		return completion_time;
	}

	public void setCompletion_time(String completion_time) {
		this.completion_time = completion_time;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIf_del() {
		return if_del;
	}

	public void setIf_del(String if_del) {
		this.if_del = if_del;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getIf_insured() {
		return if_insured;
	}

	public void setIf_insured(String if_insured) {
		this.if_insured = if_insured;
	}

	public String getPay_fee() {
		return pay_fee;
	}

	public void setPay_fee(String pay_fee) {
		this.pay_fee = pay_fee;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getAccept_time() {
		return accept_time;
	}

	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTakeself() {
		return takeself;
	}

	public void setTakeself(String takeself) {
		this.takeself = takeself;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getReal_price() {
		return real_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getGoods_nums() {
		return goods_nums;
	}

	public void setGoods_nums(String goods_nums) {
		this.goods_nums = goods_nums;
	}

	public String getGoods_weight() {
		return goods_weight;
	}

	public void setGoods_weight(String goods_weight) {
		this.goods_weight = goods_weight;
	}

	public String getGoods_array() {
		return goods_array;
	}

	public void setGoods_array(String goods_array) {
		this.goods_array = goods_array;
	}

	public String getIs_send() {
		return is_send;
	}

	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}

	public String getIs_checkout() {
		return is_checkout;
	}

	public void setIs_checkout(String is_checkout) {
		this.is_checkout = is_checkout;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
}
