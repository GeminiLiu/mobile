package cn.com.ultrapower.eoms.user.userinterface;

import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.userinterface.bean.IndexTopBean;

public class IndexShowInterface
{

	public boolean hasGrandValue(String username,String  sourcename)
	{
		boolean bl = false;
		
		IndexShowInterfaceSQL indexsql = new IndexShowInterfaceSQL();
		
		bl = indexsql.getBoolValue(username,sourcename);
		
		return bl;
	}
	/**
	 * 根据用户登陆名取得此用户有权限的导航栏LIST。
	 * 日期 2007-2-5
	 * @author wangyanguang
	 * @param username
	 */
	public List getTopList(String username)
	{
		IndexShowInterfaceSQL indexsql = new IndexShowInterfaceSQL();
		List list = indexsql.getTopList(username);
		if(list!=null)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	
	public static void main(String args[])
	{
		IndexShowInterface indexinterface = new IndexShowInterface();
		List list = indexinterface.getTopList("Demo");
		System.out.println("show:");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			IndexTopBean indexinfo = (IndexTopBean)it.next();
			String sourceid = indexinfo.getSourceid();
			String sourcename = indexinfo.getSourceenname();
			String sourcecnname = indexinfo.getSorucecnname();
			String urlvalue = indexinfo.getUrl();
			System.out.println("资源ID："+sourceid+",资源中文名称："+sourcecnname+",资源英文名称:"+sourcename+",ulr:"+urlvalue);
		}
			
	}
}
