package cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;

/**
 * <p>Description:展示表现层用户属性<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-7
 */
public class ShowPeopleAttribute {
	
	static final Logger logger	= (Logger) Logger.getLogger(ShowPeopleAttribute.class);
	
	GetFormTableName tablename	= new GetFormTableName();
    String TBLName				= tablename.GetFormName("people");
	
    /**
     * <p>Description:显示字段值根据字段Id和ShowMenu中的List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-7
	 * @param attribute
	 * @param list
	 * @return String
	 */
	public String peopleAttribute(String attribute,List list)
	{
		ShowMenu showMenu = new ShowMenu();
		
		try
		{
			String peopleAtt = "";
			if(attribute==null||attribute.equals(""))
			{
				peopleAtt = Function.nullString(attribute);
			}
			else
			{
				peopleAtt = showMenu.getName(attribute,list);
			}
			return peopleAtt;
		}
		catch(Exception e)
		{
			logger.error("[436]ShowPeopleAttribute.peopleAttribute() 显示字段值根据字段Id和ShowMenu中的List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据字段Id显示字段值<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-7
	 * @param attribute
	 * @return String
	 */
	public String peopleAttributeByFind(String attribute)
	{
		try
		{
			String peopleAtt = "";
			GetGroupInfoList groupInfo = new GetGroupInfoList();
			
			if(attribute==null||attribute.equals(""))
			{
				peopleAtt = Function.nullString(attribute);
			}
			else
			{
				if(groupInfo.getGroupInfo(attribute)==null)
				{
					peopleAtt = Function.nullString(attribute);
				}
				else
				{
					peopleAtt = groupInfo.getGroupInfo(attribute).getC630000018();
				}
			}
			return peopleAtt;
		}
		catch(Exception e)
		{
			logger.error("[437]ShowPeopleAttribute.peopleAttribute() 根据字段Id显示字段值失败"+e.getMessage());
			return null;
		}
	}
    
	/**
	 * <p>Description:根据form.properties中的对应信息显示字段值<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-8
	 * @param word
	 * @return String
	 */
	public String peopleAttributeShow(String word)
	{
		ShowMenu showMenu = new ShowMenu();
		
		try
		{
			String peopleAtt = showMenu.getMenu(tablename.GetFormName(word),TBLName).toString();
			return peopleAtt;
		}
		catch(Exception e)
		{
			logger.error("[438]ShowPeopleAttribute.peopleAttributeShow() 根据form.properties中的对应信息显示字段值失败"+e.getMessage());
			return null;
		}
	}

}
