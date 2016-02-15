package com.alipay.sdk.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LuckyPayUtils {
	private Context context;
	private ResponseHandler handler;
	// 商户PID
	public static final String PARTNER = "2088611687527351";
	// 商户收款账号
	public static final String SELLER = "aiwuxiu999@163.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5CV8gS+xZmfYn6nZN9qnIJFwcq0VqPgeYrGflCpQ1gd7aZRcBe+Jjai3HkgAtVt2fbWo1mkdDgvoe2jgX1B7Tdq9/xPTJUSbtZsyc72UVfNaV01BzvAROYRDsYmx1WpY3yAa/P8bXWN/NBIAk29AffHe+OH/F0mWws8n7C3gr9AgMBAAECgYAYisNuLC4ss3aOzjsPIK22rc0Od1tQ09FJp1xbHKW+Qo3Zq7CFLVr5vB3Thoj6D7Jq8BvKul1i+6QZ3P4XnLDe7pB74nD9nGm/srjWcZVU9LlKDvYYEadbrbJgS509NkWqH6zb5ib19/Q7MD1uyCj10INxpZPWyTmFaI+36y6cAQJBAOHsVbJ6nYGxsSJS4sKi966IG0xiKXpLVYNzfaUVakw2/4vX7JeyMrRiJDloxc6ISpH5xPcPrGTjz4V8rkD4iX0CQQDFdUBjH/5YdsEJYB/Ll9zN9b3Zq/IFSLRYNiRLmIllNsvvXzF39fQ2dwg1PZYge1PdcVKOetHtztV0VwO85z+BAkEAxvLHxJQVf9BqnD9BUQUR97AD2g/XVspgU5GwY1+oa9VC1HMg9bUG0bJbE/MfvfBnOoUo3if/OBkk9fgFu1EiQQJABU2AzSiuy/Fkfbv3HqCVbXKMBqx3c2zgav4JO359Mhqmn2cPDQmt5UiND+D3hyfIuMmyTeE+ggRr9xPfJdOQAQJBAKqomshy+165Zg4HrFS3xS/HlOXQA3gSLXrFm/JlQTbWzKCvpfu+OIu48d6QZ/5KpjgBS4nyUxxNy6JikQQSMbk=";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

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
					Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
					handler.onWebserviceSucceed();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT)
								.show();
						System.out.println(resultStatus + "dsad");

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(context, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			default:
				break;
			}
		};
	};

	public LuckyPayUtils(Context context, ResponseHandler handler) {
		super();
		this.context = context;
		this.handler = handler;
	}

	public void pay(String goodsName, String goodsInfo, String price, String orderId) {
		// 订单
		String orderInfo = getOrderInfo(goodsName, goodsInfo, "0.01", orderId);

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
				PayTask alipay = new PayTask((Activity) context);
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
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask((Activity) context);
		String version = payTask.getVersion();
		Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price, String orderId) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\""
				+ "http://121.40.127.36:8080/json/alipay_draw.du" + "\"";

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
	/*
	 * public String getOutTradeNo() { SimpleDateFormat format = new
	 * SimpleDateFormat("MMddHHmmss", Locale.getDefault()); Date date = new
	 * Date(); String key = format.format(date);
	 * 
	 * Random r = new Random(); key = key + r.nextInt(); key = key.substring(0,
	 * 15); return key; }
	 */

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

	public static interface ResponseHandler {
		public abstract void onWebserviceSucceed();

	}

}
