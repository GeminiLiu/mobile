package cn.com.ultrapower.eoms.processSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomProcessInterface {
	
	private static InterfaceCfg intcfg = null;
	private static Map processMap = null;
	
	static{
		intcfg = new InterfaceCfg();
		processMap = intcfg.getProcessMap(Contents.PERSONPROCESS);
	}
	
	/**
	 * 
	 * @return 获取工单前缀字符
	 */
	public String getPrefix()
	{
		return processMap.get(Contents.PREFIX).toString();
	}
	
	public String getBmcTable()
	{
		return processMap.get(Contents.BMCTABLE).toString();
	}
	
	public String getQuatzTable()
	{
		return processMap.get(Contents.QUATZTABLE).toString();
	}
	
	public String getUrls()
	{
		return processMap.get(Contents.URLS).toString();
	}
	
	public String getQuatzSelect()
	{
		return processMap.get(Contents.QUATZSELECT).toString();
	}
	
	public String getQuatzUpdate()
	{
		return processMap.get(Contents.QUATZUPDATE).toString();
	}
	
	/**
	 * 
	 * @return 获取个人投诉工单相关属性信息
	 */
	public Map getProcessMap()
	{
		return processMap;
	}
	
	/**
	 * 
	 * @return 获取个人投诉工单 新建工单相关信息。
	 */
	public Map<String,String> getMethodMap(String method)
	{
		Map<String,String> map = new HashMap<String,String>();
		map = intcfg.getProcessMap(Contents.PERSONPROCESS,method);
		return map;
	}
	
	public List<Map<String, String>> getBasefieldInfo(String methodName,String type)
	{
		List<Map<String, String>> revlist = new ArrayList<Map<String, String>>();
		Map<String, String> currentMap = null;
		List<Map<String, String>> list = intcfg.getBaseFieldInfo(Contents.PERSONPROCESS, methodName);
		for(int i=0;i<list.size();i++)
		{
			currentMap = list.get(i);
			if(currentMap.get(Contents.TYPE).toString().equals(type))
			{
				revlist.add(currentMap);
			}
		}
		return revlist;
	}
	
}
