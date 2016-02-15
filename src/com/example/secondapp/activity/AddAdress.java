package com.example.secondapp.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.example.secondapp.R;
import com.example.secondapp.base.BaseActivity;
import com.example.secondapp.bean.AdressBean;
import com.example.secondapp.http.AsyncHttpResponseHandler;
import com.example.secondapp.http.HttpClientUtils;
import com.example.secondapp.http.HttpParams;
import com.example.secondapp.model.CityModel;
import com.example.secondapp.model.DistrictModel;
import com.example.secondapp.model.ProvinceModel;
import com.example.secondapp.model.XmlParserHandler;
import com.example.secondapp.serviceId.ServerId;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import org.json.JSONObject;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAdress extends BaseActivity implements View.OnClickListener,OnWheelChangedListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;

	EditText receiver;
	EditText phonenumber;
	EditText detailedaddress;
	TextView save;
	TextView mine_city_province;
	public static String receiverstr;
	public static String phonenumberstr;
	public static String detailedaddressstr;
	private LinearLayout mine_city;//我的省市区
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.addadress);
		receiver = (EditText) findViewById(R.id.receiver);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		detailedaddress = (EditText) findViewById(R.id.detailedaddress);
		save = (TextView) findViewById(R.id.save);
		mine_city = (LinearLayout) this.findViewById(R.id.mine_city);
		mine_city_province = (TextView) this.findViewById(R.id.mine_city_province);
		mine_city.setVisibility(View.GONE);
		mine_city_province.setOnClickListener(this);

		LinearLayout back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				receiverstr = receiver.getText().toString().trim();
				phonenumberstr = phonenumber.getText().toString().trim();
				detailedaddressstr = detailedaddress.getText().toString().trim();
				if (TextUtils.isEmpty(receiverstr) || receiverstr.length() > 6) {
					Toast.makeText(AddAdress.this, "请输入正确的名字", Toast.LENGTH_SHORT).show();
				}else if (TextUtils.isEmpty(phonenumberstr) || phonenumberstr.length() != 11) {
					Toast.makeText(AddAdress.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(mine_city_province.getText().toString())){
					Toast.makeText(AddAdress.this, "请选择省市区", Toast.LENGTH_SHORT).show();
				}
				else if (TextUtils.isEmpty(detailedaddressstr) || detailedaddressstr.length() < 6) {
					Toast.makeText(AddAdress.this, "请输入正确的收货地址", Toast.LENGTH_SHORT).show();
				}else{
					AdressBean bean = new AdressBean(receiverstr, mine_city_province.getText().toString() +" " +detailedaddressstr, phonenumberstr);
					ReceivingAddress.list.add(bean);
					HttpParams params = new HttpParams();
					params.put("userId", getGson().fromJson(getSp().getString("uid", ""), String.class));
					params.put("name", receiverstr);
					params.put("tel", phonenumberstr);
					params.put("address", mine_city_province.getText().toString()+" " + detailedaddressstr);
					HttpClientUtils.getInstance().post(ServerId.serveradress, ServerId.addaddress, params, new AsyncHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject jsonObject) {
							System.out.println("addshopjsonObject = " + jsonObject.toString());
						}
						
						@Override
						public void onFailure(String result, int statusCode, String errorResponse) {
							super.onFailure(result, statusCode, errorResponse);
						}
					});
					ReceivingAddress.adapter.notifyDataSetChanged();
					Toast.makeText(AddAdress.this, "添加成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});

		setUpViews();
		setUpListener();
		setUpData();
	}


	/**
	 * ����ʡ
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - �� values - �ʱ�
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * ��ǰʡ������
	 */
	protected String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	protected String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	protected String mCurrentDistrictName ="";

	/**
	 * ��ǰ������������
	 */
	protected String mCurrentZipCode ="";

	/**
	 * ����ʡ������XML����
	 */

	protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
		AssetManager asset = getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// ����һ������xml�Ĺ�������
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// ����xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// ��ȡ��������������
			provinceList = handler.getDataList();
			//*/ ��ʼ��Ĭ��ѡ�е�ʡ���С���
			if (provinceList!= null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[provinceList.size()];
			for (int i=0; i< provinceList.size(); i++) {
				// ��������ʡ������
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j=0; j< cityList.size(); j++) {
					// ����ʡ����������е�����
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j).getDistrictList();
					String[] distrinctNameArray = new String[districtList.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
					for (int k=0; k<districtList.size(); k++) {
						// ����������������/�ص�����
						DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
						// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// ��-��/�ص����ݣ����浽mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// ʡ-�е����ݣ����浽mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}


	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
	}

	private void setUpListener() {
		// ���change�¼�
		mViewProvince.addChangingListener(this);
		// ���change�¼�
		mViewCity.addChangingListener(this);
		// ���change�¼�
		mViewDistrict.addChangingListener(this);
		// ���onclick�¼�
		mBtnConfirm.setOnClickListener(this);
	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AddAdress.this, mProvinceDatas));
		// ���ÿɼ���Ŀ����
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_confirm:
				showSelectedResult();
				break;
			case R.id.mine_city_province:
				//选择省市区
				mine_city.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}

	private void showSelectedResult() {
//		Toast.makeText(AddAdress.this, mCurrentProviceName+","+mCurrentCityName+","
//				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
		mine_city.setVisibility(View.GONE);
		mine_city_province.setText( mCurrentProviceName+"-"+mCurrentCityName+"-" +mCurrentDistrictName);
	}
}
