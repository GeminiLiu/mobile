package cn.com.ultrapower.ultrawf.control.design;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.design.UtilDataHandler;

/**
 * 一些模板需要的工单的数据提取业务类
 * @版本 V0.1
 * @Build 0001
 * @作者 BigMouse
 * @说明
 */
public class UtilDataManage
{
	/**
	 * 获取下拉菜单的实体类集合
	 * @param category 下拉菜单的类别
	 * @return 下拉菜单的实体类集合
	 */
	public List getSelectionModel(String category)
	{
		UtilDataHandler udHandler = new UtilDataHandler();
		if(category.equals("BaseCategory"))
		{
			return udHandler.getSelectionModel("UltraProcess:Config_BaseCategory", "C650000002", "C650000001");
		}
		else if(category.equals("User"))
		{
			return udHandler.getSelectionModel("User", "C101", "C8");
		}
		else if(category.equals("Group"))
		{
			return udHandler.getSelectionModel("Group", "C106", "C105");
		}
		else
		{
			return new ArrayList();
		}
	}

}
