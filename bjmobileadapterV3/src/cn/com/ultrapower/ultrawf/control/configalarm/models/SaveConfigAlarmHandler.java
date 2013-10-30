package cn.com.ultrapower.ultrawf.control.configalarm.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.util.FormatInt;

public class SaveConfigAlarmHandler {
	
/*	  private int cfgid;                  
	  private String cfgName;                
	  private String cfgStatus;             
	  private String cfgBaseItems;           
	  private String cfgLogicType;           
	  private String cfgResponsElevel;       
	  private String cfgCloseOpsatisfaction; 
	  private String cfgCloseDesc;*/

	  public void SaveConfigAlarm(ConfigAlarmModel configAlarm){
			 //int cfgid=configAlarm.getCfgid();
			 String cfgName=configAlarm.getCfgName();
			 String cfgStatus=configAlarm.getCfgStatus();
			 String cfgBaseItems=configAlarm.getCfgBaseItems();
			 String cfgLogicType=configAlarm.getCfgLogicType();
			 String cfgResponsElevel=configAlarm.getCfgResponsElevel();
			 String cfgCloseOpsatisfaction=configAlarm.getCfgCloseOpsatisfaction();
			 String cfgCloseDesc=configAlarm.getCfgCloseDesc();
			 StringBuffer sql=new StringBuffer();
			 sql.append("insert into CONIFGALARMCLOSE(cfgid,cfgName,cfgStatus,cfgBaseItems,cfgLogicType,cfgResponsElevel,cfgCloseOpsatisfaction,cfgCloseDesc) values(");
			 sql.append("configAlarm_seq.Nextval,'").append(cfgName).append("', '").append(cfgStatus).append("','");
			 sql.append(cfgBaseItems).append("','").append(cfgLogicType).append("','").append(cfgResponsElevel).append("','").append(cfgCloseOpsatisfaction).append("','");
			 sql.append(cfgCloseDesc).append("' ");
			 sql.append(" )");
			 System.out.println(sql.toString());
			  IDataBase idb = GetDataBase.createDataBase();
			  Statement stat = idb.GetStatement();
			  
				try {
					stat.executeQuery(sql.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						if (stat != null)
							stat.close();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}				
					idb.closeConn();
				}
		  }
		  
		  public void UpdateConfigAlarm(ConfigAlarmModel configAlarm){
			  if(configAlarm!=null){
				  int cfgid=configAlarm.getCfgid();
				  String cfgName=configAlarm.getCfgName();
				  String cfgStatus=configAlarm.getCfgStatus();
				  String cfgBaseItems=configAlarm.getCfgBaseItems();
				  String cfgLogicType=configAlarm.getCfgLogicType();
				  String cfgResponsElevel=configAlarm.getCfgResponsElevel();
				  String cfgCloseOpsatisfaction=configAlarm.getCfgCloseOpsatisfaction();
				  String cfgCloseDesc=configAlarm.getCfgCloseDesc();
				  StringBuffer hql=new StringBuffer();
				  hql.append("update CONIFGALARMCLOSE set cfgName='").append(cfgName).append("', cfgStatus='").append(cfgStatus).append("',");
				  hql.append(" cfgBaseItems='").append(cfgBaseItems).append("', cfgLogicType='").append(cfgLogicType).append("', cfgResponsElevel='").append(cfgResponsElevel).append("',");
				  hql.append(" cfgCloseOpsatisfaction='").append(cfgCloseOpsatisfaction);
				  hql.append("', cfgCloseDesc='").append(cfgCloseDesc);
				  hql.append("' where cfgid=").append(cfgid);
				  IDataBase idb = GetDataBase.createDataBase();
				  Statement stat = idb.GetStatement();
				  try {
						stat.executeQuery(hql.toString());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					finally
					{
						try {
							if (stat != null)
								stat.close();
						} catch (Exception ex) {
							System.err.println(ex.getMessage());
						}				
						idb.closeConn();
					}
			  }
			  
		  }
		  
		  public int countBySql(String sql){
			  int count=0;
			  if(sql!=null && !"".equals(sql)){
				IDataBase idb = GetDataBase.createDataBase();;
				Statement stat = idb.GetStatement();
				ResultSet rs1 = idb.executeResultSet(stat, sql);
				try {
					if(rs1.next()){
						count=FormatInt.FormatStringToInt(rs1.getString(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						if (rs1 != null)
							rs1.close();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}
					try {
						if (stat != null)
							stat.close();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}				
					idb.closeConn();
				}
			  }
			  return count;
		  }
	
		  public void DeleteConfigAlarm(String cifid){
			  StringBuffer hql=new StringBuffer();
	   	   	  hql.append(" delete from CONIFGALARMCLOSE where cfgid in (").append(cifid).append(")");
	   	      IDataBase idb = GetDataBase.createDataBase();
			  Statement stat = idb.GetStatement();
			  try {
					stat.executeUpdate(hql.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						if (stat != null)
							stat.close();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}				
					idb.closeConn();
				}
		  }
		  
			
		  /*	  private int cfgid;                  
		  	  private String cfgName;                
		  	  private String cfgStatus;             
		  	  private String cfgBaseItems;           
		  	  private String cfgLogicType;           
		  	  private String cfgResponsElevel;       
		  	  private String cfgCloseOpsatisfaction; 
		  	  private String cfgCloseDesc;*/
		  
		  /**
		   * 根据ID查找权限配置信息
		   * @param id
		   * @return
		   */
		  public ConfigAlarmModel getConfigAlarmModel(String cfgid){
			  ConfigAlarmModel cfgAlarmModel=new ConfigAlarmModel();
			  StringBuffer hql=new StringBuffer();
			  hql.append("select * from CONIFGALARMCLOSE where cfgid='").append(cfgid).append("'");
			  IDataBase idb = GetDataBase.createDataBase();
			  Statement stat = idb.GetStatement();
			  ResultSet rs1 = idb.executeResultSet(stat, hql.toString());
			  try {
					if(rs1.next()){
						cfgAlarmModel.setCfgid(rs1.getInt("cfgid"));
						cfgAlarmModel.setCfgName(rs1.getString("cfgName"));
						cfgAlarmModel.setCfgStatus(rs1.getString("cfgStatus"));
						cfgAlarmModel.setCfgBaseItems(rs1.getString("cfgBaseItems"));
						cfgAlarmModel.setCfgLogicType(rs1.getString("cfgLogicType"));
						cfgAlarmModel.setCfgResponsElevel(rs1.getString("cfgResponsElevel"));
						cfgAlarmModel.setCfgCloseOpsatisfaction(rs1.getString("cfgCloseOpsatisfaction"));
						cfgAlarmModel.setCfgCloseDesc(rs1.getString("cfgCloseDesc"));
					  }
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						rs1.close();
						if (stat != null)
							stat.close();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}				
					idb.closeConn();
				}
				return cfgAlarmModel;
		  }
		  
}
