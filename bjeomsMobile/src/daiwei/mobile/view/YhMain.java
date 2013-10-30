package daiwei.mobile.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import eoms.mobile.R;
import daiwei.mobile.activity.YhHandleActivity;
import daiwei.mobile.activity.YhTmpActivity;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.service.GDListService;
import daiwei.mobile.service.GDListServiceImp;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
/**
 * 隐患列表页
 * @author qicaihua
 *
 */
public class YhMain {
	private Context context;
	public View yh;
	private MyViewGroup mg;
	private ListView yhListview;
	private MyAdapter adapter;
	private List<Map<String, String>> list;// 隐患历史列表
	private Map<String, String> map;//map集合
	private ListModel listModel;
	private int index = 1;//接口参数，代表取第一页
	int category = 5;// 代表类别是隐患int viewItem = 5;
	public int isWait = 1;// 判断已办还是待办 1：待办 0：已办
	int isW = 0;// 已办 在接口中传入
	//dialog
	private WaitDialog wd;
	private ProgressDialog dialog;
	//日期格式转换
	private SimpleDateFormat sdf;
	//异步线程
	private YhDataListThread yhThread;
	
	private TextView textViewNewYh;// 底部文字：新建隐患
	private View footView;// 底部按钮整个相对布局：  新建隐患 
	
	private boolean flag = true;
	private Intent intent;
	private YhMainReciver reciver;//广播刷新列表
	private RelativeLayout loading;
	
	public YhMain(final Context context, MyViewGroup mg) {
		this.context = context;
		this.mg = mg;
		findViewById();
		flushList();//刷新列表
		update();//开启线程获取数据
	
		/**
		 * 防止往下触摸事件
		 */
		yh.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MyViewGroup.showright) {
					return true;// 不往下触摸
				} else {
					return false;
				}
			}
		});
		/**
		 * 新建隐患链接到新建隐患页
		 */
		footView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				 Intent intent=new Intent(context,YhHandleActivity.class);
					//intent.putExtra("BaseID", map.get("BaseID"));//工单id
					context.startActivity(intent);			
			}
		});
		/**
		 * listview链接到隐患详情页
		 */
		yhListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				map = (Map<String, String>) parent.getAdapter().getItem(
						position);
				intent = new Intent(context.getApplicationContext(),
						YhTmpActivity.class);
				intent.putExtra("BaseSchema", map.get("BaseSchema"));//BaseSchema=WF4:EL_AM_ER
				intent.putExtra("BaseSummary", map.get("BaseSummary"));//BaseSummary=ceshi001
				intent.putExtra("BaseSN", map.get("BaseSN"));//BaseSN=ID-652-20130326-00001
				intent.putExtra("BaseID", map.get("BaseID"));//工单id
				intent.putExtra("TaskID", map.get("TaskID"));//任务id
				intent.putExtra("IsWait", isWait);//待办
				context.startActivity(intent);
			}
		});
		/**
		 * 分批加载数据 
		 * 给listview设置一个滚动的监听事件  
		 */
		yhListview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==OnScrollListener.SCROLL_STATE_IDLE){
					// 判断滚动到底部
					if(view.getLastVisiblePosition()==view.getCount()-1){
						index=index+1;
						loadMore();// 加载更多
					}
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}
    /**加载更多数据*/
	private void loadMore(){
		new AsyncTask<Void, Void, List<Map<String, String>>>(){
			@Override
			protected void onPreExecute() {// 获取数据之前
				System.out.println("隐患分批加载获取数据前");
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
				System.out.println("隐患分批加载获取数据中");
				List<Map<String, String>> list = getData(category,isW);//重新获取隐患列表
				if (list.size() == 0) {
					return null;
				}
				return list;
			}
			@Override
			protected void onPostExecute(List<Map<String, String>> result) {// 获取数据后
				System.out.println("隐患分批加载获取数据后");
				loading.setVisibility(View.GONE);
				if(result!=null&&result.size()>0){
					int sec = 0;
					sec = (list == null) ? 0 : list.size() - 1;
						list.addAll(result);
						adapter.setData(list);
						adapter.notifyDataSetChanged();
						yhListview.setSelection(sec);
				}else{
					Toast.makeText(context, "无数据！", Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
    /**查找控件*/
	private void findViewById() {
		yh = LayoutInflater.from(context).inflate(R.layout.yh, null);
		yhListview = (ListView) yh.findViewById(R.id.friends_list);
		loading=(RelativeLayout) yh.findViewById(R.id.loading);
		textViewNewYh = (TextView) yh.findViewById(R.id.bottom_tv);//设置底部文字 新建隐患
		textViewNewYh.setText("新建隐患");
		footView = yh.findViewById(R.id.rl_yh_newyh);
		adapter = new MyAdapter();// 数据适配器
	}
	/**
	 * 接受广播刷新列表
	 */
	private void flushList() {
		// 接受广播 更新列表
					IntentFilter intentFilter = new IntentFilter();
					intentFilter.addAction("android.intent.action.YhMain");
					reciver = new YhMainReciver();
					this.context.registerReceiver(reciver, intentFilter);// 动态注册广播
	}
	/**
	 * 广播接受者 更新列表
	 * @author qicaihua
	 * @time 2013-5-4 下午3:12:549
	 */
	class YhMainReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			index = 1;//重置页码
			update();
		}
	}
	/**
	 * 刷新隐患列表 重新获取数据
	 */ 
	private void update() {
		yhThread = new YhDataListThread();// 开启线程获取隐患历史记录
		wd = new WaitDialog(context, "数据加载中...");
		dialog = wd.getDialog();// 创建dialog
		yhThread.start();
	}
	/**
	 * 异步获取隐患上报的历史列表
	 * 调用工单已办接口
	 * 
	 * @author qicaihua
	 * 
	 */
	class YhDataListThread extends Thread {
		@Override
		public void run() {
			if(list != null) list.clear();
			list = getData(category, isW);// 获取数据已办工单
			System.out.println("list 隐患============="+list);
			if(list.size()==0||list==null){//没测试
				handler.sendEmptyMessage(0);
			}else{
				handler.sendEmptyMessage(1);
			}
		}

	}
	/**
	 * 数据返回更新界面
	 */
	Handler handler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			System.out.println("handleMessage");
			if(msg.what==1){
				adapter = new MyAdapter();
				adapter.setData(list);
				adapter.notifyDataSetChanged();
				yhListview.setAdapter(adapter);
			}else if(msg.what==0){
				Toast.makeText(context, "没有隐患记录!", 0).show();
			}
			dialog.dismiss();
		};
	};
	
	/**
	 * 获取数据的方法
	 * 
	 * @param 根据type类型获取数据
	 * @return 返回list数组
	 */
	private List<Map<String, String>> getData(final int category, final int isW) {
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		this.category = category;
		this.isWait = isW;
		GDListService gdList = new GDListServiceImp();
		if (TempletModel.isWEB || flag) {
			//****注：这里要传getApplicationContext()
			listModel = gdList.getWaitList(context.getApplicationContext(), isW, index, 10, category+"", "");
			ls = listModel.getListInfo();

		} else {
			// list = getWaitList(viewItem1);
		}
		return ls;
	}

	/**
	 * 数据适配器
	 * 
	 * @author qicaihua
	 * 
	 */
	class MyAdapter extends BaseAdapter {
		List<Map<String, String>> list;
		
		public void setData(List<Map<String, String>> list) {
			this.list = list;
//			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.jssearch_list_item, null);
				holder = new ViewHolder();
				holder.tv_netName = (TextView) view
						.findViewById(R.id.tv_netName);
				holder.tv_content1 = (TextView) view
						.findViewById(R.id.tv_content1);// 标题
				holder.tv_position = (TextView) view
						.findViewById(R.id.tv_position);
				holder.tv_content2 = (TextView) view
						.findViewById(R.id.tv_content2);// 时间
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			// 隐藏布局中没用的字段
			holder.tv_netName.setVisibility(View.GONE);
			holder.tv_position.setVisibility(View.GONE);
			// 设置标题和时间
			holder.tv_content1.setText(list.get(position).get("BaseSummary"));
			holder.tv_content2.setText(StringToLongToString(list.get(position)
					.get("BaseCreateDate")));

			return view;
		}

	}

	public class ViewHolder {
		TextView tv_netName;
		TextView tv_content1;
		TextView tv_position;
		TextView tv_content2;
	}

	/**
	 * 日期时间转换
	 * 
	 * @param searcherInput
	 * @return string
	 */
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
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			searcherInput = sdf.format(lgTime);
		}

		return searcherInput;

	}
}
