package daiwei.mobile.Tools;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 判断手机SIM卡
 * @author 都
 * @time 2013/5/16
 * 
 */
public class SIM {
	private Context context;

	public SIM(Context context) {
		this.context = context;
	}

	/**
	 * 判断手机卡是否可用
	 * @return true:可用  false：不可用
	 */
	public boolean isCanUseSim() {
		try {
			TelephonyManager mgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
