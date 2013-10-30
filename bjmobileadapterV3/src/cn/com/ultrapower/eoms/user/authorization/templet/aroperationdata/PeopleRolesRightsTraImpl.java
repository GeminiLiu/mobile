package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesAccUserBackUpBean;
import cn.com.ultrapower.eoms.user.authorization.bean.WorkflowRoleUserBackUpBean;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.FormatTime;
import cn.com.ultrapower.ultrawf.control.design.UserManager;

public class PeopleRolesRightsTraImpl {
	
	/** 
	 * 查询用户所拥有的角色
	 * @author lihongbo
	 * @param loginname
	 * @param con_loginname
	 * @return
	 */
	public List getRolesByPeople(String loginname,String con_loginname){
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct rolesusergroup.c1,peopleinfo.c630000003,groupinfo.c630000018,rolesinfo.c660000001,rolesinfo.c1,rolesusergroup.c660000026 from ");
  		sql.append( " RolesUserGroupRelpo rolesusergroup,SysPeoplepo peopleinfo,SysGrouppo groupinfo,RolesManagepo rolesinfo where");
  		sql.append( " ((peopleinfo.c1=rolesusergroup.c660000026 and groupinfo.c1=rolesusergroup.c660000027)");
  		sql.append( " or (rolesusergroup.c660000026 is null and groupinfo.c1=rolesusergroup.c660000027 and peopleinfo.c630000001='Demo'))");
  		sql.append( " and rolesusergroup.c660000028=rolesinfo.c1 and peopleinfo.c630000001='"+con_loginname+"'");
		list = HibernateDAO.queryObject(sql.toString());
		return list;
	}
	/**
	 * 角色信息移交，分四种情况：接收方角色覆盖
	 * @author lihongbo
	 * @param list
	 * @return
	 */
	public String insertInToRolesUserGroupRel(List<String> t_list,List<String> a_list,String accloginname,String traloginname,String loginname,String groupid,String tran_type,List<String> tdetailids,List<String> adetailids,String acc_type){
		String flag = "";
		boolean role_flag = false;
		boolean detail_flag = false;
		List<String> userID = new ArrayList();
		List<String> backlist  = new ArrayList(); 
		List<String> detailbacklist = new ArrayList();
		List<String> detail_backup = new ArrayList();
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		GetUserInfoList a_getUserInfoList	= new GetUserInfoList();
		GetUserInfoList b_getUserInfoList	= new GetUserInfoList();
		String a_userid = a_getUserInfoList.getUserInfoName(accloginname).getC1();
		String a_deptid = a_getUserInfoList.getUserInfoName(accloginname).getC630000015();
		UserManager usermanager = new UserManager();
		
		Set detail_set = new HashSet();
		Set role_set   = new HashSet();
		List<String> d_extends = new ArrayList();
		List<String> r_extends = new ArrayList();
		boolean d_boolean = false;
		boolean r_boolean = false;
		
		if(acc_type.equals("2")){   //接收方式为覆盖
			//移交工单细分角色
			if(adetailids.size()>0 && tdetailids.size()>0){
				detail_backup = getBackUpDetailRolesId(accloginname);
				if(tran_type.equals("2") || tran_type.equals("1")){
					//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
					if(detail_backup.size()>0){
						deleteNewDetailRoles(detail_backup);
					}
					detailbacklist = insertIntoUserDetailBackUp(accloginname,loginname);
					usermanager.removeUserGroup(accloginname, detailbacklist);
					usermanager.addUserGroup(accloginname, tdetailids);
					if(tran_type.equals("1")){ //在原有程序上添加，当移交方式为第一种时，执行
					  usermanager.removeUserGroup(traloginname, tdetailids);
					}
				}else if(tran_type.equals("3")){
					detailbacklist = getUserDetailRolesId(accloginname);
					if(detailbacklist.size()>0){
					   usermanager.removeUserGroup(accloginname, detailbacklist);
					}
					usermanager.addUserGroup(accloginname, tdetailids);
				}
			}else if(adetailids.size()==0 && tdetailids.size()>0){
				detail_backup = getBackUpDetailRolesId(accloginname);
				if(tran_type.equals("2") || tran_type.equals("1")){
					usermanager.addUserGroup(accloginname, tdetailids);
					if(detail_backup.size()>0){
						deleteNewDetailRoles(detail_backup);
					}
					if(tran_type.equals("1")){
						usermanager.removeUserGroup(traloginname, tdetailids);
					}
				}else if(tran_type.equals("3")){
					usermanager.addUserGroup(accloginname, tdetailids);
				}
			}else if((adetailids.size()>0 && tdetailids.size()==0) || (adetailids.size()==0 && tdetailids.size()==0)){
				detail_flag = true;
			}

			
			//移交用户拥有的角色
			if(a_list.size()>0 && t_list.size()>0){         //第一种双方角色都不为空，即进行原角色备份删除和新角色插入
				if(tran_type.equals("2") || tran_type.equals("1")){
					//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
					map = getRolesForRecover(a_userid);
					if(map.size()>0){
					  List<String> list = new ArrayList(map.keySet());
					  deleteNewRoles(list,a_userid);
					}
					backlist = insertIntoBackUpRoles(a_list,accloginname,loginname);
					deleteOldRoles(backlist,accloginname);
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
					if(tran_type.equals("1")){ //在原有程序上添加，当移交方式为第一种时，执行
					  deleteOldRoles(t_list,traloginname);
					}
				}else if(tran_type.equals("3")){
					deleteOldRoles(a_list,accloginname);
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
				}
			 }else if(a_list.size()==0 && t_list.size()>0){ //交方角色不空，接方角色为空，即执行新角色插入
				if(tran_type.equals("2") || tran_type.equals("1")){
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
					map = getRolesForRecover(a_userid);
					if(map.size()>0){
					  List<String> list = new ArrayList(map.keySet());
					  deleteNewRoles(list,a_userid);
					}
					if(tran_type.equals("1")){ //在原有程序上添加，当移交方式为第一种时，执行
						   deleteOldRoles(t_list,traloginname);
					}
				}else if(tran_type.equals("3")){
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
				}
			 }else if((a_list.size()>0 && t_list.size()==0) || (a_list.size()==0 && t_list.size()==0)){ //交方角色为空，接方角色不为空，即进行原角色备份删除
				 role_flag = true;
			 }
		}else if(acc_type.equals("1")){   //接收方式为追加

			//移交工单细分角色
			if(adetailids.size()>0 && tdetailids.size()>0){
				for(int i=0;i<adetailids.size();i++){
					detail_set.add(adetailids.get(i));
				}
				for(int m=0;m<tdetailids.size();m++){
					d_boolean = detail_set.add(tdetailids.get(m));
					if(d_boolean == true){
					  d_extends.add(tdetailids.get(m));
					}
				}
				if(d_extends.size()>0){  //需要移交
					detail_backup = getBackUpDetailRolesId(accloginname);
					if(tran_type.equals("2")){
						//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
						if(detail_backup.size()>0){
							deleteNewDetailRoles(detail_backup);
						}
						detailbacklist = insertIntoUserDetailBackUp(accloginname,loginname);
						usermanager.addUserGroup(accloginname, d_extends);
					}else if(tran_type.equals("3")){
						usermanager.addUserGroup(accloginname, d_extends);
					}
				}
				if(tran_type.equals("1")){
					//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
					if(detail_backup.size()>0){
						deleteNewDetailRoles(detail_backup);
					}
					detailbacklist = insertIntoUserDetailBackUp(accloginname,loginname);
					usermanager.addUserGroup(accloginname, d_extends);
					usermanager.removeUserGroup(traloginname, tdetailids);
				}
			}else if(adetailids.size()==0 && tdetailids.size()>0){
				detail_backup = getBackUpDetailRolesId(accloginname);
				if(tran_type.equals("2") || tran_type.equals("1")){
					usermanager.addUserGroup(accloginname, tdetailids);
					if(detail_backup.size()>0){
						deleteNewDetailRoles(detail_backup);
					}
					if(tran_type.equals("1")){
						usermanager.removeUserGroup(traloginname, tdetailids);
					}
				}else if(tran_type.equals("3")){
					usermanager.addUserGroup(accloginname, tdetailids);
				}
			}else if((adetailids.size()>0 && tdetailids.size()==0) || (adetailids.size()==0 && tdetailids.size()==0)){
				detail_flag = true;
			}
			
			//移交用户拥有的角色
			if(a_list.size()>0 && t_list.size()>0){         //第一种双方角色都不为空，即进行原角色备份删除和新角色插入
				for(int i=0;i<a_list.size();i++){
					role_set.add(a_list.get(i));
				}
				for(int j=0;j<t_list.size();j++){
					r_boolean = role_set.add(t_list.get(j));
					if(r_boolean == true){
						r_extends.add(t_list.get(j));
					}
				}
				if(r_extends.size()>0){
					if(tran_type.equals("2")){
						//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
						map = getRolesForRecover(a_userid);
						if(map.size()>0){
						  List<String> list = new ArrayList(map.keySet());
						  deleteNewRoles(list,a_userid);
						}
						backlist = insertIntoBackUpRoles(a_list,accloginname,loginname);
						insertIntoRolesUserGroupRel(r_extends,traloginname,accloginname,groupid);
						
					}else if(tran_type.equals("3")){
						insertIntoRolesUserGroupRel(r_extends,traloginname,accloginname,groupid);
					}
				}
				if(tran_type.equals("1")){
					//操作前多一步判断，即当前人在备份表里是否有记录，有即删除
					map = getRolesForRecover(a_userid);
					if(map.size()>0){
					  List<String> list = new ArrayList(map.keySet());
					  deleteNewRoles(list,a_userid);
					}
					backlist = insertIntoBackUpRoles(a_list,accloginname,loginname);
					insertIntoRolesUserGroupRel(r_extends,traloginname,accloginname,groupid);
					deleteOldRoles(t_list,traloginname);
				}
			 }else if(a_list.size()==0 && t_list.size()>0){ //交方角色不空，接方角色为空，即执行新角色插入
				if(tran_type.equals("2") || tran_type.equals("1")){
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
					map = getRolesForRecover(a_userid);
					if(map.size()>0){
					  List<String> list = new ArrayList(map.keySet());
					  deleteNewRoles(list,a_userid);
					}
					if(tran_type.equals("1")){ //在原有程序上添加，当移交方式为第一种时，执行
						   deleteOldRoles(t_list,traloginname);
					}
				}else if(tran_type.equals("3")){
					insertIntoRolesUserGroupRel(t_list,traloginname,accloginname,groupid);
				}
			 }else if((a_list.size()>0 && t_list.size()==0) || (a_list.size()==0 && t_list.size()==0)){ //交方角色为空，接方角色不为空，即进行原角色备份删除
				 role_flag = true;
			 }
			
		}
		
		if(role_flag==true && detail_flag==true){
			flag = "nothing";
		}
		return flag;
	}
	/**
	 * 获取移交方的角色详细信息
	 * @author lihongbo
	 * @param list
	 * @param traloginname
	 * @return
	 */
    public Map getRoleInfo(List<String> list,String traloginname){
    	String roleids  = "";
    	String user_id  = "";
    	String group_id = "";
    	String role_id  = "";
    	String orderby  = "";
    	String desc     = "";
    	String note1    = "";
    	String note2    = "";
    	Map<String,List<String>> map = new HashMap<String,List<String>>();
    	for(int i=0;i<list.size();i++){
    	   if(i==list.size()-1){
    		   roleids = roleids+" rolesusergroup.c660000028='"+list.get(i)+"' ";
    	   }else{
    		   roleids = roleids+" rolesusergroup.c660000028='"+list.get(i)+"' or";
    	   }
    	}
    	List lists = new ArrayList();
    	RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
		GetUserInfoList getUserInfoList	= new GetUserInfoList();
		String userid = getUserInfoList.getUserInfoName(traloginname).getC1();
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select rolesusergroup.c660000026,rolesusergroup.c660000027,rolesusergroup.c660000028,rolesusergroup.c660000030,rolesusergroup.c660000031,rolesusergroup.c660000032,rolesusergroup.c660000033 from RolesUserGroupRelpo rolesusergroup where 1=1 ");
    	if(!roleids.equals("")){
    		sql.append(" and ("+roleids+") ");
    	}
    	sql.append(" and rolesusergroup.c660000026='"+userid+"'");
    	lists = HibernateDAO.queryObject(sql.toString());
    	Iterator it = lists.iterator();
	   	while (it.hasNext())
	   	{
	   		Object[]  obj  = (Object[])it.next();
	   		List oad       = new ArrayList();
	   		user_id        = FormatString.CheckNullString(""+obj[0]);
	   		group_id       = FormatString.CheckNullString(""+obj[1]);
	   		role_id        = FormatString.CheckNullString(""+obj[2]);
	   		orderby        = FormatString.CheckNullString(""+obj[3]);
	   		desc           = FormatString.CheckNullString(""+obj[4]);
	   		note1          = FormatString.CheckNullString(obj[5]); 
	   		note2          = FormatString.CheckNullString(obj[6]);
	   		oad.add(0, user_id);
	   		oad.add(1,group_id);
	   		oad.add(2,orderby);
	   		oad.add(3,desc);
	   		oad.add(4,note1);
	   		oad.add(5,note2);
	   		map.put(role_id, oad);
	   	}
    	return map;
    }
    
    /**
     * 将接收方原有的角色备份到表中，并返回删除id
     * @author lihongbo
     * @param a_list
     * @param accloginname
     * @param loginname
     * @return
     */
    public List<String> insertIntoBackUpRoles(List<String> a_list,String accloginname,String loginname){
        boolean flag = false;
        List<String> delroleid = new ArrayList();
        GetUserInfoList a_getUserInfoList = new GetUserInfoList();
        String a_fullname = a_getUserInfoList.getUserInfoName(accloginname).getC630000003();
        String tuser      = a_getUserInfoList.getUserInfoName(loginname).getC630000003();
    	RolesAccUserBackUp rolesaccuserbackup         = new RolesAccUserBackUp();
		RolesAccUserBackUpBean rolesaccuserbackupinfo = new RolesAccUserBackUpBean();
    	Map<String,List> a_map = new HashMap<String,List>();
    	a_map = getRoleInfo(a_list,accloginname);
		for(int i=0;i<a_list.size();i++){
			String a_roleid = a_list.get(i);
			rolesaccuserbackupinfo.setAcc_roleuser(""+a_map.get(a_roleid).get(0));
			rolesaccuserbackupinfo.setAcc_group_id(""+a_map.get(a_roleid).get(1));
			rolesaccuserbackupinfo.setAcc_roleid(a_roleid);
			rolesaccuserbackupinfo.setAcc_orderby(Long.valueOf(""+a_map.get(a_roleid).get(2)));
			rolesaccuserbackupinfo.setAcc_desc(""+a_map.get(a_roleid).get(3));
			rolesaccuserbackupinfo.setAcc_backup1(FormatString.CheckNullString(""+a_map.get(a_roleid).get(4)));
			rolesaccuserbackupinfo.setAcc_backup2(FormatString.CheckNullString(""+a_map.get(a_roleid).get(5)));
			rolesaccuserbackupinfo.setAcc_createtime(newTime());
			rolesaccuserbackupinfo.setAcc_operator(loginname); 
			rolesaccuserbackupinfo.setAcc_userfullname(a_fullname);
			rolesaccuserbackupinfo.setAcc_rolename(getRoleName(a_roleid));
			rolesaccuserbackupinfo.setAcc_createdate(getDate(newTime()));
			rolesaccuserbackupinfo.setAcc_operatefullname(tuser);
			flag = rolesaccuserbackup.rolesAccUserBackUpInsert(rolesaccuserbackupinfo);
			if(flag==true){
				delroleid.add(a_roleid);
			}
		}
    	return delroleid;
    }
    
    /**
     * 根据角色id和接收人登录名，查找此人在原角色表中的记录并删除
     * @author lihongbo
     * @param list
     * @param accloginname
     * @return
     */
    public List<String> deleteOldRoles(List<String> list,String accloginname){
    	boolean flag = false;
    	List<String> dellist   = new ArrayList();
    	List<String> deletes   = new ArrayList();
    	GetUserInfoList a_getUserInfoList	            = new GetUserInfoList();
    	RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
		String a_userid = a_getUserInfoList.getUserInfoName(accloginname).getC1();
		String a_deptid = a_getUserInfoList.getUserInfoName(accloginname).getC630000015();
    	if(list.size()>0){
			dellist = getNewInsertRoles(list,a_userid);
			for(int i=0;i<dellist.size();i++){
				flag = rolesusergrouprel.rolesUserGroupRelDelete(dellist.get(i));
				if(flag==true){
					deletes.add(dellist.get(i));
				}
			}
		}
    	return deletes;
    }
    
    /**
     * 将移交方的角色信息给接收方
     * @author lihongbo
     * @param t_list
     * @param traloginname
     * @param accloginname
     * @return
     */
    public List<String> insertIntoRolesUserGroupRel(List<String> t_list,String traloginname,String accloginname,String groupid){
    	boolean flag = false;
    	List<String> list = new ArrayList();
    	Map<String,List> b_map = new HashMap<String,List>();
    	b_map = getRoleInfo(t_list,traloginname);
    	GetUserInfoList a_getUserInfoList = new GetUserInfoList();
    	RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
    	String a_userid = a_getUserInfoList.getUserInfoName(accloginname).getC1();
		for(int i=0;i<t_list.size();i++){
		  String roleid = t_list.get(i);
		  rolesusergrouprelinfo.setRoleRel_UserID(a_userid);
		  rolesusergrouprelinfo.setRoleRel_GroupID(groupid);
		  rolesusergrouprelinfo.setRoleRel_RoleID(roleid);
		  rolesusergrouprelinfo.setRoleRel_OrderBy(""+b_map.get(roleid).get(2));
		  rolesusergrouprelinfo.setRoleRel_Desc(""+b_map.get(roleid).get(3));
		  rolesusergrouprelinfo.setRoleRel_memo("transfer");
		  rolesusergrouprelinfo.setRoleRel_memo1("transfer");
		  flag = rolesusergrouprel.rolesUserGroupRelInsert(rolesusergrouprelinfo);
		  if(flag==true){
			  list.add(roleid);
		  }
		}
    	return list;
    }
    /**
     * 查询新纪录id
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> getNewInsertRoles(List<String> list,String userid){
    	String roleids = "";
    	List<String> backupid = new ArrayList();
    	for(int i=0;i<list.size();i++){
     	   if(i==list.size()-1){
     		   roleids = roleids+" rabu.c660000028='"+list.get(i)+"' ";
     		   
     	   }else{
     		   roleids = roleids+" rabu.c660000028='"+list.get(i)+"' or";
     	   }
     	}
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select rabu.c1 from RolesUserGroupRelpo rabu where 1=1 ");
    	if(!roleids.equals("")){
    		sql.append(" and ("+roleids+") "); 
    	}
    	sql.append(" and rabu.c660000026='"+userid+"' ");
    	backupid = HibernateDAO.queryObject(sql.toString());
    	return backupid;
    }
    
    /**
     * 根据角色id查找角色名称
     * @author lihongbo
     * @param roleid
     * @return
     */
    public String getRoleName(String roleid){
    	String rolename = "";
    	List<String> list = new ArrayList();
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select rmp.c660000001 from RolesManagepo rmp where 1=1 ");
    	if(!roleid.equals("")){
    	  sql.append(" and rmp.c1='"+roleid+"' ");	
    	}else{
    	  sql.append(" and rmp.c1=''");
    	}
    	list = HibernateDAO.queryObject(sql.toString());
    	if(list.size()>0){
    		rolename = list.get(0);
    	}
    	return rolename;
    }
    /**
     * 获取当前时间的秒值
     * @author lihongbo
     */ 
    public long newTime(){
    	Date current = new Date();
    	SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long currentime = FormatTime.FormatDateStringToInt(dateFormat.format(current));
    	return currentime;
    }
    
    /**
     * 根据当前时间获得当前日期
     * @author lihongbo
     * @param current
     * @return
     */
    public String getDate(long current){
    	String time = FormatTime.formatIntToDateString("yyyy-MM-dd", current);
    	return time;
    }
    
    /**
     * 角色恢复时调用，查找要插入的记录
     * @author lihongbo
     * @param userid
     * @return
     */
    public Map getRolesForRecover(String userid){
    	String roleids  = "";
    	String user_id  = "";
    	String group_id = "";
    	String role_id  = "";
    	String orderby  = "";
    	String desc     = "";
    	String note1    = "";
    	String note2    = "";
    	Map<String,List<String>> map = new HashMap<String,List<String>>();
    	List<String> list = new ArrayList();
    	StringBuffer sql  = new StringBuffer();
    	if(null!=userid && !"".equals(userid)){
    		sql.append(" select raubu.c670000001,raubu.c670000002,raubu.c670000003,raubu.c670000004,raubu.c670000005 from RolesAccUserBackUppo raubu where 1=1 and raubu.c670000001='"+userid+"'");
    	    list = HibernateDAO.queryObject(sql.toString());
    	    Iterator it = list.iterator();
    	   	while (it.hasNext())
    	   	{
    	   		Object[]  obj  = (Object[])it.next();
    	   		List oad       = new ArrayList();
    	   		user_id        = FormatString.CheckNullString(""+obj[0]);
    	   		group_id       = FormatString.CheckNullString(""+obj[1]);
    	   		role_id        = FormatString.CheckNullString(""+obj[2]);
    	   		orderby        = FormatString.CheckNullString(""+obj[3]);
    	   		desc           = FormatString.CheckNullString(""+obj[4]);
    	   		note1          = "recover"; 
    	   		note2          = "recover";
    	   		oad.add(0, user_id);
    	   		oad.add(1,group_id);
    	   		oad.add(2,orderby);
    	   		oad.add(3,desc);
    	   		oad.add(4,note1);
    	   		oad.add(5,note2);
    	   		map.put(role_id, oad);
    	   	}
    	}
    	
    	return map;
    }
    
    /**
     * 将备份表中的角色插入到RolesUserGroupRelpo中
     * @author lihongbo
     * @param t_list
     * @param map
     * @return
     */
    public List<String> insertIntoByRecover(List<String> t_list,Map<String,List> map){
    	boolean flag = false;
    	List<String> list = new ArrayList();
    	RolesUserGroupRel rolesusergrouprel 			= new RolesUserGroupRel();
		RolesUserGroupRelBean rolesusergrouprelinfo 	= new RolesUserGroupRelBean();
		for(int i=0;i<t_list.size();i++){
		  String roleid = t_list.get(i);
		  rolesusergrouprelinfo.setRoleRel_UserID(""+map.get(roleid).get(0));
		  rolesusergrouprelinfo.setRoleRel_GroupID(""+map.get(roleid).get(1));
		  rolesusergrouprelinfo.setRoleRel_RoleID(roleid);
		  rolesusergrouprelinfo.setRoleRel_OrderBy(""+map.get(roleid).get(2));
		  rolesusergrouprelinfo.setRoleRel_Desc(""+map.get(roleid).get(3));
		  rolesusergrouprelinfo.setRoleRel_memo(""+map.get(roleid).get(4));
		  rolesusergrouprelinfo.setRoleRel_memo1(""+map.get(roleid).get(5));
		  flag = rolesusergrouprel.rolesUserGroupRelInsert(rolesusergrouprelinfo);
		  if(flag==true){
			  list.add(roleid);
		  }
		}
    	return list;
    }
    
    /**
     * 删除表RolesAccUserBackUp中的记录
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> deleteNewRoles(List<String> list,String userid){
    	boolean flag = false;
    	List<String> dellist   = new ArrayList();
    	List<String> deletes   = new ArrayList();
    	RolesAccUserBackUp rolesaccuserbackup         = new RolesAccUserBackUp();
		RolesAccUserBackUpBean rolesaccuserbackupinfo = new RolesAccUserBackUpBean();
    	if(list.size()>0){
			dellist = getBackUpRolesId(list,userid);
			for(int i=0;i<dellist.size();i++){
				flag = rolesaccuserbackup.rolesAccUserBackUpDelete(dellist.get(i));
				if(flag==true){
					deletes.add(dellist.get(i));
				}
			}
		}
    	return deletes;
    } 
    
    /**
     * 查询表RolesAccUserBackUp中的id，供删除时用
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> getBackUpRolesId(List<String> list,String userid){
    	String roleids = "";
    	List<String> backupid = new ArrayList();
    	for(int i=0;i<list.size();i++){
     	   if(i==list.size()-1){
     		  roleids = roleids+" raubu.c670000003='"+list.get(i)+"' ";
     	   }else{
     		  roleids = roleids+" raubu.c670000003='"+list.get(i)+"' or";
     	   }
     	}
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select raubu.c1 from RolesAccUserBackUppo raubu where 1=1 ");
    	if(!roleids.equals("")){
    		sql.append(" and ("+roleids+") ");
    	}
    	sql.append(" and raubu.c670000001='"+userid+"' ");
    	backupid = HibernateDAO.queryObject(sql.toString());
    	return backupid;
    }
    
    /**
     * 查询表RolesAccUserBackUp中特定用户的资源id
     * @author lihongbo
     * @param userid
     * @return
     */
    public List<String>  getNewRolesId(String userid){
    	String roleids = "";
    	List<String> list = new ArrayList();
    	StringBuffer sql  = new StringBuffer();
    	if(null!=userid && !"".equals(userid)){
    		sql.append(" select raubu.c670000003 from RolesAccUserBackUppo raubu where 1=1 and raubu.c670000001='"+userid+"'");
    	    list = HibernateDAO.queryObject(sql.toString());
    	}
    	return list;
    }
    
    /**
     * 根据登录名，查询接收人原有的细分角色
     * @author lihongbo
     * @param accloginname
     * @return
     */
    public Map getAccOldDetailRoles(String accloginname){
    	String UserName = "";
    	String FullName = "";
    	String ChildRoleID = "";
    	String ChildRoleName = "";
    	String RoleID = "";
    	String RoleName = "";
    	String BaseID = "";
    	String BaseSchema = "";
    	String BaseName = "";
    	String UserCorp = "";
    	String UserCorpID = "";
    	String UserDep = "";
    	String UserDepID = "";
    	String UserDN = "";
    	String UserDNID = "";
    	String UserDesc = "";
    	String UserCorpInitID = "";
    	String UserDepInitID = "";
    	StringBuffer sql = new StringBuffer();
    	Map<String,List<String>> map = new HashMap<String,List<String>>();
    	List<String> list = new ArrayList();
    	sql.append(" select wfru.c650000001,wfru.c650000002,wfru.c650000003,wfru.c650000004,wfru.c650000005,wfru.c650000006,wfru.c650000007,wfru.c650000008,wfru.c650000009,wfru.c650000010,wfru.c650000011,wfru.c650000012,wfru.c650000013,wfru.c650000014,wfru.c650000015,wfru.c650000016,wfru.c650000017,wfru.c650000018 from WorkflowRoleUserpo wfru where wfru.c650000001=");
    	sql.append("'"+accloginname+"'");
    	list = HibernateDAO.queryObject(sql.toString());
    	Iterator it = list.iterator();
	   	while (it.hasNext())
	   	{
	   		Object[]  obj  = (Object[])it.next();
	   		List detail    = new ArrayList();
	   		UserName       = FormatString.CheckNullString(obj[0]);
	   		FullName       = FormatString.CheckNullString(obj[1]);
	   		ChildRoleID    = FormatString.CheckNullString(obj[2]);
	   		ChildRoleName  = FormatString.CheckNullString(obj[3]);
	   		RoleID         = FormatString.CheckNullString(obj[4]);
	   		RoleName       = FormatString.CheckNullString(obj[5]);
	   		BaseID         = FormatString.CheckNullString(obj[6]);
	   		BaseSchema     = FormatString.CheckNullString(obj[7]); 
	   		BaseName       = FormatString.CheckNullString(obj[8]); 
	   		UserCorp       = FormatString.CheckNullString(obj[9]); 
	   		UserCorpID     = FormatString.CheckNullString(obj[10]); 
	   		UserDep        = FormatString.CheckNullString(obj[11]);
	   		UserDepID      = FormatString.CheckNullString(obj[12]);
	   		UserDN         = FormatString.CheckNullString(obj[13]);
	   		UserDNID       = FormatString.CheckNullString(obj[14]);
	   		UserDesc       = FormatString.CheckNullString(obj[15]);
	   		UserCorpInitID = FormatString.CheckNullString(obj[16]);
	   		UserDepInitID  = FormatString.CheckNullString(obj[17]);
	   		detail.add(0, UserName);
	   		detail.add(1,FullName);
	   		detail.add(2,ChildRoleName);
	   		detail.add(3,RoleID);
	   		detail.add(4,RoleName);
	   		detail.add(5,BaseID);
	   		detail.add(6,BaseSchema);
	   		detail.add(7,BaseName);
	   		detail.add(8,UserCorp);
	   		detail.add(9,UserCorpID);
	   		detail.add(10,UserDep);
	   		detail.add(11,UserDepID);
	   		detail.add(12,UserDN);
	   		detail.add(13,UserDNID);
	   		detail.add(14,UserDesc);
	   		detail.add(15,UserCorpInitID);
	   		detail.add(16,UserDepInitID);
	   		
	   		map.put(ChildRoleID, detail);
	   	}
    	return map;
    }
    /**
     * 将接收方原有的细分角色插入到备份表里
     * @author lihongbo
     * @param accloginname
     * @param loginname
     * @return
     */
    public List<String> insertIntoUserDetailBackUp(String accloginname,String loginname){
    	boolean flag = false;
        List<String> details = new ArrayList();
        GetUserInfoList a_getUserInfoList = new GetUserInfoList();
        String a_fullname = a_getUserInfoList.getUserInfoName(accloginname).getC630000003();
        String tuser      = a_getUserInfoList.getUserInfoName(loginname).getC630000003();
		WorkflowRoleUserBackUp workflowroleuserbackup = new WorkflowRoleUserBackUp();
		WorkflowRoleUserBackUpBean workflowroleuserbackupinfo = new WorkflowRoleUserBackUpBean();
    	Map<String,List<String>> a_map = new HashMap<String,List<String>>();
    	a_map = getAccOldDetailRoles(accloginname);
    	Set roleid = a_map.keySet();
    	Iterator it = roleid.iterator();
    	while(it.hasNext()){
    		String detailroleid = ""+it.next();
    		workflowroleuserbackupinfo.setUserName(""+a_map.get(detailroleid).get(0));
    		workflowroleuserbackupinfo.setFullName(""+a_map.get(detailroleid).get(1));
    		workflowroleuserbackupinfo.setChildRoleID(detailroleid);
    		workflowroleuserbackupinfo.setChildRoleName(""+a_map.get(detailroleid).get(2));
    		workflowroleuserbackupinfo.setRoleID(""+a_map.get(detailroleid).get(3));
    		workflowroleuserbackupinfo.setRoleName(""+a_map.get(detailroleid).get(4));
    		workflowroleuserbackupinfo.setBaseID(""+a_map.get(detailroleid).get(5));
    		workflowroleuserbackupinfo.setBaseSchema(""+a_map.get(detailroleid).get(6));
    		workflowroleuserbackupinfo.setBaseName(""+a_map.get(detailroleid).get(7));
    		workflowroleuserbackupinfo.setUserCorp(""+a_map.get(detailroleid).get(8));
    		workflowroleuserbackupinfo.setUserCorpID(""+a_map.get(detailroleid).get(9));
    		workflowroleuserbackupinfo.setUserDep(""+a_map.get(detailroleid).get(10));
    		workflowroleuserbackupinfo.setUserDepID(""+a_map.get(detailroleid).get(11));
    		workflowroleuserbackupinfo.setUserDN(""+a_map.get(detailroleid).get(12));
    		workflowroleuserbackupinfo.setUserDNID(""+a_map.get(detailroleid).get(13));
    		workflowroleuserbackupinfo.setUserDesc(""+a_map.get(detailroleid).get(14));
    		workflowroleuserbackupinfo.setUserCorpInitID(""+a_map.get(detailroleid).get(15));
    		workflowroleuserbackupinfo.setUserDepInitID(""+a_map.get(detailroleid).get(16));
    		workflowroleuserbackupinfo.setAcc_createtime(newTime());
    		workflowroleuserbackupinfo.setAcc_operator(loginname);
    		workflowroleuserbackupinfo.setAcc_userfullname(a_fullname);
    		workflowroleuserbackupinfo.setAcc_createdate(getDate(newTime()));
    		workflowroleuserbackupinfo.setAcc_operatefullname(tuser);
    		
    		flag = workflowroleuserbackup.workflowRoleUserBackUpInsert(workflowroleuserbackupinfo);
    	    if(flag==true){
    	    	details.add(detailroleid);
    	    }
    	}
		
    	return details;
    }
    /**
     * 删除表WorkflowRoleUserBackUp中的记录
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> deleteNewDetailRoles(List<String> delids){
    	boolean flag = false;
    	List<String> deletes   = new ArrayList();
    	WorkflowRoleUserBackUp workflowroleuserbackup = new WorkflowRoleUserBackUp();
		WorkflowRoleUserBackUpBean workflowroleuserbackupinfo = new WorkflowRoleUserBackUpBean();
		for(int i=0;i<delids.size();i++){
			flag = workflowroleuserbackup.workflowRoleUserBackUpDelete(delids.get(i));
			if(flag==true){
				deletes.add(delids.get(i));
			}
		}
    	return deletes;
    } 
    
    /**
     * 查询表WorkflowRoleUserBackUp中的id，供删除时用
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> getBackUpDetailRolesId(String loginname){
    	List<String> backupid = new ArrayList();
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select wrubu.c1 from WorkflowRoleUserBackUppo wrubu where 1=1 ");
    	sql.append(" and wrubu.c660000001='"+loginname+"' ");
    	backupid = HibernateDAO.queryObject(sql.toString());
    	return backupid;
    }
    /**
     * 查询接收方原来是否有细分角色，并返回角色id
     * @author lihongbo
     * @param accloginname
     * @return
     */
    public List<String> getUserDetailRolesId(String accloginname){
    	StringBuffer sql = new StringBuffer();
    	List<String> list = new ArrayList();
    	sql.append("select wrup.c650000003 from WorkflowRoleUserpo wrup where wrup.c650000001=");
    	sql.append("'"+accloginname+"'");
    	list = HibernateDAO.queryObject(sql.toString());
    	return list;
    }
    /**
     * 查询表WorkflowRoleUserBackUp中的细分角色id
     * @author lihongbo
     * @param list
     * @param userid
     * @return
     */
    public List<String> getBackUpDetailRoles(String loginname){
    	List<String> backupid = new ArrayList();
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select wrubu.c660000003 from WorkflowRoleUserBackUppo wrubu where 1=1 ");
    	sql.append(" and wrubu.c660000001='"+loginname+"' ");
    	backupid = HibernateDAO.queryObject(sql.toString());
    	return backupid;
    }
}
