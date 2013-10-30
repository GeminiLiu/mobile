package daiwei.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


import eoms.mobile.R;
import daiwei.mobile.adapter.BookFisherPagerAdapter;
import daiwei.mobile.common.MyData;
/**
 * 引导页
 * @author 都  3/25
 *
 */
public class GuideActivity extends Activity {

	private static final int TO_THE_END = 0;// 到达最后一张
	private static final int LEAVE_FROM_END = 1;// 离开最后一张
	private int[] ids = { R.drawable.guide11, R.drawable.guide22,
			R.drawable.guide33 };
	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;
	private ImageView open;
	private ImageView curDot;
	private int offset;// 位移量
	private int curPos = 0;// 记录当前的位置

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide);
		MyData.add(GuideActivity.this);// 启动时存入HashMap中

		for (int i = 0; i < ids.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(ids[i]);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			guides.add(iv);
		}

		curDot = (ImageView) findViewById(R.id.cur_dot);
		open = (ImageView) findViewById(R.id.open);
		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {

					public boolean onPreDraw() {
						// TODO Auto-generated method stub
						offset = curDot.getWidth();
						return true;
					}
				});

		BookFisherPagerAdapter adapter = new BookFisherPagerAdapter(guides);
		pager = (ViewPager) findViewById(R.id.contentPager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				moveCursorTo(arg0);
				if (arg0 == ids.length - 1) {// 到最后一张了
					handler.sendEmptyMessageDelayed(TO_THE_END, 500);
					open.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// ActivityManager.getAc().finish();
							Intent intent = new Intent(getApplicationContext(),
									LoginActivity.class);
							startActivity(intent);

						}
					});
				} else if (curPos == ids.length - 1) {
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}
				curPos = arg0;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 移动指针到相邻的位置
	 * 
	 * @param position
	 *            指针的索引值
	 * */
	private void moveCursorTo(int position) {
		// 使用绝对位置
		TranslateAnimation anim = new TranslateAnimation(offset * curPos,
				offset * position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		curDot.startAnimation(anim);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == TO_THE_END)
				open.setVisibility(View.VISIBLE);
			else if (msg.what == LEAVE_FROM_END)
				open.setVisibility(View.GONE);
		}

	};

	/*
	 * @Override protected void onNewIntent(Intent intent) { // TODO
	 * Auto-generated method stub super.onNewIntent(intent); //退出 if
	 * ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) { finish(); }
	 * }
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 返回时退出ActivityMap
			MyData.remove(GuideActivity.this);
		}
		return super.onKeyDown(keyCode, event);
	}

}