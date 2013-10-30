package cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

/**
 * 日期 2007-1-18
 * 
 * @author xuquanxing
 */
public class FileSubscribeTreeOperation
{
	//	从配置文件中取表名
	GetFormTableName getFormTableName		= new GetFormTableName();
	private String roleskill				= getFormTableName.GetFormName("RemedyTrole");
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");
	private String sourceconfig				= getFormTableName.GetFormName("sourceconfig");
	private String rolemanagetable		 	= getFormTableName.GetFormName("RemedyTrolesmanage");
	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @return String 当用户是Demo时取得根节点
	 */
	public String getDemoFileSourceRootTree()
	{
		StringBuffer resultbuff = new StringBuffer();
		resultbuff.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		resultbuff.append("try{");
		String sql = "select source.source_cnname,source.source_id from Sourceconfig source where  source.source_name ='ziliao'";
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res = dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(res.next())
			{
				try
				{
					String sourcname = res.getString("source_cnname");//取得资源所对应的中文名
					String sourceid = res.getString("source_id");
					resultbuff.append("tree.add(new WebFXLoadTreeItem(\"" + sourcname + "\",\"filesubscribenextxml.jsp?gid=" + sourceid + "\",\"\",\"\",\"\",\"\",\"\",\"\",\"" + sourcname + ":"+ sourceid + "\"));");
				
					//调用短信接口，将该记录插入smsorder表中
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}

		resultbuff.append("}catch(e){}");
		resultbuff.append("</script>");
		resultbuff.append("<script>document.write(tree);</script>");
		return resultbuff.toString();
	}

	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @return String
	 * @param Long
	 *            id 当用户是Demo时取得子节点
	 */
	public String getDemoSonFileSourceRootTree(Long id)
	{
		StringBuffer resultbuff = new StringBuffer();
		// String sql = " from Sourceconfig a where a.sourceType like '%0;%' and
		// a.sourceModule=(select a.sourceModule from Sourceconfig a where
		// a.sourceParentid="+id+") ";
		System.out.println("3-------------------------------");
		String sql = "select source.source_cnname,source.source_id from sourceconfig source where source.source_parentid='"+id+"'";
		System.out.println("4-------------------------------"+sql);
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res = dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(res.next())
			{
				try
				{
					String sourcname 	= res.getString("source_cnname");//取得资源所对应的中文名
					String sourceid 	= res.getString("source_id");
					
					resultbuff.append("<tree text=\"" + sourcname + "\" src=\"filesubscribenextxml.jsp?gid=" + sourceid + "\"  schkbox=\"" + sourcname + ":" + sourceid + "\"/>");
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}

		return resultbuff.toString();
	}

	public String getFileSourceRootTree(String userid)
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer resultbuff = new StringBuffer();
		resultbuff.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		resultbuff.append("try{");
		//String sql = "select distinct  a.sourceCnname,a.sourceId from Sourceconfig a,SysSkillpo b where b.c610000007='" + userid + "' and b.c610000008=a.sourceId and a.sourceName ='ziliao' and a.sourceType like '%0;%'";
		//		如果传入的字符串为空
		if(userid.equals("")||String.valueOf(userid).equals("null"))
		{
			return "";
		}
		//from Sourceconfig a,SysSkillpo b where b.c610000007='" + userid + "' and b.c610000008=a.sourceId and a.sourceName ='ziliao' and a.sourceType like '%0;%'"
		sql.append("select * from (");
		sql.append("select distinct a.source_id,a.source_parentid,a.source_cnname,a.source_orderby from "+ sourceconfig +" a,"+ roleskill +" b");
		sql.append(" where a.source_id = b.C610000008 and b.c610000007='"+userid+"'");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" union ");
		sql.append("select distinct a.source_id,a.source_parentid,a.source_cnname,a.source_orderby from "+ sourceconfig +" a,");
		sql.append(""+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser,");
		sql.append( rolemanagetable + " rolemanagetable");
		sql.append(" where a.source_id = RemedyTrolesskillmanage.C660000007");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028");
		sql.append(" and RemedyTrolesusergrouprel.C660000027=RemedyTgroupuser.C620000027 and RemedyTgroupuser.C620000028='"+userid+"'");
		sql.append(" and (RemedyTrolesusergrouprel.C660000026 is null or RemedyTrolesusergrouprel.C660000026='"+userid+"')");
		sql.append(" and rolemanagetable.c1=RemedyTrolesusergrouprel.c660000028");	
		sql.append(") alldatatable");
		sql.append(" where 1=1 and not exists ");
		sql.append("(");
		sql.append("select distinct a.source_id from "+ sourceconfig +" a,"+ roleskill +" b");
		sql.append(" where a.source_id = b.C610000008 and b.c610000007='"+userid+"'");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" and a.source_id=alldatatable.source_parentid)");
		sql.append(" and not exists( ");
		sql.append("select distinct a.source_id from "+ sourceconfig +" a,");
		sql.append(""+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser,");
		sql.append( rolemanagetable + " rolemanagetable");
		sql.append(" where a.source_id = RemedyTrolesskillmanage.C660000007");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028");
		sql.append(" and RemedyTrolesusergrouprel.C660000027=RemedyTgroupuser.C620000027 and RemedyTgroupuser.C620000028='"+userid+"'");
		sql.append(" and (RemedyTrolesusergrouprel.C660000026 is null or RemedyTrolesusergrouprel.C660000026='"+userid+"')");
		sql.append(" and rolemanagetable.c1=RemedyTrolesusergrouprel.c660000028");
		sql.append(" and a.source_id=alldatatable.source_parentid");
		sql.append(") order by source_orderby");

		System.out.println(sql.toString());
//		if(type.equals("1"))
//		{
//			sql.append(" and a.source_type like '%1;%'");
//		}
//		sql.append(" and b.c610000018='0'");
//		sql.append(" order by source_id,source_parentid");
		
		
		String foldercnname    = "";
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res = dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(res.next())
			{
				try
				{
					String sourcname = res.getString("source_cnname");//取得资源所对应的中文名
					String sourceid = res.getString("source_id");
					resultbuff.append("tree.add(new WebFXLoadTreeItem(\"" + sourcname + "\",\"filesubscribenextxml.jsp?gid=" + sourceid + "\",\"\",\"\",\"\",\"\",\"\",\"\",\"" + sourcname + ":"+ sourceid + "\"));");
				
					//调用短信接口，将该记录插入smsorder表中
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}

		resultbuff.append("}catch(e){}");
		resultbuff.append("</script>");
		resultbuff.append("<script>document.write(tree);</script>");
		return resultbuff.toString();
	}

	public String getSonFileSourceRootTree(String userid,Long id)
	{
		StringBuffer resultbuff = new StringBuffer();
		StringBuffer sql 		= new StringBuffer();
		sql.append("select distinct a.source_id,a.source_parentid,a.source_cnname,a.source_orderby from "+ sourceconfig +" a,"+ roleskill +" b");
		sql.append(" where a.source_id = b.C610000008 and b.c610000007='"+userid+"'");
		sql.append(" and a.source_parentid = '"+id+"'");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" union ");
		sql.append("select distinct a.source_id,a.source_parentid,a.source_cnname,a.source_orderby from "+ sourceconfig +" a,");
		sql.append(""+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser,");
		sql.append( rolemanagetable + " rolemanagetable");
		sql.append(" where a.source_id = RemedyTrolesskillmanage.C660000007");
		sql.append(" and a.source_parentid = '"+id+"'");
		sql.append(" and a.source_type like '%7;%'");
		sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028");
		sql.append(" and RemedyTrolesusergrouprel.C660000027=RemedyTgroupuser.C620000027 and RemedyTgroupuser.C620000028='"+userid+"'");
		sql.append(" and (RemedyTrolesusergrouprel.C660000026 is null or RemedyTrolesusergrouprel.C660000026='"+userid+"')");
		sql.append(" and rolemanagetable.c1=RemedyTrolesusergrouprel.c660000028");
		sql.append(" order by source_orderby");
		System.out.println(sql.toString());
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet res		= null;
		stm	= dataBase.GetStatement();
		res = dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(res.next())
			{
				try
				{
					String sourcname 	= res.getString("source_cnname");//取得资源所对应的中文名
					String sourceid 	= res.getString("source_id");
					
					resultbuff.append("<tree text=\"" + sourcname + "\" src=\"filesubscribenextxml.jsp?gid=" + sourceid+ "\"  schkbox=\"" + sourcname + ":" + sourceid + "\"/>");
				}catch(Exception e)
				{
					System.out.println("发生异常");
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return resultbuff.toString();
	}
}
