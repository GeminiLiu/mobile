package daiwei.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import daiwei.mobile.animal.OrgItem;
import daiwei.mobile.service.GetTreeService;
import daiwei.mobile.service.GetTreeServiceImp;
import daiwei.mobile.util.TestAnalyticXMLOrgTree;
import eoms.mobile.R;

public class OrgTreeDialogActivity extends Activity implements OnItemClickListener{
	private ListView mylistview;
	private MyAdapter adapter;
	private TextView tv_dir;
	private StringBuilder sb;
	private Button btn;
	private int parentInt = 0;
	private String currentId = "0";
	private ArrayList<String> parentIDList = new ArrayList<String>();
	
//	private List<OrgItem> list = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mydialog);
		sb = new StringBuilder();
		sb.append("路径：");
		btn = (Button) findViewById(R.id.btn_cancel);
		/**
		 * 取消按钮
		 */
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tv_dir = (TextView) findViewById(R.id.tv_dir);// 路径
		tv_dir.setText(sb);
		mylistview = (ListView) findViewById(R.id.mylistview);
		mylistview.setOnItemClickListener(this);
		adapter = new MyAdapter();
		mylistview.setAdapter(adapter);
		List<OrgItem> orgList = getData("0");//第一次获取数据
		adapter.setData(orgList);
		parentIDList.add("0");
		
	}
	private void addText(String ss) {
		sb.append(ss);
		tv_dir.setText(sb);
	}
	private List<OrgItem> getData(String str) {
		GetTreeService gs = new GetTreeServiceImp();//T002
		List<OrgItem> orgList = TestAnalyticXMLOrgTree.domParser(gs.getOrgTrr(this.getApplicationContext(), "1", "1", "1", "1", str)) ;
		if(!orgList.isEmpty())
		{
			currentId = str;
		}
		return orgList;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		OrgItem item = (OrgItem) parent.getAdapter().getItem(position);
		if(setData(item,true)){
		parentInt++;
		
		parentIDList.add(new String(currentId));
		}
	}
	
	public boolean setData(OrgItem item,boolean flag){
		boolean b = false;
		List<OrgItem> its=getData(item.getId());
		if (its != null && its.size() > 0) {
			if(true)
				addText("/" + item.getText());
			else
				System.out.println("-----------");
			adapter.setData(its);// 重新设置适配器数据
			b = true;
		}
		return b;
	} 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && !currentId.equals("0")) {
			
			//parentInt--;
			try {
				String str = parentIDList.get(parentInt-1);
				OrgItem item = new OrgItem();
				item.setId(str);
				item.setText("");
				if(setData(item,false)){
					parentIDList.remove(parentInt);
					parentInt--;
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
//			if (currentParentItem != null) {
//				Item it = TestAnalyticXML.getParent(currentParentItem, -1);
//				if (it != null) {
//					adapter.setData(it.getChild());
//					sb.delete(sb.lastIndexOf("/"), sb.length());
//					tv_dir.setText(sb);
//					currentParentItem = it;
//					return true;
//				}
//			}
		}
		return super.onKeyDown(keyCode, event);
	}
	class MyAdapter extends BaseAdapter {
		List<OrgItem> list;
		DialogActivity act;
		int level;

		MyAdapter() {
		}

		public void setActivity(DialogActivity activity) {
			this.act = activity;
		}

		public void setData(List<OrgItem> ls) {
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
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(OrgTreeDialogActivity.this, R.layout.item, null);
				holder = new ViewHolder();
				holder.tv = (TextView) view.findViewById(R.id.tv);
				holder.iv = (ImageView) view.findViewById(R.id.iv);
				holder.btn_ok = (Button) view.findViewById(R.id.btn_ok);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			if (list.get(position).isNocheckbox()) {// 是否可选 //true不可以选
				holder.btn_ok.setVisibility(View.GONE);// 隐藏选择按钮
				holder.iv.setVisibility(View.VISIBLE);// 显示箭头
			} else {// 可以选择的情况
				holder.btn_ok.setVisibility(View.VISIBLE);
				// holder.iv.setVisibility(View.GONE);
				System.out.println("list是不是空"+list);
				if(list!=null&&list.get(position)!=null&&!list.get(position).equals("")){
					System.out.println(list.get(position).getText() + ":::::"
							+ list.get(position).getType());
					if(list.get(position).getType()!=null){
						if (list.get(position).getType().equals("team")) {
							holder.iv.setImageResource(R.drawable.tree_team);
						} else if (list.get(position).getType().equals("person")) {
							holder.iv.setImageResource(R.drawable.tree_person);
						}	
					}
				
				}
				
			}
			holder.tv.setText(list.get(position).getText());
			holder.btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("name", list.get(position));
					intent.putExtras(mBundle);
					setResult(6, intent);
					OrgTreeDialogActivity.this.finish();
				}
			});
			return view;
		}

		final class ViewHolder {
			TextView tv;
			ImageView iv;
			Button btn_ok;
		}
	}

	
}
