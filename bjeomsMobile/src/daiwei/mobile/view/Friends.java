package daiwei.mobile.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import eoms.mobile.R;
import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.activity.BaseTmpActivity;
import daiwei.mobile.activity.GdBugActivity;
import daiwei.mobile.activity.GdFdActivity;
import daiwei.mobile.activity.GdTyActivity;
import daiwei.mobile.adapter.MyPagerAdapter;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;
import daiwei.mobile.service.GDListService;
import daiwei.mobile.service.GDListServiceImp;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.Type;

/**
 * 代维工单页面
 * @author 都 3/25
 */
public class Friends {
	public Context context;
	public View mFriends;
	private ListView mDisplay1;
	private ListView mDisplay2;
	private Adapter mAdapter1;
	private Adapter mAdapter2;
	private Button btnWaitGd;
	private Button btnFinishGd;
	private Button btnAbBottom;//下载按钮
	private ImageView imgIndicator;
	private MyViewPager viewPager;
	private int offset;// 动画图片偏移量
	private int bitmapWidth;// 动画图片宽度
	private List<View> viewList;// 页卡集合
	private int currIndex = 0;// 当前页卡编号
	private View view1, view2;
	private RelativeLayout loading;
	Map<String, String> map;
	public String img_text;// 图片名字
	// dbList存储第一个list的内容
	// ybList存储第二个list的内容
	private List<Map<String, String>> dbList;// 待办列表
	private List<Map<String, String>> ybList;// 已办列表
	public int isWait = 1;// 判断已办还是待办 1：待办 0：已办
	//private GDListService gdList;
//    private List<Map<String, String>> list;
	private int index1 = 1;// 待办工单页码
	private int index2 = 1;// 已办工单页码
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
	//private EditText edit_search; // 搜索框
	public static FriendsReciver reciver = null;
	public static ArrayList<FriendsReciver> list = new ArrayList<FriendsReciver>();
	private LinearLayout llTitle;// 头标题
	private ImageView ivTitle;// 头标题
	private TextView tvTitle;// 离线在线文本
	@SuppressWarnings("unused")
	private boolean isOnline = true;
	private LinearLayout popWinTitleView;
	private PopupWindow popOnlineOrOffline;
	private Map<String,TextView> textViewMap=new  HashMap<String,TextView>();//存放类型和
	
	/**
	 * 此页面接受两个页面的数据 一个是登录成功后 主页面加载view 另一个是desktop点击跳转此页面 如果logic为1：登录成功后的 的跳转 不加载数据 只加载view logic=2：只加载数据 不加载页面
	 * @param 内容
	 * @param view数组
	 * @param 跳转页面 1：登录跳转 2：desktop跳转
	 */
	public Friends(Context context, MyViewGroup mg, int logic) {
		this.context = context;
		mFriends = LayoutInflater.from(context).inflate(R.layout.friends, null);
 		/**
		 * 如果显示右边的view,触摸就不往下传了
		 */
		mFriends.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MyViewGroup.showright) {
					return true;
				} else {
					return false;
				}
			}
		});
		btnWaitGd = (Button) mFriends.findViewById(R.id.btnWaitGd);// 待办按钮
		btnFinishGd = (Button) mFriends.findViewById(R.id.btnFinishGd);// 已办按钮
		btnAbBottom = (Button) mFriends.findViewById(R.id.btn_ab_bottom);// 下载按钮
		imgIndicator = (ImageView) mFriends.findViewById(R.id.imgIndicator);
//		llTitle = (LinearLayout) mFriends.findViewById(R.id.ll_title);
		llAbBottom = (LinearLayout) mFriends.findViewById(R.id.ll_ab_bottom);
//		ivTitle = (ImageView) mFriends.findViewById(R.id.iv_title);//展开收缩的图片
//		tvTitle = (TextView) mFriends.findViewById(R.id.tv_title);//在线 离线文本
//		if (isOnline) {//在线时不显示离线文本
//			tvTitle.setVisibility(View.GONE);
//		} else {
//			tvTitle.setVisibility(View.VISIBLE);
//		}
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popWinTitleView = (LinearLayout) inflater.inflate(R.layout.popup_gdlist_title, null, true);// 初始化popWIn
//		llTitle.setOnClickListener(new View.OnClickListener() {//标题的点击事件 弹出popup
//			@Override
//			public void onClick(View v) {
//				changeTitleBarShow();
//			}
//		});
		if (logic == 2) {
			// 接受广播 更新列表
			IntentFilter inf = new IntentFilter();
			inf.addAction("android.intent.action.Friends");
			reciver = new FriendsReciver();
			this.context.registerReceiver(reciver, inf);// 动态注册广播
			/** 动态加载底部按钮 */
			bottom_lyt = (LinearLayout) mFriends.findViewById(R.id.automatic_loaded);
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			automaic_lyt = (LinearLayout) inflater.inflate(R.layout.footbg, null, true);// 底部加载的每条数据
			bottom_lyt.addView(automaic_lyt);
			
			bottom_size = LoginServiceImp.xml.getType().size();// 3
			System.out.println("bottom_size========="+bottom_size);
			currentItem = 1;//待办
			wfCategory = "ALL";// 当前类别是全部
			initViewPager(isWait);// 初始化viewpager
			initAnimation();
			setListeners(isWait);
		}
	}
	
	/**
	 * 点击切换titlebar里的标题
	 */
	private void changeTitleBarShow() {
		if (popOnlineOrOffline != null && popOnlineOrOffline.isShowing()) {//如果pop展示 就关闭  图片改成向下
			ivTitle.setImageResource(R.drawable.arrow_open);//向下
			popOnlineOrOffline.dismiss();
		} else {//没有弹出pop时
			ivTitle.setImageResource(R.drawable.arrow_close);
			popOnlineOrOffline = new PopupWindow(popWinTitleView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			popOnlineOrOffline.showAsDropDown(llTitle, 0, 2);
			TextView tvpopWinOnline = (TextView) popWinTitleView.findViewById(R.id.tv_popwin_online);//在线数据
			TextView tvpopWinOffline = (TextView) popWinTitleView.findViewById(R.id.tv_popwin_offline);//离线数据
			tvpopWinOnline.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ivTitle.setImageResource(R.drawable.arrow_open);
					popOnlineOrOffline.dismiss();
					isOnline = true;
					tvTitle.setVisibility(View.GONE);
				}
			});
			tvpopWinOffline.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ivTitle.setImageResource(R.drawable.arrow_open);
					popOnlineOrOffline.dismiss();
					isOnline = false;
					tvTitle.setVisibility(View.VISIBLE);
				}
			});
		}
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
				wfCategory = type;
				currentItem = 1;
				listNoNull = true;
				//设置当前选中的工单分类背景图片
				Friends.this.setCurTypeBackground(type);
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
		view.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
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
//			all_num = Integer.parseInt(mapNum.get("WF4:EL_UVS_TSK")) + Integer.parseInt(mapNum.get("WF4:EL_TTM_TTH"))
//					+ Integer.parseInt(mapNum.get("WF4:EL_TTM_CCH")) + Integer.parseInt(mapNum.get("WF4:EL_ITSM_EVENT"))
//					 + Integer.parseInt(mapNum.get("WF4:EL_ITSM_CHANGE")) + Integer.parseInt(mapNum.get("WF4:EL_ITSM_EVENT_TTH"));
			wfNum = String.valueOf(all_num);
		}else{
			System.out.println("type=============="+type);
			wfNum = mapNum.get(type);
		}
		//底部工单类别视图赋值
		setViewToBottom(view,type,wfNum);
		//底部添加工单类别视图
		automaic_lyt.addView(view);
	}
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
		bitmapWidth = BitmapFactory.decodeResource(context.getResources(), R.drawable.gd_viewpager_indicator).getWidth();
		// 获取分辨率
		Display display = ((WindowManager) (context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
		int screenW = display.getWidth();
		// 计算偏移量
		offset = (screenW / 2 - bitmapWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// 设置动画初始位置
		imgIndicator.setImageMatrix(matrix);
	}
	
	// 初始化ViewPager
	private void initViewPager(int viewItem) {
		viewList = new ArrayList<View>();
		view1 = View.inflate(context, R.layout.listview, null);// 待办工单的view
		view2 = View.inflate(context, R.layout.search, null);// 已办工单的view

		viewList.add(view1);
		viewList.add(view2);
		mDisplay1 = (ListView) viewList.get(0).findViewById(R.id.friends_list);// 找到待办工单列表\
		mDisplay2 = (ListView) viewList.get(1).findViewById(R.id.friends_list);
		/**
		 * 开启子线程下载数据
		 */
		DownLoadTread downLoad = new DownLoadTread();
		
		wd = new WaitDialog(context, "数据加载中...");
		dialog = wd.getDialog();
		downLoad.start();
		viewPager=new MyViewPager(context);
		viewPager = (MyViewPager) mFriends.findViewById(R.id.viewPager);
		viewPager.removeAllViews();
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(context, viewList);
		viewPager.setAdapter(myPagerAdapter);
		if (viewItem == 1) {
			viewPager.setCurrentItem(0);//显示待办列表view
		} else {
			viewPager.setCurrentItem(1);//显示已办view
		}
		/*viewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});*/
//		viewPager.getParent().requestDisallowInterceptTouchEvent(true);
	}
	
	/**
	 * 
	 * @param item 
	 */
	private void setListeners(final int item) {
		btnWaitGd.setOnClickListener(new MyOnClickListener(0));//待办
		btnFinishGd.setOnClickListener(new MyOnClickListener(1));//已办
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());      
		mDisplay1.setOnItemClickListener(new OnItemClickListener() {//待办
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
				intent.putExtra("BaseSchema", map.get("BaseSchema"));
				intent.putExtra("BaseSummary", map.get("BaseSummary"));
				intent.putExtra("BaseSN", map.get("BaseSN"));
				intent.putExtra("BaseID", map.get("BaseID"));
				intent.putExtra("TaskID", map.get("TaskID"));
				intent.putExtra("IsWait", isWait);
				context.startActivity(intent);
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
		mDisplay2.setOnItemClickListener(new OnItemClickListener() {//已办
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				map = (Map<String, String>) parent.getAdapter().getItem(position);
				intent = new Intent(context.getApplicationContext(), BaseTmpActivity.class);
				intent.putExtra("BaseSchema", map.get("BaseSchema"));
				intent.putExtra("BaseSummary", map.get("BaseSummary"));
				intent.putExtra("BaseSN", map.get("BaseSN"));
				intent.putExtra("BaseID", map.get("BaseID"));
				intent.putExtra("TaskID", map.get("TaskID"));
				intent.putExtra("IsWait", isWait);
				context.startActivity(intent);
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
		/**
		 * 下载的点击事件
		 */
		btnAbBottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isChecked()){
					loadAndSaveCache();
				}
			}
		});
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
					intent = new Intent(context.getApplicationContext(), GdBugActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("IsWait", isWait);
					context.startActivity(intent);
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
		/**
		 * 下载的点击事件
		 */
		btnAbBottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isChecked()){
					loadAndSaveCache();
				}
			}
		});
	}// 监听器结束
	
	/**
	 * 缓存选中的列表项到数据库
	 */
	private void loadAndSaveCache(){
		if(!IsNetwork.isAccessNetWork(context)){
			MToast.showWhenNetworkUnavailable(context);
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
		if (isWait == 1) {// 如果是待办工单 加载view1
			loading = (RelativeLayout) view1.findViewById(R.id.loading);
		} else {// 如果是已办 加载view2进度条
			loading = (RelativeLayout) view2.findViewById(R.id.loading);
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
				List<Map<String, String>> list11 = getData(isW);
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
						mAdapter2.setData(wfCategory, dbList, 0,1);
						mDisplay2.setSelection(sec);
					}
				} else {
					Toast.makeText(context, "下面不再有数据了！", Toast.LENGTH_SHORT).show();
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
		System.out.println("getNet()=========="+getNet());
		if(getNet()){
			if (TempletModel.isWEB || flag) {
				// ****注：这里要传getApplicationContext()
//				if (isWait == 1) {// 待办
//					lp = gdList.getWaitList(context.getApplicationContext(), isW, index1, 10, currentItem, "");
//				} else if (isWait == 0) {// 已办
//					lp = gdList.getWaitList(context.getApplicationContext(), isW, index2, 10, currentItem, "");
//				}
				lp = gdList.getWaitList(context.getApplicationContext(), isW, index1, 10, wfCategory, "");
				if(lp != null){
					list = lp.getListInfo();//待办或已办列表
				}
			} else {
				list = getWaitList(currentItem);
				list = new ArrayList();
			}
		}else{//离线的情况
			String str = "";
			switch (currentItem) {
			case 2:
				str = "WF4:EL_TTM_TTH";//故障工单
				break;
			case 3:
				//str = "WF4:EL_AM_PS";
				str ="WF4:EL_UVS_TSK";//通用任务工单
				break;
			case 4:
				//str = "WF4:EL_AM_AT";
				str = "WF4:EL_TTM_CCH";//个人投诉工单
				break;
//			case 5:
//				str="WF4:EL_AM_ER";
//				break;
//			case 6:
//				str="WF4:EL_AM_BULT";
//				break;
			default:
				break;
			}
			if(currentItem != 1)
				list = executeQuery("select * from tb_gd_cache where BaseSchema = ? and IsWait = ?",new String[]{str,String.valueOf(isWait)});
			else
				list = executeQuery("select * from tb_gd_cache where IsWait = ?",new String[]{String.valueOf(isWait)});
			Map<String,String> m = new HashMap<String,String>();
			m.put("WF4:EL_TTM_TTH", "0");
			m.put("WF4:EL_UVS_TSK", "0");
			m.put("WF4:EL_TTM_CCH", "0");
			
			List<Map<String,String>> mapList = executeQuery("select count(BaseSchema) as c, BaseSchema  from  tb_gd_cache where isWait = 1 group by baseSchema",new String[]{});
			
			for(int i = 0;i< mapList.size();i++){
				Map<String,String> map = mapList.get(i);
				m.put(map.get("BaseSchema"),map.get("c"));
			}
			lp = new ListModel();
			lp.setBaseCount(m);
			lp.setListInfo(list);
		}
		return list;
	}
	
	
	/*
	 * 判断网络连接  优化网络连接
	 */
	private boolean getNet(){

		return NetWork.checkWork(context)==1;
		//return true;

	}
	
	public ArrayList<Map<String,String>> executeQuery(String sql,String[] selectionArgs){
		DBHelper helper = new DBHelper(context);
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
				idata.append("	</recordInfo>");
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
		private int index = 0;
		
		public MyOnClickListener(int i) {
			index = i;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnWaitGd:// 待办工单
				btnWaitGdIsSelected();
				if(isChecked()){
					showOrHideAbBottom(true);
				}
				break;
			case R.id.btnFinishGd:// 已办工单
				btnFinishGdIsSelected();
				showOrHideAbBottom(false);
				break;
			default:
				break;
			}
			viewPager.setCurrentItem(index);
		}
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
	
	/*
	 * 页卡切换监听  动画效果
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bitmapWidth;// 页卡1 -> 页卡2偏移量
		
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
			Animation anim = null;
			if (arg0 > 2) {
				arg0 = arg0 % 3;
			}
			switch (arg0) {
			case 0:
				if (currIndex == 1) {// 页卡1->页卡2
					anim = new TranslateAnimation(one, 0, 0, 0);
					btnWaitGdIsSelected();
				}
				isWait = 1;//待办
				if(isChecked()){
					showOrHideAbBottom(true);
				}
				break;
			case 1:
				if (currIndex == 0) {// 页卡2->页卡1
					anim = new TranslateAnimation(offset, one, 0, 0);
					btnFinishGdIsSelected();
				}
				isWait = 0;//已办
				showOrHideAbBottom(false);
				break;
			}
			if (anim != null) {
				currIndex = arg0;
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
			//下载
			final ImageView downLoadImg=(ImageView) convertView.findViewById(R.id.down_load_img);
			
			if(mapState.get(position)!=null&&mapState.get(position)){
				downLoadImg.setBackgroundResource(R.drawable.downloadon);
			}
			else{
				downLoadImg.setBackgroundResource(R.drawable.download);
			}
			
			String iconName = "";//工单图标上的名称
			String netNameTitle = "";//网元名称title
			String netNameContent = "";//网元名称内容
			String BaseDealOuttime = data.get(position).get("BaseDealOuttime");
			if (BaseDealOuttime != null && !BaseDealOuttime.equals("")) {
				BaseDealOuttime = StringToLongToString(BaseDealOuttime);
			}
			wfType = wfCategory;
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
			}
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
			
			downLoadImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mapState.put(position, true);
					downLoadImg.setBackgroundResource(R.drawable.downloadon);
					new Thread(new Runnable() {
							@Override
							public void run() {
								TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
								HashMap<String,String> map = new HashMap<String,String>();
								map.put("taskid",data.get(position).get("TaskID") );
								tc.addElement(tc.recordInfo, "item", data.get(position).get("BaseID") + "," + data.get(position).get("BaseSchema"), map);
								
								HTTPConnection hc = new HTTPConnection(context.getApplicationContext());
								Map<String, String> parmas = new HashMap<String, String>();
								parmas.put("serviceCode", "G007");
								parmas.put("inputXml", tc.doc.asXML());
								
								hc.setInputType("File");
								hc.setFilePath(Environment.getExternalStorageDirectory().getPath()+AppConstants.FILE_CACHE);
								hc.setFileName("text.zip");
								String str = hc.Sent(parmas);
								
								DBHelper helper = new DBHelper(context);
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
			return convertView;
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
			while (WaitDialog.flag && dbList == null && ybList == null) {
				// 获取待办工单
				dbList = getData(1);
				//cacheList();
				// 获取已办工单
				ybList = getData(0);
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
				mAdapter1 = new Adapter(context);
//				mAdapter1.setData(0, null, 1);
//				mAdapter1.setData(currentItem, dbList, 1);
				mAdapter1.setData(wfCategory, dbList, 1,0);
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
//							String b = LoginServiceImp.xml.getType().get(i - 1).getCategoryType();
//							if (b.equals("YH") || b.equals("dwspecialty2")) {
//								continue;
//							}
							text_name = ((Type)templateType.get(i - 1)).getText();
							addBottomButton(automaic_lyt, ((Type)templateType.get(i - 1)).getType(), id, text_name);// 动态加载传数据方法
						}
					}
					bottom_flag = false;
				}
				if (dbList.size() == 0 && ybList.size() == 0) {
					view1.setBackgroundResource(R.drawable.nofindbg);
					view2.setBackgroundResource(R.drawable.nofindbg);
					Toast.makeText(context, "待办已办数据为空!!", 0).show();					
				}
				else if(dbList.size() != 0 && ybList.size() == 0){
					view2.setBackgroundResource(R.drawable.nofindbg);
				}
				else if(dbList.size() == 0 && ybList.size() != 0){
					view1.setBackgroundResource(R.drawable.nofindbg);
				}
				// 更新界面
				mAdapter2 = new Adapter(context);
//				mAdapter2.setData(currentItem, ybList, 0);
				mAdapter2.setData(wfCategory, dbList, 0,1);//1:daiban
				mAdapter2.notifyDataSetChanged();
				mDisplay2.setAdapter(mAdapter2);
				setNum(lp.getBaseCount());
//				TextView redCircle=textViewList.get(currentItem-1);
//				all_num = Integer.parseInt(mapNum.get("WF4:EL_AM_AT")) + Integer.parseInt(mapNum.get("WF4:EL_AM_TTH"))
//						+ Integer.parseInt(mapNum.get("WF4:EL_AM_PS"));
//				lp.getBaseCount()
//				redCircle.setText(lp.getBaseCount());
			} else if (msg.what == 0) {
				view1.setBackgroundResource(R.drawable.nofindbg);
				Toast.makeText(context, "网络不给力，数据获取失败!!", 0).show();
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
		for (Iterator<Map.Entry<String, String>> it = set
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String type = entry.getKey();
				textViewMap.get(type).setVisibility(View.VISIBLE);
				textViewMap.get(type).setText(entry.getValue());
				i += Integer.parseInt(entry.getValue());
//				if(entry.getValue().equals("0"))
//					textViewMap.get(type).setVisibility(View.GONE);
		}
		textViewMap.get("ALL").setVisibility(View.VISIBLE);
		textViewMap.get("ALL").setText(String.valueOf(i));
		if(i==0)
			textViewMap.get("ALL").setVisibility(View.GONE);
		
	}
/*	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return true;
		}
		return true;
	}*/


}
