package cn.com.ultrapower.ultrawf.control.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ConfigQueryDetailModel;
import cn.com.ultrapower.ultrawf.models.config.ConfigQueryMainModel;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;


/**
 * 业面自定义配置类
 * @author xufaqiu
 *
 */
public class PageDeploy {

	
	private ConfigQueryMainModel m_ConfigQueryMainModel;//配置信息主表信息
	private List m_ConfigQueryDetailModelList;//配置信息明细List
	
	public String getQueryTableHtmlString(ConfigQueryMainModel p_ConfigQueryMainModel,List p_ConfigQueryDetailModelList)
	{
		if(p_ConfigQueryMainModel==null||p_ConfigQueryDetailModelList==null)
			return "";
		m_ConfigQueryMainModel=p_ConfigQueryMainModel;
		m_ConfigQueryDetailModelList=p_ConfigQueryDetailModelList;
			
		StringBuffer strTableHtml=new StringBuffer();
		
		String strTemp=m_ConfigQueryMainModel.getColcount();//取每行的查询条件个数
		int rowFiledCount=0;//每行的查询条件个数 
		int lableColspan=1;//名称显示列占的列数
		
		int colCount=0;//每行列数 =每行的查询条件个数*2;
		if(strTemp.trim().equals(""))
		{
			rowFiledCount=3;
			colCount=rowFiledCount*2;
		}
		else
		{
			rowFiledCount=Integer.parseInt(strTemp);
			colCount=rowFiledCount*2;//因为每个查询条件要占两列 名称显示列和数据输入列
		}
		
		String lablePerWidth="";//名称显示列占宽度的百分比
		String inputPerWidth="";		
		lablePerWidth=m_ConfigQueryMainModel.getLablepercent();
		if(lablePerWidth.trim().equals(""))
		{
			lablePerWidth="10";
		}
		
		int intPer=Integer.parseInt(lablePerWidth)*rowFiledCount;
		
		if(intPer>=100)
		{
			int arvWidth=100/rowFiledCount;
			//显示列宽设置的不合理按默认设置进行
			lablePerWidth=String.valueOf(arvWidth);
			inputPerWidth=lablePerWidth;
		}
		else
		{
			int arvInputWidth=(100-intPer)/rowFiledCount;			
			inputPerWidth=String.valueOf(arvInputWidth);//条件输入列宽
		}
		
		int listCount=0;
		if(m_ConfigQueryDetailModelList!=null)
			listCount=m_ConfigQueryDetailModelList.size();
		
		
		
		PageDeploy m_PageDeploy=new PageDeploy();
		strTableHtml.append("<table width='97%' align='center'> ");
		strTableHtml.append("<tr>");
		//用于固定列宽
		for(int td=0;td<colCount;td++)
		{
			int mod=td%2;
			if(mod==0)
				strTableHtml.append("<td style='width:"+lablePerWidth+"%'>");
			else
				strTableHtml.append("<td style='width:"+inputPerWidth+"%'>");
			
		}	
		strTableHtml.append("</tr>");	
		int trCount=0;// 是否换行的标识 : 用于保存当前行已用了多少列,如果用的列数大于或等于行的列数则表示换行
		for(int i=0;i<listCount;i++)
		{
			ConfigQueryDetailModel m_ConfigQueryDetailModel=(ConfigQueryDetailModel)m_ConfigQueryDetailModelList.get(i);
			String strValue=m_ConfigQueryDetailModel.getEvaluate();
			if(i==0)
			{
				trCount=0;
				trCount+=lableColspan;
				String strCol=m_ConfigQueryDetailModel.getColspan();
				if(strCol.trim().equals(""))
					strCol="1";
				trCount+=Integer.parseInt(strCol);				
				strTableHtml.append("	<tr>");
			}
			else if(i==listCount)
			{
				strTableHtml.append("	</tr>");
			}
			else
			{
				String isNewline=m_ConfigQueryDetailModel.getIsNewLine();
				//是否为新的一行
				if(isNewline.equals(""))
					isNewline="0";
				if(trCount>=colCount ||isNewline.equals("1"))
				{
					trCount=0;
					strTableHtml.append("	</tr>");
					strTableHtml.append("	<tr>");
				}
				trCount+=lableColspan;
				String strCol=m_ConfigQueryDetailModel.getColspan();
				if(strCol.trim().equals(""))
					strCol="1";
				trCount+=Integer.parseInt(strCol);
			}
			strTableHtml.append(m_PageDeploy.getCtrlHtmlString(m_ConfigQueryDetailModel,strValue));
		}
  		
  		
  		//strSearch.append("	<tr>");
  		//strSearch.append("		<td>条件1：</td>");
  		//strSearch.append("		<td>");
  		//strSearch.append("	<input name='con1' value='"+FormatString.toChineseiso(request.getParameter("con1"))+"'>");
  		//strSearch.append("		</td>");
  		//strSearch.append("	</tr>");
  		
		/*
		strTableHtml.append("	<tr>");
		strTableHtml.append("		<td align='center' colspan='"+String.valueOf(colCount)+"'>");
		strTableHtml.append("		<input type='submit' class='button'name='btnSubmit' value='查  询'>&nbsp;");
		strTableHtml.append("		<input id='btnReset'  class='button' type='button' value='重  置'>");
		strTableHtml.append("		</td>");  			  		
		strTableHtml.append("	</tr>");
		*/
		strTableHtml.append("</table>");		
		
		
		return strTableHtml.toString();
		
	}
	public String getCtrlHtmlString(ConfigQueryDetailModel p_ConfigQueryDetailModel,String p_strValue)
	{

		
		StringBuffer strHtml=new StringBuffer();
		String strFieldtype=p_ConfigQueryDetailModel.getFieldtype().trim();//字段类型
		String strDisFileidType=p_ConfigQueryDetailModel.getFielddisplaytype().trim();
		if(strDisFileidType.equals(""))
			strDisFileidType="1";//没有则默认为输入框
		if(p_strValue==null)
			p_strValue="";
		
			//strHtml.append("<td style='width:"+lablePerWidth+"%'>");
			strHtml.append("<td>");
			strHtml.append(p_ConfigQueryDetailModel.getFielddisplayname());
			strHtml.append("</td>");
			//取colspan
			String colspan=p_ConfigQueryDetailModel.getColspan().trim();
			if(colspan.equals("")||colspan.equals("0"))
				colspan="1";
			//strHtml.append("<td cols='"+colspan+"' style='width:'"+inputPerWidth+"%'>");
			strHtml.append("<td colspan='"+colspan+"'>");
			//设置输入框名称
			String ctlName=p_ConfigQueryDetailModel.getFieldname();
			if(ctlName==null)
				ctlName="ctlName";
			//ctlName="C"+ctlName;
			
			//如果是输入框
			if(strDisFileidType.equals("1"))
			{
				//如果是日期
				if(strFieldtype.trim().equals("5"))
					strHtml.append( getDateHtmlString(p_ConfigQueryDetailModel,p_strValue));
				else
					strHtml.append("	<input type='text' name='"+ctlName+"' style='width:100%' value='"+p_strValue+"'>");
			}
			
			//如果是select
			if(strDisFileidType.equals("2"))
			{
				strHtml.append(getSelectHtmlString(p_ConfigQueryDetailModel,p_strValue));
				
			}//if(strFileidType.equals("2"))	

			
			
			
			strHtml.append("</td>");

		return strHtml.toString();
		
	}
	
	/**
	 * 返回select的html字符
	 * @param p_ConfigQueryDetailModel
	 * @param p_strValue
	 * @return
	 */
	private String getSelectHtmlString(ConfigQueryDetailModel p_ConfigQueryDetailModel,String p_strValue)
	{
		String strValue;
		StringBuffer strHtml=new StringBuffer();
		//设置输入框名称
		String ctlName=p_ConfigQueryDetailModel.getFieldname();
		//ctlName="C"+ctlName;
		
		strHtml.append("	<select name='"+ctlName+"' style='width:100%' >");
		String defaultvalue1=FormatString.CheckNullString(p_ConfigQueryDetailModel.getDefaultvalue1()).trim();
		String defaultvalue2=FormatString.CheckNullString(p_ConfigQueryDetailModel.getDefaultvalue2()).trim();
		//取缺值1
		if(!defaultvalue1.equals(""))
		{
			strHtml.append("		<option value=''></option >");
			String[] strOption=defaultvalue1.split(";");
			String strTemp="";
			for(int i=0;i<strOption.length;i++)
			{
				String strOptionValue=FormatString.CheckNullString(strOption[i]);
				String[] strValueAry=strOptionValue.split(",");
				strValue=FormatString.CheckNullString(strValueAry[0]);
				int len=strValueAry.length;
				if(len>=1)
				{
					if(len>1)
						strTemp=FormatString.CheckNullString(strValueAry[1]);	
					else
						strTemp="";
					if(p_strValue.equals(strValue))
					{
						strHtml.append("		<option selected value='"+strValue+"'>"+strTemp+"</option >");
					}
					else
					{
						strHtml.append("		<option value='"+strValue+"'>"+strTemp+"</option >");
					}
				}
				else
					strHtml.append("		<option value=''></option >");
			}//for(int i=0;i<strOption.length;i++)
		}
		else if(!defaultvalue2.equals(""))
		{
			//缺省值2(sql查询语句,如果是ar的表用$符分割如:select * from $UltraProcess:Config_SysBaseItems$ )
			StringBuffer strSelect= new StringBuffer();
			String strTblName;
			
			String[] strOptAry=defaultvalue2.split("\\$");
			int intSplitLen=0;//defaultvalue2.compareTo("\\$");
			for(int j=0;j<defaultvalue2.length();j++)
			{
				if(defaultvalue2.charAt(j)=='$')
					intSplitLen++;
			}
			if(intSplitLen==0)
			{
				strSelect.append(defaultvalue2);
			}
			else if(intSplitLen%2==0)
			{
				RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
				
				for(int len=0;len<intSplitLen;len++)
				{
					if(len==0)
					{
						strSelect.append(FormatString.CheckNullString(strOptAry[len]));
					}
					else
					{
						if((len+1)%2==0)
						{
							strTblName=FormatString.CheckNullString(strOptAry[len]);
							strTblName=strTblName.replaceAll("\n","").trim();
							//获取表名
							strTblName=m_RemedyDBOp.GetRemedyTableName(strTblName);
							strSelect.append(" ");
							strSelect.append(strTblName);
							strSelect.append(" ");
						}
					}
					
				}//for(int len=0;len<intSplitLen;len++)
				
			}else
			{
				//如果$符号不成对则表示有错误
				//strSelect="";
			}//if(intSplitLen==1)
				
			String strSql=strSelect.toString();
			strHtml.append("		<option value=''></option >");
			if(!strSql.trim().equals(""))
			{
				IDataBase m_dbConsole = GetDataBase.createDataBase();
				Statement stm=null;
				ResultSet m_ObjRs =null;		
				try
				{
					stm=m_dbConsole.GetStatement();
					m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
					int cols=m_ObjRs.getMetaData().getColumnCount();
					while(m_ObjRs.next())
					{
						strValue=FormatString.CheckNullString(m_ObjRs.getString(1)).trim();
						if(p_strValue.equals(strValue))
						{
							strHtml.append("		<option  selected value='");
						}
						else
						{
							strHtml.append("		<option value='");
						}
						strHtml.append(FormatString.CheckNullString(m_ObjRs.getString(1)));
						
							
						strHtml.append("'>");
						if(cols>=2)
							strHtml.append(FormatString.CheckNullString(m_ObjRs.getString(2)));
						strHtml.append("</option >");
					}
					//m_List=ConvertRsToList(m_UserRs);
					
				}catch(Exception ex)
				{
					System.err.println("PageDeploy.getCheckBoxHtmlString 方法"+ex.getMessage());
					ex.printStackTrace();				
				}
				finally
				{
					try{
						if(m_ObjRs!=null)
							m_ObjRs.close();
					}
					catch(Exception ex)
					{
						System.err.println(ex.getMessage());
					}
					try{
						if(stm!=null)
							stm.close();
					}
					catch(Exception ex)
					{
						System.err.println(ex.getMessage());
					}				
					m_dbConsole.closeConn();			
				}					
				
			}//if(!strSql.trim().equals(""))
			
		}//if(!defaultvalue1.equals(""))

		
		strHtml.append("	</select>");		
		return strHtml.toString();
	
	}
	
	/**
	 * CheckBox(未完成)
	 * @param p_ConfigQueryDetailModel
	 * @param p_strValue
	 * @return
	 */
	private String getCheckBoxHtmlString(ConfigQueryDetailModel p_ConfigQueryDetailModel,String p_strValue)
	{
		String strValue;
		StringBuffer strHtml=new StringBuffer();
		//设置输入框名称
		String ctlName=p_ConfigQueryDetailModel.getFieldname();
		//ctlName="C"+ctlName;
		String defaultvalue1=FormatString.CheckNullString(p_ConfigQueryDetailModel.getDefaultvalue1()).trim();
		String defaultvalue2=FormatString.CheckNullString(p_ConfigQueryDetailModel.getDefaultvalue2()).trim();

		if(!defaultvalue1.equals(""))
		{
			//strHtml.append("		<input type='checkbox' value=''>");
			String[] strOption=defaultvalue1.split(";");
			
			for(int i=0;i<strOption.length;i++)
			{
				String strOptionValue=FormatString.CheckNullString(strOption[i]);
				String[] strValueAry=strOptionValue.split(",");
				strValue=FormatString.CheckNullString(strValueAry[0]);
				if(strValueAry.length>=1)
				{
					if(p_strValue.equals(strValue))
						strHtml.append("		<option selected value='"+strValue+"'>"+FormatString.CheckNullString(strValueAry[1])+"</option >");
					else
						strHtml.append("		<option value='"+strValue+"'>"+FormatString.CheckNullString(strValueAry[1])+"</option >");
				}
				//else
				//	strHtml.append("		<option value=''></option >");
			}//for(int i=0;i<strOption.length;i++)
		}		
		
		
		return strHtml.toString();
	}
	
	private String getDateHtmlString(ConfigQueryDetailModel p_ConfigQueryDetailModel,String p_strValue)
	{
		StringBuffer strHtml=new StringBuffer();
		if(p_ConfigQueryDetailModel==null) 
			return "";
		String ctlName=p_ConfigQueryDetailModel.getFieldname();
		//strHtml.append("	<input type='text' name='"+ctlName+"' style='width:100%' ondblClick='calendar()' onblur='return isValidDate(this)' onfocus='this.select()' value='"+p_strValue+"'>");
		strHtml.append("	<input type='text' name='"+ctlName+"' style='width:100%' onClick='calendar()'  value='"+p_strValue+"'>");
		
		return strHtml.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
