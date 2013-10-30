package cn.com.ultrapower.ultrawf.control.configalarm.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class AutoQualityConfigHandler {
	

	  /**
	   * 根据ID查找配置信息
	   * @param id
	   * @return
	   */
	  public AutoQualityConfigModel getAutoQualityConfig(String id){
		  AutoQualityConfigModel configModel=new AutoQualityConfigModel();
		  StringBuffer hql=new StringBuffer();
		  hql.append("select * from configAutoQuality where cfg_id='").append(id).append("'");
		  IDataBase idb = GetDataBase.createDataBase();
		  Statement stat = idb.GetStatement();
		  ResultSet rs = idb.executeResultSet(stat, hql.toString());
		  try {
				if(rs.next()){
					configModel.setCfgId(rs.getInt("cfg_id"));
					configModel.setCfgName(rs.getString("cfg_name"));
					configModel.setCfgStatus(rs.getString("cfg_status"));
					configModel.setCfgBaseSchema(rs.getString("cfg_baseschema"));
					configModel.setCfgBaseName(rs.getString("cfg_basename"));
					configModel.setCfgComplainType(rs.getString("cfg_complaintype"));
					configModel.setCfgBasePriority(rs.getString("cfg_basepriority"));
					configModel.setCfgDealOutTime(rs.getString("cfg_dealouttime"));
					configModel.setCfgUserLoginName(rs.getString("cfg_userloginname"));
					configModel.setCfgDesc(rs.getString("cfg_desc"));
				  }
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					rs.close();
					if (stat != null)
						stat.close();
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}				
				idb.closeConn();
			}
			return configModel;
	  }
	  public void insertAutoQualityConfig(AutoQualityConfigModel configModel){
			String cfgName = configModel.getCfgName();
			String cfgStatus = configModel.getCfgStatus();
			String cfgBaseSchema = configModel.getCfgBaseSchema();
			String cfgBaseName = configModel.getCfgBaseName();
			String cfgComplainType = configModel.getCfgComplainType();
			String cfgBasePriority = configModel.getCfgBasePriority();
			String cfgDealOutTime = configModel.getCfgDealOutTime();
			String cfgUserLoginName = configModel.getCfgUserLoginName();
			String cfgDesc=configModel.getCfgDesc();
			StringBuffer sql=new StringBuffer();
			sql.append(" insert into configAutoQuality(cfg_id,cfg_name,cfg_status,cfg_baseschema,cfg_basename,");
			sql.append("cfg_complaintype,cfg_basepriority,cfg_dealouttime,cfg_userloginname,cfg_desc) values(");
			sql.append(" configquality_seq.nextval,'").append(cfgName).append("', '").append(cfgStatus).append("','");
			sql.append(cfgBaseSchema).append("','").append(cfgBaseName).append("','").append(cfgComplainType).append("','");
			sql.append(cfgBasePriority).append("','").append(cfgDealOutTime).append("','");
			sql.append(cfgUserLoginName).append("','").append(cfgDesc).append("')");
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
		  
		  public void updateAutoQualityConfig(AutoQualityConfigModel configModel){
			  if(configModel!=null){
				  long    cfgid=configModel.getCfgId();
				  String cfgName=configModel.getCfgName();
				  String cfgStatus=configModel.getCfgStatus();
				  String cfgBaseSchema=configModel.getCfgBaseSchema();
				  String cfgBaseName=configModel.getCfgBaseName();
				  String cfgComplainType=configModel.getCfgComplainType();
				  String cfgBasePriority=configModel.getCfgBasePriority();
				  String cfgDealOutTime=configModel.getCfgDealOutTime();
				  String cfgUserLoginName = configModel.getCfgUserLoginName();
				  String cfgDesc=configModel.getCfgDesc();
				  StringBuffer hql=new StringBuffer();
				  hql.append(" update configAutoQuality set cfg_name='").append(cfgName);
				  hql.append("', cfg_status='").append(cfgStatus);
				  hql.append("', cfg_baseschema='").append(cfgBaseSchema);
				  hql.append("', cfg_basename='").append(cfgBaseName);
				  hql.append("', cfg_complaintype='").append(cfgComplainType);
				  hql.append("', cfg_basepriority='").append(cfgBasePriority);
				  hql.append("', cfg_dealouttime='").append(cfgDealOutTime);
				  hql.append("', cfg_userloginname='").append(cfgUserLoginName);
				  hql.append("', cfg_desc='").append(cfgDesc);
				  hql.append("'  where cfg_id=").append(cfgid);
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
		  
	
		  public void deleteAutoQualityConfig(String id){
			  StringBuffer hql=new StringBuffer();
	   	   	  hql.append(" delete from configAutoQuality where cfg_id in (").append(id).append(")");
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
		  
}
