package com.example.secondapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.PayUtils;
import com.alipay.sdk.pay.PayUtils.ResponseHandler;
import com.alipay.sdk.pay.SignUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.secondapp.R;
import com.example.secondapp.adapter.ItemGoodsAdapter;
import com.example.secondapp.adapter.OnClickContentItemListener;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.AdressBean;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.bean.MyOrderBean;
import com.example.secondapp.data.Order;
import com.example.secondapp.data.OrderDATA;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.model.OrdersForm;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderConfirm extends BaseActivity implements OnClickListener,OnClickContentItemListener {
	public static List<AdressBean> list;
	RelativeLayout paymode2;
	RelativeLayout paymode3;
	CheckBox checked1;
	CheckBox checked2;
	CheckBox checked3;
	LinearLayout back;
	String orderid;
	String order;
	RelativeLayout modifyadress;
	private AdressBean adressBean;
//	FruitBean fruitBean;
//	TextView comsuppri;
//	ImageView comimage;
//	TextView comname;
//	TextView comprice;
//	TextView paid;
	TextView textpm;
	String count;
	TextView name;
	TextView phonenumber;
	TextView adress;
	Button pay;
	double total;
	CheckBox checkm;
	CheckBox checkn;
	private Order order_ob;
	private List<FruitBean> fruitlist;
	ItemGoodsAdapter adpater;
	private String typePay;
	public static Double doublePrices;
	private MyOrderBean myOrderBean;
	private RequestQueue requestQueue;
	public static String timeslot = "上午7点到11点";
	TextView morning;
	TextView afterroon;
	//---------------------------------支付开始----------------------------------------
	// 商户PID
	public static final String PARTNER = "2088021404950836";
	// 商户收款账号
	public static final String SELLER = "794370578@QQ.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMo10/sPXGOIlZSz" +
			"qfQtKApeHpzvq57dtwpiWVsbyu/jpLFeb+51IxSXrdgItDp/By42Jkxlv4KjTGLP" +
			"SsI1UO06liAq40ll439Oy3fvHn3MRJzUFnBZiwb4ksTMXoMbDLJ7VavXRi5Carfs" +
			"sIzOHovhEUA7rpQY10MnmUVyOaLLAgMBAAECgYEAsu4+zgIhttG9yv/QfAkZTVNL" +
			"s4NBUirPNZkhiMVnJi9CJNF0KoXsrOQrKOdK1BhjujigWyoN9QObLDRR6thS6oXt" +
			"GVwSS4ONvJdlslrXFdpefCSL/R9sdTPoe2cLPsU0sd8zmvUxNmt5RF6qByMBl0vu" +
			"0ejyYkNUlw4GUY31NQECQQDrmffyIlM9e8Do0cfdp7j6mkcKFkmqYYsk+AYrnVUa" +
			"2C5tSlwtW6yK3oyu3jJttjHoIN5LvL1io7qV9M265OuRAkEA27fBM7kNgrxt0lj+" +
			"7LrRdJczafpaH5lFcIUristlO1fDtgO1eVbWfPYCYdCh3iBneUhlyaTfaMugJd87" +
			"6JnimwJBAJ5BoFFfSTbFiAFb2LtFTHXZZ9qYugbe2s6MY+isGFyd1iHHjz9Qkwf9" +
			"Edbsnkcf2Uopueh58WTuwgi0lfTyjGECQQDSRS3pkPykFC5Jsw/sf/E50gXLM2MS" +
			"gH1a7Kc6AeMUgq5yv2PqBrfoCQtIjwCJ8pr0D9wRuO5xfQX7mILd3H9nAkEAgcng" +
			"TgC/q1MLSvFPoLFv+takfuZJc9hmuQddGEqxe4Vjyi1f5G641uM/iEQ5mDXOvSMj" +
			"eomIUNrroE3GcwVCvA==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						//Toast.makeText(OrderConfirm.this, "支付成功",Toast.LENGTH_SHORT).show();
						getHuiDiao("2");
						//finish();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(OrderConfirm.this, "支付结果确认中",
									Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(OrderConfirm.this, "支付失败",
									Toast.LENGTH_SHORT).show();

						}
						getHuiDiao("1");
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(OrderConfirm.this, "检查结果为：" + msg.obj,
							Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
			}
		};
	};
	//------------------------------------------------------------------------------------

	public static Map<String,String> listOrders = new HashMap<String,String>();
	private OrdersForm SGform = new OrdersForm();
	private ListView lstv;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.orderconfirm);
		morning = (TextView) findViewById(R.id.morning);
		afterroon = (TextView) findViewById(R.id.afterroon);
		checkm = (CheckBox) findViewById(R.id.checkm);
		checkm.setOnClickListener(this);
		checkn = (CheckBox) findViewById(R.id.checkn);
		checkn.setOnClickListener(this);
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			getAddressData(Integer.parseInt(getGson().fromJson(getSp().getString("uid", ""), String.class)));
		}
		requestQueue = Volley.newRequestQueue(this);
		list = new ArrayList<AdressBean>();
		pay = (Button) findViewById(R.id.pay);
		name = (TextView) findViewById(R.id.name);
		phonenumber = (TextView) findViewById(R.id.phonenumber);
		adress = (TextView) findViewById(R.id.adress);
		typePay = getIntent().getExtras().getString("typePay");
		if(typePay != null && "1".equals(typePay)){
			myOrderBean = (MyOrderBean) getIntent().getExtras().get("myOrderBean");
		}else {
			fruitlist = (List<FruitBean>) getIntent().getExtras().get("listsgoods");
		}

		textpm = (TextView) findViewById(R.id.textpm);
		paymode2 = (RelativeLayout) findViewById(R.id.paymode2);
		paymode3 = (RelativeLayout) findViewById(R.id.paymode3);
		checked1 = (CheckBox) findViewById(R.id.checked1);
		checked2 = (CheckBox) findViewById(R.id.checked2);
		checked3 = (CheckBox) findViewById(R.id.checked3);
		back = (LinearLayout) findViewById(R.id.back);
		modifyadress = (RelativeLayout) findViewById(R.id.modifyadress);
		pay.setOnClickListener(this);
		checked1.setOnClickListener(this);
		checked2.setOnClickListener(this);
		checked3.setOnClickListener(this);
		back.setOnClickListener(this);
		modifyadress.setOnClickListener(this);

		if(fruitlist != null && fruitlist.size()>0){
			adpater = new ItemGoodsAdapter(fruitlist, OrderConfirm.this);
			lstv = (ListView) this.findViewById(R.id.lstv);
			adpater.setOnClickContentItemListener(this);
			lstv.setAdapter(adpater);
			toCalculate();//计算金额
		}else {
			pay(myOrderBean);
		}

	}
	//计算金额总的
	void toCalculate(){
		if (fruitlist != null){
			doublePrices = 0.0;
			for(int i=0; i<fruitlist.size() ;i++){
				FruitBean shoppingCart = fruitlist.get(i);
				doublePrices = doublePrices + Double.parseDouble(shoppingCart.getPrice_tuangou()==null?"0":shoppingCart.getPrice_tuangou()) * Double.parseDouble(shoppingCart.getCountNum()==null?"1":shoppingCart.getCountNum());
				BigDecimal b = new BigDecimal(doublePrices);  
				doublePrices = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			textpm.setText(doublePrices.toString());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clickchange:
			paymode2.setVisibility(View.VISIBLE);
			paymode3.setVisibility(View.VISIBLE);
			break;
		case R.id.checkm:
			timeslot = morning.getText().toString();
			checkm.setChecked(true);
			checkn.setChecked(false);
			break;
		case R.id.checkn:
			timeslot = afterroon.getText().toString();
			checkn.setChecked(true);
			checkm.setChecked(false);
			break;
		case R.id.checked1:
			checked1.setChecked(true);
			checked2.setChecked(false);
			checked3.setChecked(false);
			break;
		case R.id.checked2:
			checked1.setChecked(false);
			checked2.setChecked(true);
			checked3.setChecked(false);
			break;
		case R.id.checked3:
			checked1.setChecked(false);
			checked2.setChecked(false);
			checked3.setChecked(true);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.modifyadress:
			Intent intent = new Intent(OrderConfirm.this, ReceivingAddress.class);
			intent.putExtra("changeadress", 1);
			startActivity(intent);
			break;
		case R.id.pay:
			//去支付
			if(adressBean ==null){
				Toast.makeText(OrderConfirm.this, "请选择收货人信息", Toast.LENGTH_SHORT).show();
				return;
			}
			if(StringUtil.isNullOrEmpty(textpm.getText().toString()) && "0".equals(textpm.getText().toString())){
				Toast.makeText(OrderConfirm.this, "请选择商品", Toast.LENGTH_SHORT).show();
				return;
			}

			if(fruitlist != null){
				listOrders.clear();
				for(FruitBean shoppingCart:fruitlist){
					listOrders.put(String.valueOf(shoppingCart.getProduct_id()), shoppingCart.getCountNum());
				}
			}
			SGform.setList(listOrders);
			if(listOrders!=null && listOrders.size() > 0){
				//setOrder();
			}
			Intent intent2 = new Intent(OrderConfirm.this, PayInterface.class);
			intent2.putExtra("address", adressBean);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	//生成订单
	void setOrder(){
		StringRequest request = new StringRequest(Request.Method.POST, ServerId.serveradress + ServerId.set_order_by_type,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 =  jo.getString("code");
								if(Integer.parseInt(code1) == 1){
									OrderDATA data = getGson().fromJson(s, OrderDATA.class);
										//清空购物车
//										DBHelper.getInstance(OrderMakeActivity.this).removeAll();
//										Intent intentSend = new Intent("cart_success");
//										OrderMakeActivity.this.sendBroadcast(intentSend);
										//如果是在线支付，去支付
//										if("11".equals(pay_method)){
											order_ob = data.getData();
											pay(order_ob);
//										}else {
//											Toast.makeText(OrderMakeActivity.this, "生成订单成功",
//													Toast.LENGTH_SHORT).show();
//											finish();
//										}
//                                        Intent intent = new Intent(OrderMakeActivity.this, OrderSuccessActivity.class);
//                                        intent.putExtra("order",data.getData());
//                                        startActivity(intent);
									} else{
										Toast.makeText(OrderConfirm.this,  jo.getString("msg"), Toast.LENGTH_SHORT).show();
									}
							}catch (Exception e){ 
								e.printStackTrace();
							}
						}else {
							Toast.makeText(OrderConfirm.this,"生成订单失败", Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						Toast.makeText(OrderConfirm.this, "生成订单失败2", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("uid", getGson().fromJson(getSp().getString("uid", ""), String.class));
				params.put("jsonStr", new Gson().toJson(listOrders));
				params.put("pay_method", "11");
				params.put("invoice", "0");
				params.put("invoice_title", "");
				params.put("postscript", "");
				params.put("receiver_name", adressBean.name);
				params.put("post", "");
				params.put("telephone", adressBean.phonenumber);
				params.put("country", "86");
				params.put("province","");
				params.put("county", "");
				params.put("area", "");
				params.put("address", adressBean.adress);
				params.put("mobile", adressBean.phonenumber);
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		getRequestQueue().add(request);
	}
	
	public void getAddressData(int userid){
		HttpParams params = new HttpParams();
		params.put("userId", String.valueOf(userid));
		HttpClientUtils.getInstance().get(ServerId.serveradress, ServerId.checkaddress, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						AdressBean adressBean = new AdressBean();
						JSONObject object = jsonArray.optJSONObject(i);
						adressBean.state = object.optInt("state");
						adressBean.name = object.optString("name");
						adressBean.adress = object.optString("address");
						adressBean.phonenumber = object.optString("tel");
						adressBean.id = object.optInt("id");
						if (adressBean.state == 1) {
							list.add(adressBean);
						}
					}
				}
				Message message = new Message();
				message.what = 123;
				handler.sendMessage(message);
			}
		});
	}
	
	ResponseHandler handler2 = new ResponseHandler() {
		
		@Override
		public void onWebserviceSucceed() {
			System.out.println("支付完成了！");
			//getHuiDiao();
		}
	};
	
	private void getHuiDiao(String status) {
		String url = String.format(ServerId.serveradress+ServerId.statue_url
				+ "?order_id=%s"+"&status=%s",order_ob.getOrdersn(),status);
		StringRequest request = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject jsonObject = new JSONObject(arg0);
							if (jsonObject.getInt("code") == 200) {
								OrderConfirm.this.finish();
								Toast.makeText(OrderConfirm.this, "支付成功",Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							Message message = new Message();
							Bundle bundle = new Bundle();
							message.what = 0x02;
							bundle.putString("msg", "后台链接错误");
							message.setData(bundle);
							handler.sendMessage(message);
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Message message = new Message();
						Bundle bundle = new Bundle();
						message.what = 0x02;
						bundle.putString("msg", arg0.getMessage());
						message.setData(bundle);
						handler.sendMessage(message);
					}
				});
		requestQueue.add(request);
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if(list != null && list.size()>0){
					name.setText(list.get(0).name);
					phonenumber.setText(list.get(0).phonenumber);
					adress.setText(list.get(0).adress);
					adressBean = list.get(0);
				}

				break;
			case 222:
				order = orderid;
				System.out.println("order = " + order);
				PayUtils payUtils = new PayUtils(OrderConfirm.this, handler2);
//				payUtils.pay(fruitBean.getProduct_name(), "商品描述", "0.01", order);
				break;
			default:
				break;
			}
			return false;
		}
	});
	
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	//---------------------------------------------------------支付宝------------------------------------------

	/**
	 * call alipay sdk pay. 调用SDK支付
	 *
	 */
	public void pay(Order order) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(order.getOrdersn(), order.getOrdersn(), order.getPay_money() ,order.getOrdersn());

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(OrderConfirm.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}


	/**
	 * call alipay sdk pay. 调用SDK支付
	 *
	 */
	public void pay(MyOrderBean order) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(order.getOrder_no(), order.getOrder_no(), order.getReal_amount() ,order.getOrder_no());

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(OrderConfirm.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}


	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 *
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(OrderConfirm.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 *
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 *
	 */
	public String getOrderInfo(String subject, String body, String price, String sn) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + 1 + "\"";
		//price = "0.01";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"http://115.29.200.169/alipay_notify.php\"";
//        orderInfo += "&notify_url=" + "\"http://baidu.com\"";

		orderInfo += "&notify_url=" + "\""
				+ "http://115.29.208.113/home/alipay/notify" + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 *
	 */
//	public String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
//		return key;
//	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	//-------zfb----支付完成之后的操作
	@Override
	public void onClickContentItem(int position, int flag, Object object) {
		switch (flag){
			case 1:
//				//左侧选择框按钮
//				if("0".equals(lists.get(position).getIs_select())){
//					lists.get(position).setIs_select("1");
//				}else {
//					lists.get(position).setIs_select("0");
//				}
//				adpater.notifyDataSetChanged();
//				toCalculate();
				break;
			case 2:
				//加号
				fruitlist.get(position).setCountNum(String.valueOf((Integer.parseInt(fruitlist.get(position).getCountNum()) + 1)));
				adpater.notifyDataSetChanged();
				toCalculate();
				break;
			case 3:
				//减号
				int selectNum = Integer.parseInt(fruitlist.get(position).getCountNum());
				if(selectNum == 1){
					return;
				}else {
					fruitlist.get(position).setCountNum(String.valueOf((Integer.parseInt(fruitlist.get(position).getCountNum()) - 1)));
					adpater.notifyDataSetChanged();
				}
				toCalculate();
				break;
		}
	}
	
	@Override
	public void onResume() {
		if(ReceivingAddress.adressBean != null){
			adressBean =  ReceivingAddress.adressBean;
			name.setText(ReceivingAddress.adressBean.name);
			phonenumber.setText(ReceivingAddress.adressBean.phonenumber);
			adress.setText(ReceivingAddress.adressBean.adress);
		}
		super.onResume();    
	}
}
