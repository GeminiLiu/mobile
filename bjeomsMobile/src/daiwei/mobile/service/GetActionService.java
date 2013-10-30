package daiwei.mobile.service;

import android.content.Context;

import daiwei.mobile.common.XMLUtil;
/**
 * 工单加载动作界面接口
 * @author Administrator
 *
 */
public interface GetActionService {
	public XMLUtil getData(Context  context,String baseId, String category,String taskId,String actionId,String actionCode);
}
