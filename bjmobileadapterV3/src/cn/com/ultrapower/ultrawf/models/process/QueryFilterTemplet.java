
package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;

import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.table.*;
import cn.com.ultrapower.system.table.par.*;
import cn.com.ultrapower.system.util.*;
import  cn.com.ultrapower.ultrawf.share.BASEACTION_QUERY;
import  cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;

public class QueryFilterTemplet {
	 
	
	private String getGroupUserCloseBaseGroupid(String p_strLoginName,String p_BaeSchemas)
	{
		String result="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUserCloseBaseGroup);	
		IDataBase dbConn=GetDataBase.createDataBase();
		DataBaseDAO dbdao=new DataBaseDAO(dbConn,strTblName);
		try
		{
			ParRow parRow=new ParRow();
			parRow.setFilter(ParCompare.isOR, "AssignID", "C650000004", p_strLoginName, 0);
			parRow.setFilter(ParCompare.isEquals, "BaseSchema", "C650000002", p_BaeSchemas, 0);
			//650000004 AssgineeID 
			RowSet rowSet=dbdao.getRows("C650000006 as CloseBaseGroupID","", parRow,"", 0, 0, 0);
			int rowLen=0;
			if(rowSet!=null)
				rowLen=rowSet.length();
			String groupid="";
			for(int i=0;i<rowLen;i++)
			{
				Row row=rowSet.get(i);
				
				if(groupid.equals(""))
					groupid=row.getString(0);
				else
					groupid+=","+row.getString(0);
			}
			result=groupid;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param action 动作
	 * @param loginName 处理人登陆名
	 * @param p_baseSchema 工单类别
	 * @param group 处理人等录组ID
	 * @param hs 数据字典
	 * @return ParFilter
	 */
	public  ParFilter getBaseFilterTemplet(String action,String p_baseSchema, String loginName,String p_groupid,HashMap hs)
	{
		
	
		loginName=FormatString.CheckNullString(loginName).trim();
		p_groupid=FormatString.CheckNullString(p_groupid).trim();
		ParFilter tmpParFilter=null;
		action=FormatString.CheckNullString(action).toUpperCase();
		StringBuffer sql=new StringBuffer();
		Object[] values=null;
		ParFilter parFilter=null;
		if(action.equals(""))
			return null;
		String strTemp="";
		
		ParTransfer parTransfer=new ParTransfer();
		List lstValues;
		//待办所有的
		if(action.equals("WAITDEALALL")
				||action.equals(BASEACTION_QUERY.WAITAUDITING)
				||action.equals(BASEACTION_QUERY.WAITCONFIRM)
				)
		{
			 lstValues=new ArrayList();
			//700020020 FlagActive 
			sql.append(" and C700020020=? "); //C700020020=1
			values=new Object[1];
			values[0]="1";
		
			String lgSql="";
			if(!loginName.equals(""))
			{
				//处理组  700020008 GroupID
				//查询用户组  分派的本组待处理的逻辑
				String dealGroupSql="";				
				try{
					lgSql="and ( ";
					lgSql+="(C700020016 is null  ";
					//C700020006 AssgineeID
					lgSql+=" and (C700020006 =? ";
					lstValues.add(loginName);
					//C700020048  CommissionerID
					lgSql+=" OR C700020048=? ";
					lstValues.add(loginName);					
					if(!p_groupid.equals(""))
					{
						//dealGroupSql="c700020008=?";
						tmpParFilter=parTransfer.getFilter("OR",ParCompare.isOR,"c700020008",p_groupid,0);
						if(tmpParFilter!=null)
						{
							dealGroupSql=tmpParFilter.getSql();
							lgSql+=dealGroupSql;
							lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
						}
					}

//					700020051 CloseBaseSamenessGroupID 同组关单的组ID
					if(!action.equals(BASEACTION_QUERY.WAITAUDITING))//审批的没有同组关单
					{
						String closeGroupid=getGroupUserCloseBaseGroupid(loginName,p_baseSchema);
						tmpParFilter=parTransfer.getFilter("or", ParCompare.isOR, "C700020051", closeGroupid,0);
						if(tmpParFilter!=null)
						{
							dealGroupSql=tmpParFilter.getSql();
							lgSql+= dealGroupSql;
							lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
						}
					}
					lgSql+="))";
					
					lgSql+=" or ";
					
					//已受理
					lgSql+="(C700020016>? ";
					lstValues.add("0");
					//C700020010 DealerID
					lgSql+=" and (C700020010=? ";
					lstValues.add(loginName);
					tmpParFilter=parTransfer.getFilter("and",ParCompare.isOR,"c700020008",p_groupid,0);
					if(tmpParFilter!=null)
					{
						dealGroupSql=tmpParFilter.getSql();
						
						lgSql+="or (C700020049 =? "+dealGroupSql+")" ;
						lstValues.add("0");
						lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
					}

					lgSql+="))";//lgSql+="(C700020016>0 ";
					
					lgSql+=")";//lgSql="and ( ";
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}				
				values=copyArray(values,tranListToArray(lstValues));
				sql.append(lgSql);
			}
		}
		else if(action.equals(BASEACTION_QUERY.WAITDEALISNOTACT))
		{
			 lstValues=new ArrayList();
				//700020020 FlagActive 
				sql.append(" and C700020020=? "); //C700020020=1
				values=new Object[1];
				values[0]="1";
 
				String lgSql="";
				if(!loginName.equals(""))
				{
					//处理组  700020008 GroupID
					//查询用户组  分派的本组待处理的逻辑
					String dealGroupSql="";				
					try{
						lgSql="and ( ";
						lgSql+="(C700020016 is null  ";
						//C700020006 AssgineeID
						lgSql+=" and (C700020006 =? ";
						lstValues.add(loginName);
						//C700020048  CommissionerID
						lgSql+=" OR C700020048=? ";
						lstValues.add(loginName);					
						if(!p_groupid.equals(""))
						{
							//dealGroupSql="c700020008=?";
							tmpParFilter=parTransfer.getFilter("OR",ParCompare.isOR,"c700020008",p_groupid,0);
							if(tmpParFilter!=null)
							{
								dealGroupSql=tmpParFilter.getSql();
								lgSql+=dealGroupSql;
								lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
							}
						}
//						700020051 CloseBaseSamenessGroupID 同组关单的组ID
						if(!action.equals(BASEACTION_QUERY.WAITAUDITING))//审批的没有同组关单
						{
							String closeGroupid=getGroupUserCloseBaseGroupid(loginName,p_baseSchema);
							tmpParFilter=parTransfer.getFilter("or", ParCompare.isOR, "C700020051", closeGroupid,0);
							if(tmpParFilter!=null)
							{
								dealGroupSql=tmpParFilter.getSql();
								lgSql+= dealGroupSql;
								lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
							}
						}
						lgSql+="))";
						lgSql+=")";//lgSql="and ( ";
						
					}catch(Exception ex)
					{
						ex.printStackTrace();
					}				
					values=copyArray(values,tranListToArray(lstValues));
					sql.append(lgSql);
				}
		}	
		else if(action.equals(BASEACTION_QUERY.WAITDEALISACT))
		{
			 lstValues=new ArrayList();
			//700020020 FlagActive 
			sql.append(" and C700020020=? "); //C700020020=1
			values=new Object[1];
			values[0]="1";
 
			String lgSql="";
			if(!loginName.equals(""))
			{
				//处理组  700020008 GroupID
				//查询用户组  分派的本组待处理的逻辑
				String dealGroupSql="";				
				try{
					lgSql="and ( ";
					
					//已受理
					lgSql+="(C700020016>? ";
					lstValues.add("0");
					//C700020010 DealerID
					lgSql+=" and (C700020010=? ";
					lstValues.add(loginName);
					//c700020008 
					tmpParFilter=parTransfer.getFilter("and",ParCompare.isOR,"c700020008",p_groupid,0);
					if(tmpParFilter!=null)
					{
						dealGroupSql=tmpParFilter.getSql();
						//C700020049 IsGroupSnatch 主办为组，该组是否抢单？
						lgSql+="or (C700020049 =? "+dealGroupSql+")" ;
						lstValues.add("0");
						lstValues=copyArrayToList(lstValues,tmpParFilter.getArgs());
					}

					lgSql+="))";//lgSql+="(C700020016>0 ";
					
					lgSql+=")";//lgSql="and ( ";
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}				
				values=copyArray(values,tranListToArray(lstValues));
				sql.append(lgSql);
			}
		}
		
		String strSql=sql.toString();
		if(!strSql.equals(""))
		{
			parFilter=new ParFilter();
			parFilter.setSql(sql.toString());
			parFilter.setArgs(values);
		}
		return parFilter;
	}
	
	
	private Object[] tranListToArray( List lstAry)
	{
		int lstCount=0;
		if(lstAry!=null)
			lstCount=lstAry.size();
		if(lstCount==0)
			return null;
		Object[] result=new Object[lstCount];
		for(int i=0;i<lstCount;i++)
		{
			result[i]=lstAry.get(i);
		}
		return result;
	}
	/**
	 * 合并数组
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private    Object[] copyArray(Object[] obj1,Object[] obj2)
	{
		int len1=0;
		int len2=0;
		if(obj1!=null)
			len1=obj1.length;
		if(obj2!=null)
			len2=obj2.length;
		Object[] result=new Object[len1+len2];
		try{
		if(len1>0)
			System.arraycopy(obj1, 0, result, 0, len1);
		if(len2>0)
			System.arraycopy(obj2, 0, result, len1, len2);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
		
	}
	
	private    List copyArrayToList(List list,Object[] obj2)
	{
		
		if(list==null && obj2==null)
			return null;
		else if(obj2==null )
			return list;
		else
		{
			List result=list;
			if(result==null)
				result=new ArrayList();
			int aryLen=obj2.length;
			for(int i=0;i<aryLen;i++)
			{
				list.add(obj2[i]);
			}
		}
		return list;
	}
	
	public ParFilter copyFilter(ParFilter p1,ParFilter p2)
	{
		ParFilter parFilter=null;
		if(p1==null && p2==null)
			return null;
		else if(p1==null)
			return p2;
		else if(p2==null)
			return p1;
		else
		{
			
			String sql=p1.getSql()+p2.getSql();
			Object[] args=copyArray(p1.getArgs(),p2.getArgs());
			parFilter=new ParFilter();
			parFilter.setSql(sql);
			parFilter.setArgs(args);
		}
		return parFilter;
	}

}
