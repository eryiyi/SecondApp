package com.example.secondapp.activity;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
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
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.AdressBean;
import com.example.secondapp.data.Order;
import com.example.secondapp.data.OrderDATA;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import com.google.gson.Gson;

public class PayInterface extends BaseActivity {
	TextView lqg;
	TextView zfb;
	TextView yf;
	TextView payfor;
	String moneyget;
	Double zfbf;
	Double lqgf;
	LinearLayout back;
	private Order order_ob;
	private AdressBean adressBean;
	// 商户PID
	public static final String PARTNER = "2088021404950836";
	// 商户收款账号
	public static final String SELLER = "794370578@QQ.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALc11ghQ2PIX3Fsg" +
			"dqxMtYgo4ZduJOva7d9SXWBV2lcV+3B3KJOcX4vGo/VrRhYHAYkWZRuvafcP6+ac" +
			"xbcjLv6LWdsxoI2lVcN5rzGVBdFr2Zqj4TW5GlkPibxxnLW0+9onNxwOWHbOZ8/m" +
			"kqDUqPnW+gHFajBvPT7t6ALDeKDBAgMBAAECgYB5YyQicl6rWH/ZqNK4KkMEqgF+" +
			"Ma4ozu9YTdwsXfXCSYBE7c5mru7nT3+GtcLECXRM/heTlk+hMF1eeHTPeHJDd8Vi" +
			"BmbVarAOgh34yn3ZVYi+CutfV7ENYLbNCRP7rh7y6F9SE7EqqxHngJkWHO9fG4Ah" +
			"pkiLIAQ5xEb68hyogQJBAPQ/dUYVwntDO7qNzPWxKZo0h+oFuj1WhB9M8aOccqck" +
			"kYvPNlkyxgRQq5NGzwWeQ+iHbJagjGTtI8gFcoZWfQkCQQDABor4MGopFcQLy1vS" + 
			"yLHANIEnM32ijk4SRxxsq4OBJNoslBaZmHPo5cfC04JY64jKE3e5yy1Yn1s7ArL+" +
			"NKv5AkEA41IIUNFBrz0U4vzErEpVwOpxMLJh8mKKFyOipbae0sbrGycgmwXxW+TE" +
			"uw6k8MKqy0j3HH2SDQDUU34WZ8cpGQJBALWnOt3Qhwz4vy3dIa9EFgraYHdiGW6m" +
			"qipWRQh0NAH+h7n2GW8Rrkza2l9u0PcnUY7AXdlP4ETe82Heis85qpkCQEgof2hs" +
			"1vUFgLzzKPEWSZGYcD51NeuOFBwxfp7mykaE4PCHzZgA6q3kWPTnVMxviSD2HgXl" +
			"8A0UmhVDkKRRN8o=";
	/*public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMo10/sPXGOIlZSz" +
			"qfQtKApeHpzvq57dtwpiWVsbyu/jpLFeb+51IxSXrdgItDp/By42Jkxlv4KjTGLP" +
			"SsI1UO06
			liAq40ll439Oy3fvHn3MRJzUFnBZiwb4ksTMXoMbDLJ7VavXRi5Carfs" +
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
			"eomIUNrroE3GcwVCvA==";*/
	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private RequestQueue requestQueue;
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
					// Toast.makeText(OrderConfirm.this,
					// "支付成功",Toast.LENGTH_SHORT).show();
					Toast.makeText(PayInterface.this, "支付成功", Toast.LENGTH_SHORT).show();
					paybylqg();
					getHuiDiao("2");
					// finish();
				} else {    
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayInterface.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayInterface.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
					getHuiDiao("1");
				}
			break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayInterface.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
			break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payinterface);
		requestQueue = Volley.newRequestQueue(this);
		getMoney();
		adressBean = (AdressBean) getIntent().getExtras().get("address");
		lqg = (TextView) findViewById(R.id.lqg);
		zfb = (TextView) findViewById(R.id.zfb);
		yf = (TextView) findViewById(R.id.yf);
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		payfor = (TextView) findViewById(R.id.payfor);
		payfor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (zfbf > 0) {
					if (Double.parseDouble(moneyget) != 0.0 || Double.parseDouble(moneyget) != 0.00) {
						//paybylqg();
						setOrder();
					}else {
						setOrder();
					}
				}else {
					paybylqg();
				}
			}
		});
		yf.setText(OrderConfirm.doublePrices + " 元");
	}

	private void getMoney() {
		HttpParams params = new HttpParams();
		params.put("userId", getSp().getString("uid", ""));
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getmemberInfo, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject s) throws JSONException {
						Log.i("villa", s + "");
						JSONObject data = s.getJSONObject("data");
						moneyget = data.getString("money");
						Message message = new Message();
						message.what = 123;
						handler.sendMessage(message);
					}
		});
	}

	private void paybylqg(){
		HttpParams params = new HttpParams();
		params.put("uid", getSp().getString("uid", ""));
		params.put("pay_money", lqgf);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.moneyPay, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) throws JSONException {
				System.out.println("s =" + s);
			}
		});
	}
	
	private Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {

				@Override
				public boolean handleMessage(Message msg) {
					switch (msg.what) {
					case 123:
						lqg.setText(moneyget + " 元");
						zfbf = OrderConfirm.doublePrices - Double.parseDouble(moneyget);
						if (zfbf > 0) {
							lqgf = Double.parseDouble(moneyget);
							BigDecimal b = new BigDecimal(zfbf);
							zfbf = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							zfb.setText(zfbf + " 元");
						}else {
							lqgf = OrderConfirm.doublePrices;
							zfb.setText(0.0 + " 元");
						}
						break;
					case 0x02:
						
						break;
					default:
						break;
					}
					return false;
				}
	});

	// 生成订单
	void setOrder() {
		StringRequest request = new StringRequest(Request.Method.POST, ServerId.serveradress + ServerId.set_order_by_type,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								Log.i("villa", s + "是也不是");
								JSONObject jo = new JSONObject(s);
								String code1 = jo.getString("code");
								if (Integer.parseInt(code1) == 1) {
									OrderDATA data = getGson().fromJson(s, OrderDATA.class);
									order_ob = data.getData();
									pay(order_ob);
								} else {
									Toast.makeText(PayInterface.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(PayInterface.this, "生成订单失败", Toast.LENGTH_SHORT).show();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						Toast.makeText(PayInterface.this, "生成订单失败2", Toast.LENGTH_SHORT).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("uid", getGson().fromJson(getSp().getString("uid", ""), String.class));
				params.put("jsonStr", new Gson().toJson(OrderConfirm.listOrders));
				params.put("pay_method", "11");
				params.put("invoice", "0");
				params.put("invoice_title", "");
				params.put("postscript", OrderConfirm.timeslot);
				params.put("receiver_name", adressBean.name);
				params.put("post", "");
				params.put("telephone", adressBean.phonenumber);
				params.put("country", "86");
				params.put("province", "");
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

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(Order order) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
							public void onClick(
							DialogInterface dialoginterface, int i) {
								finish();
							}
					}).show();
			return;
		}
		// 订单
		//String orderInfo = getOrderInfo(order.getOrdersn(), order.getOrdersn(), order.getPay_money(), order.getOrdersn());
		double payfor = Double.parseDouble(order.getPay_money()) - lqgf;
		BigDecimal b = new BigDecimal(payfor);
		payfor = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String orderInfo = getOrderInfo(order.getOrdersn(), order.getOrdersn(), String.valueOf(payfor), order.getOrdersn());

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
				PayTask alipay = new PayTask(PayInterface.this);
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

	private void getHuiDiao(String status) {
		String url = String.format(ServerId.serveradress + ServerId.statue_url + "?order_id=%s" + "&status=%s", order_ob.getOrdersn(), status);
		StringRequest request = new StringRequest(Request.Method.POST, url, new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						try {
							Log.i("villa", "支付进度 = " + arg0);
							JSONObject jsonObject = new JSONObject(arg0);
							if (jsonObject.getInt("code") == 200) {
								PayInterface.this.finish();
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
		// price = "0.01";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径

		orderInfo += "&notify_url=" + "\"" + "http://115.29.208.113/home/alipay/notify" + "\"";
		
		//
		// // 服务接口名称， 固定值
		// orderInfo += "&service=\"mobile.securitypay.pay\"";

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
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content 待签名订单信息
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
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
