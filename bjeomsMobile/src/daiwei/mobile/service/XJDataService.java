package daiwei.mobile.service;

import android.content.Context;
import daiwei.mobile.common.XMLUtil;
/**
 * 巡检每个item页面内容
 * @author 都 3/26
 *
 */
public interface XJDataService {
	public XMLUtil getXJData(Context context, String taskId,String serviceCode);
}
