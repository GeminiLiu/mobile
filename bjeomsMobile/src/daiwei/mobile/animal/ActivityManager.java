package daiwei.mobile.animal;

import android.app.Activity;

public  class ActivityManager{
	public static Activity ac;

	public static Activity getAc() {
		return ac;
	}

	public static void setAc(Activity ac) {
		ActivityManager.ac = ac;
	}
	
}