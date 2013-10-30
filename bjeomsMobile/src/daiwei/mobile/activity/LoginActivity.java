package daiwei.mobile.activity;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inspur.zsyw.platform.Platform;
import com.inspur.zsyw.platform.Platform.PlatformCallback;

import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.animal.Rotate3D;
import daiwei.mobile.common.MyData;
import daiwei.mobile.service.LoginService;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.CryptUtils;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import eoms.mobile.R;

/**
 * 登陆页面
 * @author 都 3/25
 *
 */
public class LoginActivity extends Activity implements AdapterView.OnItemClickListener {
	private static final String PACKAGE_NAME = "daiwei.mobile.activity" ;
	private static final String JSON_FLAG_USER = "userName" ;
	private static final String JSON_FLAG_PWD = "password" ;
	public static final String PARA_USERNAME = "para_username" ;
	public static final String PARA_PASSWORD = "para_password" ;
	private Platform platform ;
	
	private EditText login_name_edit;
	private EditText login_pwd_edit;
	private String username;
	private String passwordInput;
	private String passwordEncode;
	private Button login_bt;
	private SharedPreferences sp;
	public static Activity Ac;
	private Handler handler;
	private ProgressDialog dialog;
	private WaitDialog wd=null;
	private RelativeLayout rlContent1;
	private LinearLayout llContent2;
	private ImageView ivIpSet;
	private Button btnIpSave;
	private Button btnIpCancle;
	private EditText etIpName;
	private EditText etIpaddress;
	private ImageView ivIpArrow;
	private LinearLayout llIpName;
	private float degree = (float) 0.0;
	private int mCenterX ;
	private int mCenterY ;
	private ArrayList<String> listIPName;
	private ArrayList<String> listIPAddress;
	private ListView lvIpHistory;
	private HistoryAdapter mAdapter;
	private View popHistoryView;
	private PopupWindow popHistory;
	private boolean ipHistoryFlag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		MyData.add(LoginActivity.this);
//		String response = "{\"userName\":\"Demo\",\"password\":\"test\"}" ;
//		parseResponse(response);
		//绑定浪潮服务 
//		bundleLangChaoServer();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Map<String,Object> loginMap = (Map<String, Object>) msg.obj;
				boolean b = (Boolean) loginMap.get("IsLegal");//从集合中获取IsLegal字段，是否合法
				if (b) {//如果合法
					dialog.dismiss();//取消dialog
					sp.edit().putString(AppConstants.SP_USER_NAME, username).commit();// 把用户名存起来
					sp.edit().putString(AppConstants.SP_USER_PSW_ENCODE, passwordEncode).commit();// 把密码存起来
					 //用户名密码存内存
					BaseApplication application = (BaseApplication) LoginActivity.this.getApplication();
					application.setName(username);
					application.setPsw(passwordEncode);
					LoginModel lm = (LoginModel) loginMap.get("Ccount");//获取个数对象
//					Intent intent = new Intent(LoginActivity.this,
//							TestActivity.class);//跳转主界面
					Intent intent = new Intent(LoginActivity.this,GdListActivity.class);//跳转到工单列表页面
					Bundle mBundle=new Bundle();//传LoginModel对象
					mBundle.putSerializable("LoginModel", lm);
					intent.putExtras(mBundle);					
					startActivity(intent);
					if(MyData.activityList!=null){
						for (Activity activity : MyData.activityList) {
							if(activity!=null){
								activity.finish();
							}
						}
						MyData.activityList.clear();
					}
				} else {
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "很抱歉，登录失败！", Toast.LENGTH_LONG).show();
				}
			}
			
		};

		
		login_bt = (Button) findViewById(R.id.login_bt);// 登录按钮
		login_name_edit = (EditText) findViewById(R.id.username);// 文本框
		login_pwd_edit = (EditText) findViewById(R.id.password);//密码
		sp = getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		// cb_savepwd = (CheckBox) findViewById(R.id.cb_login_savepwd);// 复选框
		// cb_autologin = (CheckBox) findViewById(R.id.cb_login_autologin);
		rlContent1 = (RelativeLayout) findViewById(R.id.rl_content1);
		llContent2 = (LinearLayout) findViewById(R.id.ll_content2);
		ivIpSet = (ImageView) findViewById(R.id.iv_ip_set);
		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;
		ivIpArrow = (ImageView)findViewById(R.id.iv_ip_arrow);
		llIpName = (LinearLayout)findViewById(R.id.ll_ip_name);
		btnIpSave = (Button)findViewById(R.id.btn_ip_save);
		btnIpCancle = (Button)findViewById(R.id.btn_ip_cancle);
		etIpName = (EditText)findViewById(R.id.et_ip_name);
		etIpaddress = (EditText)findViewById(R.id.et_ip_adrress);
		SharedPreferences spLogin = LoginActivity.this.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		etIpName.setText(spLogin.getString(AppConstants.SP_IP_NAME, "").trim());
		etIpaddress.setText(spLogin.getString(AppConstants.SP_IP_ADDRESS, "").trim());
		// 初始化ui的显示 cb_savepwd.setChecked(sp.getBoolean("ISCHECK",false));
		//login_name_edit.setText(spLogin.getString("username", ""));
		//login_pwd_edit.setText(spLogin.getString("password", ""));
		// if(sp.getBoolean("AUTO_ISCHECK", false)){
		// cb_autologin.setChecked(true); Intent intent=new Intent(); //
		// intent.setClass(getApplicationContext(),MainGridActivity.class);
		// startActivity(intent); }

		/*
		 * //记住密码的监听 cb_savepwd.setOnCheckedChangeListener(new
		 * OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { if(cb_savepwd.isChecked()){
		 * sp.edit().putBoolean("ISCHECK",true).commit(); }else{
		 * sp.edit().putBoolean("ISCHECK",false).commit(); } } }); 
		 * //自动登录的监听
		 * cb_autologin.setOnCheckedChangeListener(new OnCheckedChangeListener()
		 * {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { if(cb_autologin.isChecked()){
		 * sp.edit().putBoolean("AUTO_ISCHECK",true).commit(); }else{
		 * sp.edit().putBoolean("AUTO_ISCHECK", false).commit(); } } });
		 */
		/**
		 * 绑定浪潮时，将onclickCommond注释掉，将autoLogin()打开。<br/>
		 * 平时测试时，将autoLogin()注释掉，将onclickCommond()打开。
		 */
		onclickCommond();
//		autoLogin();
	}
	
	/**
	 * 自动跳转到登录页面
	 */
	private void autoLogin(){
//		username = login_name_edit.getText().toString().trim();
//		passwordInput = login_pwd_edit.getText().toString().trim();
//		System.out.println("[username = "+username+" ][ passwordInput = "+passwordInput+"]");
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwordInput)) {
			Animation shake = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.shake);
			if (TextUtils.isEmpty(username) && TextUtils.isEmpty(passwordInput)) {
				login_name_edit.setAnimation(shake);
				login_pwd_edit.setAnimation(shake);
				login_name_edit.startAnimation(shake);
				login_pwd_edit.startAnimation(shake);
				Toast.makeText(getApplicationContext(), "用户名和密码不能为空", 0)
						.show();
			} else if (TextUtils.isEmpty(username)) {
				login_name_edit.setAnimation(shake);
				login_name_edit.startAnimation(shake);
				Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT)
						.show();
			} else if (TextUtils.isEmpty(passwordInput)) {
				login_pwd_edit.setAnimation(shake);
				login_pwd_edit.startAnimation(shake);
				Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT)
						.show();
			}
			passwordEncode = "";
			return;
		} else {
			if(!IsNetwork.isAccessNetWork(LoginActivity.this.getApplicationContext())){
				networkStateCheck();
			}else{
//				username="ttdw_xuyanming";
//				passwordInput="123";
			passwordEncode = CryptUtils.getInstance(null).encode(passwordInput);
			wd = new WaitDialog(LoginActivity.this, "正在连接服务器，请耐心等待");
			dialog = wd.getDialog();
			login();
		}}
	}
	
	/**
	 * 提取登录页面onclick事件
	 */
	private void onclickCommond(){
		// 登录按钮点击事件
				login_bt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						username = login_name_edit.getText().toString().trim();
						passwordInput = login_pwd_edit.getText().toString().trim();
						if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwordInput)) {
							Animation shake = AnimationUtils.loadAnimation(
									getApplicationContext(), R.anim.shake);
							if (TextUtils.isEmpty(username) && TextUtils.isEmpty(passwordInput)) {
								login_name_edit.setAnimation(shake);
								login_pwd_edit.setAnimation(shake);
								login_name_edit.startAnimation(shake);
								login_pwd_edit.startAnimation(shake);
								Toast.makeText(getApplicationContext(), "用户名和密码不能为空", 0)
										.show();
							} else if (TextUtils.isEmpty(username)) {
								login_name_edit.setAnimation(shake);
								login_name_edit.startAnimation(shake);
								Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT)
										.show();
							} else if (TextUtils.isEmpty(passwordInput)) {
								login_pwd_edit.setAnimation(shake);
								login_pwd_edit.startAnimation(shake);
								Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT)
										.show();
							}
							passwordEncode = "";
							return;
						} else {
							if(!IsNetwork.isAccessNetWork(LoginActivity.this.getApplicationContext())){
								networkStateCheck();
							}else{
//								username="ttdw_xuyanming";
//								passwordInput="123";
							passwordEncode = CryptUtils.getInstance(null).encode(passwordInput);
							wd = new WaitDialog(LoginActivity.this, "正在连接服务器，请耐心等待");
							dialog = wd.getDialog();
							login();
						}}
					}
				});
				ivIpSet.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (rlContent1.getVisibility() == View.VISIBLE) {
							Rotate3D rotate3d = new Rotate3D(degree, -90, 0, mCenterX, mCenterY);
							Rotate3D rotate3d3 = new Rotate3D(90 + degree, 0, 0, mCenterX, mCenterY);
							rotate3d.setDuration(300);
							rotate3d3.setDuration(300);
							rlContent1.startAnimation(rotate3d);
							llContent2.startAnimation(rotate3d3);
							llContent2.setVisibility(View.VISIBLE);
							rlContent1.setVisibility(View.GONE);
						} else {
							Rotate3D rotate3d = new Rotate3D(degree, 90, 0, mCenterX, mCenterY);
							Rotate3D rotate3d3 = new Rotate3D(-90 + degree, 0, 0, mCenterX, mCenterY);
							rotate3d.setDuration(300);
							rotate3d3.setDuration(300);
							rlContent1.startAnimation(rotate3d3);
							llContent2.startAnimation(rotate3d);
							llContent2.setVisibility(View.GONE);
							rlContent1.setVisibility(View.VISIBLE);
						}
					}
				});
				btnIpSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String tempIpName = etIpName.getText().toString().trim();
						String tempIpaddress = etIpaddress.getText().toString().trim();
						SharedPreferences sp = LoginActivity.this.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
						String name = sp.getString(AppConstants.SP_IP_NAME_HIS, "");
						String address = sp.getString(AppConstants.SP_IP_ADDRESS_HIS, "");
						
						Editor editor = sp.edit();
						if(StringUtil.isEmpty(name)){
							editor.putString(AppConstants.SP_IP_NAME_HIS, tempIpName);
							editor.putString(AppConstants.SP_IP_ADDRESS_HIS, tempIpaddress);
						}else{
							if(name.indexOf(tempIpName)==-1 || address.indexOf(tempIpaddress)==-1){
								name = new StringBuilder(name).append(";").append(tempIpName).toString();
								address = new StringBuilder(address).append(";").append(tempIpaddress).toString();
								editor.putString(AppConstants.SP_IP_NAME_HIS, name);
								editor.putString(AppConstants.SP_IP_ADDRESS_HIS, address);
							}
						}
						editor.putString(AppConstants.SP_IP_NAME, tempIpName);
						editor.putString(AppConstants.SP_IP_ADDRESS, tempIpaddress);
						editor.commit();
						BaseApplication application = (BaseApplication) LoginActivity.this.getApplication();
						application.setCustomIpName(tempIpName);
						application.setCustomIpAddress(tempIpaddress);
						llContent2.setVisibility(View.GONE);
						rlContent1.setVisibility(View.VISIBLE);
					}
				});
				btnIpCancle.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (llContent2.getVisibility() == View.VISIBLE) {
							Rotate3D rotate3d = new Rotate3D(degree, 90, 0, mCenterX, mCenterY);
							Rotate3D rotate3d3 = new Rotate3D(-90 + degree, 0, 0, mCenterX, mCenterY);
							rotate3d.setDuration(300);
							rotate3d3.setDuration(300);
							rlContent1.startAnimation(rotate3d3);
							llContent2.startAnimation(rotate3d);
							llContent2.setVisibility(View.GONE);
							rlContent1.setVisibility(View.VISIBLE);
							if (popHistory != null && popHistory.isShowing()) {
								popHistory.dismiss();
								popHistory = null;
							}
						}
						
					}
				});
				etIpName.addTextChangedListener(watcher);
				ivIpArrow.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!ipHistoryFlag) {
							openIPHistory();
						}else{
							closeIPHistory();
						}
					}
				});
	}
	/**
	 * 设置网络
	 */
	private void networkStateCheck() {
		Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage("WIFI网络连接"); 
		b.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				Intent intent=null;
				if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(Settings.ACTION_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
				startActivity(intent); 		 
			} 
		}).setNeutralButton("取消", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				dialog.cancel(); 
			} 
		}).create(); 
		b.show(); 	 	
	}
	//就这样取异步请求
	private void login(){		
		new Thread(){
			public void run() {
				LoginService lg = new LoginServiceImp();
				Map<String,Object> loginMap = lg.login(getApplicationContext(), username, passwordEncode);//返回Map<String,Object>集合
				Message msg = new Message();
				msg.obj = loginMap;
				
				handler.sendMessage(msg);
			};
		}.start();
	}


	/**
	 * 显示ip历史
	 */
	private void openIPHistory(){
	    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(),
	    InputMethodManager.HIDE_NOT_ALWAYS);
		ipHistoryFlag=true;
		ivIpArrow.setImageResource(R.drawable.arrow_down);
		popHistoryView = getLayoutInflater().inflate(R.layout.login_ip_history, null);
		popHistory = new PopupWindow(popHistoryView, llIpName.getWidth(), LayoutParams.WRAP_CONTENT, true);
		lvIpHistory = (ListView)popHistoryView.findViewById(R.id.lv_ip_history);
		lvIpHistory.setOnItemClickListener(this);
		SharedPreferences sp = this.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		String name = sp.getString(AppConstants.SP_IP_NAME_HIS, "");
		String address = sp.getString(AppConstants.SP_IP_ADDRESS_HIS, "");
		if(StringUtil.isEmpty(name)){
			return;
		}
		String[] names = name.split(";");
		String[] addressses = address.split(";");
		listIPName = new ArrayList<String>();
		listIPAddress = new ArrayList<String>();
		for (int i = 0; i < names.length; i++) {
			listIPName.add(names[i]);
		}
		for (int i = 0; i < addressses.length; i++) {
			listIPAddress.add(addressses[i]);
		}
		mAdapter = new HistoryAdapter(listIPName, listIPAddress, LoginActivity.this);
		lvIpHistory.setAdapter(mAdapter);
		popHistory.showAsDropDown(etIpName, 0, 0);
		popHistory.getContentView().setOnTouchListener(new View.OnTouchListener(){  
			 @Override  
			     public boolean onTouch(View v, MotionEvent event) { 
				 ivIpArrow.setImageResource(R.drawable.arrow_left);
				 closeIPHistory();
				 return true;  
		     }   
		 });  
		lvIpHistory.setOnKeyListener(new View.OnKeyListener(){  
            @Override  
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            	if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            		closeIPHistory();
                    return true;
            	}else{
                    return false; 
            	}
            }  
              
        });
	}
	
	/**
	 * 隐藏ip历史
	 */
	private void closeIPHistory() {
		ipHistoryFlag=false;
		ivIpArrow.setImageResource(R.drawable.arrow_left);
		if(popHistory!=null && popHistory.isShowing()){
			popHistory.dismiss();
		}
		
	}
	
	private class HistoryAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<String> listIPName;
		private ArrayList<String> listIPAddress;
		Context context;
		
		public HistoryAdapter(ArrayList<String> listIPName, ArrayList<String> listIPAddress, Context mContext) {
			super();
			this.context = mContext;
			this.listIPName = listIPName;
			this.listIPAddress = listIPAddress;
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return listIPName.size();
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (listIPName.size() <= 0) {
				return convertView;
			}
			final ViewHolder holder;
			if (convertView == null) { // recycling views
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.login_ip_history_item, parent, false);
				holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				holder.ivDel = (ImageView) convertView.findViewById(R.id.iv_del);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if(position<AppConfigs.URL_HOST_NAME_DEFAULTS.length){//默认ip不能删除
				holder.ivDel.setVisibility(View.GONE);
			}else{
				holder.ivDel.setVisibility(View.VISIBLE);
			}
			final String name = listIPName.get(position);
			holder.tvName.setText(name);
			holder.ivDel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listIPName.remove(position);
					listIPAddress.remove(position);
					mAdapter.notifyDataSetChanged();
					new Thread(new Runnable() {
						@Override
						public void run() {
							SharedPreferences sp = LoginActivity.this.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
							Editor editor = sp.edit();
							StringBuilder buildName = new StringBuilder("");
							StringBuilder buildAddress = new StringBuilder("");
							if(listIPName.size()>0){
								for (int i = 0; i < listIPName.size(); i++) {
									buildName.append(listIPName.get(i)).append(";");
									buildAddress.append(listIPAddress.get(i)).append(";");
								}	
							}
							if(StringUtil.isNotEmpty(buildName.toString())){
								buildName.deleteCharAt(buildName.length()-1);
								buildAddress.deleteCharAt(buildAddress.length()-1);
							}
							editor.putString(AppConstants.SP_IP_NAME_HIS, buildName.toString());
							editor.putString(AppConstants.SP_IP_ADDRESS_HIS, buildAddress.toString());
							editor.commit();
						}
					}).start();
				}
			});
			return convertView;
		}
		
		class ViewHolder {
			TextView tvName; //名称
			ImageView ivDel; //删除
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position < mAdapter.listIPName.size()) {
			etIpName.setText(mAdapter.listIPName.get(position));
			etIpaddress.setText(mAdapter.listIPAddress.get(position));
			closeIPHistory();
		}
	}
	
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(llContent2.getVisibility()==View.VISIBLE){
				llContent2.setVisibility(View.GONE);
				rlContent1.setVisibility(View.VISIBLE);
				if (popHistory != null && popHistory.isShowing()) {
					popHistory.dismiss();
					popHistory = null;
				}
				return true;// 我已经处理完毕back键，不再交系统处理
			}else{// 返回时退出ActivityMap
				MyData.remove(LoginActivity.this);
			}	
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		if (popHistory != null && popHistory.isShowing()) {
			popHistory.dismiss();
			popHistory = null;
		}
		super.onDestroy();
	}
	
	/**
	 * 解析json
	 * @param response
	 */
	private void parseResponse(String response){
		if(null != response){
			try {
				JSONObject jo = new JSONObject(response);
				username = jo.getString(JSON_FLAG_USER) ;
				passwordInput = jo.getString(JSON_FLAG_PWD) ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 绑定浪潮服务
	 */
	private void bundleLangChaoServer(){
		/** 绑定浪潮app store START **/ 
		// 初始化,设置回调接口
		platform = Platform.getInstance(LoginActivity.this, callback); 
		// 绑定服务                                                         
		platform.bindService();                                         
		try {
			//上传日志
			platform.uploadLog(null, null, null, null, null) ;          
		} catch (RemoteException e) {                                   
			e.printStackTrace();                                        
		}                                                          
		/** 绑定浪潮app store END **/
	}
	
	/**
	 * 浪潮服务 回调函数
	 */
	PlatformCallback callback = new PlatformCallback() {
		@Override
		public void onSuccess(String response) {
			response = "{\"userName\":\"Demo\",\"password\":\"test\"}";
			parseResponse(response);
		}

		@Override
		public void onFailure(String response) {
		}

		@Override
		public void onServiceConnected() {
			// 连接成功,调用接口
			platform.getUserName(PACKAGE_NAME);
		}

	};
}