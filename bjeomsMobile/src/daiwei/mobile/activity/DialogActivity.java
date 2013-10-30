package daiwei.mobile.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import daiwei.mobile.animal.Item;
import daiwei.mobile.service.GetTreeService;
import daiwei.mobile.service.GetTreeServiceImp;
import daiwei.mobile.util.TestAnalyticXML;
import daiwei.mobile.util.WaitDialog;
import eoms.mobile.R;

public class DialogActivity extends Activity implements OnItemClickListener {
	private ListView mylistview;
	private MyAdapter adapter;
	private TextView tv_dir;
	private Map<String, ArrayList<String>> map;
	private StringBuilder sb;
	private Item currentParentItem;
	private Button btn;
	private static Item item;
	private Item curItem;
	private List<Item> list = null;
	private WaitDialog waitDiolog ;
	private ProgressDialog progressDiolog;
	private String treeID = "";
	private String selectObject = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
//				   WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		setContentView(R.layout.mydialog);
		item=(Item) getIntent().getSerializableExtra("item");//获取传过来的Item对象
		//currentParentItem = item.getParentItem();
		currentParentItem = item;
		list = item.getChild();//子节点列表
		sb = new StringBuilder();
		sb.append("路径：");
		btn = (Button) findViewById(R.id.btn_cancel);
		selectObject = item.getType();
		/**
		 * 取消按钮
		 */
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		tv_dir = (TextView) findViewById(R.id.tv_dir);//路径
		tv_dir.setText(sb);
		mylistview = (ListView) findViewById(R.id.mylistview);
		mylistview.setOnItemClickListener(this);//点击监听事件
		adapter = new MyAdapter();
		mylistview.setAdapter(adapter);
		adapter.setData(list);
		
	}

	private void addText(String ss) {
		sb.append(ss);
		tv_dir.setText(sb);
	}

	private ArrayList<String> getData(String ss) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			list.add(ss + i);
		}
		return list;
	}
	@Override
	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
		
		//获取当前节点的编号
		Item clickitem = (Item)parent.getAdapter().getItem(position);
//		curItem = (Item)list.get(position);
		curItem =clickitem;
		treeID = curItem.getId();
		String type = curItem.getType();
//		selectObject = curItem.getType();
		if(!"person".equalsIgnoreCase(type)){
			waitDiolog = new WaitDialog(this, "数据加载中...");
			progressDiolog = waitDiolog.getDialog();

			Thread getSubTree = new Thread(){
				public void run(){
					GetTreeService tree = new GetTreeServiceImp();// 调用T001接口 获取派发树
					String showCorp = "0";
					String showCenter = "0";
					String showStation = "0";
					String showTeam = "0";
					String showPerson = "0";
					String multi = "0";
					String specialtyID = "0";
					String strTree = tree.getTree(
									DialogActivity.this.getApplicationContext(),showCorp,
									showCenter, showStation, showTeam, showPerson, multi,
									selectObject, treeID, specialtyID);
					Message msg = new Message();
					msg.obj = strTree;
					handler.sendMessage(msg);
				}
			};
			getSubTree.start();
		}else{
			Toast.makeText(getApplicationContext(), "已是最后一级数据!", Toast.LENGTH_SHORT).show();
		}
	}
	public static void getAllItem(Item curItem,Item parentItem,String in){
		SAXReader reader = new SAXReader();
		try {
			Document doc=DocumentHelper.parseText(in);
			Element root = doc.getRootElement();
			TestAnalyticXML.Analytic(item,curItem,parentItem,root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String strSubTree = (String)msg.obj;
			progressDiolog.dismiss();
			//===============服务端返回的 xml 文件 进行测试=================================
			//String fileName = "bb.xml";
			//strSubTree = getTestTree(fileName);
			//==========================================
			getAllItem(curItem,currentParentItem,strSubTree);
			List subList = curItem.getChild();
//			parentItem = curItem.getParentItem();//节点添加所有的子节点
			if (subList != null && subList.size() > 0) {
				addText("/" + curItem.getText());
				adapter.setData(subList);//重新设置适配器数据
				currentParentItem = curItem;
			}
		}
	};
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	 public String getTestTree(String fileName){
//			String fileName = "aa.xml"; //文件名字
		   String strTree = "";
			try{ 

			   InputStream in = getResources().getAssets().open(fileName);

			   // \Test\assets\yan.txt这里有这样的文件存在
				int length = in.available();         
				byte [] buffer = new byte[length];        
				in.read(buffer);            
				strTree = EncodingUtils.getString(buffer, "UTF-8");     
			}catch(Exception e){ 
			      e.printStackTrace();         
			}
			return strTree;
	   }
	
	/*
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Item item = (Item) parent.getAdapter().getItem(position);
		List<Item> its = item.getChild();
		if (its != null && its.size() > 0) {
			addText("/" + item.getText());
			adapter.setData(its);//重新设置适配器数据
			currentParentItem = item;
		}
	}
	*/
/*
	private void creatData() {
		map = new HashMap<String, ArrayList<String>>();
		map.put("公司", getData("部门"));
		map.put("部门0", getData("李明"));
		map.put("部门1", getData("徐哲"));
		map.put("部门2", getData("张帅"));
	}*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (curItem != null) {
				Item it = TestAnalyticXML.getParent(curItem, -1);
				if (it != null) {
					adapter.setData(it.getChild());
					if(sb.lastIndexOf("/") >=0){
						sb.delete(sb.lastIndexOf("/"), sb.length());
					}
					tv_dir.setText(sb);
					curItem = it;
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (currentParentItem != null) {
				Item it = TestAnalyticXML.getParent(currentParentItem, -1);
				if (it != null) {
					adapter.setData(it.getChild());
					sb.delete(sb.lastIndexOf("/"), sb.length());
					tv_dir.setText(sb);
					currentParentItem = it;
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
*/
	class MyAdapter extends BaseAdapter {
		List<Item> list;
		DialogActivity act;
		int level;

		MyAdapter() {
		}

		public void setActivity(DialogActivity activity) {
			this.act = activity;
		}

		public void setData(List<Item> ls) {
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(DialogActivity.this, R.layout.item, null);
				holder = new ViewHolder();
				holder.tv = (TextView) view.findViewById(R.id.tv);
				holder.iv = (ImageView) view.findViewById(R.id.iv);
				holder.btn_ok = (Button) view.findViewById(R.id.btn_ok);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			if(list != null && list.size() >0){
				if (list.get(position).getNocheckbox()) {//是否可选    //true不可以选
					holder.btn_ok.setVisibility(View.GONE);//隐藏选择按钮
					holder.iv.setVisibility(View.VISIBLE);//显示箭头
				} else {//可以选择的情况
					holder.btn_ok.setVisibility(View.VISIBLE);
	//				holder.iv.setVisibility(View.GONE);
					System.out.println(list.get(position).getText()+":::::"+list.get(position).getType());
					if(list.get(position).getType() != null){
						if(list.get(position).getType().equals("team")){
							holder.iv.setImageResource(R.drawable.tree_team);
						}else if(list.get(position).getType().equals("person")){
							holder.iv.setImageResource(R.drawable.tree_person);
						}
					}
				}
				holder.tv.setText(list.get(position).getText());
				holder.btn_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						Bundle mBundle = new Bundle();  
	//					intent.putExtra("name", sb.append("/") + list.get(position).getText());
						mBundle.putSerializable("name",list.get(position));
						intent.putExtras(mBundle);
						setResult(2, intent);
						DialogActivity.this.finish();
					}
				});
			}
			
			return view;
		}

		final class ViewHolder {
			TextView tv;
			ImageView iv;
			Button btn_ok;
		}
	}

}