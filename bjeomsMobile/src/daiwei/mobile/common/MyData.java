package daiwei.mobile.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
/**
 * Activity栈  添加activity，移除activity，关闭activity
 * @author 都 3/25
 *
 */
public class MyData {
	public static HashMap<String,Activity> activityHashMap=new HashMap<String,Activity>();
	public static List<Activity> activityList = new ArrayList<Activity>();

	public static void remove(Activity activity) {
		activityList.remove(activity);
	}

	public static void add(Activity activity) {
		activityList.add(activity);

	}

	public static void finishProgram() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
