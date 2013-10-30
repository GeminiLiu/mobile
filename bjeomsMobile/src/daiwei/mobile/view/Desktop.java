package daiwei.mobile.view;


import java.util.ArrayList;
import java.util.List;

import eoms.mobile.R;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.service.LoginServiceImp;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Desktop {
	private Context myContext;
	public  View mDesktop;
	private ListView mDisplay;
	private TextView userName;
	private Adapter mAdapter;
	private MyViewGroup mg;
	private Boolean b=false;
	private  String gdNum;//工单个数
	private String xjNum;//巡检个数
	private int intFrom;
	LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	/**桌面列表图标*/
	private ArrayList<Integer>  listIcon=new ArrayList<Integer>();
	/**桌面列表文字*/
	private ArrayList<String>  listName=new ArrayList<String>();
	private int logic_jump=2;
	public Desktop(final Context context, final MyViewGroup mg,String gdNum,String xjNum,int intFrom) {
		this.mg = mg;
		this.myContext=context;
		this.gdNum=gdNum;
		this.xjNum=xjNum;
		this.intFrom=intFrom;
		mDesktop = LayoutInflater.from(context).inflate(R.layout.desktop, null);
		mDesktop.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("=========desktop=====touch=========");
				return false;
			}
		});
		addList();
		mDisplay = (ListView) mDesktop.findViewById(R.id.desktop_list);
		userName = (TextView) mDesktop.findViewById(R.id.user_name);
		userName.setText(LoginServiceImp.userName);
		mAdapter = new Adapter(context);
		mDisplay.setAdapter(mAdapter);		
		mDisplay.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				view.setBackgroundResource(R.drawable.listview_item_bg_select);
//				Toast.makeText(myContext, position, Toast.LENGTH_SHORT).show();
				//点击条目弹出右边的菜单
//				System.out.println("===========我被点击了===========");
//				if(beforeView!=null)
//				beforeView.setTextColor(Color.WHITE);
//			text.setTextColor(Color.BLUE);
//			
			TextView itemTv = (TextView) view.findViewById(R.id.desktop_list_text);
			String itemName =(String) itemTv.getText();
			if(itemName.equals("EOMS工单")){
				view=parent.getChildAt(position);
				Friends friends = new Friends(context,mg,logic_jump);
				mg.removeViewAt(1);
				mg.addView(friends.mFriends,params);
				mg.showRight();
			}	
			if(itemName.equals("系统设置")){
				Setting set = new Setting(context,mg);
				mg.removeViewAt(1);
				mg.addView(set.set,params);
				mg.showRight();
			}
			if(itemName.equals("值班")){
				Toast.makeText(context, "值班功能待定", 800).show();
			}
			if(itemName.equals("KPI")){
				Toast.makeText(context, "KPI功能待定", 800).show();
			}
			//=====================以下 是辽宁代维系统中的功能点  辽宁EOMS用不到======================
			if(itemName.equals("基站巡检")){
				XjMain xj = new XjMain(context,mg,"basestation",itemName);
				mg.removeViewAt(1);
				mg.addView(xj.xj,params);
				mg.showRight();
			}
			if(itemName.equals("线路巡检")){
//				XjLineMain xj1 = new XjLineMain(context,mg);
//				mg.removeViewAt(1);
//				mg.addView(xj1.xj,params);
//				mg.showRight();
				XjMain xj = new XjMain(context,mg,"dwline",itemName);
				mg.removeViewAt(1);
				mg.addView(xj.xj,params);
				mg.showRight();
			}
			if(itemName.equals("WLAN巡检")){
				XjMain xj = new XjMain(context,mg,"zfzwlan.wlan",itemName);
				mg.removeViewAt(1);
				mg.addView(xj.xj,params);
				mg.showRight();
				//Toast.makeText(context, "WLAN巡检", 0).show();
			}
			if(itemName.equals("直放站/室分巡检")){
				XjMain xj = new XjMain(context,mg,"zfzwlan.zfz",itemName);
				mg.removeViewAt(1);
				mg.addView(xj.xj,params);
				mg.showRight();
				//Toast.makeText(context, "直放站/室分巡检", 0).show();
			}
			if(itemName.equals("集客家客巡检")){
				XjMain xj = new XjMain(context,mg,"grouphome",itemName);
				mg.removeViewAt(1);
				mg.addView(xj.xj,params);
				mg.showRight();
				//Toast.makeText(context, "集客家客巡检", 0).show();
			}
			if(itemName.equals("抽查巡检")){
				Toast.makeText(context, "抽查巡检", 0).show();
//				XjMain xj = new XjMain(context,mg);
//				mg.removeViewAt(1);
//				mg.addView(xj.xj,params);
//				mg.showRight();
			}
			if(itemName.equals("隐患上报")){
				YhMain yh = new YhMain(context,mg);
				mg.removeViewAt(1);
				mg.addView(yh.yh,params);
				mg.showRight();
			}
			if(itemName.equals("资源检索")){
				MessSearchMain ms = new MessSearchMain(context,mg);
				mg.removeViewAt(1);
				mg.addView(ms.ms,params);
				mg.showRight();
			}
			if(itemName.equals("系统公告")){
				Notice notice = new Notice(context,mg);
				mg.removeViewAt(1);
				mg.addView(notice.notice,params);
				mg.showRight();
			}
			//===============以上为 辽宁代维系统的内容=========================
			}
		});
//		mg.showLeft();
	}//oncreate
	
	private void addList() {
		String dwName = null;
		if(NetWork.checkWork(myContext)==1){
			dwName=LoginServiceImp.dwSpecialtyId;
		}
		else{
			SharedPreferences sharedPrefOffLogin = myContext.getSharedPreferences("OFFLOGIN", Context.MODE_PRIVATE);
			dwName=sharedPrefOffLogin.getString("dwSpecialtyId", "");
		}       
		listIcon.add(R.drawable.icon_gd);
		listName.add("EOMS工单");
		//================以下为代维巡检部分，eoms系统不需要=====================
		if(dwName!=""){
			String dwNameList[]=dwName.split(",");
			for(int i=0;i<dwNameList.length;i++){			
			if(dwNameList[i].equals("basestation")){
			listIcon.add(R.drawable.icon_xja);
			listName.add("基站巡检");
			}
			if(dwNameList[i].equals("dwline")){
				listIcon.add(R.drawable.icon_xja1);	
				listName.add("线路巡检");
				}
			if(dwNameList[i].equals("zfzwlan.wlan")){
				listIcon.add(R.drawable.icon_wlan);
				listName.add("WLAN巡检");
				}
			if(dwNameList[i].equals("zfzwlan.zfz")){
				listIcon.add(R.drawable.icon_xja);
				listName.add("直放站/室分巡检");
				}
			if(dwNameList[i].equals("grouphome")){
				listIcon.add(R.drawable.icon_jikejiake);
				listName.add("集客家客巡检");
				}
//			if(dwName.equals("抽查巡检")){
//				listIcon.add(R.drawable.icon_select);
//				}	
			}
		}
		//================eoms系统手机端只留工单部分 其他去掉  modify by xiaxj========================
		/*
		listIcon.add(R.drawable.icon_select);
		listName.add("抽查巡检");
		listIcon.add(R.drawable.icon_yh);
		listName.add("隐患上报");
		listIcon.add(R.drawable.icon_search);
		listName.add("资源检索");
		listIcon.add(R.drawable.icon_gg);
		listName.add("系统公告");
		*/
		listIcon.add(R.drawable.icon_select);
		listName.add("值班");
		listIcon.add(R.drawable.icon_gg);
		listName.add("KPI");
		//================end modify========================
		listIcon.add(R.drawable.icon_terminal);
		listName.add("系统设置");
		
		
		
	}

	public void showSetting(){
		Setting set = new Setting(this.myContext, this.mg);
		mg.removeViewAt(1);
		mg.addView(set.set,params);
		mg.showRight();
	}

	private class Adapter extends BaseAdapter {
		private Context mContext;
		
		public Adapter(Context context) {
			mContext = context;
		}
		
		public int getCount() {
			return listName.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.desktop_list_item, null);
				
				holder.img = (ImageView) convertView
						.findViewById(R.id.desktop_list_img);
				holder.text = (TextView) convertView
						.findViewById(R.id.desktop_list_text);
				holder.num = (TextView) convertView
						.findViewById(R.id.desktop_list_point);
				
				convertView.setTag(holder);
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}			
			holder.img.setImageResource(listIcon.get(position));
			holder.text.setText(listName.get(position));
			
			/**
			 * 工单巡检个数
			 */
		/*	if(position==0){
				holder.num.setVisibility(View.VISIBLE);
				holder.num.setText(gdNum);
			}else if(position==1){
				holder.num.setVisibility(View.VISIBLE);
				holder.num.setText(xjNum);
			}*/
			
			
			return convertView;
		}

		class ViewHolder {
			ImageView img;
			TextView text;
			TextView num;
		}
		public void setListBg(){
			 Boolean tag;
			 
		}
	}
	
	public void showFriends(){
		
	}
	
	
	
}
