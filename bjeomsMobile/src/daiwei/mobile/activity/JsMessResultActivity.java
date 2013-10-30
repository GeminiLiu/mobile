package daiwei.mobile.activity;

import eoms.mobile.R;
import android.app.Activity;
import android.content.Intent;
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

public class JsMessResultActivity extends Activity {
private MyAdapter adapter;
private ListView listview;
private ImageView iv_friends;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.jssearch_detail_bg);
	listview = (ListView) findViewById(R.id.friends_list);
	iv_friends = (ImageView) findViewById(R.id.iv_friends);
	
	adapter = new MyAdapter();
	listview.setAdapter(adapter);
	setListener();

}

private void setListener() {
	// TODO Auto-generated method stub
	listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent=new Intent(getApplicationContext(),XjMessageActivity.class);
			startActivity(intent);
			
		}
	});
	iv_friends.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
}

class  MyAdapter extends BaseAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if(convertView==null){
			convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.jssearch_list_item, null);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
}
	public class ViewHolder{
	
	}
}
