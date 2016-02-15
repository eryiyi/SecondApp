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

public class PayUtils {
	private Context context;
	private ResponseHandler handler;
	// // 商户PID
	// public static final String PARTNER = "2088611687527351";
	// // 商户收款账号
	// public static final String SELLER = "aiwuxiu999@163.com";
	// // 商户私钥，pkcs8格式
	// public static final String RSA_PRIVATE =
	// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5CV8gS+xZmfYn6nZN9qnIJFwcq0VqPgeYrGflCpQ1gd7aZRcBe+Jjai3HkgAtVt2fbWo1mkdDgvoe2jgX1B7Tdq9/xPTJUSbtZsyc72UVfNaV01BzvAROYRDsYmx1WpY3yAa/P8bXWN/NBIAk29AffHe+OH/F0mWws8n7C3gr9AgMBAAECgYAYisNuLC4ss3aOzjsPIK22rc0Od1tQ09FJp1xbHKW+Qo3Zq7CFLVr5vB3Thoj6D7Jq8BvKul1i+6QZ3P4XnLDe7pB74nD9nGm/srjWcZVU9LlKDvYYEadbrbJgS509NkWqH6zb5ib19/Q7MD1uyCj10INxpZPWyTmFaI+36y6cAQJBAOHsVbJ6nYGxsSJS4sKi966IG0xiKXpLVYNzfaUVakw2/4vX7JeyMrRiJDloxc6ISpH5xPcPrGTjz4V8rkD4iX0CQQDFdUBjH/5YdsEJYB/Ll9zN9b3Zq/IFSLRYNiRLmIllNsvvXzF39fQ2dwg1PZYge1PdcVKOetHtztV0VwO85z+BAkEAxvLHxJQVf9BqnD9BUQUR97AD2g/XVspgU5GwY1+oa9VC1HMg9bUG0bJbE/MfvfBnOoUo3if/OBkk9fgFu1EiQQJABU2AzSiuy/Fkfbv3HqCVbXKMBqx3c2zgav4JO359Mhqmn2cPDQmt5UiND+D3hyfIuMmyTeE+ggRr9xPfJdOQAQJBAKqomshy+165Zg4HrFS3xS/HlOXQA3gSLXrFm/JlQTbWzKCvpfu+OIu48d6QZ/5KpjgBS4nyUxxNy6JikQQSMbk=";
	// // 支付宝公钥
	// public static final String RSA_PUBLIC =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	// 商户PID
	// public static final String PARTNER = "2088611195088449";

	public static final String PARTNER = "2088021404950836";
	// 商户收款账号
	public static final String SELLER = "794370578@QQ.com";
	// 商户公钥，pkcs8格式
//	 public static final String RSA_PUBLIC =
//	 "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5CV8gS+xZmfYn6nZN9qnIJFwcq0VqPgeYrGflCpQ1gd7aZRcBe+Jjai3HkgAtVt2fbWo1mkdDgvoe2jgX1B7Tdq9/xPTJUSbtZsyc72UVfNaV01BzvAROYRDsYmx1WpY3yAa/P8bXWN/NBIAk29AffHe+OH/F0mWws8n7C3gr9AgMBAAECgYAYisNuLC4ss3aOzjsPIK22rc0Od1tQ09FJp1xbHKW+Qo3Zq7CFLVr5vB3Thoj6D7Jq8BvKul1i+6QZ3P4XnLDe7pB74nD9nGm/srjWcZVU9LlKDvYYEadbrbJgS509NkWqH6zb5ib19/Q7MD1uyCj10INxpZPWyTmFaI+36y6cAQJBAOHsVbJ6nYGxsSJS4sKi966IG0xiKXpLVYNzfaUVakw2/4vX7JeyMrRiJDloxc6ISpH5xPcPrGTjz4V8rkD4iX0CQQDFdUBjH/5YdsEJYB/Ll9zN9b3Zq/IFSLRYNiRLmIllNsvvXzF39fQ2dwg1PZYge1PdcVKOetHtztV0VwO85z+BAkEAxvLHxJQVf9BqnD9BUQUR97AD2g/XVspgU5GwY1+oa9VC1HMg9bUG0bJbE/MfvfBnOoUo3if/OBkk9fgFu1EiQQJABU2AzSiuy/Fkfbv3HqCVbXKMBqx3c2zgav4JO359Mhqmn2cPDQmt5UiND+D3hyfIuMmyTeE+ggRr9xPfJdOQAQJBAKqomshy+165Zg4HrFS3xS/HlOXQA3gSLXrFm/JlQTbWzKCvpfu+OIu48d6QZ/5KpjgBS4nyUxxNy6JikQQSMbk=";

	
	//public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKNdP7D1xjiJWUs6n0LSgKXh6c76ue3bcKYllbG8rv46SxXm/udSMUl63YCLQ6fwcuNiZMZb+Co0xiz0rCNVDtOpYgKuNJZeN/Tst37x59zESc1BZwWYsG+JLEzF6DGwyye1Wr10YuQmq37LCMzh6L4RFAO66UGNdDJ5lFcjmiywIDAQAB";
	public static final String RSA_PUBLIC = "";
	// 支付宝私钥
	//MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALFlxzTHJ6w/OImMUIZciWe9CDJ6/WC1Ov7gE/7XWXuzoJXTHWkap+MYjAwurKj43ZO5L0dAWCCapsktn0pIs50CYNE4fdUFqIh3qmUA6OYKZ8IxpthabT+kcNaYUsFrxwotGTtdHGepfHhqYc1JChbd0yX+5/GKrEY1wi1Hf1wBAgMBAAECgYAsMyi8PLbk0ue6VcymfZ36y/2j3kMNenpVkO1KfMud57amPfDBBFQ6YB5c1rBMdZ13thdSkHSkwwO1cnmS36Rom0GKjY1vpMND+xXjegxf+L7350gSsaBORKUl0G9ZJnWsfm0nESV+hpVLayOgsJJpl2/zZdtX2Pv+yPZSL01pwQJBANaZR7lfYzVOoSiAZ5JVGCx75kSKQsxnV6gSvU0NYzqcioe6Oeai8vGYPU7O4lz1GX7wAE55cAT5utf0kflRi5kCQQDTny+VebNz9t860Qa60esqngJtMsC0Ts8PJJIEsA+KV9bL3YAyMIdbmaL/Hkdeuxt5W7n4oZaagQ3CB1BPPFSpAkBRKuCqCJ4ph3gFLav++RiPmr1Zp0HktiG9fHkFL88cWCE9fP5xcaiaII7vw+hyaHIgRP6Yr+3is+Ir1GFx/5nhAkBMH+xHEeNa6Mh+aOjJ1W+KAhMPYv1tHxhe8yHPq+G7O0PaTBCsfUaDze2TZjn4Osh0TGnGpaH+bEQdsvHRvvsZAkEAt/zeriEVKTmW9k2XtrEpDGtBilo9vTli2r0C0T/WgI7hoF8RhB0yU0xhZluSf9VOWIhHRf2OkW+mzRvK+lip4Q==
	 public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMo10/sPXGOIlZSzqfQtKApeHpzvq57dtwpiWVsbyu/jpLFeb+51IxSXrdgItDp/By42Jkxlv4KjTGLPSsI1UO06liAq40ll439Oy3fvHn3MRJzUFnBZiwb4ksTMXoMbDLJ7VavXRi5CarfssIzOHovhEUA7rpQY10MnmUVyOaLLAgMBAAECgYEAsu4+zgIhttG9yv/QfAkZTVNLs4NBUirPNZkhiMVnJi9CJNF0KoXsrOQrKOdK1BhjujigWyoN9QObLDRR6thS6oXtGVwSS4ONvJdlslrXFdpefCSL/R9sdTPoe2cLPsU0sd8zmvUxNmt5RF6qByMBl0vu0ejyYkNUlw4GUY31NQECQQDrmffyIlM9e8Do0cfdp7j6mkcKFkmqYYsk+AYrnVUa2C5tSlwtW6yK3oyu3jJttjHoIN5LvL1io7qV9M265OuRAkEA27fBM7kNgrxt0lj+7LrRdJczafpaH5lFcIUristlO1fDtgO1eVbWfPYCYdCh3iBneUhlyaTfaMugJd876JnimwJBAJ5BoFFfSTbFiAFb2LtFTHXZZ9qYugbe2s6MY+isGFyd1iHHjz9Qkwf9Edbsnkcf2Uopueh58WTuwgi0lfTyjGECQQDSRS3pkPykFC5Jsw/sf/E50gXLM2MSgH1a7Kc6AeMUgq5yv2PqBrfoCQtIjwCJ8pr0D9wRuO5xfQX7mILd3H9nAkEAgcngTgC/q1MLSvFPoLFv+takfuZJc9hmuQddGEqxe4Vjyi1f5G641uM/iEQ5mDXOvSMjeomIUNrroE3GcwVCvA==";

	//public static final String RSA_PRIVATE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVBS/X5d8TmUoIhK8fc9DGX7Xf4Cr2s29sBauiUkyXXLp4qqmP9WdGu9NQsPNIoN6AM938htR8Bs5fvAuD+k00FMLXH0AFBByicIL8KsZZ6rwqYTWb7u2BZwW/vtw7EcV7RPbvO1ca60a0CSv6Z8PYgB0H4xXtaxH5ToPHbtO1bwIDAQAB";

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
									 
	public PayUtils(Context context, ResponseHandler handler) {
		super();
		this.context = context;
		this.handler = handler;
	}

	public void pay(String goodsName, String goodsInfo, String price, String orderId) {
		// 订单
		String orderInfo = getOrderInfo(goodsName, goodsInfo, price, orderId);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

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
				+ "http://115.29.208.113/alipay_notify.php" + "\"";
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
