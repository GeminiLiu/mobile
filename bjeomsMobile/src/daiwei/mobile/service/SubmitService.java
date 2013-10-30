package daiwei.mobile.service;

import java.io.File;
import java.util.Map;

import android.content.Context;

import daiwei.mobile.common.XMLUtil;

public interface SubmitService {
	public XMLUtil submitData(Context context,String baseId, String category,String taskId,String actionCode,String actionText,String hasPic,String hasRec,Map<String, String> parmas);
	
	public XMLUtil submitData(Context context,String baseId, String category,String taskId,String actionCode,String actionText,Map<String, String> parmas,Map<String, File> files);
}
