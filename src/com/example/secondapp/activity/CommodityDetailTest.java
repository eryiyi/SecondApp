package com.example.secondapp.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.FruitBean;
import com.example.secondapp.data.FruitBeanSingleData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.networkbitmap.BitmapUtil;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.sharedprefernces.SharedPrefsUtil;
import com.example.secondapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommodityDetailTest extends BaseActivity implements OnClickListener{
	RelativeLayout evaluation;
	LinearLayout back;
	RelativeLayout shoppingcart;
	RelativeLayout imgtext;
	Button buyrightnow;
	Button addshoppingcart;
	FruitBean fruitbean = new FruitBean();
	TextView commodityname;
	TextView commodityprice;
	TextView specification;
	TextView message;
	TextView minus;
	TextView type_name_text;
	TextView plus;
	FrameLayout frameimg;
	ViewPager viewpager;
	public static TextView count;
	CheckBox collectioncheckbox;
	public static TextView evaluationcount;
	private static int countall = 1;
	private static int getfruitid;
	private static int numbercount;
	private static TextView number;
	private static ArrayList<ImageView> imageviews;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	private int isNum;
	private RadioButton radioButton1;  
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	private RadioGroup radiogroup;
	private TextView content;
	
	//测试评论
	private TextView type_name_relate;
	private PopupWindow popupWindow;
	private View viewpopup;
	private EditText evaluate;
	private TextView cancel;
	private TextView confirm;
	private static RatingBar ratingbar;
	private int ratingnumber = 3;
	
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.commoditydetail);
		//测试评论
		type_name_relate = (TextView) findViewById(R.id.type_name_relate);
		type_name_relate.setOnClickListener(this);
		
		frameimg = (FrameLayout) findViewById(R.id.frameimg);
		viewpager = (ViewPager) findViewById(R.id.imageviewpager);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton1 = (RadioButton) findViewById(R.id.radio1);
		radioButton2 = (RadioButton) findViewById(R.id.radio2);
		radioButton3 = (RadioButton) findViewById(R.id.radio3);
		radioButton4 = (RadioButton) findViewById(R.id.radio4);
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		radioButton3.setOnClickListener(this);
		radioButton4.setOnClickListener(this);
		imageviews = new ArrayList<ImageView>();
		LayoutInflater inflater =getLayoutInflater();
		imageView1 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView2 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView3 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView4 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0 % imageviews.size()) {
				case 0:
					radioButton1.setChecked(true);
					break;
				case 1:
					radioButton2.setChecked(true);
					break;
				case 2:
					radioButton3.setChecked(true);
					break;
				case 3:
					radioButton4.setChecked(true);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		number = (TextView) findViewById(R.id.number);
		commodityname = (TextView) findViewById(R.id.commodityname);
		commodityprice = (TextView) findViewById(R.id.commodityprice);
		specification = (TextView) findViewById(R.id.specification);
		message = (TextView) findViewById(R.id.message);
		getfruitid = getIntent().getIntExtra("id", 0);
		getdata();
		minus = (TextView) findViewById(R.id.minus);
		plus = (TextView) findViewById(R.id.plus);
		count = (TextView) findViewById(R.id.count);
		count.setText(countall + "");
		type_name_text = (TextView) findViewById(R.id.type_name_text);
		back = (LinearLayout) findViewById(R.id.back);
		evaluation = (RelativeLayout) findViewById(R.id.evaluation);
		addshoppingcart = (Button) findViewById(R.id.addshoppingcart);
		buyrightnow = (Button) findViewById(R.id.buyrightnow);
		shoppingcart = (RelativeLayout) findViewById(R.id.shoppingcart);
		evaluationcount = (TextView) findViewById(R.id.evaluationcount);
		imgtext = (RelativeLayout) findViewById(R.id.imgtext);
		collectioncheckbox = (CheckBox) findViewById(R.id.collectioncheckbox);
		collectioncheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean ischecked) {
				if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
					Intent id1 = new Intent(CommodityDetailTest.this, Logon.class);
					startActivity(id1);
				}else{
					if (ischecked) {
						HttpParams params = new HttpParams();
						params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
						params.put("product_id", fruitbean.getProduct_id());
						HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.addproductCollection, params, new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(JSONObject jsonObject) {
								System.out.println("addshopjsonObject = " + jsonObject.toString());
								Message message = new Message();
								message.what = 123456;
								handler.sendMessage(message);
							}
						});
					}else{
						HttpParams params = new HttpParams();
						params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
						params.put("product_id", fruitbean.getProduct_id());
						HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.deleteCollection, params, new AsyncHttpResponseHandler(){
						});
					}
				}

			}
		});
		minus.setOnClickListener(this);
		plus.setOnClickListener(this);
		evaluation.setOnClickListener(this);
		back.setOnClickListener(this);
		shoppingcart.setOnClickListener(this);
		buyrightnow.setOnClickListener(this);
		addshoppingcart.setOnClickListener(this);
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			numbercount = SharedPrefsUtil.getValue(this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			if (numbercount != 0) {
				frameimg.setVisibility(View.VISIBLE);
				number.setText(numbercount + "");
			}else {
				frameimg.setVisibility(View.GONE);
			}
		}
		content = (TextView) this.findViewById(R.id.content);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.evaluation:
			//评价
			Intent id = new Intent(CommodityDetailTest.this, Evaluation.class);
			id.putExtra("fruitbean", fruitbean);
			startActivity(id);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.shoppingcart:
			if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
				Toast.makeText(CommodityDetailTest.this, "请登录后查看购物车", Toast.LENGTH_SHORT).show();
				Intent id1 = new Intent(CommodityDetailTest.this, Logon.class);
				id1.putExtra("nozero", 2);
				startActivity(id1);
			}else{
				Intent intent = new Intent(CommodityDetailTest.this, ShoppingCartList.class);
				number.setText((numbercount +1) + "");
				startActivity(intent);
			}
			break;
		case R.id.addshoppingcart:
			Log.i("villa", "count ========" + Integer.parseInt(count.getText().toString()));
			if (isNum >= Integer.parseInt(count.getText().toString())) {
				if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
					Intent intent = new Intent(CommodityDetailTest.this, Logon.class);
					intent.putExtra("nozero", 1);
					startActivity(intent);
				}else{
					HttpParams params = new HttpParams();
					params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
					params.put("product_id", fruitbean.getProduct_id());
					params.put("product_name", fruitbean.getProduct_name());
					params.put("product_tuangou", fruitbean.getPrice_tuangou());
					params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
					params.put("price", fruitbean.getPrice());
					params.put("price_tuangou", fruitbean.getPrice_tuangou());
					params.put("numbers", "" + countall);
					HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.addshoppingcart, params, new AsyncHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject jsonObject) {
							System.out.println("addshopjsonObject = " + jsonObject.toString());
						}  
						@Override
						public void onFailure(String result, int statusCode, String errorResponse) {
							super.onFailure(result, statusCode, errorResponse);
						}
					});
					//发通知
					Intent intent = new Intent("add_goods_success");
					this.sendBroadcast(intent);
					number.setText((numbercount +1) + "");
					Toast.makeText(CommodityDetailTest.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(CommodityDetailTest.this, "该商品库存量不足", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.buyrightnow:
			if (isNum >= Integer.parseInt(count.getText().toString())) {
				if ("0".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
					Intent intent = new Intent(CommodityDetailTest.this, Logon.class); 
					intent.putExtra("nozero", 5);
					Bundle bundle = new Bundle();
					bundle.putSerializable("fruitdetail", fruitbean);
					intent.putExtras(bundle);
					startActivity(intent);
				}else {
					ArrayList<FruitBean> arrayList = new ArrayList<FruitBean>();
//					for(int i=0;i<lists.size();i++){
//						if(lists.get(i).getIs_select().equals("0")){
					arrayList.add(fruitbean);
					if(arrayList != null && arrayList.size() > 0){
						Intent orderMakeView = new Intent(CommodityDetailTest.this, OrderConfirm.class);
						orderMakeView.putExtra("listsgoods",arrayList);
						startActivity(orderMakeView);
					}
				}
			}else {
				Toast.makeText(CommodityDetailTest.this, "该商品库存量不足", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.minus:
			if (countall > 1) {
				count.setText("" + --countall);
			}
			break;
		case R.id.plus:
			count.setText("" + ++countall);
			break;
		case R.id.radio1:
			viewpager.setCurrentItem(0);
			break;
		case R.id.radio2:
			viewpager.setCurrentItem(1);
			break;
		case R.id.radio3:
			viewpager.setCurrentItem(2);
			break;
		case R.id.radio4:
			viewpager.setCurrentItem(3);
			break;
		//测试评论
		case R.id.type_name_relate:
			showPopupWindow();
			break;
		case R.id.cancel:
			popupWindow.dismiss();
			break;
		case R.id.confirm:
			if (evaluate.getText().length() == 0) {
				Toast.makeText(CommodityDetailTest.this, "尚未填写商品评论哦", Toast.LENGTH_SHORT).show();
			}else if (evaluate.getText().length() < 5 || evaluate.getText().length() >= 100) {
				Toast.makeText(CommodityDetailTest.this, "评论字数在5-100个字之间哦", Toast.LENGTH_SHORT).show();
			}else if (ratingnumber == 0) {
				Toast.makeText(CommodityDetailTest.this, "您还没进行星级评价哦", Toast.LENGTH_SHORT).show();
			}else {
				evtOrder();
			}
			popupWindow.dismiss();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onPause() {
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			frameimg.setVisibility(View.VISIBLE);
			numbercount = SharedPrefsUtil.getValue(this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			number.setText(numbercount + "");
		}
		super.onPause();
	}
	
	@Override
	public void onResume() {
		if ("1".equals(getGson().fromJson(getSp().getString("is_login", ""), String.class))) {
			frameimg.setVisibility(View.VISIBLE);
			numbercount = SharedPrefsUtil.getValue(this, "Setting" + getGson().fromJson(getSp().getString("uid", ""), String.class), 0);
			number.setText(numbercount + "");
		}
		super.onResume();
	}
	
	public void getdata(){
		HttpParams params = new HttpParams();
		params.put("product_id", getfruitid);
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.getProductDetails, params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject s) {
						if (StringUtil.isJson(s.toString())) {
							try {
								JSONObject jo = new JSONObject(s.toString());
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									FruitBeanSingleData data = getGson().fromJson(s.toString(), FruitBeanSingleData.class);
									if(data != null && data.getData() != null){
										fruitbean = data.getData();
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						Message message = new Message();
						message.what = 123;
						handler.sendMessage(message);
					}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (fruitbean.getProduct_pic1() !=null && fruitbean.getProduct_pic2() !=null &&  fruitbean.getProduct_pic3() != null) {
					radiogroup.setVisibility(View.VISIBLE);
					imageviews.add(imageView2);
					imageviews.add(imageView3);
					imageviews.add(imageView4);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic1(), imageView2);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic2(), imageView3);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic3(), imageView4);
				}else if (fruitbean.getProduct_pic1() != null && fruitbean.getProduct_pic2() != null && fruitbean.getProduct_pic3() == null) {
					radiogroup.setVisibility(View.VISIBLE);
					radioButton4.setVisibility(View.GONE);
					imageviews.add(imageView2);
					imageviews.add(imageView3);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic1(), imageView2);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic2(), imageView3);
				}else if (fruitbean.getProduct_pic1() != null && fruitbean.getProduct_pic2() == null){
					radiogroup.setVisibility(View.VISIBLE);
					radioButton3.setVisibility(View.GONE);
					radioButton4.setVisibility(View.GONE);
					imageviews.add(imageView2);
					BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic1(), imageView2);
				}
				isNum = Integer.parseInt(fruitbean.getIs_num());
				Log.i("villa", "isNum ========" + isNum);
				imageviews.add(imageView1);
				viewpager.setAdapter(mPagerAdapter);
				BitmapUtil.getInstance().download(ServerId.serveradress, fruitbean.getProduct_pic(), imageView1);
				commodityname.setText(fruitbean.getProduct_name() == null?"":fruitbean.getProduct_name());
				commodityprice.setText("¥" + fruitbean.getPrice_tuangou());
				specification.setText(fruitbean.unit==null?"":fruitbean.unit);
				evaluationcount.setText(getSp().getString("evaluatecount" + fruitbean.getProduct_id(), "0"));
				content.setText(Html.fromHtml(fruitbean.info==null?"":fruitbean.getInfo()));
				type_name_text.setText(fruitbean.getType_name()==null?"":fruitbean.getType_name());
				break;
				case 123456:
					Toast.makeText(CommodityDetailTest.this, "已加入收藏",Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}
	});
	
	PagerAdapter mPagerAdapter = new PagerAdapter(){

        @Override
        //获取当前窗体界面数
        public int getCount() {
            return imageviews.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView(imageviews.get(arg1));  
        }  
        
        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(imageviews.get(arg1));
            return imageviews.get(arg1);                
        }
    };
    
    //测试评论
    private void showPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		viewpopup = inflater.inflate(R.layout.popupevaluate, null);
		evaluate = (EditText) viewpopup.findViewById(R.id.evaluate);
		cancel = (TextView) viewpopup.findViewById(R.id.cancel);
		confirm = (TextView) viewpopup.findViewById(R.id.confirm);
		ratingbar = (RatingBar) viewpopup.findViewById(R.id.ratingbar);
		ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
				ratingnumber = (int) rating;
				System.out.println("ratingnumber = " + ratingnumber);
			}
		});
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
		DisplayMetrics metric = new DisplayMetrics();
	   	getWindowManager().getDefaultDisplay().getMetrics(metric);
	   	mScreenWidth = metric.widthPixels;
	   	mScreenHeight = metric.heightPixels;
		//WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//int wid = wm.getDefaultDisplay().getWidth();
		popupWindow = new PopupWindow(viewpopup, mScreenWidth-100, LayoutParams.WRAP_CONTENT);
	    /**
	     * 获取屏幕宽度和高度
	     */
		//popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true); // 可聚焦
		popupWindow.setOutsideTouchable(true);// 设置外部可点击
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); // 设置背景
		//设置popupwindow动画效果
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.setContentView(viewpopup);
		popupWindow.showAtLocation(viewpopup, Gravity.CENTER, 0, 0);
		//popupWindow.showAtLocation(viewpopup.findViewById(R.id.cancelinvate), Gravity.CENTER, 0, 0);
	}

    public void evtOrder(){
		HttpParams params = new HttpParams();
		params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
		params.put("product_id", fruitbean.getProduct_id());
		params.put("content", evaluate.getText().toString());
		params.put("grade", ratingnumber);
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.toassess, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObjectorder = " + jsonObject);
				Message message = new Message();
				message.what = 345;
				handler.sendMessage(message);
			}
		});
	}
	
}
