package daiwei.mobile.adapter;

import java.util.List;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

//ViewPager
public class MyPagerAdapter extends PagerAdapter {
	private Context mcontext;
	private List<View> viewList;
	
	public MyPagerAdapter(Context context, List<View> viewList) {
		this.mcontext = context;
		this.viewList = viewList;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(viewList.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {

	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(viewList.get(arg1), 0);
		return viewList.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
}
