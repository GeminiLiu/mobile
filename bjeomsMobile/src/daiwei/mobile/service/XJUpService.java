package daiwei.mobile.service;

import java.util.Map;

import daiwei.mobile.common.XMLUtil;
import android.content.Context;

/**
 * 巡检完成上传接口
 * @author du 3/31
 *
 */
public interface XJUpService {
	/**
	 * 
	 * @param context
	 * @param taskId  任务主键
	 * @param taskResult  巡检结果 如预警 告警 正常等
	 * @param taskNote 寻见备注
	 * @param taskContentId  巡检内容ID 主键
	 * @param dataValue 填写值
     * @param status 处理状态
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return
	 */
	public XMLUtil getXJUpLoad(Context context,String taskId,String status,String longitude,String latitude,String taskResult,String taskNote,Map<String,String> ContentMap,String serviceCode);
}
