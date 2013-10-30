package daiwei.mobile.service;

import java.util.List;
import java.util.Map;

import android.content.Context;

import daiwei.mobile.animal.ListModel;

public interface GDListService{
	
	/**
	 * 注：这里要传getApplicationContext()
	 * @param context
	 * @param isWait
	 * @param pageNum
	 * @param pageSize
	 * @param category
	 * @param queryCondition
	 * @return
	 */
	//public ListModel getWaitList(Context context,int isWait,int pageNum ,int pageSize, int category ,String queryCondition);
	public ListModel getWaitList(Context context,int isWait,int pageNum ,int pageSize, String category ,String queryCondition);
	
	public List<Map<String,String>> getFinishList(int pageNum);
	/**
	 * eoms系统手机公告已办列表
	 */
	public ListModel getGgWaitList(Context context,int isWait,int pageNum,int pageSize, String category, String queryCondition);
	
}