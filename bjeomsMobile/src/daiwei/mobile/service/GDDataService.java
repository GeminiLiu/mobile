package daiwei.mobile.service;


import java.util.List;

import android.content.Context;
import daiwei.mobile.common.XMLUtil;
/**
 * 工单每个item内容页
 * @author 都 3/26
 *
 */
public interface GDDataService {
	public XMLUtil getData(Context context,String baseId, String taskId,String category);
	public List<String> getProcessInfo(Context context,String baseId,String category);
}
