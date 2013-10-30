package cn.com.ultrapower.ultrawf.models.queryinterface;

import cn.com.ultrapower.ultrawf.models.process.QueryFilterTemplet;
import cn.com.ultrapower.ultrawf.share.BASEACTION_QUERY;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.table.*;
import cn.com.ultrapower.system.table.par.*;
import cn.com.ultrapower.system.util.*;
public class BaseQuery {
	
	private int allRowCount=0;
	public int getAllRowCount()
	{
		return allRowCount;
	}
	private int pageCount=0;
	public int getPageCount()
	{
		return pageCount;
	}
	/**
	 *  
	 * @param sortFileds
	 * @param sortModel 0升序 1倒序
	 * @return
	 */
	public String getOrderby(String sortFileds,int sortModel)
	{
		String strRe="";
		if(!sortFileds.trim().equals(""))
		{
			//如果升序
			if(sortModel==0)
				strRe=" order by "+sortFileds;
			else
			{
				String[] strAry =sortFileds.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+=" order by "+ strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				//strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return strRe;	
	}
	
	public RowSet getBaseInfo(String tableName, String filedids, String relate,
			String extendBy, String action, ParRow parRow, int curpage,
			int pageSize, int isCount)
	{
		RowSet rowSet=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		DataBaseDAO queryDAO=new DataBaseDAO(m_dbConsole,tableName);
		if(parRow==null)
			parRow=new ParRow();
		if(!action.equals(""))
		{
			ParFilter parFilter=new QueryFilterTemplet().getBaseFilterTemplet(action,"","","",null);
			parRow.setFilter(action, parFilter);
		}
		try{
			rowSet=queryDAO.getRows(filedids,relate,parRow,extendBy,curpage,pageSize,isCount);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		allRowCount=queryDAO.getRowCount();
		calculatePages(allRowCount,pageSize);
		
		return rowSet;		
	}
	
	
	public RowSet getBaseAndProcessForSysuer(String filedids, String extendBy,
			String baseSchema, String loginName, ParRow parRow, int curpage,
			int pageSize, int isCount)
	{
		RowSet rowSet=null;
		
		
		return rowSet;
		
	}
	
	
	
	/**
	 * 待办信息查询（待处理、待审批、待处理和待审批）
	 * @param filedids
	 * @param extendBy
	 * @param action
	 * @param baseSchema
	 * @param loginName
	 * @param groupid
	 * @param parRow
	 * @param curpage
	 * @param pageSize
	 * @param isCount
	 * @return
	 */
	public RowSet getWaitDeal(String filedids, String extendBy, String action,
			String baseSchema, String loginName, String groupid, ParRow parRow,
			int curpage, int pageSize, int isCount)
	{
		RowSet rowSet=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		action=FormatString.CheckNullString(action);
		groupid=FormatString.CheckNullString(groupid);
		String tblBase="";
		if(baseSchema.equals(""))
			tblBase=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
		else
			tblBase=m_RemedyDBOp.GetRemedyTableName(baseSchema);
		String tblProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
		String tblAuditing=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
		DataBaseDAO queryDAO=null;
		if(parRow==null)
			parRow=new ParRow();
		
		ParFilter pf=parRow.getFilter("ACTION");
		if(pf==null)
		{
			String strAction=getWaitDealAction(action);
			if(!strAction.equals(""))
			{
				//700020019 FlagType 
				parRow.setFilter(ParCompare.isOR,"ACTION","c700020019",strAction,4);
			}
		}
		
		//待办查询
		if (action.equals(BASEACTION_QUERY.WAITDEALALL)
				|| action.equals(BASEACTION_QUERY.WAITDEALISACT)
				|| action.equals(BASEACTION_QUERY.WAITDEALISNOTACT)
				|| action.equals(BASEACTION_QUERY.WAITAUDITING)
				) 
		{
			ParFilter parFilter2 = new QueryFilterTemplet().getBaseFilterTemplet(action, baseSchema, loginName,groupid, null);
			if(parFilter2!=null)
				parRow.setFilter("WAITDEAL", parFilter2);
			
			if (BASEACTION_QUERY.WAITAUDITING.equals(action))
				queryDAO = new DataBaseDAO(m_dbConsole, tblBase + " base,"	+ tblAuditing + " dp");
			else
				queryDAO = new DataBaseDAO(m_dbConsole, tblBase + " base,"	+ tblProcess + " dp");
			String relate="";
			if("".equals(baseSchema)){//查询所有工单；
				relate="base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002 ";
			}
			else{//查询具体的工单； 
				relate="base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002";
			}
			try{
			rowSet=queryDAO.getRows(filedids,relate,parRow,extendBy,curpage,pageSize,isCount);
			}catch(Exception ex)
			{
				ex.printStackTrace(); 
			}
			allRowCount=queryDAO.getRowCount();
			calculatePages(allRowCount,pageSize);			
			
		} else if (action.equals(BASEACTION_QUERY.WAITDEALANDAUDITING)) 
		{
			ParFilter parFilter2 ;
			ParFilter unPF;
			Object[] args=null;
			//待处理和待审批
			StringBuffer sql=new StringBuffer();
			
			//StringBuffer sqlWait=new StringBuffer();
			
			QueryFilterTemplet queryFilterTemplet=new QueryFilterTemplet(); 
			sql.append("select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase+" base,"+tblProcess+" dp ");
			
			if(baseSchema.equals("")){//查询所有工单；
				sql.append(" where base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			//待处理
			parFilter2 = (queryFilterTemplet.getBaseFilterTemplet(BASEACTION_QUERY.WAITDEALALL, baseSchema, loginName,groupid, null));
			if(parFilter2!=null)
			{
				sql.append(parFilter2.getSql());
				args=ArrayTransfer.CopyArray(args,parFilter2.getArgs());
			}
			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());
			
			sql.append(" union all " );
			//待审批
			sql.append(" select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase+" base,"+tblAuditing+" dp ");
			
			if(baseSchema.equals("")){//查询所有工单；
				sql.append(" where base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002");
			} 
			//待处理
			parFilter2 = (queryFilterTemplet.getBaseFilterTemplet(BASEACTION_QUERY.WAITAUDITING, baseSchema, loginName,groupid, null));
			if(parFilter2!=null)
			{
				sql.append(parFilter2.getSql());
				args=ArrayTransfer.CopyArray(args,parFilter2.getArgs());
			}
			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());			
			
			queryDAO = new DataBaseDAO(m_dbConsole, "");
			String strSQL=sql.toString();
			strSQL=strSQL.replaceAll("SFIEDSS", " count(*) rowcount");
			if(isCount>0)
			{
				strSQL=" select sum(rowcount) rowcount from ("+strSQL+") t";
				rowSet=queryDAO.executeQuery(strSQL, args, 0,0,isCount);
				allRowCount=0;
				if(rowSet!=null)
				{
					allRowCount=rowSet.get(0).getInt("rowcount");
					calculatePages(allRowCount,pageSize);
				}
			}
			if(isCount>0 && allRowCount<1  )
			{
				rowSet=null;
			}
			else
			{
				if(isCount!=9)//9 只做数量计算
				{
					strSQL=sql.toString();
					String cFiledids=filedids;

					strSQL=strSQL.replaceFirst("SFIEDSS", cFiledids);
					//因为审批环节没有 DealOverTimeDate处理时限的字段 所以用0取代
					cFiledids=cFiledids.replace("c700020014", "0");//审批没有 处理时限 
					cFiledids=cFiledids.replace("c700020052", "0");//Flag32IsToTransfer
					strSQL=strSQL.replaceFirst("SFIEDSS", cFiledids);
					strSQL+=extendBy;
					rowSet=queryDAO.executeQuery(strSQL, args,curpage,pageSize,isCount);
				}
			}
		}
		
		return rowSet;
	}
	
	
	/**
	 * 工单信息和日志查询
	 * @param filedids
	 * @param extendBy 
	 * @param action 动作
	 * @param baseSchema 工单类别
	 * @param loginName 处理人
	 * @param groupid 处理组
	 * @param parRow
	 * @param curpage
	 * @param pageSize
	 * @param isCount
	 * @return
	 */
	public RowSet getBaseAndLog(String filedids,String groupby, String extendBy,
			String action, String baseSchema, String loginName, String groupid,
			ParRow parRow, int curpage, int pageSize, int isCount)
	{
		groupby=FormatString.CheckNullString(groupby);
		
		RowSet rowSet=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		
		if(parRow==null)
			parRow=new ParRow();
		loginName=FormatString.CheckNullString(loginName);
		groupid=FormatString.CheckNullString(groupid);
		
		StringBuffer sql;
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblBase="";
		ParFilter tmpParFilter;
		ParFilter unPF;
		Object[] args=null;
		if(baseSchema.equals(""))
			tblBase=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
		else
			tblBase=m_RemedyDBOp.GetRemedyTableName(baseSchema);
		
		ParTransfer parTransfer=new ParTransfer();
		DataBaseDAO queryDAO = new DataBaseDAO(m_dbConsole, "");
		/**
		 * 工单信息和LOG信息
		 */
		if(action.equals(BASEACTION_QUERY.BASEANDDPLOG)||action.equals(BASEACTION_QUERY.BASEANDAPLOG))
		{
			String tblDPLog="";
			if(action.equals(BASEACTION_QUERY.BASEANDAPLOG))
				tblDPLog=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);
			else
			    tblDPLog=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
		   
			//String tblAPLog=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);
			String tblSysuser=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysUser);
			
			sql=new StringBuffer();
			sql.append("select ");
			sql.append(" SFIEDSS ");
			if(!groupid.equals(""))
			{
				sql.append(" from "+tblBase+" base,"+tblDPLog+" dpl,"+tblSysuser+" sysuser ");
			}
			else
				sql.append(" from "+tblBase+" base,"+tblDPLog+" dpl ");
			
			if(baseSchema.equals("")){//查询所有工单；
			{
//				700020407 ProcessLogBaseID 700020408 ProcessLogBaseSchema
				sql.append(" where base.c700000000 = dpl.c700020407 and base.c700000001 = dpl.c700020408");
			}
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1=dpl.c700020407 and base.c700000001= dpl.c700020408");
			}
			if(!groupid.equals(""))
			{
				sql.append(" and dpl.c700020404=sysuser.c630000001 ");//c630000001 User_LoginName
			}
			//700020404 logUserID 记录的用户登陆名：
			if(!groupid.equals(""))
			{
				String groupid2=groupid.replaceAll(",", ";,");
				//630000036 User_BelongGroupID 所属组ID 
				tmpParFilter=parTransfer.getFilter(ParCompare.isLike,"c630000036",groupid2,0);
				parRow.setFilter("DEALGROUP1", tmpParFilter);
			}
			else if(!loginName.equals(""))
			{
				//700020404 logUserID 记录的用户登陆名：
				tmpParFilter=parTransfer.getFilter(ParCompare.isOR,"C700020404",loginName,0);
				parRow.setFilter("LOGUSER1", tmpParFilter);				
			}

			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());
			args=unPF.getArgs();
			
			sql.append(groupby);
			
			String strSQL=sql.toString();
			if(groupby.equals(""))
				strSQL=strSQL.replaceAll("SFIEDSS", " count(*) rowcount");
			else
			{
				strSQL=strSQL.replaceAll("SFIEDSS", " 1 rowcount");
				strSQL="select sum(rowcount) rowcount from ("+strSQL+") B";
			}
			
			if(isCount>0)
			{
				rowSet=queryDAO.executeQuery(strSQL, args, 0,0,isCount);
				allRowCount=0;
				if(rowSet!=null)
				{
					allRowCount=rowSet.get(0).getInt("rowcount");
					calculatePages(allRowCount,pageSize);
				}
			}
			if(isCount>0 && allRowCount<1 )
			{
				rowSet=null;
			}
			else
			{
				strSQL=sql.toString();
				strSQL=strSQL.replaceAll("SFIEDSS", filedids);
				strSQL+=extendBy;
				rowSet=queryDAO.executeQuery(strSQL, args,curpage,pageSize,isCount);
			}
		}
		else if(action.equals(BASEACTION_QUERY.BASEANDDPAPLOG))
		{
			String tblDPLog=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
			String tblAPLog=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);
			String tblSysuser=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysUser);
			
			sql=new StringBuffer();
			sql.append("select ");
			sql.append(" SFIEDSS ");
			if(!groupid.equals(""))
			{
				sql.append(" from "+tblBase+" base,"+tblDPLog+" dpl,"+tblSysuser+" sysuser ");
			}
			else
				sql.append(" from "+tblBase+" base,"+tblDPLog+" dpl ");
			
			if(baseSchema.equals("")){//查询所有工单；
			{
//				700020407 ProcessLogBaseID 700020408 ProcessLogBaseSchema
				sql.append(" where base.c700000000 = dpl.c700020407 and base.c700000001 = dpl.c700020408");
			}
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1=dpl.c700020407 and base.c700000001= dpl.c700020408");
			}
			if(!groupid.equals(""))
			{
				sql.append(" and dpl.c700020404=sysuser.c630000001 ");//c630000001 User_LoginName
			}
			//700020404 logUserID 记录的用户登陆名：
			if(!loginName.equals(""))
			{
				//700020404 logUserID 记录的用户登陆名：
				tmpParFilter=parTransfer.getFilter(ParCompare.isOR,"c700020404",loginName,0);
				sql.append(tmpParFilter.getSql());
				args=ArrayTransfer.CopyArray(args,tmpParFilter.getArgs());
				//parRow.setFilter("DEALUSER1", tmpParFilter);				
			}			
			else if(!groupid.equals(""))
			{
				String groupid2=groupid.replaceAll(",", ";,");
				//630000036 User_BelongGroupID 所属组ID 
				tmpParFilter=parTransfer.getFilter(ParCompare.isLike,"c630000036",groupid2,0);
				sql.append(tmpParFilter.getSql());
				args=ArrayTransfer.CopyArray(args,tmpParFilter.getArgs());
			}

			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());
			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());
			sql.append(groupby);
			
			sql.append(" union all ");
			
			
			sql.append("select ");
			sql.append(" SFIEDSS ");
			if(!groupid.equals(""))
			{
				sql.append(" from "+tblBase+" base,"+tblAPLog+" apl,"+tblSysuser+" sysuser ");
			}
			else
				sql.append(" from "+tblBase+" base,"+tblAPLog+" apl ");
			
		   if(baseSchema.equals(""))
		   {
			   //查询所有工单；
			   // 700020407 ProcessLogBaseID 700020408 ProcessLogBaseSchema
			   sql.append(" where base.c700000000 = apl.c700020407 and base.c700000001 = apl.c700020408");
			}
			else
			{//查询具体的工单； 
				sql.append(" where base.c1=apl.c700020407 and base.c700000001= apl.c700020408");
			}//if(baseSchema.equals(""))
		   
			if(!groupid.equals(""))
			{
				sql.append(" and apl.c700020404=sysuser.c630000001 ");//c630000001 User_LoginName
			}
			//700020404 logUserID 记录的用户登陆名：
			if(!loginName.equals(""))
			{
				//700020404 logUserID 记录的用户登陆名：
				tmpParFilter=parTransfer.getFilter(ParCompare.isOR,"c700020404",loginName,0);
				if(tmpParFilter!=null)
				{
					sql.append(tmpParFilter.getSql());
					args=ArrayTransfer.CopyArray(args,tmpParFilter.getArgs());
				}
			}			

			else if(!groupid.equals(""))
			{
				String groupid2=groupid.replaceAll(",", ";,");
				//630000036 User_BelongGroupID 所属组ID 
				tmpParFilter=parTransfer.getFilter(ParCompare.isLike,"c630000036",groupid2,0);
				if(tmpParFilter!=null)
				{
					sql.append(tmpParFilter.getSql());
					args=ArrayTransfer.CopyArray(args,tmpParFilter.getArgs());
				}				
			}

			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());
			sql.append(groupby);
			
			String strSQL=sql.toString();
			if(groupby.equals(""))
				strSQL=strSQL.replaceAll("SFIEDSS", " count(*) rowcount");
			else
				strSQL=strSQL.replaceAll("SFIEDSS", " 1 rowcount");
			strSQL="select sum(rowcount) rowcount from ("+strSQL+") t";
			if(isCount>0)
			{
				rowSet=queryDAO.executeQuery(strSQL, args, 0,0,isCount);
				allRowCount=0;
				if(rowSet!=null)
				{
					allRowCount=rowSet.get(0).getInt("rowcount");
					calculatePages(allRowCount,pageSize);
				}
			}
			if(isCount>0 && allRowCount<1)
			{
				rowSet=null;
			}
			else
			{
		 
				strSQL=sql.toString();
				strSQL=strSQL.replaceAll("SFIEDSS", filedids);
				strSQL+=extendBy;
				rowSet=queryDAO.executeQuery(strSQL, args,curpage,pageSize,isCount);	
			}
		}
		else
		{
			rowSet=null;
		}//if(action.equals(BASEACTION_QUERY.BASEANDDPLOG)||action.equals(BASEACTION_QUERY.BASEANDAPLOG))
		
		return rowSet;
	}

	/**
	 *公司或部部门所有的待办
	 * @param filedids
	 * @param baseSchema
	 * @param departmentid
	 * @param extendBy
	 * @param parRow
	 * @param curpage
	 * @param pageSize
	 * @param isCount
	 * @return
	 */
	public RowSet getWaitDepartment(String action,String filedids,String baseSchema,String corpid, String extendBy,ParRow parRow, int curpage, int pageSize, int isCount)
	{
		RowSet rowSet=null;
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		DataBaseDAO queryDAO=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		ParTransfer parTransfer=new ParTransfer();
		if(parRow==null)
			parRow=new ParRow();
		String tblBase="";
		corpid=FormatString.CheckNullString(corpid);
		if(corpid.equals(""))
			return null;
		if(baseSchema.equals(""))
			tblBase=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
		else
			tblBase=m_RemedyDBOp.GetRemedyTableName(baseSchema);
		String tblProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
		//String tblAuditing=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
		
 
		parRow.setFilter(ParCompare.isEquals, "waitdeal","dp.c700020020","1",4);
		
		if (action.equals(BASEACTION_QUERY.BASEANDPROCESS)) 
		{
			StringBuffer sql=new StringBuffer();
			queryDAO = new DataBaseDAO(m_dbConsole, tblBase + " base,"	+ tblProcess + " dp");
			String relate="";
			sql.append("select ");
		}
		else
		{
			StringBuffer sql=new StringBuffer();
			
			String tblSysgroup=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysGroup);
			String tblSysuser=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysUser);
			
			sql=new StringBuffer();
			sql.append("select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase + " base,"	+ tblProcess + " dp,"+tblSysuser+" sysuser ");
			sql.append(" where  base.c700000000 = dp.c700020001 and  base.c700000001 = dp.c700020002");
			sql.append(" and dp.c700020006 = sysuser.c630000001 ");
			
			ParFilter parFilter=parTransfer.getFilter(ParCompare.isOR,"sysuser.c630000013",corpid,4);
			
			sql.append(parFilter.getSql());
			Object[] args=parFilter.getArgs();
			
			ParFilter unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());			
			
			//sql.append(extendBy);
			
			sql.append(" union all ");
			
			sql.append(" select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase + " base,"	+ tblProcess + " dp,"+tblSysgroup+" sysgroup ");
			sql.append(" where  base.c700000000 = dp.c700020001 and  base.c700000001 = dp.c700020002");
			sql.append(" and dp.c700020008 = sysgroup.c630000030 ");
			
			parFilter=parTransfer.getFilter(ParCompare.isOR,"sysgroup.c630000026",corpid,4);
			sql.append(parFilter.getSql());
			args=ArrayTransfer.CopyArray(args,parFilter.getArgs());

			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());			
			
		//sql.append(extendBy);
			
			queryDAO = new DataBaseDAO(m_dbConsole, "");
			String strSQL=sql.toString();
			strSQL=strSQL.replaceAll("SFIEDSS", " count(*) rowcount");
			if(isCount>0)
			{
				strSQL=" select sum(rowcount) rowcount from ("+strSQL+") t";
				rowSet=queryDAO.executeQuery(strSQL, args, 0,0,isCount);
				allRowCount=0;
				if(rowSet!=null)
				{
					allRowCount=rowSet.get(0).getInt("rowcount");
					calculatePages(allRowCount,pageSize);
				}
			}
			if(isCount>0 && allRowCount<1  )
			{
				rowSet=null;
			}
			else
			{
				strSQL=sql.toString();
				strSQL=strSQL.replaceAll("SFIEDSS", filedids);
				strSQL+=extendBy;
				rowSet=queryDAO.executeQuery(strSQL, args,curpage,pageSize,isCount);
			}
			
		}
	
		return rowSet;
	}
	
	
	/**
	 * 工单信息和Process、Auditing联合查询
	 * @param filedids
	 * @param extendBy
	 * @param action
	 * @param baseSchema
	 * @param parRow
	 * @param curpage
	 * @param pageSize
	 * @param isCount
	 * @return
	 */
	public RowSet getBaseAndProcess(String filedids, String extendBy,
			String action, String baseSchema,ParRow parRow, int curpage, int pageSize, int isCount)
	{
		RowSet rowSet=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		action=FormatString.CheckNullString(action);
		String tblBase="";
		if(baseSchema.equals(""))
			tblBase=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
		else
			tblBase=m_RemedyDBOp.GetRemedyTableName(baseSchema);
		String tblProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
		String tblAuditing=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
		DataBaseDAO queryDAO=null;
		if(parRow==null)
			parRow=new ParRow();
		if(action.equals(""))
			action=BASEACTION_QUERY.BASEANDPROCESS;
		//待办查询
		if (action.equals(BASEACTION_QUERY.BASEANDPROCESS)) 
		{
			
			if (BASEACTION_QUERY.BASEANDAUDITING.equals(action))
				queryDAO = new DataBaseDAO(m_dbConsole, tblBase + " base,"	+ tblAuditing + " dp");
			else
				queryDAO = new DataBaseDAO(m_dbConsole, tblBase + " base,"	+ tblProcess + " dp");
			String relate="";
			if(baseSchema.equals("")){//查询所有工单；
				relate="base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002";
			}
			else{//查询具体的工单； 
				relate="base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002";
			}
			try{
			rowSet=queryDAO.getRows(filedids,relate,parRow,extendBy,curpage,pageSize,isCount);
			}catch(Exception ex)
			{
				ex.printStackTrace(); 
			}
			allRowCount=queryDAO.getRowCount();
			calculatePages(allRowCount,pageSize);			
			
		} else if (action.equals(BASEACTION_QUERY.WAITDEALANDAUDITING)) 
		{
			ParFilter unPF;
			Object[] args=null;
			//待处理和待审批
			StringBuffer sql=new StringBuffer();
			
			//StringBuffer sqlWait=new StringBuffer();
			
			sql.append("select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase+" base,"+tblProcess+" dp ");
			
			if(baseSchema.equals("")){//查询所有工单；
				sql.append(" where base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());
			
			sql.append(" union all " );
			//待审批
			sql.append(" select ");
			sql.append(" SFIEDSS ");
			sql.append(" from "+tblBase+" base,"+tblProcess+" dp ");
			
			if(baseSchema.equals("")){//查询所有工单；
				sql.append(" where base.c700000000 = dp.c700020001 and base.c700000001 = dp.c700020002");
			}
			else{//查询具体的工单； 
				sql.append(" where base.c1 = dp.c700020001 and base.c700000001 = dp.c700020002");
			} 
			unPF=parRow.UnionFilter();
			sql.append(unPF.getSql());			
			args=ArrayTransfer.CopyArray(args,unPF.getArgs());			
			
			queryDAO = new DataBaseDAO(m_dbConsole, "");
			String strSQL=sql.toString();
			strSQL=strSQL.replaceAll("SFIEDSS", " count(*) rowcount");
			if(isCount>0)
			{
				strSQL=" select sum(rowcount) rowcount from ("+strSQL+") t";
				rowSet=queryDAO.executeQuery(strSQL, args, 0,0,isCount);
				allRowCount=0;
				if(rowSet!=null)
				{
					allRowCount=rowSet.get(0).getInt("rowcount");
					calculatePages(allRowCount,pageSize);
				}
			}
			if(isCount>0 && allRowCount<1  )
			{
				rowSet=null;
			}
			else
			{
				strSQL=sql.toString();
				strSQL=strSQL.replaceAll("SFIEDSS", filedids);
				strSQL+=extendBy;
				rowSet=queryDAO.executeQuery(strSQL, args,curpage,pageSize,isCount);
			}
		}
		return rowSet;		
	}
	
	private String getWaitDealAction(String action)
	{
		String result ="";
		String strTemp="";

			if(action.equals(BASEACTION_QUERY.WAITAUDITING))
			{
				
				strTemp=Constants.WaitAuditing.trim();
				if(strTemp.equals(""))
				{
					strTemp="3";//0主办、1协办、2抄送、3审批、4质检、20、复核;
				}
							
			}
			else if(action.equals(BASEACTION_QUERY.WAITCONFIRM))
			{
				strTemp=Constants.WaitConfirmAction.trim();
				if(strTemp.equals(""))
				{
					strTemp="2";//0主办、1协办、2抄送、3审批、4质检、20、复核;
				}	
			}		
			else
			{
				strTemp=Constants.WaitDealAction.trim();
				if(strTemp.equals(""))
				{
					strTemp="0,1,4";//0主办、1协办、2抄送、3审批、4质检、20、复核;
				}
				
			}
			result=strTemp;
		return result;
	}
	
	/**
	 * 计算总页数

	 * @param p_RusultRows
	 * @param p_StepRow
	 */
	private void calculatePages(int allRows,int pageSize)
	{
		int intPages=0;
		if(allRows>0 && pageSize>0)
		{
			intPages=allRows/pageSize;
			if(allRows%pageSize>0)
				intPages++;
		}
		pageCount=intPages;
	}

}
