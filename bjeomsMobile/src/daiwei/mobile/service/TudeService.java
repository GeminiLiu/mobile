package daiwei.mobile.service;

import android.content.Context;
import daiwei.mobile.common.XMLUtil;
/**
 * 经纬度上传接口
 * @author 都  5/14
 * 
 */
public interface TudeService {
	public XMLUtil getTudeData(Context context, String trackType,String userName,String trackObjectName,String longitude,String latitude);
}
