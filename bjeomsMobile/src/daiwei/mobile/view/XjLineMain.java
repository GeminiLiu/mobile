package daiwei.mobile.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import eoms.mobile.R;
import daiwei.mobile.activity.XjZfzLineActivity;
import daiwei.mobile.adapter.MyPagerAdapter;


public class XjLineMain {
	private Context context;
	public View xj;
	private ListView mDisplay1;
	private ListView mDisplay2;
	// private Adapter mAdapter;
	private int[] mIcon = { R.drawable.bug, R.drawable.fadian, R.drawable.tousu, R.drawable.tousu,R.drawable.tousu};

	private int MAX_WIDTH = 0;
	private final static int SPEED = 20;
	private LayoutParams lp;
	private ImageView menu;

	private Adapter mAdapter;
	private Button btnWaitXj;
	private Button btnFinishXj;
	private ImageView imgIndicator;
	private ViewPager viewPager;
	private int offset;// 动画图片偏移量
	private int bitmapWidth;// 动画图片宽度
	private List<View> viewList;// 页卡集合
	private int currIndex = 0;// 当前页卡编号
	private View view1, view2;
	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
	private ImageButton imageButton4;
	private ImageButton imageButton5;
	private MyViewGroup mg;
	private ImageView iv_xjtitle;

	public XjLineMain(Context context, MyViewGroup mg) {
		this.context = context;
		this.mg = mg;
		xj = LayoutInflater.from(context).inflate(R.layout.xj, null);
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
		btnWaitXj = (Button) xj.findViewById(R.id.btnWaitXj);
		btnFinishXj = (Button) xj.findViewById(R.id.btnFinishXj);
		imgIndicator = (ImageView) xj.findViewById(R.id.imgIndicator);
		iv_xjtitle = (ImageView) xj.findViewById(R.id.iv_xjtitle);
		iv_xjtitle.setImageResource(R.drawable.icon_linexj);
		
//		imageButton1 = (ImageButton) xj.findViewById(R.id.imageButton1);
//		imageButton2 = (ImageButton) xj.findViewById(R.id.imageButton2);
//		imageButton3 = (ImageButton) xj.findViewById(R.id.imageButton3);
//		imageButton4 = (ImageButton) xj.findViewById(R.id.imageButton4);
//		imageButton5 = (ImageButton) xj.findViewById(R.id.imageButton5);
		initAnimation();
		initViewPager();
		setListeners();

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
		mAdapter = new Adapter(context);
		mDisplay1.setAdapter(mAdapter);

		mDisplay2 = (ListView) view2.findViewById(R.id.friends_list);
		mAdapter = new Adapter(context);
		mDisplay2.setAdapter(mAdapter);
		viewPager = (ViewPager) xj.findViewById(R.id.viewPager);

		MyPagerAdapter myPagerAdapter = new MyPagerAdapter(context, viewList);
		viewPager.setAdapter(myPagerAdapter);
		viewPager.setCurrentItem(0);
	}

	private void setListeners() {
		btnWaitXj.setOnClickListener(new MyOnClickListener(0));
		btnFinishXj.setOnClickListener(new MyOnClickListener(1));

		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mDisplay1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(context.getApplicationContext(),
						XjZfzLineActivity.class);
				intent.putExtra("position",position);
				context.startActivity(intent);
			}
		});

	/*	imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton1.setBackgroundResource(R.drawable.siteon);
//				Toast.makeText(context, "1", 0).show();
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton2.setBackgroundResource(R.drawable.customeron);
//				Toast.makeText(context, "2", 0).show();
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton3.setBackgroundResource(R.drawable.jzon);
//				Toast.makeText(context, "3", 0).show();
			}
		});
		imageButton4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton4
						.setBackgroundResource(R.drawable.translineon);
//				Toast.makeText(context, "4", 0).show();
			}
		});
		imageButton5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton5
						.setBackgroundResource(R.drawable.wlanon);
//				Toast.makeText(context, "5", 0).show();
			}
		});*/
	}
	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.site);
		imageButton2.setBackgroundResource(R.drawable.customer);
		imageButton3.setBackgroundResource(R.drawable.jz);
		imageButton4.setBackgroundResource(R.drawable.transline);
		imageButton5.setBackgroundResource(R.drawable.wlan);
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

		public Adapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return 10;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.xj_main_item, null);
				holder = new ViewHolder();
				holder.xj_list_text=(TextView) convertView.findViewById(R.id.xj_list_text);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.xj_list_text.setText("线段：浑南-本溪(高速)");

			return convertView;
		}

		class ViewHolder {
			TextView xj_list_text;
		}

	}


}
