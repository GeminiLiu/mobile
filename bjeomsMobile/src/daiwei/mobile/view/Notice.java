package daiwei.mobile.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eoms.mobile.R;
import daiwei.mobile.activity.NoticeTmpActivity;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.service.GDListService;
import daiwei.mobile.service.GDListServiceImp;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 系统公告页
 * @author qicaihua
 *
 */
public class Notice {
	private Context context;
	public View notice;
	private MyViewGroup mg;
	private ListView noticeListview;
	private MyAdapter adapter;
	private List<Map<String, String>> list;//公告列表
	private Map<String, String> map;//map集合
	private ListModel listModel;
	private int index = 1;//接口参数，代表取第一页
	int category = 6;// 代表类别是隐患int viewItem = 5;
	public int isWait = 1;// 判断已办还是待办 1：待办 0：已办
	int isW = 0;// 已办 在接口中传入
	//dialog
	private WaitDialog wd;
	private ProgressDialog dialog;
	//日期格式转换
	private SimpleDateFormat sdf;
	//异步线程
//	private Handler handler;
	private MyThread myThread;

	private boolean flag = true;
	private Intent intent;
	private RelativeLayout loading;
	
	public Notice(final Context context,MyViewGroup mg){
		this.context=context;
		this.mg=mg;
		findViewById();
		update();//获取数据
		/**
		 * 防止往下触摸事件
		 */
		notice.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(MyViewGroup.showright){
					return true;
				}else{
					return false;
				}
			}
		});
		noticeListview.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				map = (Map<String, String>) parent.getAdapter().getItem(
						position);
				intent = new Intent(context.getApplicationContext(),
						NoticeTmpActivity.class);
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
		noticeListview.setOnScrollListener(new OnScrollListener() {
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
					List<Map<String, String>> list = getData(category,isW);//重新获取公告列表
					if (list.size() == 0) {
						return null;
					}
					return list;
				}
				@Override
				protected void onPostExecute(List<Map<String, String>> result) {// 获取数据后
					loading.setVisibility(View.GONE);
					if(result!=null&&result.size()>0){
						int sec = 0;
						sec = (list == null) ? 0 : list.size() - 1;
							list.addAll(result);
							adapter.setData(list);
							adapter.notifyDataSetChanged();
							noticeListview.setSelection(sec);
					}else{
						Toast.makeText(context, "无数据！", Toast.LENGTH_SHORT).show();
					}
					super.onPostExecute(result);
				}
			}.execute();
		}
	private void findViewById() {
		notice=LayoutInflater.from(context).inflate(R.layout.notice, null);
		noticeListview = (ListView) notice.findViewById(R.id.friends_list);
		loading=(RelativeLayout) notice.findViewById(R.id.loading);
		adapter=new MyAdapter();// 数据适配器
		
	}
	
	/**
	 * 公告列表 重新获取数据
	 */ 
	private void update() {
		myThread = new MyThread();// 开启线程获取公告列表
		wd = new WaitDialog(context, "数据加载中...");
		dialog = wd.getDialog();// 创建dialog
		myThread.start();
	}
	/**
	 * 异步获取公告数据列表
	 * @author qicaihua
	 */
	class MyThread extends Thread {

		@Override
		public void run() {
			list = getData(category, isW);// 获取数据
			System.out.println("list 公告============="+list);
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
			if(msg.what==1){
				adapter = new MyAdapter();
				adapter.setData(list);
				adapter.notifyDataSetChanged();
				noticeListview.setAdapter(adapter);
			}else if(msg.what==0){
				Toast.makeText(context, "没有公告记录!", 0).show();
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
			List<Map<String, String>> ls =  new ArrayList<Map<String, String>>();
			this.category = category;
			this.isW = isW;
			GDListService gdList = new GDListServiceImp();
			if (TempletModel.isWEB || flag) {
				//****注：这里要传getApplicationContext()
				listModel = gdList.getWaitList(context.getApplicationContext(), isW, index, 5, category+"", "");
				ls = listModel.getListInfo();

			} else {
				// list = getWaitList(viewItem1);
			}
			return ls;
		}

	class MyAdapter extends BaseAdapter{
		List<Map<String, String>> list;
		
		public void setData(List<Map<String, String>> list){
			this.list=list;
			this.notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			return list==null?0:list.size();
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
			ViewHolder holder=null;
			if(convertView==null){
				view = LayoutInflater.from(context).inflate(
						R.layout.notice_item, null);
				holder=new ViewHolder();
				holder.content=(TextView)view.findViewById(R.id.content);
				holder.one=(TextView)view.findViewById(R.id.tv_one);
				holder.two=(TextView)view.findViewById(R.id.tv_two);
				holder.three=(TextView)view.findViewById(R.id.tv_three);
				view.setTag(holder);
			}else{
				view=convertView;
				holder=(ViewHolder) view.getTag();
			}
			holder.content.setText(list.get(position).get("BaseSummary"));
//			holder.two.setText("dddd");//发布人
			holder.two.setText(list.get(position).get("SiteName"));//发布人  未知字段
			holder.three.setText(StringToLongToString(list.get(position)
					.get("BaseCreateDate")));//日期
			
			return view;
		}
		
	}
	public class ViewHolder {
		TextView content;
		TextView one;
		TextView two;
		TextView three;
		
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
