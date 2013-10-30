package com.ultrapower.eoms.mobile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.po.ManageGroupUserpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.GrandActionConfigpo;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.bean.RoleInfoBean;

public class UserGroupGrandInterface 
{
	static final Logger logger = (Logger) Logger.getLogger(UserGroupGrandInterface.class);
	
	/**
	 * 首先根据用户名与资源名查出对应的用户ID与资源ID然后调用getUserGroup方法返回Document对象
	 * 日期 2006-12-5
	 * @author wangyanguang/王彦广 
	 * @param argusername      		用户名
	 * @param sourcename			资源名
	 * @return Document
	 */
	public Document getGroupUserDoc(String argusername, String sourcename) 
	{
		Long sourceid = null;
		String userid = null;
		
		if(argusername.equals(""))
		{
			logger.error("用户名不能为空");
			return null;
		}
		if(sourcename.equals(""))
		{
			logger.error("资源名不能为空！");
			return null;
		}
		Document returnDoc = null;
		RoleInterfaceAssociate roleasso = new RoleInterfaceAssociate();
		// 根据资源英文名查出资源Bean;
		
		Sourceconfig sourceconfig = null;
		try
		{
			sourceconfig = roleasso.getSourceBean(sourcename);
		}
		catch(Exception e)
		{
			logger.error("没有查到资源名");
		}
		if (sourceconfig != null) 
		{
			sourceid = sourceconfig.getSourceId();
		}
		else 
		{
			logger.error("资源名有误！");
			return null;
		}
		
		// 根据用户名查询用户ID，归属组ID。
		SysPeoplepo syspeople = roleasso.getUserID(argusername);
		
		if(syspeople!=null)
		{
			userid = syspeople.getC1();
		}
		if (userid == null) 
		{
			logger.error("用户名有误！");
			return null;
		}
		
		returnDoc = getUserGroup(userid, String.valueOf(sourceid));
		
		if(returnDoc==null)
		{
			return null;
		}else
		{
			return returnDoc;
		}
	}
	
	/**
	 * 根据用户ID，与资源ID，返回Document对象。
	 * 1.  根据参数用户ID与资源ID调用关联查询方法getAllUserGroupGrand(userid, sourceid)查询出
	 * 	   技能表信息、用户信息、派发信息和权限动作信息四个Bean 以List返回。
	 * 2.  循环List得到每一个对象值，将其转化为四个Bean信息.
	 * 3.  根据用户信息Bean得到用户ID，与用户所属组ID，根据用户ID+组ID查询节点看引节点是否存在.
	 * 4.  如果存在，那么直接加<grand>节点。
	 * 5.  如果不存在，再判断所属组节点是否存在。
	 * 6.  如果组节点存在，调用addUserElement方法添加用户节点。
	 * 7.  如果组节点不存在，调用addGroupElemet(doc,groupid)方法添加组节点，然后再调用addUserElement方法添加用户节点。
	 * 
	 * 日期 2006-12-5
	 * @author wangyanguang/王彦广 
	 * @param userid			用户ID
	 * @param sourceid			资源ID
	 * @return Document
	 */
	public Document getUserGroup(String userid, String sourceid) 
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		try 
		{
			Element root 							= doc.addElement("treeinfo");
			root.addAttribute("ID", "0");
			GetRole role 							= new GetRole();
			//在技能授权表中的关联查询。
			List list = null;
			try
			{
				list 								= role.getAllUserGroupGrand(userid, sourceid);
			}
			catch(Exception e)
			{
				logger.error("UserGroupGrandInterface.getUserGroup"+
						"从GetRole.getAllUserGroupGrand(userid, sourceid)查数据时出现异常。");
			}
			if(list==null)
			{
				logger.error("UserGroupGrandInterface.getUserGroup"+
						"从GetRole.getAllUserGroupGrand(userid, sourceid)中无法查出数据。");
			}
			if(list!=null)
			{
				logger.info("关联查询后共有："+list.size()+"条记录！");
				Iterator it 							= list.iterator();
				while (it.hasNext()) 
				{
					Object[] syspo 						= (Object[]) it.next();
					SysSkillpo sysskillpo 				= (SysSkillpo) syspo[0];
					SysGroupUserpo syspeoplepo 			= (SysGroupUserpo) syspo[1];
					ManageGroupUserpo managegroupuserpo = (ManageGroupUserpo) syspo[2];
					GrandActionConfigpo grandActionpo   = (GrandActionConfigpo)syspo[3];
					String tmpuserid 					= sysskillpo.getC610000007();
					String groupid 						= syspeoplepo.getC620000027();
					String userid1						= Function.dropZero(tmpuserid);
					String groupid1						= Function.dropZero(groupid);
					
					Element userelement = doc.elementByID(groupid1+userid1);
					
					if (userelement == null) 
					{
						Element groupelement = doc.elementByID(Function.dropZero(groupid));
						if (groupelement == null) 
						{
							//取得当前组DN，添加节点。
							addGroupElemet(doc,groupid);
							groupelement = doc.elementByID(Function.dropZero(groupid));
							if(groupelement==null)
							{
								System.out.println("groupelement == null");
							}
							else
							{	
								addUserElement(groupelement,groupid,tmpuserid,sysskillpo,grandActionpo);
							}
						} else 
						{
							//在组下添加用户节点，用户名，用户grand.
							addUserElement(groupelement,groupid,tmpuserid,sysskillpo,grandActionpo);
						}
					}
					else 
					{
						//添加用户grand.
						Element grand 				= userelement.addElement("grand");
						Element query 				= grand.addElement("Skill_CategoryQueryID");
						query.setText(Function.nullString(sysskillpo.getC610000009()));
						Element action 				= grand.addElement("Skill_Action");
						action.setText(Function.nullString(sysskillpo.getC610000010()));
						Element commissionGID 		= grand.addElement("Skill_CommissionGID");
						commissionGID.setText(Function.dropZero(Function.nullString(sysskillpo.getC610000012())));
						Element commissionUID 		= grand.addElement("Skill_CommissionUID");
						commissionUID.setText(Function.dropZero(Function.nullString(sysskillpo.getC610000014())));
						Element commissionclosetime = grand.addElement("Skill_CommissionCloseTime");
						commissionclosetime.setText(Function.nullString(String.valueOf(sysskillpo.getC610000015())));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 根据组表中的DN字段，添加所需组的节点。
	 *  1.DN字段中的内容标识了父根节点到当前节点的ID值，根据这个字段能够查询出从根节点到当前节点的所有ID值。
	 *  2.DN中从第一个节点（根节点）开始，取出ID后首先判断当前节点是否存在，如果存在取下一节点ID，如果不存在添加节点。 
	 *   
	 * 日期 2006-12-5
	 * @author wangyanguang/王彦广 
	 * @param doc
	 * @param groupid 
	 */
	public void addGroupElemet(Document doc,String groupid)
	{
		GetGroupInfoList groupinfo 		= new GetGroupInfoList();
		SysGrouppo grouppo 				= groupinfo.getGroupInfo(groupid);
		String groupname 				= Function.nullString(grouppo.getC630000018()).trim();
		String parentinfo 				= grouppo.getC630000037();
		logger.info("parentinfo:"+parentinfo);
		String[] parentArray 			= parentinfo.split(";");
		logger.info("parentArray:"+parentArray[0]);
		for(int i=0;i<parentArray.length;i++)
		{
			if(parentArray[i].equals("0"))
			{
				continue;
			}
			SysGrouppo tmp_grouppo 		= groupinfo.getGroupInfo(Function.nullString(parentArray[i]));
			String groupparentid 		= Function.nullString(tmp_grouppo.getC630000020());
			logger.info("parentid:"+groupparentid);
			groupname = Function.nullString(tmp_grouppo.getC630000018()).trim();
			//先依次判断数组中的节点是否已经存在，如果存在，不做任何动作，如果不存在，执行添加操作。
			
			Element test = doc.elementByID(Function.dropZero(parentArray[i]));
			{
				if(test==null)
				{
					if(groupparentid.equals("0"))
					{
						Element gen 			= doc.getRootElement();
						Element newelement 		= gen.addElement("group");
						System.out.println(parentArray[i]);
						
						newelement.addAttribute("ID",Function.dropZero(parentArray[i]));
						System.out.println(newelement.attributeValue("ID"));
						Element property 		= newelement.addElement("property");
						Element groupidelement  = property.addElement("groupid");
						groupidelement.setText(Function.dropZero(parentArray[i]));
						Element group_pid       = property.addElement("parentid");
						if(groupparentid.equals("0"))
						{
							group_pid.setText(groupparentid);
						}
						else
						{
							group_pid.setText(Function.dropZero(groupparentid));
						}
						Element name 			= property.addElement("groupname");
						name.setText(groupname);
					}else
					{
						Element tmpelement = null;
						try
						{
							 tmpelement = doc.elementByID(Function.dropZero(groupparentid));
							 String name = tmpelement.getName();
							 System.out.println(name);
							
						}catch(Exception e)
						{
							System.out.println("Exception");
						}
						if(tmpelement!=null)
						{
							Element newelement	= tmpelement.addElement("group");
							newelement.addAttribute("ID",Function.dropZero(parentArray[i]));
							Element property 		= newelement.addElement("property");
							Element groupidelement  = property.addElement("groupid");
							groupidelement.setText(Function.dropZero(parentArray[i]));
							Element group_pid       = property.addElement("parentid");
							group_pid.setText(Function.dropZero(groupparentid));
							Element name 			= property.addElement("groupname");
							name.setText(groupname);
						}
					}
				}
			}
		}
	}
	/**
	 * 根据组节点，组ID，用户ID，技能BEAN，将用户名（在用户名中查询），用户技能（在技能表中查询）添加到组节点上。
	 *  1.根据传入的参数，首先根据用户ID查询出用户信息
	 *  2.根据用户ID与资源ID查询技能授权表，查询出用户的<grand>节点下信息。
	 *  
	 * 日期 2006-12-7
	 * 
	 * @author wangyanguang/王彦广 
	 * @param groupelement			组节点Element
	 * @param groupid				组ID
	 * @param userid				用户ID
	 * @param sysskillpo			技能授权表Bean
	 * @param grandactionpo 		
	 */
	public void addUserElement(Element groupelement,String groupid,String userid,SysSkillpo sysskillpo,GrandActionConfigpo grandactionpo)
	{
		String username 				= "";
		String userloginname			= "";
		String userid1 = Function.dropZero(userid);
		String groupid1 = Function.dropZero(groupid);
		GetUserInfoList userinfolist 	= new GetUserInfoList();
		SysPeoplepo sysuserpo 			= userinfolist.getUserInfoID(userid);
		username 						= Function.nullString(sysuserpo.getC630000003().trim());
		userloginname                   = Function.nullString(sysuserpo.getC630000001().trim());
		//添加属性，用户名节点。
		Element userelement 			= groupelement.addElement("user");
		userelement.addAttribute("ID",groupid1+userid1);
		Element property 				= userelement.addElement("property");
		Element useridelement           = property.addElement("userID");
		
		useridelement.setText(userid1);
		Element userloginnameelement	= property.addElement("loginname");
		userloginnameelement.setText(userloginname);
		Element name 					= property.addElement("username");
		name.setText(username);
		//添加动作技能节点。
		Element grand 				= userelement.addElement("grand");
		Element query 				= grand.addElement("Skill_CategoryQueryID");
		query.setText(Function.nullString(sysskillpo.getC610000009()));
		Element action 				= grand.addElement("Skill_Action");
		action.setText(Function.nullString(sysskillpo.getC610000010()));
		Element commissionGID 		= grand.addElement("Skill_CommissionGID");
		commissionGID.setText(Function.dropZero(Function.nullString(sysskillpo.getC610000012())));
		Element commissionUID 		= grand.addElement("Skill_CommissionUID");
		commissionUID.setText(Function.dropZero(Function.nullString(sysskillpo.getC610000014())));
		Element commissionclosetime = grand.addElement("Skill_CommissionCloseTime");
		commissionclosetime.setText(Function.nullString(String.valueOf(sysskillpo.getC610000015())));
	}
	
	public static void main(String[] args) {
		UserGroupGrandInterface usergroupgrand = new UserGroupGrandInterface();
//		RoleInfoBean roleinfobean = new RoleInfoBean();
		//roleinfobean.setSkill_Action("3,4,7");
		long start = System.currentTimeMillis();
		Document doc =usergroupgrand.getGroupUserDoc("Demo","UltraProcess:App_Base");
		System.out.println("此次查询用时"+(System.currentTimeMillis()-start)/1000+"."+((System.currentTimeMillis()-start)%1000)+"秒！");
		if(doc!=null)
		{			
		try
		{
			
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/usergroup.xml")));
			
			writer.write(doc);
			writer.close();
		} catch (Exception e1) 
		{
			System.out.println("Excepiton");
			e1.getMessage();
		}
		}
		
	}
}
