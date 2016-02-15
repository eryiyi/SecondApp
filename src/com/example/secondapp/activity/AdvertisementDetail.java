package com.example.secondapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;

public class AdvertisementDetail extends BaseActivity implements View.OnClickListener{
	WebView webView;
	private String url ;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		url = getIntent().getExtras().getString("url");
		setContentView(R.layout.webview);
		this.findViewById(R.id.logonback).setOnClickListener(this);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
		//webView.addJavascriptInterface(new MyWebAppInterface(this), "demo");
		//webView.loadUrl("http://www.12365auto.com/news/2014-07-10/20140710115457.shtml");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.logonback:
				finish();
				break;
		}
	}
	
	/*class MyWebViewClient extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//通过返回的webview加载webview默认脚本在脚本中动态注入js
			view.loadUrl(
							"javascript:(function(){"  
		                    + "  var objs = document.getElementsByTagName(\"img\"); "  
		                    + "  for(var i=0;i<objs.length;i++){"  
		                    + "     objs[i].onclick=function(){"  
		                    + "          window.demo.jsInvokeJava(this.src); "  
		                    + "     }"
		                    + "  }"  
		                    + "})()");

		}
	}*/
}
