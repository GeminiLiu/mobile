package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;

public class ShowGroupAttribute {

	static final Logger logger	= (Logger) Logger.getLogger(ShowGroupAttribute.class);
	
	GetFormTableName tablename	= new GetFormTableName();
    String TBLName				= tablename.GetFormName("group");
	
	/**
	 * <p>Description:显示字段值根据字段Id和ShowMenu中的List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-7
	 * @param attribute
	 * @param word
	 * @return String
	 */
	public String groupAttribute(String attribute,List list)
	{
		ShowMenu showMenu = new ShowMenu();
		
		try
		{
			String groupAtt = "";
			if(attribute==null||attribute.equals(""))
			{
				groupAtt = Function.nullString(attribute);
			}
			else
			{
				groupAtt = showMenu.getName(attribute,list);
			}
			return groupAtt;
		}
		catch(Exception e)
		{
			logger.error("[411]ShowGroupAttribute.groupAttribute() 显示字段值根据字段Id和ShowMenu中的List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:显示字段值根据字段Id<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-7
	 * @param attribute
	 * @return String
	 */
	public String groupAttributeByFind(String attribute)
	{
		try
		{
			String groupAtt = "";
			GetGroupInfoList groupInfo = new GetGroupInfoList();
			
			if(attribute == null||attribute.equals(""))
			{
				groupAtt = Function.nullString(attribute);
				
			}
			else if(attribute.equals("0"))
			{
				groupAtt = "";
			}
			else if(groupInfo.getGroupInfo(attribute) == null||groupInfo.getGroupInfo(attribute).equals(""))
			{
				groupAtt = Function.nullString(attribute);
			}
			else if(groupInfo.getGroupInfo(attribute).getC630000018().equals("0"))
			{
				groupAtt = "";
			}
			else
			{
				groupAtt = groupInfo.getGroupInfo(attribute).getC630000018();
			}
			
			return groupAtt;
		}
		catch(Exception e)
		{
			logger.error("[412]ShowGroupAttribute.groupAttributeByFind() 显示字段值根据字段Id失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:显示字段值根据form.properties中的对应信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-8
	 * @param word
	 * @return String
	 */
	public String groupAttributeShow(String word)
	{
		ShowMenu showMenu = new ShowMenu();
		
		try
		{
			String peopleAtt = showMenu.getMenu(tablename.GetFormName(word),TBLName).toString();
			return peopleAtt;
		}
		catch(Exception e)
		{
			logger.error("[413]ShowGroupAttribute.groupAttributeShow() 显示字段值根据form.properties中的对应信息失败"+e.getMessage());
			return null;
		}
	}

}
