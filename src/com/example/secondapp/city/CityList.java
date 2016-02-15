package com.example.secondapp.city;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.example.secondapp.R;
import com.example.secondapp.SecondApplication;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.city.SideBar.OnTouchingLetterChangedListener;
import com.example.secondapp.data.CityLocatData;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.model.SortModel;
import com.example.secondapp.serviceId.ServerId;
import com.example.secondapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * �����б�
 * 
 * @author sy
 * 
 */
public class CityList extends BaseActivity implements View.OnClickListener{
	private ListAdapter adapter;
	//private SortAdapter adapter;
	private ListView mCityLit;
	private TextView overlay;
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	private OverlayThread overlayThread;
	private String current_city_str = "";
	private List<SortModel> mCityNames = new ArrayList<SortModel>();
	
	private SideBar sideBar;
	private TextView dialog;
	//private List<SortModel> SourceDateList;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 123:
				for(int i=0; i<mCityNames.size(); i++){
					SortModel sortModel = new SortModel();
					//sortModel.setName(date[i]);
					//汉字转换成拼音
					String pinyin = characterParser.getSelling(mCityNames.get(i).getCity_name());
					String sortString = null;
					if (pinyin != "" && pinyin.length() != 0) {
						sortString = pinyin.substring(0, 1).toUpperCase();
					}else {
					}
					
					/*// 正则表达式，判断首字母是否是英文字母
					if (sortString != null) {
						if(sortString.matches("[A-Z]")){
							sortModel.setFirst(sortString.toUpperCase());
						}else{
							sortModel.setFirst("#");
						}
					}*/
					//mCityLit.add(sortModel);
				}
				//initOverlay();
				setAdapter(mCityNames);
				break;
			default:
				break;
			}
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);
		getCityNames();
		initViews();
		current_city_str = this.getIntent().getStringExtra("city");
		TextView current_city = (TextView) this.findViewById(R.id.current_city);
		current_city.setText(current_city_str);

		this.findViewById(R.id.back).setOnClickListener(this);
	}

	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.cityLetterListView);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					mCityLit.setSelection(position);
				}
				
			}
		});
		
		mCityLit = (ListView) findViewById(R.id.city_list);
		//getCityNames();
		//SourceDateList = filledData(getResources().getStringArray(R.array.date));
		// 根据a-z进行排序源数据
		Collections.sort(mCityNames, pinyinComparator);
		adapter = new ListAdapter(this, mCityNames);
		//mCityLit.setAdapter(adapter);
		
	}

	public void getCityNames(){
		HttpParams params = new HttpParams();
		HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.get_city_list_url, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject s) {
				if (StringUtil.isJson(s.toString())) {
					try {
						JSONObject jo = new JSONObject(s.toString());
						String code =  jo.getString("code");
						String dataJ =  jo.getString("data");
						if(Integer.parseInt(code) == 200 && !"false".equals(dataJ)){
							CityLocatData data = getGson().fromJson(s.toString(), CityLocatData.class);
							if(data != null && data.getData() != null){
								if(data.getData().size() > 0){
									mCityNames.addAll(data.getData());
									//letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
									alphaIndexer = new HashMap<String, Integer>();
									overlayThread = new OverlayThread();
									mCityLit.setOnItemClickListener(new CityListOnItemClick());
								}else {
									showMsg(CityList.this,"暂无城市数据");
								}
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

	
	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.back:
				//if(SecondApplication.dingWeiFlag){
					finish();
				//}else{
					//Toast.makeText(this, "请选择所在城市", Toast.LENGTH_SHORT).show();
			//	}
				break;
		}  
	}

	/**
	 * �����б����¼�
	 * 
	 * @author sy
	 * 
	 */
	class CityListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
			SortModel cityModel = (SortModel) mCityLit.getAdapter().getItem(pos);
			save("city", cityModel.getCity_name());
			save("cityid", cityModel.getCity_id());
			SecondApplication.cityName = cityModel.getCity_name();
			Intent intent = new Intent("select_city_success");
			CityList.this.sendBroadcast(intent);
			//SecondApplication.dingWeiFlag = true;
			finish();

		}

	}

	/**
	 * ΪListView����������
	 * 
	 * @param list
	 */
	private void setAdapter(List<SortModel> list) {
		if (list != null) {
			//adapter = new ListAdapter(this, list);
			mCityLit.setAdapter(adapter);
		}

	}

	/**
	 * ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter extends BaseAdapter implements SectionIndexer{
		private LayoutInflater inflater;
		private List<SortModel> list;

		public ListAdapter(Context context, List<SortModel> list) {

			this.inflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			final SortModel mContent = list.get(position);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			//根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			
			if(position == getPositionForSection(section - 32)){
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(mContent.getFirst());
			}else{
				holder.alpha.setVisibility(View.GONE);
			}
		
			holder.name.setText(this.list.get(position).getCity_name());
			return convertView;
		}
		
		/**
		 * 根据ListView的当前位置获取分类的首字母的Char ascii值
		 */
		public int getSectionForPosition(int position) {
			if (list.get(position).getFirst().length() != 0) {
				return list.get(position).getFirst().charAt(0);
			}
			return -1;
		}
		
		/**
		 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
		 */
		public int getPositionForSection(int section) {
			for (int i = 0; i < getCount(); i++) {
				String sortStr = list.get(i).getFirst();
				if (sortStr.length() != 0) {
					char firstChar = sortStr.toUpperCase().charAt(0);
					if (firstChar == section) {
						return i;
					}
				}
				
			}
			return -1;
		}
		
		@Override
		public Object[] getSections() {
			return null;
		}

		private class ViewHolder {
			TextView alpha;
			TextView name;
		}

	}

	// ��ʼ������ƴ������ĸ������ʾ��
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class LetterListViewListener implements
			MyLetterListView.OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				mCityLit.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// �ӳ�һ���ִ�У���overlayΪ���ɼ�
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// ����overlay���ɼ�
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

}