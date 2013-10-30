package daiwei.mobile.view;

import eoms.mobile.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

public class MyViewGroup extends ViewGroup implements OnClickListener {
	
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mWidth;
	
	private static final int SCREEN_STATE_CLOSE = 0;
	private static final int SCREEN_STATE_OPEN = 1;
	private static final int TOUCH_STATE_RESTART = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private static final int SCROLL_STATE_NO_ALLOW = 0;
	private static final int SCROLL_STATE_ALLOW = 1;
	private int mScreenState;
	private int mTouchState;
	private int mScrollState;
	private int mVelocityValue;
	private boolean desktop = true; 
	public static boolean showright;
	private Context contextx;
	private boolean flag=false;//初始进来不可以滑动
	private Activity act;
	private int moveFirstX=0;
	
	public MyViewGroup(Context context) {
		super(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setLayoutParams(params);		
		mScroller = new Scroller(context);
		mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				54, getResources().getDisplayMetrics());
		this.contextx = context;

	}

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyViewGroup(Context context,Activity act){
		super(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setLayoutParams(params);
		
		mScroller = new Scroller(context);
		mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				54, getResources().getDisplayMetrics());
		this.contextx = context;
		this.act=act;
	}
	
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);//desktop friends
			int height = child.getMeasuredHeight();
			int width = child.getMeasuredWidth();
			child.layout(0, 0, width, height);
			
			if(i==1){//如果当前是friends
				if(desktop){
					getChildAt(1).scrollBy(getWidth(), 0);
					getChildAt(1).scrollBy(-(getWidth()- Math.abs(getChildAt(1).getScrollX()) - mWidth), 0);
					desktop = false;
				}
				ImageView iv = (ImageView) getChildAt(1).findViewById(R.id.iv_friends);//列表图标
				iv.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						if(getChildAt(1).getScrollX() == 0)//距离左边边界的距离
							showLeft();//显示桌面
						else
							showRight();//显示工单
						return false;
					} 
				});
			}
		}
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		obtainVelocityTracker(ev);

		switch (ev.getAction()) {	

		case MotionEvent.ACTION_DOWN:	//按下去判断是否	
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_RESTART
					: TOUCH_STATE_SCROLLING;
			moveFirstX= (int) ev.getX();
			int screenWidth = getWidth();
			if (mScreenState == SCREEN_STATE_CLOSE
					&& mTouchState == TOUCH_STATE_RESTART
					|| moveFirstX >= screenWidth - mWidth
					&& mScreenState == SCREEN_STATE_OPEN
					&& mTouchState == TOUCH_STATE_RESTART) {
				mScrollState = SCROLL_STATE_ALLOW;
			} else {
				mScrollState = SCROLL_STATE_NO_ALLOW;
			}
			break;

		case MotionEvent.ACTION_MOVE:
//			EditText edit_search = (EditText) getChildAt(1).findViewById(R.id.editSearch);
//			InputMethodManager imm=(InputMethodManager)contextx.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
//			imm.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);
			//隐藏系统软键盘 把TestActivity传进来
			((InputMethodManager)contextx.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
			hideSoftInputFromWindow(act.getCurrentFocus().
					getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
			mVelocityTracker.computeCurrentVelocity(1000,
					ViewConfiguration.getMaximumFlingVelocity());
			if (mScrollState == SCROLL_STATE_ALLOW
					&& Math.abs(mVelocityTracker.getXVelocity()) > 180 &&Math.abs(mVelocityTracker.getYVelocity()) < 180) {
				return true;
			}
			break;

		case MotionEvent.ACTION_UP:
			releaseVelocityTracker();
			if (mScrollState == SCROLL_STATE_ALLOW
					&& mScreenState == SCREEN_STATE_OPEN) {
				return true;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent event) {
		obtainVelocityTracker(event);
		if(flag==true){//如果为真 可以滑动
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;
		case MotionEvent.ACTION_MOVE:
			mVelocityTracker.computeCurrentVelocity(1000,
					ViewConfiguration.getMaximumFlingVelocity());
			mVelocityValue = (int) mVelocityTracker.getXVelocity();	
			if(moveFirstX<event.getX()){
			getChildAt(1).scrollTo(-(int) event.getX(), 0);
			showLeft();
			}
			break;

		case MotionEvent.ACTION_UP:
			if (mScrollState == SCROLL_STATE_ALLOW) {
				if (mVelocityValue > 2000) {
					mScreenState = SCREEN_STATE_OPEN;
					showright = false;
					mScroller
							.startScroll(
									getChildAt(1).getScrollX(),
									0,
									-(getWidth()
											- Math.abs(getChildAt(1)
													.getScrollX()) -

									mWidth), 0, 250);
					invalidate();

				} else if (mVelocityValue < -2000) {
					mScreenState = SCREEN_STATE_CLOSE;
					showright = true;
					mScroller.startScroll(getChildAt(1).getScrollX(), 0,
							-getChildAt(1).getScrollX(), 0, 250);
					invalidate();
				} else if (event.getX() < getWidth() / 2) {
					mScreenState = SCREEN_STATE_CLOSE;
					showright = true;
					mScroller.startScroll(getChildAt(1).getScrollX(), 0,
							-getChildAt(1).getScrollX(), 0, 800);
					invalidate();
				} else {
					mScreenState = SCREEN_STATE_OPEN;
					showright = false;
					mScroller
							.startScroll(
									getChildAt(1).getScrollX(),
									0,
									-(getWidth()
											- Math.abs(getChildAt(1)
													.getScrollX()) -

									mWidth), 0, 800);
					invalidate();
				}
			}
			break;
		}
		}//if
		return super.onTouchEvent(event);
		
	}
/**
 * computeScroll 方法 会在滑动中调用 包括手指的滑动和模拟的滑动
 * 在方法中 获取滑动的位置 改变 getChildAt(1) 的位置
 */
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			getChildAt(1).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	private void obtainVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private void releaseVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	@Override
	public void onClick(View v) {
		
		
	}
	//显示左边view
	public void showLeft(){//展示左边的列表
		showright = false;		
		mScreenState = SCREEN_STATE_OPEN;
		mScroller.startScroll(getChildAt(1).getScrollX(), 0, -(getWidth()- Math.abs(getChildAt(1).getScrollX()) - mWidth), 0, 800);//工单向右滑动
		invalidate();
		TextView user=(TextView)act.findViewById(R.id.user);
		user.setPadding(0, 0, 100, 35);
		TextView userName=(TextView) act.findViewById(R.id.user_name);
		userName.setPadding(0, 35, 100, 0);
	}
	//弹出右边的view
	public void showRight(){//工单向左滑动
		flag=true;
		showright = true;
		mScreenState = SCREEN_STATE_CLOSE;
		mScroller.startScroll(getChildAt(1).getScrollX(), 0,
				-getChildAt(1).getScrollX(), 0, 800);
		invalidate();
	}
}
