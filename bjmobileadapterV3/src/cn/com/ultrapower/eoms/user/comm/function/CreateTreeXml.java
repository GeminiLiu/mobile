package cn.com.ultrapower.eoms.user.comm.function;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;

public class CreateTreeXml {

	static final Logger logger = (Logger) Logger.getLogger(CreateTreeXml.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void createxml(SendScopePram sendscopepram)
	{
		String usertablename		 = "";
		String grouptablename		 = "";
		String groupusertablename	 = "";
		StringBuffer sql 	= new StringBuffer();
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertablename		 = getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		 = getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	 = getTableProperty.GetFormName("RemedyTgroupuser");
			sql.append("select * " +
					  " from "+grouptablename+" sysgroup" +
					  " where 1=1" +
					  " and sysgroup.c630000025='0'" +
					  " and sysgroup.c630000020='0'");
			
		}
		catch(Exception exp)
		{
			exp.getMessage();
		}
	}
	
	public void readxml(SendScopePram sendscopepram)
	{
		
	}

}
