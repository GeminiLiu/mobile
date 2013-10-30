package com.ultrapower.eoms.mobile.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRole;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
//import cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage.GetAttInfoList;
//import cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage.SourceConfigInfo;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributevalue;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfigattribute;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;

public class TemSourceInterface {
	static final Logger logger 			= (Logger) Logger.getLogger(TemSourceInterface.class);

	List list1 = null;
	
	public Document getMenuTree(String username, SourceConfigInfoBean sourceConfigBean,List beanList,String showType,String treeType)
	{	
		if(username.equals(""))
		{
			System.out.println("用户名不能为空值！");
			return null;
		}
		if(sourceConfigBean==null)
		{
			System.out.println("资源信息类为空。");
		}
		if(beanList==null)
		{
			System.out.println("条件LIST 为空！");
		}
		if(!showType.equals(""))
		{
			System.out.println("showType:"+showType);
		}
		if(!treeType.equals(""))
		{
			System.out.println("treeType:"+treeType);
		}
		GetFormTableName gftn 	= new GetFormTableName();
	    
		//取得属性配制中的sourceconfigtype的值，此值标识用户查询时是否查询技能授权表。
		String sourceconfigtype 		= gftn.GetFormName("sourcecofigtype");
		
		Document returnDoc 				= null;
		
		RoleInterfaceAssociate roleasso = new RoleInterfaceAssociate();
		
		//根据用户名查询用户ID，归属组ID。
		SysPeoplepo syspeoplepo = null; 
		String userid			= "";
		String userfullname 	= "";   
		String str 				= "";
		try
		{
			syspeoplepo 			= roleasso.getUserID(username);
		}catch(Exception e)
		{
			syspeoplepo = null;
			logger.info("根据用户英文名查询用户ID时出现异常！");
		}
		if(syspeoplepo!=null)
		{
			userid					= syspeoplepo.getC1();
			userfullname 			= syspeoplepo.getC630000003();
		}
		if(sourceConfigBean!=null)
		{
			str						= Function.nullString(sourceConfigBean.getSource_type());
		}
		System.out.println("用户ID："+userid);
		if(userid == null||userid.equals("")||userid.equals("null"))
		{
			System.out.println("用户名有误！");
			return null;
		}
		if(treeType.equals("1"))
		{
			//到组织结构目录树类中执行，生成组织目录树Document.
			SourceDirectoryInterface sourcedirectory = new SourceDirectoryInterface();
			returnDoc = sourcedirectory.getMenuTree(username,sourceConfigBean,beanList,showType);
		}
		else if(showType.equals("0")&&!str.equals(sourceconfigtype))
		{
			//遍历所有结点。
			returnDoc = getAllSourceElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(showType.equals("")&&!str.equals(sourceconfigtype))
		{
			//遍历所有结点。
			returnDoc = getAllSourceElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(showType.equals("1")&&!str.equals(sourceconfigtype))
		{
			//只查询一级节点。
			returnDoc = getOneLevelElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(str.equals(sourceconfigtype))
		{
			//只查询属性：(Menu资源)
			TemSourceTypeInterface typeinterface = new TemSourceTypeInterface();
			returnDoc = typeinterface.getMenuTree(userfullname,username,userid,sourceConfigBean,beanList,showType,treeType);
		}
		return returnDoc;
	}
	/**
	 * 根据参数得到Document对象。   
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param username             用户登陆名。
	 * @param userid			   用户ID，根据用户登陆名得到。
	 * @param sourceConfigBean     资源信息Bean类，
	 * @param beanList			   条件List
	 * @return Document			   Document对象。
	 */
	public Document getAllSourceElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		String moduleid = null;
		String tmpmoduleid	="";
		//添加根结点.
		try
		{
			addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		}
		catch(Exception e)
		{
			System.out.println("添加根结点时出现异常！");
		}
		//调用递归方法，遍历资源树。
		GetRole getRole = new GetRole();
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。
		String source_type = "";
		if(sourceConfigBean!=null)
		{
			source_type = sourceConfigBean.getSource_type();
		}
		if(list1!=null)
		{
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj = (Object[])its.next();
				Long id = (Long)obj[0];
				//获得资源所属模块ID。
				moduleid = (String)obj[1];
				if(moduleid.equals("")||moduleid.equals("null"))
				{
					moduleid = "0";
				}
				if(String.valueOf(moduleid).equals("0"))
				{
					tmpmoduleid	= String.valueOf(id);
				}
				else
				{
					tmpmoduleid	= moduleid;
				}
				GetRole getRole_l = new GetRole();
				List rolelist = getRole_l.getRoleRelationSourceInfo(userid,String.valueOf(id));
				if(rolelist!=null)
				{
					//遍历当前节点的所有父节点

					try
					{
						findLastElements(username,userid,id,doc,source_type,moduleid);
					}
					catch(Exception e1)
					{
						System.out.println("遍历父节点时出现异常！");
						
					}
					//****后修改1----start 添加了if判断当前ID节点是否存在，如果此节点有父并且已经遍历过父节点了，那么如果不在此进行
					//*****再一次判断，那么就会重复的添加同一<grand>节点内容。此判断解决了程序先执行了父，然后又执行子节点时，这时子
					//*****节点在程序操作父节点时已经添加了，再一次执行时就会重复一个<grand>节点
					Element tmp = doc.elementByID(username+String.valueOf(id));
					if(tmp!=null)
					{
						///
						Element e = (Element)tmp.selectSingleNode("grand");
						if(e==null)
						{
							//获得当前用户ID与资源ID的属性和技能授权。
							try
							{
								addCommissionGrand(username,doc,userid,String.valueOf(id),source_type);
							}
							catch(Exception e2)
							{
								System.out.println("获得当前用户与资源属性和技能授权时出现异常！");
							}
							//遍历当前节点下的所有节点.
							try
							{
								findPID(username,userid,id,doc,source_type);
							}
							catch(Exception e3)
							{
								System.out.println("遍历当前节点的子节点时出现异常！");
							}
						}
					}
				}
			}
		}
		if(!String.valueOf(tmpmoduleid).equals("")&&!String.valueOf(tmpmoduleid).equals("null"))
		{
			//
			List commissionlist = null;
			//根据代办人ID，资源moduleID，查询role、sourceconfig表，取出所有代办人信息。
			try
			{
				commissionlist = getRole.getCommissionInfo(userid,tmpmoduleid);
			}catch(Exception e)
			{
				System.out.println("根据用户ID，模块ID查询技能表，查询代办人时出现异常！");
				commissionlist = null;
			}
			if(commissionlist!=null)
			{
				Iterator commissionit = commissionlist.iterator();
				while(commissionit.hasNext())
				{
					Object[] obj 				= (Object[])commissionit.next();
					SysSkillpo sysskill 		= (SysSkillpo)obj[0];
					Sourceconfig sourceconfig 	= (Sourceconfig)obj[1];
					String roleskillname 		= "";
					String userfullname1 		= "";
					String rolesourceid  		= ""; 
					String roleskillid 			= sysskill.getC610000007();
					SysPeoplepo peoplepo 		= null;
					try
					{
						peoplepo = RoleInterfaceAssociate.findModify(roleskillid);
					}
					catch(Exception e)
					{
						System.out.println("根据用户ID查询用户信息时出现异常！");
						peoplepo = null;
					}
					if(peoplepo !=null)
					{
						roleskillname = peoplepo.getC630000001();
						userfullname1 = peoplepo.getC630000003();
						rolesourceid = sysskill.getC610000008();
					}
					Element roleElement = doc.elementByID(roleskillid);
					if(roleElement==null)
					{
						Element root = doc.getRootElement();
						roleElement = root.addElement("username");
						roleElement.addAttribute("userid",roleskillid);
						roleElement.addAttribute("ID",roleskillid);
						roleElement.addAttribute("loginname",roleskillname);
						roleElement.addAttribute("userfullname",userfullname1);
						findLastElements(roleskillname,roleskillid,new Long(rolesourceid),doc,source_type,moduleid);
					}
					
					//查询资源ID的技能授权。
					addCommissionGrand(roleskillname,doc,roleskillid,rolesourceid,source_type);
				}
			}
		}
		return doc;
	}
	
	//遍历当前节点的所有父节点。
	public void findLastElements(String username,String userid,Long id,Document doc,String sourceType,String moduleid)
	{
		Element currentElement = doc.elementByID(username+String.valueOf(id));
		if(currentElement==null)
		{
			//先得到ID的父ID，如果等于moduleid加授权，加属性，否则递归。
			RoleInterfaceAssociate roleasso 	= new RoleInterfaceAssociate();
			Sourceconfig sourceinfo 			= new Sourceconfig();
			try
			{
				sourceinfo 							= roleasso.getSourceInfo(id);
			}
			catch(Exception e)
			{
				System.out.println("根据资源ID查询资源信息时出现异常！");
				sourceinfo = null;
			}
			if(sourceinfo!=null)
			{
				Long   parentid 		= sourceinfo.getSourceParentid();
				String parentid_str 	= Function.nullString(String.valueOf(parentid));
				if(parentid_str!=null&&!parentid_str.equals(""))
				{
					if(parentid_str.equals("0"))
					{
						Element moduleElement = doc.elementByID(userid);
						if(moduleElement!=null)
						{
							//添加moduleid节点 
							Element module_e = moduleElement.addElement("source");
							
							module_e.addAttribute("source_id",String.valueOf(sourceinfo.getSourceId()));
							module_e.addAttribute("ID",username+String.valueOf(sourceinfo.getSourceId()));
							module_e.addAttribute("parentid", String.valueOf(sourceinfo.getSourceParentid()));
							module_e.addAttribute("source_cnname", String.valueOf(sourceinfo.getSourceCnname()));
							module_e.addAttribute("source_enname", String.valueOf(sourceinfo.getSourceName()));
							addAttribute(module_e,id,null);
						}
					}
					else
					{
						Element m = doc.elementByID(username+parentid_str);
						if(m!=null)
						{
							Element mm = m.addElement("source");
							mm.addAttribute("source_id",String.valueOf(sourceinfo.getSourceId()));
							mm.addAttribute("ID",username+String.valueOf(sourceinfo.getSourceId()));
							mm.addAttribute("parentid", String.valueOf(sourceinfo.getSourceParentid()));
							mm.addAttribute("source_cnname", String.valueOf(sourceinfo.getSourceCnname()));
							mm.addAttribute("source_enname", String.valueOf(sourceinfo.getSourceName()));
						}else
						{
							findLastElements(username,userid,parentid,doc,sourceType,moduleid);
							m = doc.elementByID(username+parentid_str);
							Element mm = m.addElement("source");
							mm.addAttribute("source_id",String.valueOf(sourceinfo.getSourceId()));
							mm.addAttribute("ID",username+String.valueOf(sourceinfo.getSourceId()));
							mm.addAttribute("parentid", String.valueOf(sourceinfo.getSourceParentid()));
							mm.addAttribute("source_cnname", String.valueOf(sourceinfo.getSourceCnname()));
							mm.addAttribute("source_enname", String.valueOf(sourceinfo.getSourceName()));
						}
					}
				}
			}
		}
	}
	
	//递归查询资源ID，遍历资源ID。
	public void findPID(String username,String userid,Long id,Document doc,String sourceType) 
	{
		SourceConfigInfo t1Info = new SourceConfigInfo();
		List t1ParentList 		= null;
		if (id != null) 
		{
			try 
			{
				//查询T1表，查看以当前ID为父ID的所有记录。
				t1ParentList = t1Info.GetsourceCFGInfo(id);
			} 
			catch (Exception e) 
			{
				System.out.println("查询T1表，查看以当前ID为父ID的所有记录 异常！");
				t1ParentList = null;
			}
			if(t1ParentList!=null)
			{
				Iterator parent_it = t1ParentList.iterator();
				while (parent_it.hasNext()) 
				{
					Sourceconfig sourceconfig 	= (Sourceconfig) parent_it.next();
					Long p_id 					= sourceconfig.getSourceId();
					Element tmp = doc.elementByID(username+String.valueOf(p_id));
					//****后修改部分2--start 添加了if判断，解决了重复的第二种情况，就是子节点已经添加完成，当添加到父节点再次遍
					//****到当前节点时，看当前节点是否已经判断完了，如果存在证明已经处理过，不再处理了。否则会出现重复的<grand>节点。
					if(tmp==null)
					{
						SourceConfigInfo p1Info 	= new SourceConfigInfo();
						try 
						{
							//查询T1表，查看是否存在以当前ID为父ID的记录。如果有，先添加当前ID信息到Document对象中，然后再遍历。
							//如果没有，添加当前ID信息到Document对象中，添加属性和动作。
							List plist 				= p1Info.GetsourceCFGInfo(p_id);
							if (plist!=null)
							{	
								GetRole getRole 	= new GetRole();
								//添加技能动作部分。关联查询（即得到属性，又得到动作。）
								List rolelist 		= getRole.getRoleRelationSourceInfo(userid,String.valueOf(p_id));
								if(rolelist!=null)
								{
									//调用方法添加属性和技能动作。
									try
									{
										getS(username,doc,sourceconfig,rolelist,sourceType);
									}
									catch(Exception e)
									{
										System.out.println("调用添加属性和技能动作方法时出现异常！");
									}
								}
								//递归的调用。
								try
								{
									findPID(username,userid,p_id,doc,sourceType);
								}
								catch(Exception e)
								{
									System.out.println("递归调用出现异常！");
								}
							} else 
							{
								GetRole getRole 	= new GetRole();
								//添加技能动作部分。关联查询（即得到属性，又得到动作。）
								List rolelist = getRole.getRoleRelationSourceInfo(userid,String.valueOf(p_id));
								if(rolelist!=null)
								{
									//调用方法添加属性和技能动作。
									try
									{
										getS(username,doc,sourceconfig,rolelist,sourceType);
									}
									catch(Exception e)
									{
										System.out.println("调用添加属性和技能动作方法时出现异常！");
									}
								}
							}
						} catch (Exception e) 
						{
							System.out.println(e.getMessage());
							logger.info("Exception:");
						}
					}
				}
			}
		}
	}
	//根据参数添加属性与技能动作。(参数List 为在技能授权表中以用户ID与资源ID查询后返回的结果集。)
	public void getS(String username,Document doc,Sourceconfig sourceConfig,List list,String sourceType) 
	{
		String id			= "";
		String parentid 	= "";
		String cnname 		= "";
		String enname 		= "";
		if(sourceConfig!=null)
		{
			 id			= Function.nullString(String.valueOf(sourceConfig.getSourceId()));
			 parentid 	= Function.nullString(String.valueOf(sourceConfig.getSourceParentid()));
			 cnname 		= Function.nullString(sourceConfig.getSourceCnname());
			 enname 		= Function.nullString(sourceConfig.getSourceName());
		}
		Element t 			= null;
		Element value 		= null;
		t 					= doc.elementByID(username+parentid);
		if(t!=null)
		{
			if(list!=null)
			{
				Iterator it = list.iterator();
				while(it.hasNext())
				{
					Object[] object 			= (Object[])it.next();
					SysSkillpo sysskillpo 		= (SysSkillpo)object[0];
					Sourceconfig sourceconfig 	= (Sourceconfig)object[1];
					String sourceid 			= String.valueOf(sourceconfig.getSourceId());
					Element currentElement 		= doc.elementByID(username+sourceid);
					if(currentElement==null)
					{
						currentElement = t.addElement("source");
						currentElement.addAttribute("ID",username+sourceid);
						currentElement.addAttribute("source_id", sourceid);
						currentElement.addAttribute("parentid", String.valueOf(sourceconfig.getSourceParentid()));
						currentElement.addAttribute("source_cnname", String.valueOf(sourceconfig.getSourceCnname()));
						currentElement.addAttribute("source_enname", String.valueOf(sourceconfig.getSourceName()));
					}
						//调用添加属性的方法，添加属性。
						Long source_id 				= sourceconfig.getSourceId();
						String source_type 			= sourceType;
						addAttribute(currentElement,source_id,source_type);
						//添加技能动作（grand）
						Element grand 				= currentElement.addElement("grand");
						Element role				= grand.addElement("Skill_UserID");
						role.setText(Function.nullString(sysskillpo.getC610000007()));
						Element query 				= grand.addElement("Skill_CategoryQueryID");
						query.setText(Function.nullString(sysskillpo.getC610000009()));
						Element action 				= grand.addElement("Skill_Action");
						action.setText(Function.nullString(sysskillpo.getC610000010()));
						Element commissionGID 		= grand.addElement("Skill_CommissionGID");
						commissionGID.setText(Function.nullString(sysskillpo.getC610000012()));
						Element commissionUID 		= grand.addElement("Skill_CommissionUID");
						commissionUID.setText(Function.nullString(sysskillpo.getC610000014()));
						Element commissionclosetime = grand.addElement("Skill_CommissionCloseTime");
						commissionclosetime.setText(Function.nullString(String.valueOf(sysskillpo.getC610000015())));
				}
			}
		}
	}
	public void addCommissionGrand(String username,Document doc,String userid,String sourceid,String sourceType)
	{
		GetRole getRole 	= new GetRole();
		Element srcelement 	= doc.elementByID(username+sourceid);
		if(srcelement!=null)
		{
			addAttribute(srcelement,new Long(sourceid),sourceType);
			List rolelist = null;
			try
			{
				rolelist = getRole.getRoleRelationSourceInfo(userid,String.valueOf(sourceid));
			}
			catch(Exception e)
			{
				rolelist = null;
				System.out.println("根据用户ID与资源ID查询技能表时出现异常！");
			}
			if(rolelist!=null)
			{
				Iterator it = rolelist.iterator();
				while(it.hasNext())
				{
					Object[] object 			= (Object[])it.next();
					SysSkillpo sysskillpo 		= (SysSkillpo)object[0];
					Sourceconfig sourceconfig 	= (Sourceconfig)object[1];
					Element grand 				= srcelement.addElement("grand");
					Element role				= grand.addElement("Skill_UserID");
					role.setText(Function.nullString(sysskillpo.getC610000007()));
					Element query 				= grand.addElement("Skill_CategoryQueryID");
					query.setText(Function.nullString(sysskillpo.getC610000009()));
					Element action 				= grand.addElement("Skill_Action");
					action.setText(Function.nullString(sysskillpo.getC610000010()));
					Element commissionGID 		= grand.addElement("Skill_CommissionGID");
					commissionGID.setText(Function.nullString(sysskillpo.getC610000012()));
					Element commissionUID 		= grand.addElement("Skill_CommissionUID");
					commissionUID.setText(Function.nullString(sysskillpo.getC610000014()));
					Element commissionclosetime = grand.addElement("Skill_CommissionCloseTime");
					commissionclosetime.setText(Function.nullString(String.valueOf(sysskillpo.getC610000015())));
				}
			}
		}
	}
	
	//获得一级节点的方法。
	public Document getOneLevelElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		String moduleid = "";
		String source_type = "";
		if(sourceConfigBean!=null)
		{
			source_type = sourceConfigBean.getSource_type();
		}
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。		
		//首先遍历list1然后添加每一资源ID的技能动作。。
		if(list1!=null)
		{
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj = (Object[])its.next();
				Long id = (Long)obj[0];
				//获得资源所属模块ID。
				moduleid = (String)obj[1];
				if(moduleid.equals("")||moduleid.equals("null"))
				{
					moduleid = "0";
				}
				//遍历当前节点的所有父节点
				Element usrelement = doc.elementByID(userid);
				if(usrelement!=null)
				{
					Element eleme = doc.elementByID(username+String.valueOf(id));
					if(eleme==null)
					{
						RoleInterfaceAssociate roleasso = new RoleInterfaceAssociate();
						Sourceconfig p1Info = roleasso.getSourceInfo(id);
						
						Element e = usrelement.addElement("source");
						e.addAttribute("ID",username+String.valueOf(id));
						e.addAttribute("source_id",String.valueOf(id));
						e.addAttribute("parentid",String.valueOf(p1Info.getSourceParentid()));
						e.addAttribute("source_cnname",p1Info.getSourceCnname());
						e.addAttribute("source_enname",p1Info.getSourceName());
					}
					addCommissionGrand(username,doc,userid,String.valueOf(id),source_type);
				}
			}
		}
		GetRole getRole 	= new GetRole();
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。
		//首先遍历list1然后添加每一个资源ID下一级的技能动作。
		if(list1!=null)
		{
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj = (Object[])its.next();
				Long id = (Long)obj[0];
				//获得资源所属模块ID。
				moduleid = (String)obj[1];
				System.out.println("..id:"+id);
				//获得要添加一级节点的父节点，
				Element oneLevel = doc.elementByID(username+String.valueOf(id));
				if(oneLevel!=null)
				{	SourceConfigInfo p1Info = new SourceConfigInfo();
					List plist	= null;
					try
					{
						plist 				= p1Info.GetsourceCFGInfo(id);
					}catch(Exception e)
					{
						System.out.println("Exception");
					}
					if (plist!=null)
					{
						Iterator it1 = plist.iterator();
						while(it1.hasNext())
						{
							Sourceconfig tmpinfo = (Sourceconfig)it1.next();
							Long tmpid = tmpinfo.getSourceId();
						
							//GetRole getRole = new GetRole();
							List list = null;
							try
							{
								list = getRole.getRoleRelationSourceInfo(userid,String.valueOf(tmpid));
							}catch(Exception e)
							{
								logger.info("用户ID与资源ID在技能表中无法查出。");
								list = null;
							}
							if(list!=null)
							{
								Iterator it = list.iterator();
								while(it.hasNext())
								{
									Object[] object 			= (Object[])it.next();
									SysSkillpo sysskillpo 		= (SysSkillpo)object[0];
									Sourceconfig sourceconfig 	= (Sourceconfig)object[1];
									String sourceid 			= String.valueOf(sourceconfig.getSourceId());
									Element currentElement 		= doc.elementByID(sourceid);
									if(currentElement==null)
									{
										currentElement = oneLevel.addElement("source");
										currentElement.addAttribute("ID",username+sourceid);
										currentElement.addAttribute("source_id", sourceid);
										currentElement.addAttribute("parentid", String.valueOf(sourceconfig.getSourceParentid()));
										currentElement.addAttribute("source_cnname", String.valueOf(sourceconfig.getSourceCnname()));
										currentElement.addAttribute("source_enname", String.valueOf(sourceconfig.getSourceName()));
									}
									//调用添加属性的方法，添加属性。
									Long source_id = sourceconfig.getSourceId();
									addAttribute(currentElement,source_id,source_type);
									
									//添加技能动作（grand）
									Element grand 				=currentElement.addElement("grand");
									Element role				= grand.addElement("Skill_UserID");
									role.setText(Function.nullString(sysskillpo.getC610000007()));
									Element query 				= grand.addElement("Skill_CategoryQueryID");
									query.setText(Function.nullString(sysskillpo.getC610000009()));
									Element action 				= grand.addElement("Skill_Action");
									action.setText(Function.nullString(sysskillpo.getC610000010()));
									Element commissionGID 		= grand.addElement("Skill_CommissionGID");
									commissionGID.setText(Function.nullString(sysskillpo.getC610000012()));
									Element commissionUID 		= grand.addElement("Skill_CommissionUID");
									commissionUID.setText(Function.nullString(sysskillpo.getC610000014()));
									Element commissionclosetime = grand.addElement("Skill_CommissionCloseTime");
									commissionclosetime.setText(Function.nullString(String.valueOf(sysskillpo.getC610000015())));
									}
								}
							}
						}
					}
				}
		}
		////***********////
		
		if(moduleid!=null)
		{
			//根据代办人ID，资源moduleID，查询role、sourceconfig表，取出所有代办人信息。
			System.out.println("代办人ID"+userid);
			System.out.println("资源模块类别："+moduleid);
			List commissionlist = null;
			try
			{
				commissionlist = getRole.getCommissionInfo(userid,moduleid);
			}catch(Exception e)
			{
				commissionlist = null;
				System.out.println("根据用户ID与资源ID查询技能表，查询代办人信息时出现异常！");
			}
			if(commissionlist!=null)
			{
				Iterator commissionit = commissionlist.iterator();
				while(commissionit.hasNext())
				{
					Object[] obj 				= (Object[])commissionit.next();
					SysSkillpo sysskill 		= (SysSkillpo)obj[0];
					Sourceconfig sourceconfig 	= (Sourceconfig)obj[1];
					String roleskillname = "";
					String roleskillid 	= sysskill.getC610000007();
					SysPeoplepo peoplepo = RoleInterfaceAssociate.findModify(roleskillid);
					roleskillname = peoplepo.getC630000001();
					String userfullname1 = peoplepo.getC630000003();
					String rolesourceid = sysskill.getC610000008();
					Element roleElement = doc.elementByID(roleskillid);
					if(roleElement==null)
					{
						Element root = doc.getRootElement();
						roleElement = root.addElement("username");
						roleElement.addAttribute("userid",roleskillid);
						roleElement.addAttribute("ID",roleskillid);
						roleElement.addAttribute("loginname",roleskillname);
						roleElement.addAttribute("userfullname",userfullname1);
						Element sourceelement = roleElement.addElement("source");
						sourceelement.addAttribute("ID",roleskillname+String.valueOf(sourceconfig.getSourceId()));
						sourceelement.addAttribute("source_id",String.valueOf(sourceconfig.getSourceId()));
						sourceelement.addAttribute("parentid",String.valueOf(sourceconfig.getSourceParentid()));
						sourceelement.addAttribute("source_cnname",sourceconfig.getSourceCnname());
						sourceelement.addAttribute("source_enname",sourceconfig.getSourceName());
					}
					
					//查询资源ID的技能授权。
					addCommissionGrand(roleskillname,doc,roleskillid,rolesourceid,source_type);
				}
			}
		}
		
		return doc;
	}
	//添加根结点及根结点的属性，技能动作的方法。
	public void addRootElement(String userfullname,String username,String userid,Document doc,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		GetRole getRole 	= new GetRole();
		Element root1 		= doc.addElement("treeinfo");
		Element root		= root1.addElement("username");
		root.addAttribute("userid",userid);
		root.addAttribute("ID",userid);
		root.addAttribute("loginname",username);
		root.addAttribute("userfullname",userfullname);
		try
		{
			list1 = getRole.getFlag(sourceConfigBean,beanList);
			if(list1!=null)
			{
				System.out.println("从T1，T2，T3表中关联查询共得到:"+list1.size()+"条数据！");
			}
		}
		catch(Exception e)
		{
			System.out.println("从T1,T2,T3表中查询数据异常！");
		}
	}
	//添加属性的方法。
	public void addAttribute(Element element,Long sourceid,String type)
	{
		GetAttInfoList getAttInfo 	= new GetAttInfoList();
		List rootAtt 				= null;
		try
		{
			rootAtt = getAttInfo.getAttValue(sourceid,type);
		}
		catch(Exception e)
		{
			logger.info("资源ID与用户参数(type)无法在T1中查出。");
			rootAtt = null;
		}
		if(rootAtt!=null)
		{
			Element property = (Element)element.selectSingleNode("property");
			
			if(property==null)
			{
				property = element.addElement("property");
				Iterator it = rootAtt.iterator();
				while(it.hasNext())
				{
					Object[] object 			= (Object[])it.next();
					Sourceconfig t1 			= (Sourceconfig)object[0];
					Sourceconfigattribute t2 	= (Sourceconfigattribute)object[1]; 
					Sourceattributevalue t3		= (Sourceattributevalue)object[2];
					Element url 				= property.addElement(t2.getSourceattEnname());
					url.setText(Function.nullString(t3.getSourceattvalueValue()));
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		//List getFlag(SourceConfigBean sourceConfigBean, List beanList)
		SourceConfigInfoBean sourceconfigBean = new SourceConfigInfoBean();
//		sourceconfigBean.setSource_id("187;");
		sourceconfigBean.setSource_parentid(new Long(232));
//		sourceconfigBean.setSource_type("4;");
		
		SourceAttQueryBean sourceatt = new SourceAttQueryBean();
		sourceatt.setsource_attname("source_type");
		sourceatt.setsource_attnamevalue("1");
		sourceatt.setsource_attqueryop("=");
		List list1 = new ArrayList();
		list1.add(sourceatt);
		GetRole getRole = new GetRole();
		TemSourceInterface sourceRoleGrandInterface = new TemSourceInterface();
		
		try 
		{
			Document doc = sourceRoleGrandInterface.getMenuTree("Demo", sourceconfigBean,list1,"1","0");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/testaaa.xml")));
			writer.write(doc);
			writer.close();
		} catch (Exception e1) 
		{
			System.out.println("Excepiton");
			e1.getMessage();
		}
	}
	
	
}
