package cn.com.ultrapower.eoms.user.sla.bean;
import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.ShowMenu;

/**
 * 日期 2006-10-23
 * @author xuquanxing/徐全星
 * 该类用于返回sla的状态字段值 
 */
public class SlaShowMenu 
{   

	static final Logger logger = (Logger) Logger.getLogger(SlaShowMenu.class);
	public String getMenuResult(String formname, String field)
	{   
		String name          = "";
		String fieldvalue    = "";
		try
		{   
			GetFormTableName getformtablename = new GetFormTableName();
			fieldvalue                        = getformtablename.GetFormName(formname);
			name                              = getformtablename.GetFormName(field);
			System.out.print(name);
			System.out.print(fieldvalue);
			StringBuffer buff =new StringBuffer();
			ShowMenu showmenu = new ShowMenu();
			buff = showmenu.getMenu(fieldvalue,name);
			System.out.println("fieldvalue::"+fieldvalue);
			System.out.println("name::"+name);
			System.out.println("szie:-"+buff.length());
			if(buff.toString().equals("")){return ""; }
			else
			{
				//System.out.println("overe");
			    return buff.toString();
			}
		}catch(Exception e)
		{
			logger.error("[100]SlaShowMenu"+e.getMessage());
			return "";
		}

	
	}
	
	public static void main(String[] args){
		
		SlaShowMenu sla= new SlaShowMenu();
		
		System.out.print(sla.getMenuResult("slaquery","syssla").toString());
		
	}
}
