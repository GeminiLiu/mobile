package daiwei.mobile.service;

import java.util.List;
import java.util.Map;

import android.content.Context;
import daiwei.mobile.animal.XJListModel;
/**
 * 巡检工单接口
 * @author 都 3/15
 *
 */
public interface XJListService {
	
	/**
	 * 注：这里要传getApplicationContext()
	 * @param context
	 * @param isWait
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public XJListModel getXJWaitList(Context context,int isWait,int pageNum ,int pageSize,String specialtyId,String serviceCode);
	
	public List<Map<String,String>> getFinishList(int pageNum);
}
