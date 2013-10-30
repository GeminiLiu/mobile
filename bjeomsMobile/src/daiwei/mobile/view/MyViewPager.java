package daiwei.mobile.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 重写ViewPager 不允许滑动
 * @author qch
 *
 */
public class MyViewPager extends ViewPager {
	public MyViewPager(Context context) {
		super(context);
	}
	public MyViewPager(Context context,AttributeSet att) {
		super(context,att);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
//		return super.onInterceptTouchEvent(arg0);
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
//		return super.onTouchEvent(arg0);
		return false;
	}
}
