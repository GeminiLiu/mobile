package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;

public class GrandActionInterface
{
	/**
	 * 根据参数将值插入到技能动作表中。 日期 2007-1-23
	 * 
	 * @author wangyanguang
	 */
	public boolean insertGrandActionInterface(List list)
	{
		GrandActionConfig grandactionconfig = new GrandActionConfig();
		boolean flag = false;
		for (Iterator it = list.iterator(); it.hasNext();)
		{
			GrandActionConfigBean grandactioninfo = (GrandActionConfigBean) it
					.next();
			boolean bl = getDupValue(grandactioninfo);
			if (bl)
			{

			} else
			{
				flag = grandactionconfig.grandActionConfigInsert(grandactioninfo);
			}
		}
		return flag;
	}

	/**
	 * 判断Bean信息值在数据库中是否存在。 日期 2007-1-23
	 * 
	 * @author wangyanguang
	 */
	public boolean getDupValue(GrandActionConfigBean grandactioninfo)
	{
		if (grandactioninfo != null)
		{
			String sourceid = Function.nullString(grandactioninfo.getDropDownConf_FieldID());
			String fieldValue = Function.nullString(grandactioninfo.getDropDownConf_FieldValue());
			String numberValue = Function.nullString(grandactioninfo.getDropDownConf_NumValue());
			if (!sourceid.equals("") && !fieldValue.equals("")&& !numberValue.equals(""))
			{
				StringBuffer sql = new StringBuffer();
				sql.append("from GrandActionConfigpo t where t.c620000032='"
						+ sourceid + "'");
				sql.append(" and t.c620000033='" + fieldValue + "'");
				sql.append(" and t.c620000034='" + numberValue + "'");
				try
				{
					List list = HibernateDAO.queryObject(sql.toString());
					if (list != null)
					{
						if (list.size() > 0)
						{
							return true;
						} else
						{
							return false;
						}
					} else
					{
						return false;
					}
				} catch (Exception e)
				{
					System.out.println("调用HibernateDAO进行查询时出现异常！");
					return true;
				}
			} else
			{
				System.out.println("参数格式不对，或者数据不全。");
				return true;
			}

		} else
		{
			System.out.println("参数格式不对，或者数据不全。");
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		GrandActionInterface grandinterface = new GrandActionInterface();
		GrandActionConfigBean grandinfo = new GrandActionConfigBean();
		List list = new ArrayList();
		grandinfo.setDropDownConf_FieldID("50001");
		grandinfo.setDropDownConf_FieldValue("abcd");
		grandinfo.setDropDownConf_NumValue("2");
		grandinfo.setDropDownConf_OrderBy("2");
		GrandActionConfigBean grandinfo1 = new GrandActionConfigBean();
		grandinfo1.setDropDownConf_FieldID("50002");
		grandinfo1.setDropDownConf_FieldValue("abcd");
		grandinfo1.setDropDownConf_NumValue("2");
		grandinfo1.setDropDownConf_OrderBy("2");
		list.add(grandinfo);
		list.add(grandinfo1);
		boolean bl = grandinterface.insertGrandActionInterface(list);
		if(bl)
		{
			System.out.println("OK");
		}
		else
		{
			System.out.println("Sorry");
		}
	}
}
