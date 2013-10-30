package daiwei.mobile.service;
import java.util.ArrayList;
import java.util.HashMap;

import android.R.integer;
import daiwei.mobile.animal.Site;
/** 
 * @ClassName: XJLineService 
 * @Description: 取巡检轨迹
 * @author jiangshidi QQ:82421098 
 * @date 2013-5-27 下午3:19:59 
 *  
 */
public interface XJLineService {
	
	/** 
	 * @Title: getSiteMap 
	 * @Description: 取得已巡检节点
	 * @param @param taskId
	 * @param @return    设定文件 
	 * @return HashMap<integer,ArrayList<Site>>    返回类型 
	 * @throws 
	 */
	public HashMap<Integer,ArrayList<Site>>  getSiteMap(String taskId);
	
	
	/** 
	 * @Title: getSiteList 
	 * @Description: 取得线段更新
	 * @param @param taskId
	 * @param @param lineNo
	 * @param @return    设定文件 
	 * @return ArrayList<Site>    返回类型 
	 * @throws 
	 */
	public ArrayList<Site> getSiteList(String taskId,integer lineNo);
}
