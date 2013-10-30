package daiwei.mobile.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.adapter.MyPagerAdapter;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;
import daiwei.mobile.service.AutoUploadService;
import daiwei.mobile.service.BootService;
import daiwei.mobile.service.GDListService;
import daiwei.mobile.service.GDListServiceImp;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.Type;
import daiwei.mobile.view.MToast;
import daiwei.mobile.view.MyViewPager;
import eoms.mobile.R;
/**
 * @author Administrator
 * 显示工单列表页面
 */
public class GdListActivity extends Activity {
	private int from = 1;//请求来源，1：来源登录   
	private String gdNum;//初始化显示工单的条数
	public Context context;
	private ListView mDisplay1;//待办工单列表
	private ListView mDisplay2;//已办工单列表
//	private ListView mDisplay3;//已办公告列表
	private Adapter mAdapter1;
	private Adapter mAdapter2;
	private Adapter mAdapter3;//已办公告适配
	private Button btnWaitGd;//待办工单按钮
	private Button btnFinishGd;//已办工单按钮
//	private Button btnFinishGg;//已办公告按钮
//	private Button btnAbBottom;//下载按钮
	private ImageView imgIndicator;
	private ImageView mImageView;//工单列表页的退出列表按钮
	private ImageView mSettingView;//系统设置按钮
	private MyViewPager viewPager;
	private int offset;// 动画图片偏移量
	private int fromX = 0;//x方向起始坐标
	private int toX = 0;//x方向移动到的坐标值
	private int bitmapWidth;// 动画图片宽度
	private List<View> viewList;// 页卡集合
	private int currIndex = 0;// 当前页卡编号
	private View view1, view2;
//	private View view3;//公告视图
	private RelativeLayout loading;
	Map<String, String> map;
	public String img_text;// 图片名字
	// dbList存储第一个list的内容
	// ybList存储第二个list的内容
	private List<Map<String, String>> dbList;// 待办列表
	private List<Map<String, String>> ybList;// 已办列表
//	private List<Map<String,String>> ybGgList;//已办公告列表
	public int isWait = 1;// 判断已办还是待办 1：待办 0：已办
	private int index1 = 1;// 待办工单页码
	private int index2 = 1;// 已办工单页码
	private int index3 = 1;//已办公告页码
	private Boolean listNoNull = true;// 判断返回的list是否为空
	private static int currentItem = 1;// 处在当前工单类型     
	private static String wfCategory= "";//工单分类  add by xiaxj
	/** 底部动态加载涉及到的属性 */
	private LayoutInflater inflater;
	private LinearLayout automaic_lyt;
	private LinearLayout bottom_lyt;
	private LinearLayout llAbBottom;
	public int bottom_size;
	public int id = 1;
	private ListModel lp;
	private Map<String, String> mapNum;
	private int all_num = 0;
	private boolean flag, bottom_flag = true;
	private String text_name;
	private ProgressDialog dialog = null;
	private WaitDialog wd = null;
	public static FriendsReciver reciver = null;
	public static ArrayList<FriendsReciver> list = new ArrayList<FriendsReciver>();
//	private LinearLayout llTitle;// 头标题
//	private ImageView ivTitle;// 头标题
//	private TextView tvTitle;// 离线在线文本
//	private boolean isOnline = true;
//	private LinearLayout popWinTitleView;
//	private PopupWindow popOnlineOrOffline;
	private Map<String,TextView> textViewMap=new  HashMap<String,TextView>();//存放类型和
	
	private String gonggaoType = "WF:EL_UVS_BULT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gdlist_activity);
		LoginModel lm = (LoginModel)getIntent().getSerializableExtra("LoginModel");
		if(lm != null){
			gdNum = lm.getGdNum();//初始化显示工单的条数
		}else{
			gdNum = "10";
		}
		//退出按钮
		mImageView = (ImageView)findViewById(R.id.iv_friends);
		//添加设置按钮
		mSettingView = (ImageView)findViewById(R.id.iv_setting);
		
		btnWaitGd = (Button) findViewById(R.id.btnWaitGd);// 待办按钮
		btnFinishGd = (Button) findViewById(R.id.btnFinishGd);// 已办按钮
//		btnFinishGg = (Button) findViewById(R.id.btnGongGao);//已办公告按钮
//		btnAbBottom = (Button) mFriends.findViewById(R.id.btn_ab_bottom);// 下载按钮
		llAbBottom = (LinearLayout) findViewById(R.id.ll_ab_bottom);
//		popWinTitleView = (LinearLayout) inflater.inflate(R.layout.popup_gdlist_title, null, true);// 初始化popWIn
		imgIndicator = (ImageView) findViewById(R.id.imgIndicator);//指示器
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  
		
		// 接受广播 更新列表
		IntentFilter inf = new IntentFilter();
		inf.addAction("android.intent.action.GdListActivity");
		reciver = new FriendsReciver();
		registerReceiver(reciver, inf);// 动态注册广播
		/** 动态加载底部按钮 */
		bottom_lyt = (LinearLayout) findViewById(R.id.automatic_loaded);
		automaic_lyt = (LinearLayout) inflater.inflate(R.layout.footbg, null, true);// 底部加载的每条数据
		bottom_lyt.addView(automaic_lyt);
		
		bottom_size = LoginServiceImp.xml.getType().size();// 获取手机模板中的工单类型数目
		currentItem = 1;//待办
		wfCategory = "ALL";// 当前类别是全部
		initViewPager(isWait);// 初始化viewpager
		initAnimation();
		setListeners(isWait);
		
//		//短信拦截
//		startService(new Intent(this,BootService.class));
//		//启动工单附件自动上传服务
//		startService(new Intent(this, AutoUploadService.class));
	}
	
	
	/**
	 * 广播接受者 更新列表
	 * @author qicaihua
	 * @time 2013-4-1 下午3:12:49
	 */
	class FriendsReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("返回更新。。。。。。。。。。=" + reciver);
			list.add(reciver);
			update();
		}
	}
	/**
	 * @author xiaxj
	 * @param view  底部view
	 * @param type  工单类型编号
	 * @param wfNum 待办或一般工单数目
	 */
	private void setViewToBottom(final View view,final String type,final String wfNum){
		//图标隐藏掉
		ImageButton bottom_img = (ImageButton) view.findViewById(R.id.imageButton);
		bottom_img.setVisibility(View.GONE);
		//工单类型名称
		TextView bottom_tv = (TextView) view.findViewById(R.id.bottom_tv);
		bottom_tv.setText(text_name);
		//待办或已办工单数目
		TextView msg_num = (TextView) view.findViewById(R.id.msg_num_tv);
		msg_num.setVisibility(View.VISIBLE);
		msg_num.setText(wfNum);
		//工单类型编号
		TextView msg_type = (TextView)view.findViewById(R.id.msg_type);
		msg_type.setText(type);//工单类型
		
		textViewMap.put(type, msg_num);
		//单击工单分类按钮 监听
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				index1 = 1;
				index2 = 1;
				index3 = 1;
				wfCategory = type;
				currentItem = 1;
				listNoNull = true;
				//设置当前选中的工单分类背景图片
				GdListActivity.this.setCurTypeBackground(type);
				initViewPager(isWait);//默认显示待办列表，显示到第0个view中
				setListeners(isWait);//默认显示待办列表，显示到第0个view中
			}
		});
	}
	
	/**
	 * 动态加载底部按钮以及图片的方法
	 * @param 底部按钮图片和文字的加载view
	 * @param 每个图片代表的类型 ==登录解析到的类型
	 * @param 代表第一个图片
	 */
	private void addBottomButton(final LinearLayout automaic_lyt, final String type, final int id, String text_name) {
		View view = inflater.inflate(R.layout.footitem, null, true);
		view.setBackgroundColor(Color.TRANSPARENT);
		 DisplayMetrics metric = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metric);
		 int width = metric.widthPixels;     // 屏幕宽度（像素）
         int widthLayout = width/4;
		view.setLayoutParams(new LinearLayout.LayoutParams(widthLayout,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		String wfNum = "0";
	
				
		
		if (type.equals("ALL")) {
			//循环map计算待办工单总数
			Iterator it = mapNum.keySet().iterator();
			int num = 0;
			while(it.hasNext()){
				String key = (String)it.next();
				String value = (String)mapNum.get(key);
				if(StringUtil.isNotEmpty(value)){
					num = Integer.parseInt(value);
				}
			}
			all_num = all_num+num;
			wfNum = String.valueOf(all_num);
		}else{
			wfNum = mapNum.get(type);
		}
		if(wfNum != null){
			//底部工单类别视图赋值
			setViewToBottom(view,type,wfNum);
			//底部添加工单类别视图
			automaic_lyt.addView(view);
		}
	}
	/**
	 * 添加   更多。。。  的按钮
	 */
//	private void addBottomButtonMore(final LinearLayout automaic_lyt, 
//			final String type, final int id, String text_name){
//		View mView = inflater.inflate(R.layout.footitem_more, null, true);
//		mView.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
//				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
//		TextView mTextView = (TextView)mView.findViewById(R.id.tv_more);//更多...
//		ImageButton mImageButton = (ImageButton)mView.findViewById(R.id.iv_more);
//		mImageButton.setImageResource(drawable.menu1);
//		mTextView.setText(text_name);
//		mView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoreGD(v);
//			}
//		});
//		automaic_lyt.addView(mView);
//	}
	/**
	 * 显示更多工单类型信息
	 */
//	public void selectMoreGD(View parent){
//		//显示更多的工单类型列表
//		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//		View mView_more = (View)inflater.inflate(R.layout.gdtype_more, null,true);
//		ListView mListView = (ListView)mView_more.findViewById(R.id.gdtype_more);
//		//view  list
//		ArrayList<View> viewList = new ArrayList<View>();
//		//获取模板中的工单类别列表
//		List<Type>  templateType= LoginServiceImp.xml.getType();
//		if(templateType.size() >3){
//			for(int i = 3;i<templateType.size();i++){
//				Type mType = (Type)templateType.get(i);//从工单模板中取的模板类别信息
//				String category = mType.getType();//工单分类
//				//映射view
//				View mView = inflater.inflate(R.layout.desktop_list_item, null,true) ;
//				//工单类别前的图片
//				ImageView mImageView = (ImageView)mView.findViewById(R.id.desktop_list_img);
//				mImageView.setImageResource(R.drawable.arrow);
//				//工单类别名称
//				TextView mTextView = (TextView)mView.findViewById(R.id.desktop_list_text);
//				mTextView.setText(mType.getText());
//				//待办工单数目
//				TextView mTextView_num = (TextView)mView.findViewById(R.id.desktop_list_point);
//				mTextView_num.setText(mapNum.get(category) == null?"0":mapNum.get(category));
//				
//				viewList.add(mView);
//			}
//			//列表适配
//			MyGDTypeAdapter myAdapter = new MyGDTypeAdapter(this.getBaseContext(),viewList);
//			mListView.setAdapter(myAdapter);
//			//点击监听事件
//			mListView.setOnItemClickListener(itemClickListenter);
//			PopupWindow pw = new PopupWindow(mView_more, LayoutParams.WRAP_CONTENT,
//					LayoutParams.WRAP_CONTENT, true);
//			pw.setBackgroundDrawable(new ColorDrawable(Color.RED));//设置背景
//			pw.setOutsideTouchable(true);
//			pw.showAtLocation(parent, Gravity.RIGHT | Gravity.BOTTOM, 10,10); 			
////			pw.showAsDropDown();
//			pw.update();
//		}
//	}
	/**
	 * 定义点击监听事件
	 */
	OnItemClickListener itemClickListenter = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
		}
	};
	/**
	 * 给点中的工单分类设置背景图案
	 * @param type
	 */
	private void setCurTypeBackground(String type){
		int childNum = automaic_lyt.getChildCount();
		for(int i = 0;i<childNum;i++){
			View childView = (View)automaic_lyt.getChildAt(i);
			CharSequence msgType = ((TextView)childView.findViewById(R.id.msg_type)).getText();
			if(type.equals(msgType)){
				childView.findViewById(R.id.bottom_tv).setBackgroundResource(R.drawable.bottom_senddown);
			}else{
				childView.findViewById(R.id.bottom_tv).setBackgroundResource(R.color.black);
			}
		}
	}
	// 初始化动画
	private void initAnimation() {
		// 获取指示图片的宽度
		bitmapWidth = BitmapFactory.decodeResource(this.getResources(), R.drawable.gd_viewpager_indicator).getWidth();
		// 获取分辨率
		Display display = ((WindowManager) (getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
		int screenW = display.getWidth();
		// 计算偏移量
		offset = (screenW /  - bitmapWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// 设置动画初始位置
		imgIndicator.setImageMatrix(matrix);
	}
	
	// 初始化ViewPager
	private void initViewPager(int viewItem) {
		viewList = new ArrayList<View>();
		view1 = View.inflate(this, R.layout.listview, null);// 待办工单的view
		view2 = View.inflate(this, R.layout.search, null);// 已办工单的view
//		view3 = View.inflate(this, R.layout.listview, null);//公告view

		viewList.add(view1);
		viewList.add(view2);
//		viewList.add(view3);
		mDisplay1 = (ListView) viewList.get(0).findViewById(R.id.friends_list);// 找到待办工单列表\
		mDisplay2 = (ListView) viewList.get(1).findViewById(R.id.friends_list);//已办工单列表
//		mDisplay3 = (ListView) view3.findViewById(R.id.friends_list);//公告列表
		/**
		 * 开启子线程下载数据
		 */
		DownLoadTread downLoad = new DownLoadTread();
		
		wd = new WaitDialog(this, "数据加载中...");
		dialog = wd.getDialog();
		downLoad.start();
		viewPager=new MyViewPager(this);
		viewPager = (MyViewPager) findViewById(R.id.viewPager);
		viewPager.removeAllViews();
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(this, viewList);
		viewPager.setAdapter(myPagerAdapter);
		if (viewItem == 1) {
			viewPager.setCurrentItem(0);//显示待办列表view
		} else if(viewItem == 0) {
			viewPager.setCurrentItem(1);//显示已办view
//		}else{
//			viewPager.setCurrentItem(2);//显示已办工单 view
		}
	}
	
	/**
	 * 
	 * @param item 
	 */
	private void setListeners(final int item) {
		//退出系统按钮
		mImageView.setOnClickListener(new MyOnClickListener(-1));
		//系统设置按钮
		mSettingView.setOnClickListener(new MyOnClickListener(-2));
		
		btnWaitGd.setOnClickListener(new MyOnClickListener(0));//待办
		btnFinishGd.setOnClickListener(new MyOnClickListener(1));//已办
//		btnFinishGg.setOnClickListener(new MyOnClickListener(2));//已办公告列表
		//页卡切换监听，建指示线移到对应的也卡下
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());      
		
		mDisplay1.setOnItemClickListener(new OnItemClickListener() {//待办
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				intent = new Intent(getBaseContext(), BaseTmpActivity.class);
				intent.putExtra("BaseSchema", map.get("BaseSchema"));
				intent.putExtra("BaseSummary", map.get("BaseSummary"));
				intent.putExtra("BaseSN", map.get("BaseSN"));
				intent.putExtra("BaseID", map.get("BaseID"));
				intent.putExtra("TaskID", map.get("TaskID"));
				intent.putExtra("IsWait", isWait);
				startActivity(intent);
			}
		});
		// 给listview设置一个滚动的监听事件
		mDisplay1.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)&& view.getCount() > (9 + 10 * (index1 - 1)) ) {
						//if (listNoNull) {// list返回不为[]时
							index1 = index1 + 1;
							loadMore(0, isWait);// 加载更多
						//}
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
		mDisplay2.setOnItemClickListener(new OnItemClickListener() {//已办
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				intent = new Intent(getApplicationContext(), BaseTmpActivity.class);
				intent.putExtra("BaseSchema", map.get("BaseSchema"));
				intent.putExtra("BaseSummary", map.get("BaseSummary"));
				intent.putExtra("BaseSN", map.get("BaseSN"));
				intent.putExtra("BaseID", map.get("BaseID"));
				intent.putExtra("TaskID", map.get("TaskID"));
				intent.putExtra("IsWait", isWait);
				startActivity(intent);
			}
		});
		mDisplay2.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					System.out.println("工单已办加载更多=======" + isWait + view.getLastVisiblePosition() + ",,," + (view.getCount() - 1) + ",,,,"
							+ view.getCount());
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == view.getCount() - 1 && view.getCount() > (9 + (index2 - 1))) {
						System.out.println("mDisplay2 index2=============!!!!!!!!!!!" + index2);
						//if (listNoNull) {// list返回不为[]时
							index2 = index2 + 1;
							loadMore(1, isWait);// 加载更多
						//}
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}
		});
		//**********************已办公告工单*********************************
//		//链接到公告详情页面
//		mDisplay3.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				map = (Map<String, String>) parent.getAdapter().getItem(
//						position);
//				Intent intent = new Intent(getApplicationContext(),
//						NoticeTmpActivity.class);//BaseTmpActivity
//				intent.putExtra("BaseSchema", map.get("BaseSchema"));// BaseSchema=WF4:EL_AM_ER
//				intent.putExtra("BaseSummary", map.get("BaseSummary"));// BaseSummary=ceshi001
//				intent.putExtra("BaseSN", map.get("BaseSN"));// BaseSN=ID-652-20130326-00001
//				intent.putExtra("BaseID", map.get("BaseID"));// 工单id
//				intent.putExtra("TaskID", map.get("TaskID"));// 任务id
//				intent.putExtra("IsWait", isWait);// 待办
//				startActivity(intent);
//			}
//		});
//		mDisplay3.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				// TODO Auto-generated method stub
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					// 判断滚动到底部
//					if (view.getLastVisiblePosition() == view.getCount() - 1 && view.getCount() > (9 + (index3 - 1))) {
//						//if (listNoNull) {// list返回不为[]时
//							index3 = index3 + 1;
//							loadMore(2, 0);// 加载更多
//						//}
//					}
//				}
//			}
//			
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				// TODO Auto-generated method stub
//			}
//		});
		
		/**
		 * 下载的点击事件
		 */
//		btnAbBottom.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(isChecked()){
//					loadAndSaveCache();
//				}
//			}
//		});
	}// 监听器结束
	
	/**
	 * 
	 * @param item 
	 */
	private void setListenersOld(final int item) {
		btnWaitGd.setOnClickListener(new MyOnClickListener(0));
		btnFinishGd.setOnClickListener(new MyOnClickListener(1));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());      
		mDisplay1.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				switch (item) {
				case 1:
					intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					context.startActivity(intent);
					break;
				case 2:
					if (TempletModel.isWEB) {
						intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
						intent.putExtra("BaseSchema", map.get("BaseSchema"));
						intent.putExtra("BaseSummary", map.get("BaseSummary"));
						intent.putExtra("BaseSN", map.get("BaseSN"));
						intent.putExtra("BaseID", map.get("BaseID"));
						intent.putExtra("TaskID", map.get("TaskID"));
						intent.putExtra("IsWait", isWait);
					} else {
						intent = new Intent(context.getApplicationContext(), GdFdActivity.class);
						intent.putExtra("position", position);
					}
					context.startActivity(intent);
					break;
				case 3:
					intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					context.startActivity(intent);
					break;
				case 4:
					intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					context.startActivity(intent);
					break;
				case 5:
					intent = new Intent(context.getApplicationContext(), GdTyActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("IsWait", isWait);
					context.startActivity(intent);
					break;
				}
			}
		});
		// 给listview设置一个滚动的监听事件
		mDisplay1.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)&& view.getCount() > (9 + 10 * (index1 - 1)) ) {
						if (listNoNull) {// list返回不为[]时
							index1 = index1 + 1;
							loadMore(item, isWait);// 加载更多
						}
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
		mDisplay2.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				switch (item) {
				case 1:
					intent = new Intent(getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					startActivity(intent);
					break;
				case 2:
					if (TempletModel.isWEB) {
						intent = new Intent(getApplicationContext(), BaseTmpActivity.class);
						intent.putExtra("BaseSchema", map.get("BaseSchema"));
						intent.putExtra("BaseSummary", map.get("BaseSummary"));
						intent.putExtra("BaseSN", map.get("BaseSN"));
						intent.putExtra("BaseID", map.get("BaseID"));
						intent.putExtra("TaskID", map.get("TaskID"));
						intent.putExtra("IsWait", isWait);
					} else {
						intent = new Intent(getApplicationContext(), GdFdActivity.class);
						intent.putExtra("position", position);
					}
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(getApplicationContext(), BaseTmpActivity.class);
					intent.putExtra("BaseSchema", map.get("BaseSchema"));
					intent.putExtra("BaseSummary", map.get("BaseSummary"));
					intent.putExtra("BaseSN", map.get("BaseSN"));
					intent.putExtra("BaseID", map.get("BaseID"));
					intent.putExtra("TaskID", map.get("TaskID"));
					intent.putExtra("IsWait", isWait);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(getApplicationContext(), GdBugActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("IsWait", isWait);
					startActivity(intent);
					break;
				}
			}
		});
		mDisplay2.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					System.out.println("工单已办加载更多=======" + isWait + view.getLastVisiblePosition() + ",,," + (view.getCount() - 1) + ",,,,"
							+ view.getCount());
					// 判断滚动到底部
					if (view.getLastVisiblePosition() == view.getCount() - 1 && view.getCount() > (9 + (index2 - 1))) {
						System.out.println("mDisplay2 index2=============!!!!!!!!!!!" + index2);
						if (listNoNull) {// list返回不为[]时
							index2 = index2 + 1;
							loadMore(item, isWait);// 加载更多
						}
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}
		});
//		/**
//		 * 下载的点击事件
//		 */
//		btnAbBottom.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(isChecked()){
//					loadAndSaveCache();
//				}
//			}
//		});
	}// 监听器结束
	
	/**
	 * 缓存选中的列表项到数据库
	 */
	private void loadAndSaveCache(){
		if(!IsNetwork.isAccessNetWork(this)){
			MToast.showWhenNetworkUnavailable(getBaseContext());
			return;
		}
		final ArrayList<ContentValues> list = new ArrayList<ContentValues>();
		if(dbList!=null && dbList.size()>0){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < dbList.size(); i++) {
//						if(mAdapter1.getArray()[i]){
							Map<String, String> map = dbList.get(i);
							ContentValues cv = new ContentValues();//存taskId，baseId，category
							cv.put("taskId", map.get("TaskID"));
							cv.put("baseId", map.get("BaseID"));
							cv.put("category", map.get("BaseSchema"));
							list.add(cv);
//						}
					}
//					boolean result =  GDCacheService.getInstance().loadGDCache(context.getApplicationContext(), list);
//					System.out.println("loadAndSaveCache result=" + result);
				}
			}).start();
		}
	}
	/**
	 * 缓存列表
	 */
	private void cacheList(){
		final ArrayList<ContentValues> list = new ArrayList<ContentValues>();
		if(dbList!=null && dbList.size()>0){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < dbList.size(); i++) {
							Map<String, String> map = dbList.get(i);
							ContentValues cv = new ContentValues();//存taskId，baseId，category
							cv.put("taskId", map.get("TaskID"));
							cv.put("baseId", map.get("BaseID"));
							cv.put("category", map.get("BaseSchema"));
							list.add(cv);
					}
//					boolean result =  GDCacheService.getInstance().loadGDCache(context.getApplicationContext(), list);
//					System.out.println("loadAndSaveCache result=" + result);
				}
			}).start();
		}
	}
	/**
	 * 加载更多数据
	 */
	private void loadMore(final int item, final int isW) {
		// if(!b){//如果list返回为null 不加载
		// return;
		// }
		isWait = isW;
		if (item == 0) {// 如果是待办工单 加载view1
			loading = (RelativeLayout) view1.findViewById(R.id.loading);
		} else if(item == 1) {// 如果是已办 加载view2进度条
			loading = (RelativeLayout) view2.findViewById(R.id.loading);
//		}else if(item == 2){//已办公告工单
//			loading = (RelativeLayout) view3.findViewById(R.id.loading);
		}
		new AsyncTask<Void, Void, List<Map<String, String>>>() {
			@Override
			protected void onPreExecute() {// 获取数据之前
				AnimationSet set = new AnimationSet(true);
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(500);
				set.addAnimation(animation);
				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation.setDuration(500);
				set.addAnimation(animation);
				LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
				loading.setVisibility(View.VISIBLE);
				loading.setLayoutAnimation(controller);
				super.onPreExecute();
			}
			
			@Override
			protected List<Map<String, String>> doInBackground(Void... params) {// 获取数据
				// SystemClock.sleep(500);
				
				List<Map<String, String>> list11 = new ArrayList<Map<String,String>>();
				if(item <2){//已办或待办工单列表 
					list11 = getData(isW);
				}else if(item == 2){//公告列表
					list11 = getYbGgData(0,gonggaoType);
				}
				if (list11.size() == 0) {
					listNoNull = false;
					return null;
				}
				return list11;
			}
			
			@Override
			protected void onPostExecute(List<Map<String, String>> result) {// 获取数据后
				super.onPostExecute(result);
				loading.setVisibility(View.GONE);
				if (result != null && result.size() > 0) {
					int sec = 0;
					// 这里简单的就将新添加来的数据遍历加入到list1中，在设置adapter数据就ok了
					List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
//					int sec = list1.size() - 1;
					if(item != 2){//待办或已办工单列表
					
						if (isWait == 1) {// 如果是待办
							sec = (dbList == null) ? 0 : dbList.size() - 1;
							for (Map<String, String> map : result) {
								ls.add(map);
							}
							dbList.addAll(ls);
	//						cacheList();//缓存列表
	//						mAdapter1.setData(0, dbList, 1);
							mAdapter1.setData(wfCategory, dbList, 1,0);//取类型为wfCategory的工单数据，item=0待办列表显示到第一个view中
							mAdapter1.notifyDataSetChanged();
							mDisplay1.setSelection(sec);//将第sec条记录置顶
						} else if (isWait == 0) {// 如果是已办
							sec = ybList == null ? 0 : ybList.size() - 1;
							for (Map<String, String> map : result) {
								ls.add(map);
							}
							ybList.addAll(ls);
							mAdapter2.setData(wfCategory, ybList, 0,1);
							mDisplay2.setSelection(sec);
						}
//					}else{//已办公告列表
//						sec = ybGgList == null?0:(ybGgList.size()-1);
//						for (Map<String, String> map : result) {
//							ls.add(map);
//						}
//						ybGgList.addAll(ls);
//						mAdapter3.setData(gonggaoType, ybGgList, 0,2);
//						mDisplay3.setSelection(sec);
					}
				} else {
					Toast.makeText(getApplicationContext(), "下面不再有数据了！", Toast.LENGTH_SHORT).show();
				}
			}
		}.execute();
	}
	
	/**
	 * 获取数据
	 * @param 根据type类型获取数据 isW 1:待办 0:已办 index1、index2:页数 5:一页几条数据
	 * @return 返回list数组
	 */
	private List<Map<String, String>> getData(final int isW) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		mapNum = new HashMap<String, String>();//各类工单的待办条数
		GDListService gdList = new GDListServiceImp();
		System.out.println("======getNet()===="+getNet());
		System.out.println("===GdListActivity().getData()==获取数据=====");
		if(getNet()){
			if (TempletModel.isWEB || flag) {
				// ****注：这里要传getApplicationContext()
//				if (isWait == 1) {// 待办
//					lp = gdList.getWaitList(context.getApplicationContext(), isW, index1, 10, currentItem, "");
//				} else if (isWait == 0) {// 已办
//					lp = gdList.getWaitList(context.getApplicationContext(), isW, index2, 10, currentItem, "");
//				}
				lp = gdList.getWaitList(this.getApplicationContext(), isW, index1, 10, wfCategory, "");
				if(lp != null){
					list = lp.getListInfo();//待办或已办列表
				}
			} else {
				list = getWaitList(currentItem);
				list = new ArrayList();
			}
		}else{
			MToast.show(this, R.string.msg_error_network_unavailable);//网络不能用，给出提示
		}
//		else{//离线的情况
//			String str = "";
//			switch (currentItem) {
//			case 2:
//				str = "WF4:EL_TTM_TTH";//故障工单
//				break;
//			case 3:
//				//str = "WF4:EL_AM_PS";
//				str ="WF4:EL_UVS_TSK";//通用任务工单
//				break;
//			case 4:
//				//str = "WF4:EL_AM_AT";
//				str = "WF4:EL_TTM_CCH";//个人投诉工单
//				break;
////			case 5:
////				str="WF4:EL_AM_ER";
////				break;
////			case 6:
////				str="WF4:EL_AM_BULT";
////				break;
//			default:
//				break;
//			}
//			if(currentItem != 1)
//				list = executeQuery("select * from tb_gd_cache where BaseSchema = ? and IsWait = ?",new String[]{str,String.valueOf(isWait)});
//			else
//				list = executeQuery("select * from tb_gd_cache where IsWait = ?",new String[]{String.valueOf(isWait)});
//			Map<String,String> m = new HashMap<String,String>();
//			m.put("WF4:EL_TTM_TTH", "0");
//			m.put("WF4:EL_UVS_TSK", "0");
//			m.put("WF4:EL_TTM_CCH", "0");
//			
//			List<Map<String,String>> mapList = executeQuery("select count(BaseSchema) as c, BaseSchema  from  tb_gd_cache where isWait = 1 group by baseSchema",new String[]{});
//			
//			for(int i = 0;i< mapList.size();i++){
//				Map<String,String> map = mapList.get(i);
//				m.put(map.get("BaseSchema"),map.get("c"));
//			}
//			lp = new ListModel();
//			lp.setBaseCount(m);
//			lp.setListInfo(list);
//		}
		return list;
	}
	/**
	 * 获取已办公告列表
	 * @param isW
	 * @return
	 */
	private List<Map<String, String>> getYbGgData(int isW,String gdType) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		mapNum = new HashMap<String, String>();//各类工单的待办条数
		GDListService gdList = new GDListServiceImp();
		System.out.println("===GdListActivity().getData()==获取已办公告列表=====");
		if(getNet()){
			ListModel listMode = gdList.getGgWaitList(this.getApplicationContext(), isW, index3, 10, gdType, "");
			if(listMode != null){
				list = listMode.getListInfo();//待办或已办列表
			}
		}else{
			MToast.show(this, R.string.msg_error_network_unavailable);//网络不能用，给出提示
		}
		return list;
	}	
	/*
	 * 判断网络连接  优化网络连接
	 */
	private boolean getNet(){

		return NetWork.checkWork(getBaseContext())==1;
		//return true;

	}
	
	public ArrayList<Map<String,String>> executeQuery(String sql,String[] selectionArgs){
		DBHelper helper = new DBHelper(getBaseContext());
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor  cursor = db.rawQuery(sql, selectionArgs); 
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		while(cursor.moveToNext()){
			int columnCount = cursor.getColumnCount();
			HashMap<String,String> map = new HashMap<String,String>();
			for(int i = 0 ; i < columnCount; i++)
				map.put(cursor.getColumnName(i), cursor.getString(i));
			list.add(map);
		}
		cursor.close(); 
		db.close();
		return list;
	}
	/**
	 * 本地列表
	 * @param classify
	 * @return
	 */
	public List<Map<String, String>> getWaitList(int classify) {
		StringBuffer idata = new StringBuffer("<opDetail  sp='1'  ep='1'  ps='5' pc='2'  rc='10'  rs='20'>");
		switch (classify) {
		case 1:
			for (int i = 0; i < 5; i++) {
				idata.append("	<recordInfo>");
				idata.append("	<field code='BaseType'>" + (i + 1) + "</field>");
				idata.append("	<field code='BaseSummary'>请对相关基站内部空调设备进行厂家统计" + (i + 1) + "</field>");
				idata.append("	<field code='BaseDealOuttime'>2012-12-11 21:34:41</field>");
				idata.append("  <field code='BaseStatus'>未签收</field>");
				idata.append("	</recordInfo>");
			}
			break;
		case 2:
			for (int i = 0; i < 5; i++) {
				idata.append("	<recordInfo>");
				idata.append("	<field code='BaseType'>" + (i + 1) + "</field>");
				idata.append("	<field code='BaseSummary'>铁岭市.昌图.检察院基站发电保障" + (i + 1) + "</field>");
				idata.append("	<field code='NetElement'>铁岭.昌图.检察院基站</field>");
				idata.append("  <field code='BaseDealOuttime'>2012-12-11 21:34:41</field>");
				idata.append("  <field code='BaseStatus'>未签收</field>");
				idata.append("  <field code='BaseSchema'>发电工单主题</field>");// 发电工单主题
				idata.append("  <field code='BaseSN'>ID00000</field>");// ID
				idata.append("	<field code='BaseID'>0000000</field>");
				idata.append("  <field code='TaskID'>1111111</field>");
				idata.append("	</recordInfo>");
			}
			break;
		case 3:
			for (int i = 0; i < 5; i++) {
				idata.append("	<recordInfo>");
				idata.append("	<field code='BaseType'>" + (i + 1) + "</field>");
				idata.append("	<field code='BaseSummary'>铁岭市.昌图.检察院基站故障" + (i + 1) + "</field>");
				idata.append("	<field code='NetElement'>铁岭.昌图.检察院基站</field>");
				idata.append("  <field code='BaseDealOuttime'>2012-12-11 21:34:51</field>");
				idata.append("  <field code='BaseStatus'>未签收</field>");
				idata.append("	</recordInfo>");
			}
			break;
		case 4:
			for (int i = 0; i < 5; i++) {
				idata.append("	<recordInfo>");
				idata.append("	<field code='BaseType'>" + (i + 1) + "</field>");
				idata.append("	<field code='BaseSummary'>新投诉申告/基础通信/话音基本业务/网络覆盖/室外/乡镇" + (i + 1) + "</field>");
				idata.append("	<field code='BaseDealOuttime'>2012-12-11 21:34:51</field>");
				idata.append("  <field code='BaseStatus'>未签收</field>");
				idata.append("	</recordInfo>3" +
						"");
			}
			break;
		case 5:
			for (int i = 0; i < 5; i++) {
				idata.append("	<recordInfo>");
				idata.append("	<field code='BaseType'>" + (i + 1) + "</field>");
				idata.append("	<field code='BaseSummary'>请对相关基站内部空调设备进行厂家统计" + (i + 1) + "</field>");
				idata.append("	<field code='NetElement'>铁岭.昌图.检察院基站</field>");
				idata.append("  <field code='BaseDealOuttime'>2012-12-11 21:34:51</field>");
				idata.append("  <field code='BaseStatus'>未签收</field>");
				idata.append("	</recordInfo>");
			}
			break;
		}
		idata.append("</opDetail>");
		XMLUtil xml = new XMLUtil(idata.toString());
		return xml.getList();
	}
	
	/*
	 * 页头标监听器 点击切换
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;//哪个页卡显示到当前页
		
		public MyOnClickListener(int i) {
			index = i;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnWaitGd:// 待办工单
				//btnWaitGdIsSelected();
				bottom_lyt.setVisibility(View.VISIBLE);
				btnButtonSelected();
//				if(isChecked()){
//					showOrHideAbBottom(true);//显示隐藏下载按钮
//				}
				break;
			case R.id.btnFinishGd:// 已办工单
				//btnFinishGdIsSelected();
				bottom_lyt.setVisibility(View.VISIBLE);
				btnButtonSelected();
//				showOrHideAbBottom(false);
				break;
//			case R.id.btnGongGao://公告工单
//				bottom_lyt.setVisibility(View.GONE);
//				btnButtonSelected();
//				break;
			case R.id.iv_friends://退出工单列表按钮
				 backtoLogin();
				 break;
			case R.id.iv_setting://系统设置
				setting();
				break;
			default:
				break;
			}
			if(index >=0){
				viewPager.setCurrentItem(index);
			}
		}
	}
	
	private void setting(){
		Intent intent = new Intent(GdListActivity.this,SettingActivity.class);
		startActivity(intent);
		
	}
	/**
	 * 点击工单上的退出按钮，返回到登录页面
	 */
	private void backtoLogin(){
		new AlertDialog.Builder(this)
		.setView(View.inflate(this, R.layout.exit_dialog, null))
		.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {

					}
				})
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						NotificationManager nm = (NotificationManager) getApplicationContext()
								.getSystemService(
										Context.NOTIFICATION_SERVICE);
						nm.cancelAll();
						
                     MyData.finishProgram();
					}
				}).show();
	}
	
	// 待办工单是否被选中
	public void btnWaitGdIsSelected() {
		if (!btnWaitGd.isSelected()) {
			btnWaitGd.setSelected(true);
		}
		if (btnFinishGd.isSelected()) {
			btnFinishGd.setSelected(false);
		}
	}
	
	// 已办工单是否被选中
	public void btnFinishGdIsSelected() {
		if (!btnFinishGd.isSelected()) {
			btnFinishGd.setSelected(true);
		}
		if (btnWaitGd.isSelected()) {
			btnWaitGd.setSelected(false);
		}
	}
	//设置已办、待办工单和已办公告按钮是否被选中的状态
	public void btnButtonSelected(){
		if(btnWaitGd.isSelected()){//已选择待办工单
			btnFinishGd.setSelected(false);
//			btnFinishGg.setSelected(false);
		}else if(btnFinishGd.isSelected()){//已选择已办工单
			btnWaitGd.setSelected(false);
//			btnFinishGg.setSelected(false);
//		}else if(btnFinishGg.isSelected()){//已选择已办公告工单
//			btnWaitGd.setSelected(false);
//			btnFinishGg.setSelected(false);
		}
	}

	/*
	 * 页卡切换监听  动画效果
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
//		int one = offset * 2 + bitmapWidth;// 页卡1 -> 页卡2偏移量
		// int two = one * 2;// 页卡1 -> 页卡3偏移量
		// int three = one * 3;// 页卡1 ->页卡4偏移量
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageSelected(int arg0) {
			System.out.println("arg0==========="+arg0);
			// 获取分辨率
			Display display = ((WindowManager) (getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
			//屏幕宽度
			int screenW = display.getWidth();
			

			Animation anim = null;
//			if (arg0 > 2) {
//				arg0 = arg0 % 3;
//			}
			switch (arg0) {
			case 0:
				//if (currIndex == 1) {// 页卡1->页卡2
					toX = 0;
					anim = new TranslateAnimation(fromX, toX, 0, 0);
					btnButtonSelected();
				//}
				isWait = 1;//待办
				if(isChecked()){
					showOrHideAbBottom(true);
				}
				fromX = 0;
				break;
			case 1:
				toX = screenW/2;
				//if (currIndex == 0) {// 页卡2->页卡1
					anim = new TranslateAnimation(fromX, toX,0 , 0);
					btnButtonSelected();
				//}
				fromX = toX;
				isWait = 0;//已办
				showOrHideAbBottom(false);
				break;
//			case 2:	
//				toX = (screenW/3)*2;
//				anim = new TranslateAnimation(fromX, toX,0 , 0);
//				fromX = toX;
//				break;
			}
			if (anim != null) {
//				currIndex = arg0;
				anim.setFillAfter(true);// True:图片停在动画结束位置
				anim.setDuration(300);
				imgIndicator.startAnimation(anim);
			}
		}
	}
	
	/**
	 * 数据适配器
	 * @author Administrator
	 */
	private class Adapter extends BaseAdapter {
		private Context mContext;
		private int item;//当前页面是已办还是待办列表页面
		private String wfType;//工单类型
		
		private List<Map<String, String>> data;
		private Map<Integer,Boolean> mapState;
		@SuppressWarnings("unused")
		private int type;//待办1,已办0
		private boolean[] checkedItems;
		
		public Adapter(Context context) {
			this.mContext = context;
			//上来new出一个100大小的数组
			checkedItems = new boolean[100];
			mapState=new HashMap<Integer,Boolean>();
		}
		
		// 设置数据
		private void setData(int item, List<Map<String, String>> data, int type) {
			this.item = item;
			this.data = data;
			this.type = type;
			if(type == 1){//待办
				if(data != null && data.size() >= checkedItems.length){//如果列表的长度比数组大
					changeArraySize();
				}
			}
		}
		// 设置数据
		private void setData(String category, List<Map<String, String>> data, int type,int item) {
			this.wfType = category;
			this.item = item;
			this.data = data;
			this.type = type;
			if(type == 1){//待办
				if(data != null && data.size() >= checkedItems.length){//如果列表的长度比数组大
					changeArraySize();
				}
			}
		}
		public boolean[] getArray(){
			return checkedItems;
		}
		@Override
		public int getCount() {
			return this.data == null ? 0 : this.data.size();
		}
		
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.friends_list_item, null);
				holder = new ViewHolder();
				//主题：xxx主题
				holder.tv_theme_contents = (TextView) convertView.findViewById(R.id.tv_theme_content);
				//网元名称：test
				holder.tv_netName = (TextView) convertView.findViewById(R.id.tv_netName);
				holder.tv_person = (TextView) convertView.findViewById(R.id.tv_person);
				//处理时限
//				holder.tv_timeName = (TextView) convertView.findViewById(R.id.tv_timeName);
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				//工单状态
				holder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
				//工单类型图标
				holder.iv_iconImage = (TextView) convertView.findViewById(R.id.gd_item_image);
				
				//网元名称所在layout
				holder.ll_netName = (LinearLayout) convertView.findViewById(R.id.ll_netName);
				//工单状态所在layout
				holder.ll_StatuName = (LinearLayout) convertView.findViewById(R.id.ll_StatuName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
//			//下载
//			final ImageView downLoadImg=(ImageView) convertView.findViewById(R.id.down_load_img);
//			
//			if(mapState.get(position)!=null&&mapState.get(position)){
//				downLoadImg.setBackgroundResource(R.drawable.downloadon);
//			}
//			else{
//				downLoadImg.setBackgroundResource(R.drawable.download);
//			}
//			//公告时隐藏掉图标
//			if("WF4:EL_UVS_BULT".equalsIgnoreCase(wfType)){
//				holder.iv_iconImage.setVisibility(View.GONE);
//			}else{
//				holder.iv_iconImage.setVisibility(View.VISIBLE);
//			}
			String iconName = "";//工单图标上的名称
			String netNameTitle = "工单名称:";//网元名称title
			String netNameContent = "";//网元名称内容
			String BaseDealOuttime = data.get(position).get("BaseDealOuttime");
			if (BaseDealOuttime != null && !BaseDealOuttime.equals("")) {
				BaseDealOuttime = StringToLongToString(BaseDealOuttime);
			}
			netNameContent = data.get(position).get("basename");
			iconName = String.valueOf(position+1);
			/*
//			wfType = wfCategory;
			if("WF4:EL_TTM_TTH".equalsIgnoreCase(wfType)){
				iconName = "故障";
				netNameTitle = "网元名称：";
				netNameContent = data.get(position).get("INC_NE_Name");
			}else if("WF4:EL_UVS_TSK".equalsIgnoreCase(wfType)){
				iconName = "任务";
				netNameTitle = "任务类型：";
				netNameContent = data.get(position).get("BaseTaskType");
			}else if("WF4:EL_TTM_CCH".equalsIgnoreCase(wfType)){
				iconName = "投诉";
				netNameTitle = "投诉分类：";
				netNameContent = data.get(position).get("CCH_ComplainType");
			}else if("WF4:EL_ITSM_EVENT".equalsIgnoreCase(wfType)){//IT事件投诉
				iconName = "IT投诉";
				netNameTitle = "事件分类：";
				netNameContent = data.get(position).get("ITMS_Event_Classify");
			}else if("WF4:EL_ITSM_CHANGE".equalsIgnoreCase(wfType)){
				iconName = "IT变更";
				netNameTitle = "变更类型:";
				netNameContent = data.get(position).get("Approval1_Type");
			}else if("WF4:EL_ITSM_EVENT_TTH".equalsIgnoreCase(wfType)){//it事件故障
				iconName = "IT故障";
				netNameTitle = "变更类型:";
				netNameContent = data.get(position).get("Approval1_Type");
			}else if("WF4:EL_ITSM_DBUA".equalsIgnoreCase(wfType)){//IT账号权限变更
				iconName = "账号权限变更";
				netNameTitle = "变更类型:";
				netNameContent = data.get(position).get("ITSM_DBUA_Change_Type");
			}else if("WF4:EL_UVS_BULT".equalsIgnoreCase(wfType)){//已办公告工单
				iconName = "公告";
				netNameTitle = "发布部门:";
				netNameContent = data.get(position).get("BaseCreatorDep");
			}else if("ALL".equalsIgnoreCase(wfType)){
					String img_type = data.get(position).get("BaseSchema");
					if("WF4:EL_TTM_TTH".equalsIgnoreCase(img_type)){
						iconName = "故障";
						netNameTitle = "网元名称：";
						netNameContent = data.get(position).get("INC_NE_Name");
					}else if("WF4:EL_UVS_TSK".equalsIgnoreCase(img_type)){
						iconName = "任务";
						netNameTitle = "任务类型：";
						netNameContent = data.get(position).get("BaseTaskType");
					}else if("WF4:EL_TTM_CCH".equalsIgnoreCase(img_type)){
						iconName = "投诉";
						netNameTitle = "投诉分类：";
						netNameContent = data.get(position).get("CCH_ComplainType");
					}else if("WF4:EL_ITSM_EVENT".equalsIgnoreCase(img_type)){
						iconName = "IT投诉";
						netNameTitle = "事件分类：";
						netNameContent = data.get(position).get("ITMS_Event_Classify");
					}else if("WF4:EL_ITSM_EVENT_TTH".equalsIgnoreCase(wfType)){//it事件故障
						iconName = "IT故障";
						netNameTitle = "变更类型";
						netNameContent = data.get(position).get("Approval1_Type");
					}else if("WF4:EL_ITSM_CHANGE".equalsIgnoreCase(img_type)){
						iconName = "IT变更";
						netNameTitle = "变更类型";
						netNameContent = data.get(position).get("Approval1_Type");
					}else if("WF4:EL_ITSM_DBUA".equalsIgnoreCase(img_type)){
						iconName = "账号权限变更";
						netNameTitle = "变更类型:";
						netNameContent = data.get(position).get("ITSM_DBUA_Change_Type");
					}else{
						iconName = "其它";
						netNameTitle = "其它:";
						netNameContent = data.get(position).get("INC_NE_Name");
					}
			}*/
			// 标题内容
			holder.tv_theme_contents.setText(data.get(position).get("BaseSummary"));
			//工单图标
			holder.iv_iconImage.setText(iconName);
			
			holder.tv_netName.setText(netNameTitle);
			holder.tv_person.setText(netNameContent);// 网元名称内容
			holder.tv_time.setText(BaseDealOuttime);// 处理时限内容
			holder.tv_sign.setText(data.get(position).get("BaseStatus"));// 工单状态内容：未签收
			holder.BaseSchema = data.get(position).get("BaseSchema");
			holder.BaseSummary = data.get(position).get("BaseSummary");
			holder.BaseSN = data.get(position).get("BaseSN");
			holder.BaseID = data.get(position).get("BaseID");
			holder.TaskID = data.get(position).get("TaskID");
			return convertView;
			/*downLoadImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mapState.put(position, true);
					downLoadImg.setBackgroundResource(R.drawable.downloadon);
					new Thread(new Runnable() {
							@Override
							public void run() {
								TestXmlCreat tc = new TestXmlCreat(getBaseContext().getApplicationContext());
								HashMap<String,String> map = new HashMap<String,String>();
								map.put("taskid",data.get(position).get("TaskID") );
								tc.addElement(tc.recordInfo, "item", data.get(position).get("BaseID") + "," + data.get(position).get("BaseSchema"), map);
								
								HTTPConnection hc = new HTTPConnection(getBaseContext().getApplicationContext());
								Map<String, String> parmas = new HashMap<String, String>();
								parmas.put("serviceCode", "G007");
								parmas.put("inputXml", tc.doc.asXML());
								
								hc.setInputType("File");
								hc.setFilePath(Environment.getExternalStorageDirectory().getPath()+AppConstants.FILE_CACHE);
								hc.setFileName("text.zip");
								String str = hc.Sent(parmas);
								
								DBHelper helper = new DBHelper(getBaseContext());
								SQLiteDatabase db = helper.getWritableDatabase();
								
								String sel = "BaseID,TaskID,BaseDealOuttime,AffectBussType,BaseStatus,INC_NE_Name,SiteName,BaseSchema,BaseSummary,BaseSN,IsWait";
								if(db.isOpen()){
									db.execSQL("delete from tb_gd_cache where BaseID=?", new Object[]{data.get(position).get("BaseID")});
									
									db.execSQL("insert into tb_gd_cache ("+sel+") values (?,?,?,?,?,?,?,?,?,?,?)", 
											new Object[]{
											data.get(position).get("BaseID"),
											data.get(position).get("TaskID"),
											data.get(position).get("BaseDealOuttime"),
											data.get(position).get("AffectBussType"),
											data.get(position).get("BaseStatus"),
											data.get(position).get("INC_NE_Name"),
											data.get(position).get("SiteName"),
											data.get(position).get("BaseSchema"),
											data.get(position).get("BaseSummary"),
											data.get(position).get("BaseSN"),
											isWait});
									db.close();
								}
							}
					}).start();					
				}
			});
			
			return convertView;*/
		}
	}
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = null;
//			
//			if (convertView == null) {
//				convertView = LayoutInflater.from(mContext).inflate(R.layout.friends_list_item, null);
//				holder = new ViewHolder();
//				holder.tv_theme_contents = (TextView) convertView.findViewById(R.id.tv_theme_content);
//				holder.tv_person = (TextView) convertView.findViewById(R.id.tv_person);
//				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//				holder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
//				holder.iv_iconImage = (TextView) convertView.findViewById(R.id.gd_item_image);
//				holder.ll_netName = (LinearLayout) convertView.findViewById(R.id.ll_netName);
//				holder.tv_netName = (TextView) convertView.findViewById(R.id.tv_netName);
//				holder.tv_timeName = (TextView) convertView.findViewById(R.id.tv_timeName);
//				holder.ll_StatuName = (LinearLayout) convertView.findViewById(R.id.ll_StatuName);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			final ImageView downLoadImg=(ImageView) convertView.findViewById(R.id.down_load_img);
//			if(mapState.get(position)!=null&&mapState.get(position)){
//				downLoadImg.setBackgroundResource(R.drawable.downloadon);
//			}
//			else{
//				downLoadImg.setBackgroundResource(R.drawable.download);
//			}
//			switch (this.item) {
//			
//			case 2: {
//				holder.iv_iconImage.setText("故障");
//				holder.tv_netName.setText("基站名称：");
//				holder.tv_theme_contents.setText(data.get(position).get("BaseSummary"));// 标题内容
//				holder.tv_person.setText(data.get(position).get("INC_NE_Name"));// 网元名称内容
//				String BaseDealOuttime = data.get(position).get("BaseDealOuttime");
//				if (BaseDealOuttime != null && !BaseDealOuttime.equals("")) {
//					BaseDealOuttime = StringToLongToString(BaseDealOuttime);
//				}
//				holder.tv_time.setText(BaseDealOuttime);// 处理时限内容
//				holder.tv_sign.setText(data.get(position).get("BaseStatus"));// 工单状态内容：未签收
//				holder.BaseSchema = data.get(position).get("BaseSchema");
//				holder.BaseSummary = data.get(position).get("BaseSummary");
//				holder.BaseSN = data.get(position).get("BaseSN");
//				holder.BaseID = data.get(position).get("BaseID");
//				holder.TaskID = data.get(position).get("TaskID");
//				break;
//			}
//			case 3: {//通用任务
//				// holder.iv_iconImage.setImageResource(R.drawable.bug);
//				holder.iv_iconImage.setText("任务");
//				holder.tv_netName.setText("任务类型：");
//				holder.tv_theme_contents.setText(data.get(position).get("BaseSummary"));// 标题内容
//				holder.tv_person.setText(data.get(position).get("BaseTaskType"));// 任务类型
//				holder.tv_time.setText(StringToLongToString(data.get(position).get("BaseDealOuttime")));// 处理时限内容
//				holder.tv_sign.setText(data.get(position).get("BaseStatus"));// 工单状态内容：未签收
//				break;
//			}
//			case 4: {//个人投诉
//				holder.iv_iconImage.setText("投诉");
//				holder.tv_netName.setText("投诉分类：");
//				holder.tv_timeName.setText("处理时限：");
//				holder.tv_theme_contents.setText(data.get(position).get("BaseSummary"));// 标题内容
//				holder.tv_person.setText(data.get(position).get("CCH_ComplainType"));// 投诉分类
//				holder.tv_time.setText(StringToLongToString(data.get(position).get("BaseDealOuttime")));// 处理时限内容
//				holder.tv_sign.setText(data.get(position).get("BaseStatus"));
//				break;
//			}
//			case "ALL": {
//				String img_type = data.get(position).get("BaseSchema");
//				if (img_type.equals("WF4:EL_UVS_TSK")) {
//					img_text = "任务";
//					holder.tv_netName.setText("任务类型：");
//					holder.tv_person.setText(data.get(position).get("BaseTaskType"));// 任务类型
//				} else if (img_type.equals("WF4:EL_AM_TTH")) {//故障
//					img_text = "故障";
//					holder.tv_netName.setText("网元名称：");
//					holder.tv_person.setText(data.get(position).get("INC_NE_Name"));// 网元名称内容
//				} else if (img_type.equals("WF4:EL_TTM_CCH")) {//投诉
//					img_text = "投诉";
//					holder.tv_netName.setText("投诉分类：");
//					holder.tv_person.setText(data.get(position).get("CCH_ComplainType"));//基站名称内容
//				} else {
//					img_text = "其它";
//				}
//				holder.iv_iconImage.setText(img_text);
//				holder.tv_theme_contents.setText(data.get(position).get("BaseSummary"));// 标题内容
//				holder.tv_time.setText(StringToLongToString(data.get(position).get("BaseDealOuttime")));// 处理时限内容
//				holder.tv_sign.setText(data.get(position).get("BaseStatus"));// 工单状态内容：未签收
//				break;
//			}
//			}
//			downLoadImg.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					mapState.put(position, true);
//					downLoadImg.setBackgroundResource(R.drawable.downloadon);
//					new Thread(new Runnable() {
//							@Override
//							public void run() {
//								TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
//								HashMap<String,String> map = new HashMap<String,String>();
//								map.put("taskid",data.get(position).get("TaskID") );
//								tc.addElement(tc.recordInfo, "item", data.get(position).get("BaseID") + "," + data.get(position).get("BaseSchema"), map);
//								
//								HTTPConnection hc = new HTTPConnection(context.getApplicationContext());
//								Map<String, String> parmas = new HashMap<String, String>();
//								parmas.put("serviceCode", "G007");
//								parmas.put("inputXml", tc.doc.asXML());
//								
//								hc.setInputType("File");
//								hc.setFilePath(Environment.getExternalStorageDirectory().getPath()+AppConstants.FILE_CACHE);
//								hc.setFileName("text.zip");
//								String str = hc.Sent(parmas);
//								
//								DBHelper helper = new DBHelper(context);
//								SQLiteDatabase db = helper.getWritableDatabase();
//								
//								String sel = "BaseID,TaskID,BaseDealOuttime,AffectBussType,BaseStatus,INC_NE_Name,SiteName,BaseSchema,BaseSummary,BaseSN,IsWait";
//								if(db.isOpen()){
//									db.execSQL("delete from tb_gd_cache where BaseID=?", new Object[]{data.get(position).get("BaseID")});
//									
//									db.execSQL("insert into tb_gd_cache ("+sel+") values (?,?,?,?,?,?,?,?,?,?,?)", 
//											new Object[]{
//											data.get(position).get("BaseID"),
//											data.get(position).get("TaskID"),
//											data.get(position).get("BaseDealOuttime"),
//											data.get(position).get("AffectBussType"),
//											data.get(position).get("BaseStatus"),
//											data.get(position).get("INC_NE_Name"),
//											data.get(position).get("SiteName"),
//											data.get(position).get("BaseSchema"),
//											data.get(position).get("BaseSummary"),
//											data.get(position).get("BaseSN"),
//											isWait});
//									db.close();
//								}
//							}
//					}).start();					
//				}
//			});
//			return convertView;
//		}
//	}
	
	
/**
 * 判断代办中有没有选中的checkbox
 * @return
 */
	private boolean isChecked(){
		boolean haveChecked = false;
		if(mAdapter1.getArray()!=null){
			for (int i = 0; i < mAdapter1.getArray().length; i++) {
				if(mAdapter1.getArray()[i]){//如果有一个为真
					haveChecked = true;
				}
			}
		}
		return haveChecked;
	}
/**
 * 增加数组长度
 */
	private void changeArraySize() {
		boolean newarray[] = new boolean[mAdapter1.getArray().length + 50];
		for(int i = 0; i < mAdapter1.getArray().length; i++){
			newarray[i] = mAdapter1.getArray()[i];
		}
		mAdapter1.checkedItems = newarray;
	}
	
	/**
	 * 显示或者隐藏下载按钮
	 * @param show
	 */
	private void showOrHideAbBottom(boolean show){
		if(show){
			if(llAbBottom.getVisibility()!=View.VISIBLE){
				llAbBottom.setVisibility(View.VISIBLE);
				bottom_lyt.setVisibility(View.GONE);
			}
		}else{
			if(llAbBottom.getVisibility()!=View.GONE){
				llAbBottom.setVisibility(View.GONE);
				bottom_lyt.setVisibility(View.VISIBLE);
			}
		}
	}
	/**
	 * 显示或者隐藏下载按钮
	 * @param show
	 */
	private void showOrHideAbBottomOld(boolean show){
		if(show){
			if(llAbBottom.getVisibility()!=View.VISIBLE){
				llAbBottom.setVisibility(View.VISIBLE);
				bottom_lyt.setVisibility(View.GONE);
			}
		}else{
			if(llAbBottom.getVisibility()!=View.GONE){
				llAbBottom.setVisibility(View.GONE);
				bottom_lyt.setVisibility(View.VISIBLE);
			}
		}
	}
	@SuppressLint("SimpleDateFormat")
	private String StringToLongToString(String searcherInput) {
		long lgTime = 0;
		Boolean judge = false;
		try {
			
			Integer.parseInt(searcherInput);
			searcherInput += "000";
			judge = true;
		} catch (NumberFormatException e) {
			judge = false;
		}
		if (judge == true) {			
				lgTime = Long.parseLong(searcherInput);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
					searcherInput = sdf.format(lgTime);		

		}
		return searcherInput;
	}
	
	public class ViewHolder {
		TextView tv_theme_contents;// 标题内容
		TextView tv_person;// 网元名称内容
		TextView tv_time;// 处理时限内容
		TextView tv_sign;// 工单状态内容：未签收
		TextView iv_iconImage;
		LinearLayout ll_netName;
		TextView tv_netName;
		LinearLayout ll_timeName;
		TextView tv_timeName;
		LinearLayout ll_StatuName;
		String BaseSchema;
		String BaseSummary;
		String BaseSN;
		String BaseID;
		String TaskID;
		CheckBox checkBox;
	}
	

	public void update() {
		bottom_flag = false;
		index1=1;
		index2=1;
		index3 = 1;
		initViewPager(currentItem);
		setListeners(currentItem);
		//setNum(lp.getBaseCount());
	}
	
	@SuppressLint("HandlerLeak")
	class DownLoadTread extends Thread {
		@Override
		public void run() {
			ybList = null;//已办listMode
			dbList = null;//待办listMode
//			ybGgList = null;//已办公告工单
			while (WaitDialog.flag && dbList == null && ybList == null) {
				// 获取待办工单
				dbList = getData(1);
				//cacheList();
				// 获取已办工单
				ybList = getData(0);
//				if("All".equalsIgnoreCase(wfCategory)){
					//已办公告工单
//					ybGgList = getYbGgData(0,gonggaoType);//"WF4:EL_UVS_BULT"
//				}
//				if (dbList.size() > 0 || ybList.size() > 0 || ybGgList.size() >0) {
				if (dbList.size() > 0 || ybList.size() > 0 ) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
			if (ybList == null && dbList == null) {
				handler.sendEmptyMessage(0);
			}
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {//待办列表或已办列表不为空
				mAdapter1 = new Adapter(getBaseContext());
//				mAdapter1.setData(0, null, 1);
//				mAdapter1.setData(currentItem, dbList, 1);
				mAdapter1.setData(wfCategory, dbList, 1,0);//0：待办工单显示到第一个view  
				mAdapter1.notifyDataSetChanged();
				mDisplay1.setAdapter(mAdapter1);
				mapNum = lp.getBaseCount();
				if (bottom_flag) {
					/** 根据登录解析到的版本类型动态加载底部按钮 */
					List<Type>  templateType= LoginServiceImp.xml.getType();
					for (int i = 0; i < bottom_size + 1; i++) {
						id = i + 1;
						if (i == 0) {// 全部的处理
							flag = true;
							text_name = "全部";
							addBottomButton(automaic_lyt, "ALL", id, text_name);// 动态加载传数据方法
						} else {
							flag = false;// 非全部
							text_name = ((Type)templateType.get(i - 1)).getText();
							String categoryType = ((Type)templateType.get(i - 1)).getType();
							if(!gonggaoType.equalsIgnoreCase(categoryType)){
								addBottomButton(automaic_lyt, categoryType, id, text_name);// 动态加载传数据方法
							}
						}
					}
					//底部 放  更多...  按钮
//					text_name = "更多";
//					addBottomButtonMore(automaic_lyt,"more",id,text_name);
					bottom_flag = false;
				}
				if (dbList.size() == 0 && ybList.size() == 0) {
					view1.setBackgroundResource(R.drawable.nofindbg);
					view2.setBackgroundResource(R.drawable.nofindbg);
					Toast.makeText(getBaseContext(), "待办已办数据为空!!", 0).show();					
				}
				else if(dbList.size() != 0 && ybList.size() == 0){
					view2.setBackgroundResource(R.drawable.nofindbg);
				}
				else if(dbList.size() == 0 && ybList.size() != 0){
					view1.setBackgroundResource(R.drawable.nofindbg);
				}
//				//已办公告工单列表为空
//				if(ybGgList.size() == 0){
//					view3.setBackgroundResource(R.drawable.nofindbg);
//				}
				// 更新界面
				mAdapter2 = new Adapter(getBaseContext());
				mAdapter2.setData(wfCategory, ybList, 0,1);//1:已办工单显示到第二个view  PagerView
				mAdapter2.notifyDataSetChanged();
				mDisplay2.setAdapter(mAdapter2);
				setNum(lp.getBaseCount());
//				//已办公告PagerView
//				mAdapter3 = new Adapter(getBaseContext());
//				mAdapter3.setData(gonggaoType, ybGgList, 0,2);//2:PagerView 显示到第三个view中
//				mDisplay3.setAdapter(mAdapter3);
			} else if (msg.what == 0) {
				view1.setBackgroundResource(R.drawable.nofindbg);
				Toast.makeText(getBaseContext(), "网络不给力，数据获取失败!!", 0).show();
			}
			dialog.dismiss();
		};
	};
	

	/**
	 * 设置底部类别个数
	 * @param map
	 */
	private void setNum(Map<String, String> map){
		Set<Entry<String, String>> set = map.entrySet();
		int i = 0;
		if(textViewMap != null && !textViewMap.isEmpty()){
			for (Iterator<Map.Entry<String, String>> it = set
						.iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
					String type = entry.getKey();
					TextView textView = (TextView)textViewMap.get(type);
					if(textView != null){
						textView.setVisibility(View.VISIBLE);
						textView.setText(entry.getValue());
						i += Integer.parseInt(entry.getValue());
					}
			}
			textViewMap.get("ALL").setVisibility(View.VISIBLE);
			textViewMap.get("ALL").setText(String.valueOf(i));
			if(i==0)
				textViewMap.get("ALL").setVisibility(View.GONE);
		}
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}

}
