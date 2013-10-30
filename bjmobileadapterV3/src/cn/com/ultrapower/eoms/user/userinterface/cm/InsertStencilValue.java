package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.config.menu.aroperationdata.ArMenu;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.InsertGrandValue;
import cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata.RolesSkillManageAssociate;
public class InsertStencilValue {

	/**
	 * date 2007-2-28
	 * author shigang
	 * @param args
	 * @return void
	 */
	GetFormTableName getFormTableName	= new GetFormTableName();
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	  static final Logger logger = (Logger) Logger.getLogger(InsertStencilValue.class);
	GetFormTableName  tablename = new GetFormTableName();
	String            driverurl = tablename.GetFormName("driverurl");
	String                 user = tablename.GetFormName("user");
	String             password = tablename.GetFormName("password");
	int              serverport = Integer.parseInt(tablename.GetFormName("serverport"));
	String 			  TBLName   = tablename.GetFormName("rolesskillmanage");
	
	ArEdit Ar=new ArEdit(user,password,driverurl,serverport);
	
	public boolean Stencil(String parentID,String childID) throws SQLException{
		try{
		if (StencilExe(parentID,childID)){
			return false;
		}
		return true;
		}catch(Exception e){
			return false;
		}
	}
	//通过转来的parentid 求出相关的数据
	public boolean StencilExe(String parentID,String childID) throws SQLException{
		
		List skillManageList=new ArrayList();
		String sql   = "select * from "+RemedyTrolesskillmanage +" RolesSkillManage where RolesSkillManage.c660000007="+parentID;
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		//获得数据库查询结果集
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		String RoleSkill_Id="";
		String RoleSkill_Name="";
		String RoleSkill_SourceID="";
		String RoleSkill_Sourcequery="";
		String RoleSkill_Grand="";
		String RoleSkill_OrderBy="";
		String RoleSkill_Desc="";
		String RoleSkill_memo="";
		String RoleSkill_memo1="";

		while(rs.next())
		{
			

			RolesSkillManageBean RolesSkillManagebean=new RolesSkillManageBean();
			
			RoleSkill_Name			= rs.getString("c660000006");
			RoleSkill_Sourcequery	= rs.getString("c660000008");
			RoleSkill_Grand			= rs.getString("c660000009");
			RoleSkill_OrderBy		= rs.getString("c660000010");
			RoleSkill_Desc			= rs.getString("c660000011");
			RoleSkill_memo			= rs.getString("c660000012");
			RoleSkill_memo1			= rs.getString("c660000013");
			
			
			RolesSkillManagebean.setRoleSkill_Desc(RoleSkill_Desc);
			RolesSkillManagebean.setRoleSkill_Grand(RoleSkill_Grand);
			RolesSkillManagebean.setRoleSkill_memo(RoleSkill_memo);
			RolesSkillManagebean.setRoleSkill_memo1(RoleSkill_memo1);
			RolesSkillManagebean.setRoleSkill_Name(RoleSkill_Name);
			RolesSkillManagebean.setRoleSkill_OrderBy(RoleSkill_OrderBy);
			RolesSkillManagebean.setRoleSkill_SourceID(childID);
			RolesSkillManagebean.setRoleSkill_Sourcequery(RoleSkill_Grand);
		
			skillManageList.add(RolesSkillManagebean);
		}
		if(insertStencil(skillManageList,childID)==true){
			logger.info("insertStencilValue==插入ok");
			System.out.println("insertStencilValue==插入ok");
		}else{
			logger.info("insertStencilValue==插入失败");
			System.out.println("insertStencilValue==插入失败");
			return false;
		}
		return true;
	}
	//一条一条的把数据保存，用ar的api进保存
	public boolean insertStencil(List rolesSkillManagebean,String childID){
		try{
			ArrayList skillManageList=new ArrayList();
			//RolesSkillManagebean RolesSkillManagebean=new RolesSkillManagebean();
			RolesSkillManageAssociate RolesSkillManageAssociate=new RolesSkillManageAssociate();
			for(int i=0;i<rolesSkillManagebean.size();i++){
				RolesSkillManageBean RolesSkillManagebean =(RolesSkillManageBean)rolesSkillManagebean.get(i);
				Ar.ArInster(TBLName,RolesSkillManageAssociate.associateCondition(RolesSkillManagebean));
				System.out.println("insertStencil==插入1条ok!");
			}
			return true;
		}catch(Exception e){
			System.out.println("insertStencil==插入失败");
			logger.info("insertStencil==插入失败");
			return false;
		}
	}
	//把数据插入bean
	 public static ArInfo MenuBean(String strid,String strvalue,String strflag){
	    	ArInfo Info = new ArInfo();
			Info.setFieldID(strid);
			Info.setValue(strvalue);
			Info.setFlag(strflag);
			return Info;
	    }
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		InsertStencilValue insertStencilValue=new InsertStencilValue();
		insertStencilValue.Stencil("99","123123");
	}

}
