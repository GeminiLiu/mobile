package daiwei.mobile.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import eoms.mobile.R;

public class Rim extends Activity implements OnItemLongClickListener{
	private FileAdatper adapter;
//	private TextView titlePath;
	private ListView listview;
	private int longClickIndex;
	private String filePath;
	private RelativeLayout frame;//空的帧布局
	private RelativeLayout rl_del;//底部删除的布局
	private Button bt_delall;//批量编辑
	private boolean flag=true;
	private Button btn_del;//删除
	private Button btn_delall;//全选
	private Button btn_dismiss;//取消
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_folder);
		frame = (RelativeLayout) findViewById(R.id.frame_null);
		bt_delall = (Button) findViewById(R.id.bt_deleteAll);
		rl_del = (RelativeLayout) findViewById(R.id.rl_del);
		btn_del = (Button) findViewById(R.id.btn_del);
		btn_delall = (Button) findViewById(R.id.btn_delall);
		btn_dismiss = (Button) findViewById(R.id.btn_dismiss);
		/**
		 *删除的处理
		 */
		btn_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final boolean [] checkFlags=adapter.getCheckFlags();//获取数组中的标记
				new AlertDialog.Builder(Rim.this).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).setTitle("确定删除文件吗？")
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						for(int i=0;i<checkFlags.length;i++){
							if(checkFlags[i]){//如果为true 就删除文件
								File file=new File(adapter.getFilePath()+"/"+adapter.fileList.get(i));
								 try {
										file.delete();//删除文件成功就更新列表
										Toast.makeText(getApplicationContext(), "删除成功", 0).show();
									} catch (Exception e) {
										// TODO: handle exception
										Log.e("log","没删了");
									}
							}
							
						}//for
						ArrayList<String> delNames = getSubFiles(adapter.getFilePath());
						adapter.setData(delNames, adapter.getFilePath());
						adapter.notifyDataSetChanged();//通知更新
						
						/**
						 * 删完文件判断 
						 * 如果没有文件了 就隐藏底部和批量编辑按钮
						 */
						if(delNames.size()==0){
							rl_del.setVisibility(View.GONE);//取消底部布局
							bt_delall.setVisibility(View.GONE);//取消编辑按钮
							adapter.setIsShow(false);//取消checkbox
							frame.setVisibility(View.VISIBLE);//空空如也
						}else{
							rl_del.setVisibility(View.VISIBLE);
							bt_delall.setVisibility(View.VISIBLE);
						}
					}
				}).show();
				
			
				
			}
		});
		/**
		 * 全选
		 */
		btn_delall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				adapter.checkAll(flag);//调用全选的方法
				if(flag){
					btn_delall.setText("全不选");
				}else{
					btn_delall.setText("全选");
				}
				
				flag = !flag;
			}
		});
		/**
		 * 取消的处理
		 */
		btn_dismiss.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_del.setVisibility(View.GONE);
				adapter.setIsShow(false);//取消checkbox
				
			}
		});
		String baseId = getIntent().getStringExtra("baseId");
		listview = (ListView) findViewById(R.id.lv_folder);
//		titlePath = (TextView) findViewById(R.id.tv_path);
		filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daiwei/" + baseId;
		final ArrayList<String> fileName = getSubFiles(filePath);//列出工单下所有文件
		
		if(fileName.size()==0){
			frame.setVisibility(View.VISIBLE);//空空如也
			bt_delall.setVisibility(View.GONE);//没有编辑按钮
		}else{
			bt_delall.setVisibility(View.VISIBLE);
		}
		bt_delall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rl_del.setVisibility(View.VISIBLE);//显示底部删除的布局
				adapter.setIsShow(true);//显示checkbox
			}
		});
		
		adapter = new FileAdatper(getApplicationContext());
		adapter.setData(fileName, filePath);
		listview.setAdapter(adapter);
		OnItemClickListener listener = new FileOnItemClickListener();
		listview.setOnItemClickListener(listener);//listview点击
		listview.setOnItemLongClickListener(this);//listview长按点击
		LongContextClickListener longContextListener = new LongContextClickListener();//listview上下文菜单
		listview.setOnCreateContextMenuListener(longContextListener);

	}
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}



	/**
	 * 文件数据适配器
	 * @author qch
	 *
	 */
	class FileAdatper extends BaseAdapter {
		private Context context;
		public ArrayList<String> fileList;
		private String filePath;
		
		private Boolean isShow=false;//checkbox是否显示
		private boolean checkFlags[];//存放checkbox选择状态的数组
		
		public FileAdatper(Context context) {
			this.context = context;
		}
		public void setData(ArrayList<String> fileList, String filePath){
			this.fileList = fileList;
			this.filePath = filePath;
			
			if(fileList.size()!=0){//如果fileList有内容就初始化同样大小的数组
				checkFlags = new boolean[fileList.size()];

			}
			this.notifyDataSetChanged();
			
		}
		
		public String getFilePath(){//获取文件所在目录
			return filePath;
		}
		public void setIsShow(boolean isShow){
			   this.isShow = isShow;
			   this.notifyDataSetChanged();
		}
		public void checkAll(boolean checked){//全选方法
	    	if(checkFlags != null && checkFlags.length > 0){
	    		for(int i = 0; i < checkFlags.length; i++){
	    			checkFlags[i] = checked;//给数组全部赋值为true
	    		}
	    		this.notifyDataSetChanged();
	    	}
	    }

		public boolean [] getCheckFlags(){//提供公有的方法获取数组中的标记
			return  checkFlags;
		}
		
		@Override
		public int getCount() {
			if (fileList != null) {
				return fileList.size();
			}
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return fileList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			Bitmap src = null;
			final ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(), R.layout.listview_folderitem, null);
				holder = new ViewHolder();
				holder.tv_filename = (TextView) view.findViewById(R.id.tv_filename);
				holder.tv_fileNum=(TextView) view.findViewById(R.id.tv_fileNum);//文件个数
				holder.img_file = (ImageView) view.findViewById(R.id.img_file);
				holder.cb_delete=(CheckBox) view.findViewById(R.id.cb_delete);//删除的checkBox
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.tv_filename.setText(fileList.get(position));
			holder.img_file.setImageResource(R.drawable.ic_launcher);
			
			 if(isShow){//如果显示checkbox
				 holder.cb_delete.setVisibility(View.VISIBLE);
			 }else{
				 holder.cb_delete.setVisibility(View.GONE);
			 }
			 File file=new File(filePath+"/"+fileList.get(position));//拼接文件路径
			 if(file.isDirectory()){//是文件夹 设置图片展示个数
				 holder.img_file.setImageResource(R.drawable.folder_icon);
				 holder.tv_fileNum.setVisibility(View.VISIBLE);//显示个数
				 ArrayList<String> list = getSubFiles(filePath+"/"+fileList.get(position));
				 holder.tv_fileNum.setText("[文件："+list.size()+"个]");
			 }else{//是文件
				 holder.tv_fileNum.setVisibility(View.GONE);//隐藏个数
					if (fileList.get(position).endsWith(".JPG")) {
						String path = filePath + "/"+fileList.get(position);
						BitmapFactory.Options opt = new BitmapFactory.Options();
						opt.inPreferredConfig = Bitmap.Config.RGB_565;
						opt.inPurgeable = true;
						opt.inInputShareable = true;
						// 获取资源图片
						try {
						InputStream is = new BufferedInputStream(new FileInputStream(path));
						src = BitmapFactory.decodeStream(is, null, opt);
						try {
						is.close();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						} catch (FileNotFoundException e) {
						e.printStackTrace();
						}
						//Bitmap bitmap = BitmapFactory.decodeFile(path);
						holder.img_file.setImageBitmap(src);
					} else if (fileList.get(position).endsWith(".amr")) {
						holder.img_file.setImageResource(R.drawable.music);
					}
			 }
			 /**
			  * checkBox的选中监听事件
			  */
			 holder.cb_delete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					checkFlags[position]=isChecked;//记录选中状态
				}
			});
			 
			 if(checkFlags != null && checkFlags.length > 0){
				  holder.cb_delete.setChecked(checkFlags[position]);//给checkBox设置选中的状态
				}

			return view;
		}
	}
	class ViewHolder {
		TextView tv_filename;
		TextView tv_fileNum;
		ImageView img_file;
		CheckBox cb_delete;
		
	}

	/**
	 * 长按上下文菜单
	 * @author qch
	 */
	class LongContextClickListener implements OnCreateContextMenuListener {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
			menu.setHeaderTitle("文件操作");
			menu.add(0, 0, 0, "删除");
		}
	}
	
	class FileOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		 String name = (String)adapter.getItem(position);//文件名
		 String path =adapter.getFilePath();//文件目录
		 String filePath = path + "/" + name;
		 File file=new File(filePath);
		 if(file.exists()){
			 if(file.isDirectory()&&file.canRead()){// 如果是文件夹 设置数据
				ArrayList<String> fileList= getSubFiles(filePath);
				if(fileList.size()==0){
					frame.setVisibility(View.VISIBLE);//空空如也 编辑按钮取消
					 bt_delall.setVisibility(View.GONE);
				}
				adapter.setData(fileList, filePath);
//				titlePath.setText(filePath);
			 }else if(file.isFile()){//是文件 就打开
				 Intent intent = new Intent("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Uri uri = Uri.fromFile(file);
					if (name.endsWith(".JPG")) {
						intent.setDataAndType(uri, "image/*");
					} else if (name.endsWith(".amr")) {
						intent.setDataAndType(uri, "audio/*");
					}
					startActivity(intent);
			 }
		 }

		}
	}
	
	/**
	 * 重写监听选中上下文菜单的方法
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(adapter.fileList!=null && adapter.fileList.size()>0){
			File file = new File(filePath+"/"+adapter.fileList.get(longClickIndex));
			try {
				file.delete();//删除文件
			} catch (Exception e) {
				Log.e("Rim", "onContextItemSelected " + e.toString());
			}
		}
		adapter.fileList.remove(longClickIndex);//移除文件名
		adapter.notifyDataSetChanged();//通知更新
		if(adapter.fileList.size()==0){
			rl_del.setVisibility(View.GONE);//取消底部布局
			bt_delall.setVisibility(View.GONE);//取消编辑按钮
			adapter.setIsShow(false);//取消checkbox
			frame.setVisibility(View.VISIBLE);//空空如也
		}else{
			rl_del.setVisibility(View.VISIBLE);
			bt_delall.setVisibility(View.VISIBLE);
		}
		return false;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		longClickIndex = position;// 记录长按项位置
		return false;
	}
	
	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * @param baseDir
	 * baseDir 指定的目录
	 * @return 包含java.io.File的List
	 */
	private ArrayList<String> getSubFiles(String baseDir) {
		ArrayList<String> ret = new ArrayList<String>();
		File file = new File(baseDir);
		File[] tmp = file.listFiles();
		if(tmp==null || tmp.length<1){
			return ret;
		}
		for (int i = 0; i < tmp.length; i++) {
				ret.add(tmp[i].getName());
		}
		return ret;
	}
	/**
	 * 回退事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode==KeyEvent.KEYCODE_BACK){
		}
		return super.onKeyDown(keyCode, event);
	}
}
