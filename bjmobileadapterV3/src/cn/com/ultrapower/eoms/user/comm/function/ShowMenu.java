package cn.com.ultrapower.eoms.user.comm.function;

import cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage.MenuFindInfoList;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.SysDropDownConfigpo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * <p>Description:根据查询条件显示不同的下拉菜单<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-19
 */
public class ShowMenu {
	
	static final Logger logger = (Logger) Logger.getLogger(ShowMenu.class);
    
	/**
	 * <p>Description:根据字段名和表名封装出下拉菜单中的option(下拉框中的所有值)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-19
	 * @param FieldName
	 * @param TableName
	 * @return StringBuffer
	 */
	public StringBuffer getMenu(String FieldName,String TableName)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			System.out.println(FieldName+"---"+TableName);
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			
			selectMenu.append("<option value=''>请选择</option>");
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	public StringBuffer getNoSelectMenu(String FieldName,String TableName)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			System.out.println(FieldName+"---"+TableName);
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			
//			selectMenu.append("<option value=''>请选择</option>");
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据字段名和表名以及相应的value值定位下拉菜单中的option(下拉框中的所有值,默认显示改value的值)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-9
	 * @param FieldName
	 * @param TableName
	 * @param optionValue
	 * @return StringBuffer
	 */
	public StringBuffer getMenu(String FieldName,String TableName,String optionValue)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if(String.valueOf(dropDownConfig.getC620000017()).equals(optionValue))
				{
					selectMenu.append("<option value='"+optionValue+"' selected>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
				}
				else
				{
					selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
				}	
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据字段名和表名以及相应的optionValue定位下拉菜单中的option(下拉框中的所有值,默认显示改value的值)
	 * exceptValue为不显示得值<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-9
	 * @param FieldName
	 * @param TableName
	 * @param optionValue
	 * @param exceptValue
	 * @return StringBuffer
	 */
	public StringBuffer getMenu(String FieldName,String TableName,String optionValue,String exceptValue)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if(String.valueOf(dropDownConfig.getC620000017()).equals(optionValue))
				{
					selectMenu.append("<option value='"+optionValue+"' selected>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
				}
				else if(String.valueOf(dropDownConfig.getC620000017()).equals(exceptValue))
				{
					continue;
				}
				else
				{
					selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
				}	
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据字段名和表名以及相应的value值显示除该value的所有下拉菜单中的option(除该value外的下拉框中的所有值)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-02-05
	 * @param FieldName
	 * @param TableName
	 * @param optionValue
	 * @return StringBuffer
	 */
	public StringBuffer getExceptMenu(String FieldName,String TableName,String optionValue)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			selectMenu.append("<option value=''>请选择</option>");
			
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if(String.valueOf(dropDownConfig.getC620000017()).equals(optionValue))
				{
					continue;
				}
				else
				{
					selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
				}	
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据字段名和表名以及相应的value值只显示该value的下拉菜单中的option(只显示该value外的下拉框值)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-02-05
	 * @param FieldName
	 * @param TableName
	 * @param optionValue
	 * @return StringBuffer
	 */
	public StringBuffer getOnlyMenu(String FieldName,String TableName,String optionValue)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if(String.valueOf(dropDownConfig.getC620000017()).equals(optionValue))
				{
					selectMenu.append("<option value='"+String.valueOf(dropDownConfig.getC620000017())+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</option>");
					break;
				}
				else
				{
					continue;
				}	
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
	/**
	 * <p>Description:根据字段名和表名 和传过来的字段id 传回对应的中文名<p>
	 * @author shigang
	 * @creattime 2006-10-27
	 * @param FieldName
	 * @param TableName
	 * @param fieldType
	 * @return String
	 */
	public String getTd(String FieldName,String TableName,String fieldType)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				System.out.println("17=="+dropDownConfig.getC620000017()+"16=="+String.valueOf(dropDownConfig.getC620000016()));
			
				if (fieldType.equals(dropDownConfig.getC620000017())||fieldType==dropDownConfig.getC620000017()){
					
					return String.valueOf(dropDownConfig.getC620000016());
				}
			}
		
			logger.info("生成下拉框列表值成功。");
		}
		catch(Exception e)
		{	e.printStackTrace();
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}
		return fieldType;	
	}
	
	/**
	 * <p>Description:根据字段名和表名返回对应List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-10
	 * @param FieldName
	 * @param TableName
	 * @return List
	 */
	public List getList(String FieldName,String TableName)
	{
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			return list;
		}
		catch(Exception e)
		{	
			logger.error("根据字段名和表名返回对应List失败"+e.getMessage());
			return null;
		}	
	}
	
	/**
	 * <p>Description:使用字段值与SYSDropDownConfig表匹配显示中文名<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-10
	 * @param fieldType
	 * @param list
	 * @return String
	 */
	public String getName(String fieldType,List list)
	{
		try
		{
			for(Iterator it=list.iterator();it.hasNext();)
			{
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if (fieldType.equals(dropDownConfig.getC620000017())||fieldType==dropDownConfig.getC620000017()){
					return String.valueOf(dropDownConfig.getC620000016());
				}
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("匹配字段值失败:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据字段名和表名以及相应的value值定位下拉菜单中的option(下拉框中的所有值,默认显示改value的值)<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-9
	 * @param FieldName
	 * @param TableName
	 * @param optionValue
	 * @return StringBuffer
	 */
	public StringBuffer getCheckBox(String FieldName,String TableName)
	{
	
		MenuFindInfoList menuInfo = new MenuFindInfoList();
		StringBuffer selectMenu   = new StringBuffer();
		try
		{
			List list = menuInfo.getMenuListInfo(FieldName,TableName);
			Iterator it=list.iterator();
			int i=1;
			//selectMenu.append("<option value=''>请选择</option>");
			while(it.hasNext())
			{	
				SysDropDownConfigpo dropDownConfig = (SysDropDownConfigpo)it.next();
				if(i==1)
				{
					//selectMenu.append("<tr><td colspan='4'>");
				}
				if((i%6)==1&&list.size()>=i)
				{
					selectMenu.append("<tr><td colspan='4'>");
				}
				if((i%6)==0)
				{
					selectMenu.append("<input type='checkbox' class='checkbox' value='"+String.valueOf(dropDownConfig.getC620000017())+"' name='"+FieldName+"'>"+String.valueOf(dropDownConfig.getC620000016())+"</td></tr>\n");
				}
				else
				{
					selectMenu.append("<input type='checkbox' class='checkbox' value='"+String.valueOf(dropDownConfig.getC620000017())+"' name='"+FieldName+"'>"+String.valueOf(dropDownConfig.getC620000016())+" \n");
				}
				i++;
			}	
			if(list.size()>0&&list.size()%6!=0)
			{
				selectMenu.append("</td></tr>");
			}
			logger.info("生成下拉框列表值成功。");
			return selectMenu;
		}
		catch(Exception e)
		{
			logger.error("生成下拉框列表值失败"+e.getCause());
			return null;
		}	
	}
	
}