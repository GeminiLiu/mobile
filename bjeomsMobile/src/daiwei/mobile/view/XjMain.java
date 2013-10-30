package daiwei.mobile.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import eoms.mobile.R;
import daiwei.mobile.activity.XjLineActivity;
import daiwei.mobile.activity.XjTmpActivity;
import daiwei.mobile.adapter.MyPagerAdapter;
import daiwei.mobile.animal.XJListModel;
import daiwei.mobile.service.XJListService;
import daiwei.mobile.service.XJListServiceImp;
import daiwei.mobile.util.WaitDialog;

/**
 * 巡检页面
 * 包括基站巡检,集客家客,直放站,WLAN(接口为PT001)
 * 线路巡检(单独做的处理，接口为PTL001)
 * @都 3/25
 */
public class XjMain {
	private Context context;
	public View xj;
	private ListView mDisplay1;
	private ListView mDisplay2;
	private Adapter mAdapter1,mAdapter2;
	private Button btnWaitXj;
	private Button btnFinishXj;
	private ImageView imgIndicator;
	private TextView xjTopTitleName;
	private MyViewPager viewPager;
	private int offset;// 动画图片偏移量
	private int bitmapWidth;// 动画图片宽度
	private List<View> viewList;// 页卡集合
	private int currIndex = 0;// 当前页卡编号
	private View view1, view2;
	private MyViewGroup mg;
	private String specialtyId;//桌面传入巡检类型
	private String titleName;//头部标题名
	private ToDoThread toDoThread=null;
	private XJListService XJList;
	private List<Map<String, String>> list;
	private List<Map<String, String>> listTo,listDone=null;
    private XJListModel xlp;
    private int pageNum=1;
    private int pageNumNext=1;   
    private boolean todoFlag=true;
    private boolean doneFlag=true;
    private WaitDialog wd=null;
    private ProgressDialog dialog = null;
	public static XJReciver re;
	public static ArrayList<XJReciver> xjlist=new ArrayList<XJReciver>();

	public XjMain(Context context, MyViewGroup mg,String specialtyId,String titleName) {
		this.context = context;
		this.mg = mg;
		this.specialtyId=specialtyId;
		this.titleName=titleName;
		//接受广播 更新列表
		IntentFilter inf = new IntentFilter();
		inf.addAction("refresh");
		re = new XJReciver();
		this.context.registerReceiver(re, inf);
		findViewById();		
		xj.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(MyViewGroup.showright){
					return true;
				}else{
					return false;
				}
			}
		});		
		initAnimation();
		initViewPager();
		setListeners();

	}
	public void update(XJReciver reciver) {	
//		listTo=null;
//		listDone=null;
		pageNum=1;
		pageNumNext=1;
		initViewPager();
		setListeners();
		
	}	
	/**
	 * 广播接受者 更新列表
	 * @author qicaihua
	 * @time 2013-4-1 下午3:12:49
	 */
	class XJReciver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			xjlist.add(re);
			update(re);
		}
	}
	//查找控件
    private void findViewById(){
    	xj = LayoutInflater.from(context).inflate(R.layout.xj, null);
    	btnWaitXj = (Button) xj.findViewById(R.id.btnWaitXj);
		btnFinishXj = (Button) xj.findViewById(R.id.btnFinishXj);
		imgIndicator = (ImageView) xj.findViewById(R.id.imgIndicator);
		xjTopTitleName = (TextView) xj.findViewById(R.id.iv_xjtitle);
    }
	// 初始化动画
	private void initAnimation() {
		// 获取指示图片的宽度
		bitmapWidth = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.gd_viewpager_indicator).getWidth();
		// 获取分辨率
		Display display = ((WindowManager) (context
				.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
		int screenW = display.getWidth();
		// 计算偏移量
		offset = (screenW / 2 - bitmapWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// 设置动画初始位置
		imgIndicator.setImageMatrix(matrix);
	}

	// 初始化ViewPager
	private void initViewPager() {		
		viewList = new ArrayList<View>();
		view1 = View.inflate(context, R.layout.listview, null);
		view2 = View.inflate(context, R.layout.listview, null);
		viewList.add(view1);
		viewList.add(view2);

		
		mDisplay1 = (ListView) view1.findViewById(R.id.friends_list);
		mDisplay2 = (ListView) view2.findViewById(R.id.friends_list);
		
		toDoThread=new ToDoThread();
		wd = new WaitDialog(context, "数据加载中...");
		dialog = wd.getDialog();
		toDoThread.start();
		
		viewPager=new MyViewPager(context);
		viewPager = (MyViewPager) xj.findViewById(R.id.viewPager);
		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(context, viewList);
		viewPager.setAdapter(myPagerAdapter);
		viewPager.setCurrentItem(0);
	}

	private void setListeners() {
		xjTopTitleName.setText(titleName);
		btnWaitXj.setOnClickListener(new MyOnClickListener(0));
		btnFinishXj.setOnClickListener(new MyOnClickListener(1));

		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mDisplay1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(titleName.equals("线路巡检")){
				/**临时写死  等服务器数据*/
				Intent xjLineIntent=new Intent(context.getApplicationContext(),XjLineActivity.class);
				xjLineIntent.putExtra("taskId",listTo.get(position).get("taskId"));
				xjLineIntent.putExtra("taskName",listTo.get(position).get("planName"));
				xjLineIntent.putExtra("bottomFlag", true);
				xjLineIntent.putExtra("isW", "1");
				xjLineIntent.putExtra("titleName", titleName);
				xjLineIntent.putExtra("specialtyId", specialtyId);
				context.startActivity(xjLineIntent);
				}
				else{
				Intent intent = new Intent(context.getApplicationContext(),
						XjTmpActivity.class);
				intent.putExtra("taskId",listTo.get(position).get("taskId"));
				intent.putExtra("taskName",listTo.get(position).get("taskName"));
				intent.putExtra("distance",listTo.get(position).get("distance"));
				intent.putExtra("bottomFlag", true);
				intent.putExtra("isW", "1");
				intent.putExtra("titleName", titleName);
				intent.putExtra("specialtyId", specialtyId);
				context.startActivity(intent);
				}
			}
		});
		mDisplay2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
               if(titleName.equals("线路巡检")){
            	Intent xjLineIntent=new Intent(context.getApplicationContext(),XjLineActivity.class);
            	xjLineIntent.putExtra("taskId",listDone.get(position).get("taskId"));
				xjLineIntent.putExtra("taskName",listDone.get(position).get("planName"));
				xjLineIntent.putExtra("bottomFlag", false);
				xjLineIntent.putExtra("isW", "0");
				xjLineIntent.putExtra("titleName", titleName);
				xjLineIntent.putExtra("specialtyId", specialtyId);
   				context.startActivity(xjLineIntent);	
				}
				else{
				Intent intent = new Intent(context.getApplicationContext(),
						XjTmpActivity.class);
				intent.putExtra("taskId",listDone.get(position).get("taskId"));
				intent.putExtra("taskName",listDone.get(position).get("taskName"));
				intent.putExtra("bottomFlag", false);
				intent.putExtra("isW", "0");
				intent.putExtra("titleName", titleName);
				intent.putExtra("specialtyId", specialtyId);
				context.startActivity(intent);
			}
			}
		});
		// 给listview设置一个滚动的监听事件
				mDisplay1.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {			
							// 判断滚动到底部
							if (view.getLastVisiblePosition() == (view.getCount() - 1)&&view.getCount()>(9+10*(pageNum-1))) {
								if (todoFlag) {// list返回不为[]时
									pageNum = pageNum + 1;
									loadMore(1,pageNum);// 加载更多
								}
							}

						}
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {

					}
				});
				// 给listview设置一个滚动的监听事件
				mDisplay2.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
							// 判断滚动到底部
							if (view.getLastVisiblePosition() == (view.getCount() - 1)&&view.getCount()>(9+10*(pageNumNext-1))) {
								if (doneFlag) {// list返回不为[]时
									pageNumNext = pageNumNext + 1;
									loadMore(0,pageNumNext);// 加载更多
								}
							}

						}
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {

					}
				});

		}

	/*
	 * 页头标监听器
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
				break;
			case R.id.btnFinishGd:// 已办工单
				btnFinishGdIsSelected();
				break;
			default:
				break;
			}
			viewPager.setCurrentItem(index);
		}

	}
	
	/**
	 * 加载更多数据
	 */
	private void loadMore(final int isW,final int page) {
		// if(!b){//如果list返回为null 不加载
		// return;
		// }
		final RelativeLayout loadMore;
		if(isW==1){
			loadMore = (RelativeLayout) view1.findViewById(R.id.loading);
		}
		else{
			loadMore = (RelativeLayout) view2.findViewById(R.id.loading);
		}
		new AsyncTask<Void, Void, List<Map<String, String>>>() {

			@Override
			protected void onPreExecute() {// 获取数据之前
				AnimationSet set = new AnimationSet(true);

				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(500);
				set.addAnimation(animation);

				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, -1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation.setDuration(500);
				set.addAnimation(animation);

				LayoutAnimationController controller = new LayoutAnimationController(
						set, 0.5f);
				loadMore.setVisibility(View.VISIBLE);
				loadMore.setLayoutAnimation(controller);
				super.onPreExecute();
			}

			@Override
			protected List<Map<String, String>> doInBackground(Void... params) {// 获取数据
				List<Map<String, String>> list11 = getData(isW,page);
				if (list11.size() == 0) {					
					if(isW==1){
						todoFlag = false;
					}else{
					    doneFlag=false;
					}
					return null;

				}
				return list11;
			}

			@Override
			protected void onPostExecute(List<Map<String, String>> result) {// 获取数据后
				super.onPostExecute(result);
				loadMore.setVisibility(View.GONE);
				if (result != null && result.size() > 0) {
					// 这里简单的就将新添加来的数据遍历加入到list1中，在设置adapter数据就ok了
					int sec=0;
					List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
					if(isW==1){
					sec = listTo.size() - 1;
					for (Map<String, String> map : result) {
						ls.add(map);
					}
					listTo.addAll(ls);
					mAdapter1.setData(listTo);
					mDisplay1.setSelection(sec);
					}
					else{
						sec = listDone.size() - 1;
						for (Map<String, String> map : result) {
							ls.add(map);
						}
						listDone.addAll(ls);
						mAdapter2.setData(listDone);
						mDisplay2.setSelection(sec);
					}
				} else {
					Toast.makeText(context, "别看了，没有数据了！", Toast.LENGTH_SHORT)
							.show();
				}

			}

		}.execute();
	}

	/**
	 * 获取数据
	 * @param 根据type类型获取数据
	 * @return 返回list数组
	 */
	private List<Map<String, String>> getData(int isW,int number) {
		list= new ArrayList<Map<String, String>>();
		XJList = new XJListServiceImp();
		if(titleName.equals("线路巡检")){			
		xlp=XJList.getXJWaitList(context,isW, number, 10,specialtyId,"PTL001");	
		}
		else{
		xlp=XJList.getXJWaitList(context,isW, number, 10,specialtyId,"PT001");	
		}
		list = xlp.getXJListInfo();	
		return list;
	}
	// 待办工单是否被选中
	public void btnWaitGdIsSelected() {
		if (!btnWaitXj.isSelected())
			btnWaitXj.setSelected(true);
		if (btnFinishXj.isSelected())
			btnFinishXj.setSelected(false);
	}

	// 已办工单是否被选中
	public void btnFinishGdIsSelected() {
		if (!btnFinishXj.isSelected())
			btnFinishXj.setSelected(true);
		// intent.putExtra("btn", btn2);
		if (btnWaitXj.isSelected())
			btnWaitXj.setSelected(false);
	}

	/*
	 * 页卡切换监听
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
				btnWaitGdIsSelected();
				break;
			case 1:
				if (currIndex == 0) {// 页卡2->页卡1
					anim = new TranslateAnimation(offset, one, 0, 0);
					btnFinishGdIsSelected();
				}
				break;
			}
			currIndex = arg0;
			anim.setFillAfter(true);// True:图片停在动画结束位置
			anim.setDuration(300);
			imgIndicator.startAnimation(anim);

		}

	}

	private class Adapter extends BaseAdapter {
		private Context mContext;
		private List<Map<String, String>> data;
		public Adapter(Context context) {
			mContext = context;
		}
		// 设置数据
		private void setData(List<Map<String, String>> data) {
			this.data = data;
			this.notifyDataSetChanged();
		}
		public int getCount() {
			return this.data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			System.out.println("titleName========"+titleName);
			if (convertView == null) {				
				if(titleName.equals("线路巡检")){
					convertView = LayoutInflater.from(mContext).inflate(R.layout.xj_line_item, null);
					holder = new ViewHolder();
					holder.xjLineId = (TextView) convertView.findViewById(R.id.line_id_name);
					holder.xjLineDate = (TextView) convertView.findViewById(R.id.line_data_name);
					holder.xjLineCompletion = (TextView) convertView.findViewById(R.id.line_completion_name);
				}
				else{
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.xj_main_item, null);
				holder = new ViewHolder();				
				holder.xj_list_text=(TextView) convertView.findViewById(R.id.xj_list_text);		
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if(titleName.equals("线路巡检")){
				holder.xjLineId.setText(data.get(position).get("planNo"));
				holder.xjLineDate.setText(data.get(position).get("planDate"));
				holder.xjLineCompletion.setText("80%");//服务器没数据  临时写死
			}
			else{
			holder.xj_list_text.setText(data.get(position).get("taskName"));
			}
			return convertView;
		}

		class ViewHolder {
			TextView xj_list_text;
			TextView xjLineId;
			TextView xjLineDate;
			TextView xjLineCompletion;
			
			
		}

	}
	/**
	 * 待办 已办工单的异步下载
	 * @author admin
	 *
	 */
private	class ToDoThread extends Thread{
		@Override
		public void run() {
			listTo=null;
			listDone=null;
			while(WaitDialog.flag&&listTo==null&&listDone==null){				
			listTo=getData(1,pageNum);
			listDone=getData(0,pageNumNext);	
			if(listTo.size() > 0 || listDone.size() > 0){
				System.out.println("!!!!!!!!!!!!!!!!");
    			handler.sendEmptyMessage(1);
    		}else{
    			handler.sendEmptyMessage(0);
    		}
		}	
		}

	}
   Handler handler = new Handler(){
	public void handleMessage(android.os.Message msg) {
		if(msg.what == 1){
			mAdapter1 = new Adapter(context);
			mAdapter1.setData(listTo);
			mAdapter1.notifyDataSetChanged();
			mDisplay1.setAdapter(mAdapter1);
			 //更新界面  	 
			mAdapter2 = new Adapter(context);
        	mAdapter2.setData(listDone);
        	mAdapter2.notifyDataSetChanged();
    		mDisplay2.setAdapter(mAdapter2);
    		if(listTo.size()==0&&listDone.size()!=0){
    			view1.setBackgroundResource(R.drawable.nofindbg);
    			}
    			if(listTo.size()!=0&&listDone.size()==0){
    			view2.setBackgroundResource(R.drawable.nofindbg);
    			}
		}else if(msg.what == 0){
				view1.setBackgroundResource(R.drawable.nofindbg);
				view2.setBackgroundResource(R.drawable.nofindbg);
				Toast.makeText(context, "巡检待办和已办数据为空!!", 0).show();			
		}
		dialog.dismiss();
	};
};	    	    
}
