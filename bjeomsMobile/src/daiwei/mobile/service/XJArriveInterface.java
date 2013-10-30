package daiwei.mobile.service;

import android.content.Context;
import daiwei.mobile.common.XMLUtil;
/**
 * 巡检到达上传经纬度接口
 * @author 都  4/18
 *
 */
public interface XJArriveInterface {
	public XMLUtil getArrive(Context context,String taskId,String longitude,String latitude);

}
