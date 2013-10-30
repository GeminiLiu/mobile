package daiwei.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import eoms.mobile.R;
import daiwei.mobile.util.TempletUtil.TMPModel.HistoryResult;

public class YhHisResultActivity extends Activity {
private MyAdapter adapter;
private ImageView iv_friends;
private ArrayList<HistoryResult> list;
private HistoryResult historyResult;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.historysearch_detail_bg);
	
	ListView listview=(ListView) findViewById(R.id.friends_list);//listview
	iv_friends = (ImageView) findViewById(R.id.iv_friends);//返回按钮
	list=getData("铁岭基站蓄电池存在隐患");
	adapter = new MyAdapter();
	listview.setAdapter(adapter);
	adapter.setData(list);
	
	
	listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			Intent intent=new Intent(getApplicationContext(),XjMessageActivity.class);
//			startActivity(intent);
//			
		}
	});
	iv_friends.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	});
}

/**
 * 获取数据 需要异步获取数据
 * @param str
 * @return
 */
private ArrayList<HistoryResult> getData(String str) {
	list = new ArrayList<HistoryResult>();
	for (int i = 0; i < 5; i++) {
		historyResult = new HistoryResult();
		historyResult.setTitle(str+i);
		historyResult.setTime("2013-03-10 12:12:3"+i);
		list.add(historyResult);
	}
	return list;
}

class  MyAdapter extends BaseAdapter{
	List<HistoryResult> list;
	YhHisResultActivity act;
	
	public void setActivity(YhHisResultActivity activity) {
		this.act = activity;
	}

	public void setData(List<HistoryResult> ls) {
		this.list = ls;
		this.notifyDataSetChanged();
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
		if(convertView==null){
			view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.jssearch_list_item, null);
			holder = new ViewHolder();
			holder.tv_netName=(TextView)view.findViewById(R.id.tv_netName);
			holder.tv_content1=(TextView)view.findViewById(R.id.tv_content1);//标题
			holder.tv_position=(TextView)view.findViewById(R.id.tv_position);
			holder.tv_content2=(TextView)view.findViewById(R.id.tv_content2);//时间
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		//隐藏布局中没用的字段
		holder.tv_netName.setVisibility(View.GONE);
		holder.tv_position.setVisibility(View.GONE);
		//设置标题和时间
		holder.tv_content1.setText(list.get(position).getTitle());
		holder.tv_content2.setText(list.get(position).getTime());
		
//		holder.tv_netName.setText("主题：");
//		holder.tv_position.setText("日期：");
		
//		String arr[]=new String[]{"铁岭基站蓄电池存在隐患","大连基站天线前方有障碍物阻挡存在隐患","沈阳基站地阻值小于5欧姆存在隐患","铁岭基站各种支撑件不牢固","大连基站蓄电池存在隐患","大连基站天线前方有障碍物阻挡存在隐患","沈阳基站地阻值小于5欧姆存在隐患","沈阳基站各种支撑件不牢固","大连基站各种支撑件不牢固","锦州基站各种支撑件不牢固"};
//		holder.tv_content1.setText(arr[position]);
//		holder.tv_content2.setText("2013-01-04 12:13:56");
/*		switch (position) {
		case 0:
			holder.tv_content2.setText("2013-01-01 12:13:56");
			break;
		case 1:
			holder.tv_content2.setText("2013-01-01 13:45:53");
			break;
		case 2:
			holder.tv_content2.setText("2013-01-02 09:30:01");
			break;
		case 3:
			holder.tv_content2.setText("2013-01-02 10:42:12");
			break;
		case 4:
			holder.tv_content2.setText("2013-01-03 11:22:34");
			break;
		case 5:
			holder.tv_content2.setText("2013-01-03 14:42:11");
			break;
		case 6:
			holder.tv_content2.setText("2013-01-04 08:35:27");
			break;
		case 7:
			holder.tv_content2.setText("2013-01-05 13:45:50");
			break;
		case 8:
			holder.tv_content2.setText("2013-01-06 13:54:44");
			break;
		case 9:
			holder.tv_content2.setText("2013-01-06 13:45:53");
			break;
	
		
		}*/
		return view;
	}
	
}
	public class ViewHolder{
		TextView tv_netName;
		TextView tv_content1;
		TextView tv_position;
		TextView tv_content2;
		
	
	}
}
