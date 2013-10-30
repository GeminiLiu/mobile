package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.process.*;

public class TplStateObj
{
	/**
	 * 存放的环节实体类
	 */
	public TplBaseFixStateModel tbfsModel = new TplBaseFixStateModel();
	
	private List processList = new ArrayList();
	
	/**
	 * 向状态中添加环节
	 * @param processID 环节ID
	 */
	public void addProcess(String processID)
	{
		processList.add(processID);
	}
	
	/**
	 * 从状态中删除环节
	 * @param processID 环节ID
	 */
	public void deleteProcess(String processID)
	{
		for(int i = 0; i < processList.size(); i++)
		{
			if(((String)processList.get(i)).equals(processID))
			{
				processList.remove(i);
				break;
			}
		}
	}
}
