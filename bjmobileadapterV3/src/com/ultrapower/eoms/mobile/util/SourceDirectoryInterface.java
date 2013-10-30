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
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;

public class SourceDirectoryInterface 
{

	static final Logger logger 	= (Logger) Logger.getLogger(SourceDirectoryInterface.class);
	
	String userid 	= "";
	String showtype = "";
	String strtype  = "";

	
	public Document getMenuTree(String username, SourceConfigInfoBean sourceConfigBean,List beanList,String showType)
	{	
		showtype = showType;
		if(username.equals(""))
		{
			logger.error("用户名不能为空值！");
			return null;
		}
		if(sourceConfigBean==null)
		{
			logger.error("资源信息类为空。");
		}
		if(beanList==null)
		{
			logger.info("条件LIST 为空！");
		}
		if(!showType.equals(""))
		{
			logger.info("showType:"+showType);
		}

		GetFormTableName gftn 	= new GetFormTableName();
	    
		//取得属性配制中的sourceconfigtype的值，此值标识用户查询时是否查询技能授权表。
		String sourceconfigtype 		= gftn.GetFormName("sourcecofigtype");
		Document returnDoc 				= null;
		RoleInterfaceAssociate roleasso = new RoleInterfaceAssociate();
		//根据用户名查询用户ID，归属组ID。
		SysPeoplepo syspeoplepo = null;
		try
		{
			 syspeoplepo         = roleasso.getUserID(username);
		}
		catch(Exception e)
		{
			logger.info("SourceRoleGrandInterface.getMenuTree()根据用户英文名查询用户ID出现异常！");
			syspeoplepo = null;
		}
		
		String userfullname = "";
		
		if(syspeoplepo!=null)
		{
			userid 			=  syspeoplepo.getC1();
			userfullname 	= syspeoplepo.getC630000003();
		}
		
		if(sourceConfigBean!=null)
		{
			strtype = Function.nullString(sourceConfigBean.getSource_type());
		}
		
		if(userid == null)
		{
			logger.error("用户名有误！");
			return null;
		}
		//根据用户ID，资源Bean，条件List查询出组信息，资源信息，技能信息。
		List getGroupSourceRoleList = null;
		try
		{
			getGroupSourceRoleList = roleasso.getSource(userid,sourceConfigBean,beanList);
		}
		catch(Exception e)
		{
			logger.error("关联查询组信息，资源信息，技能信息出现异常！");
			getGroupSourceRoleList = null;
		}
		if(strtype.equals(sourceconfigtype))
		{
			//调用只显示属性的方法。
			SourceDirectoryTypeInterface sourceDirectoryInterface = new SourceDirectoryTypeInterface();
			returnDoc = sourceDirectoryInterface.getMenuTree(username,sourceConfigBean,beanList,showType);
		}
		else if(getGroupSourceRoleList!=null&&showType.endsWith("0"))
		{
			//返回所有节点的值。
			returnDoc = getGrandProperty(getGroupSourceRoleList,strtype);
		}
		else if(getGroupSourceRoleList!=null&& showType.equals("1"))
		{
			//仅返回当前节点的一级节点的值。
			returnDoc = getOneLevel(getGroupSourceRoleList,strtype);
		}
		if(returnDoc == null)
		{
			logger.error("您所输入的条件在数据库中无法查询出相应的数据。");
		}
		return returnDoc;
	}
	//获得组织结构目录树(list:关联查询组信息，资源信息，技能信息得到的集合,type:为查询属性时用到的).
	public Document getGrandProperty(List list,String type)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		Element root 	= doc.addElement("treeInfo");
		
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				Object[] obj 				= (Object[])it.next();
				Sourceconfig sourceconfig 	= (Sourceconfig)obj[0];
				SysGrouppo sysgroup      	= (SysGrouppo)obj[1];
				SysSkillpo sysskillpo		= (SysSkillpo)obj[2];
				
				String groupid 				= Function.nullString(sysgroup.getC1());
				String groupname 			= Function.nullString(sysgroup.getC630000018());
				Long   sourceid 			= sourceconfig.getSourceId();
				String groupmoduleid 		= Function.nullString(sourceconfig.getSourceModule());
				//将组信息添加到根节点。
				addGroupElement(sysgroup,doc);
				Element groupelement = doc.elementByID(Function.nullString(sysgroup.getC1()));
				if(groupelement!=null)
				{
					Element sourceelement = doc.elementByID(groupid+String.valueOf(sourceid));
					//将资源及资源的父资源信息添加到组信息节点上。
					if(sourceelement ==null)
					{
						addParentElements(groupname,groupid,sourceid,doc,groupmoduleid);
						if(showtype.equals("0"))
						{
							//遍历了节点，将子节点信息添加到Document对象中。
							try
							{
								findPID(groupname,groupid,sourceid,doc,strtype);
							}
							catch(Exception e)
							{
								logger.error("getGrandProperty调用findPID遍历子节点时出现异常！");
							}
						}
					}
					sourceelement = doc.elementByID(groupid+String.valueOf(sourceid));
					//判断资源ID是否存在，如果存在将资源属性节点('property')添加到资源ID下。
					if(sourceelement!=null)
					{	
						//为当前资源节点添加属性。
						addAttribute(sourceelement,sourceid,type);
						
						//添加技能动作（grand）
						Element grand 				= sourceelement.addElement("grand");
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

						//添加当前节点的子节点。
						
					}
					
				}
				
			}
		}
		
		return doc;
	}
	
	//获得组织目录树一级节点的方法。
	public Document getOneLevel(List list,String type)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		Element root 	= doc.addElement("treeInfo");
		
		if(list!=null)
		{
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				Object[] obj 				= (Object[])it.next();
				Sourceconfig sourceconfig 	= (Sourceconfig)obj[0];
				SysGrouppo sysgroup      	= (SysGrouppo)obj[1];
				SysSkillpo sysskillpo		= (SysSkillpo)obj[2];
				
				String groupid 				= Function.nullString(sysgroup.getC1());
				String groupname 			= Function.nullString(sysgroup.getC630000018());
				Long sourceid 				= sourceconfig.getSourceId();
				Long parentid				= sourceconfig.getSourceParentid();
				String source_cn			= sourceconfig.getSourceCnname();
				String source_en 			= sourceconfig.getSourceName();
				String groupmoduleid 		= Function.nullString(sourceconfig.getSourceModule());
				
				//将组信息添加到根节点。
				addGroupElement(sysgroup,doc);
				Element groupelement = doc.elementByID(Function.nullString(sysgroup.getC1()));
				if(groupelement!=null)
				{
					Element sourceelement = doc.elementByID(groupid+String.valueOf(sourceid));
					//将资源及资源的父资源信息添加到组信息节点上。
					if(sourceelement ==null)
					{
						sourceelement = groupelement.addElement("source");
						sourceelement.addAttribute("source_id",String.valueOf(sourceid));
						sourceelement.addAttribute("ID",groupid+String.valueOf(sourceid));
						sourceelement.addAttribute("parentid",String.valueOf(parentid));
						sourceelement.addAttribute("source_cnname",source_cn);
						sourceelement.addAttribute("source_enname",source_en);
						//添加property节点。
						//为当前资源节点添加属性。
						addAttribute(sourceelement,sourceid,type);
						//添加grand节点.
						//添加技能动作（grand）
						Element grand 				= sourceelement.addElement("grand");
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
						
						//添加当前节点的一级节点
						addOneLevelElement(doc,groupid,sourceid,type);
						
					}else
					{
						//添加grand节点.
						//添加技能动作（grand）
						Element grand 				= sourceelement.addElement("grand");
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
		return doc;
	}
	//将组节点添加到根节点上。
	public void addGroupElement(SysGrouppo sysgroup,Document doc)
	{
		if(sysgroup!=null)
		{
			String groupid = Function.nullString(sysgroup.getC1());
			String groupname = Function.nullString(sysgroup.getC630000018());
			Element groupelement = doc.elementByID(groupid);
			if(groupelement==null)
			{
				Element root = doc.getRootElement();
				groupelement = root.addElement("group");
				groupelement.addAttribute("groupid",groupid);
				groupelement.addAttribute("ID",groupid);
				groupelement.addAttribute("groupname",groupname);
			}
		}
	}
	
	//遍历当前节点的所有父节点。
	public void addParentElements(String groupname,String groupid,Long sourceid,Document doc,String moduleid)
	{
		Element currentElement = doc.elementByID(groupid+String.valueOf(sourceid));
		if(currentElement==null)
		{
			//先得到ID的父ID，如果等于moduleid加授权，加属性，否则递归。
			RoleInterfaceAssociate roleasso 	= new RoleInterfaceAssociate();
			Sourceconfig sourceinfo 			= new Sourceconfig();
			try
			{
				sourceinfo 						= roleasso.getSourceInfo(sourceid);
			}
			catch(Exception e)
			{
				logger.info("sourceDirectoryInterface.findLastElements()查询资源信息时出现异常！");
				System.out.println("sourceDirectoryInterface.findLastElements()查询资源信息时出现异常！");
				sourceinfo = null;
			}
			if(sourceinfo!=null)
			{
				Long   parentid 		= sourceinfo.getSourceParentid();
				String parentid_str 	= Function.nullString(String.valueOf(parentid));
				if(parentid_str.equals("")||parentid_str.equals("null"))
				{
					parentid_str = "0";
				}
				if(parentid_str!=null&&!parentid_str.equals(""))
				{
					if(parentid_str.equals("0"))
					{
						Element moduleElement = doc.elementByID(groupid);
						if(moduleElement!=null)
						{
							//添加moduleid节点 (类别根结点)
							Element module_e = moduleElement.addElement("source");
							
							module_e.addAttribute("source_id",String.valueOf(sourceinfo.getSourceId()));
							module_e.addAttribute("ID",groupid+String.valueOf(sourceinfo.getSourceId()));
							module_e.addAttribute("parentid", String.valueOf(sourceinfo.getSourceParentid()));
							module_e.addAttribute("source_cnname", String.valueOf(sourceinfo.getSourceCnname()));
							module_e.addAttribute("source_enname", String.valueOf(sourceinfo.getSourceName()));
						}
					}
					else
					{
						addParentElements(groupname,groupid,parentid,doc,moduleid);
						Element m 	= doc.elementByID(groupid+parentid_str);
						Element mm 	= m.addElement("source");
						mm.addAttribute("source_id",String.valueOf(sourceinfo.getSourceId()));
						mm.addAttribute("ID",groupid+String.valueOf(sourceinfo.getSourceId()));
						mm.addAttribute("parentid", String.valueOf(sourceinfo.getSourceParentid()));
						mm.addAttribute("source_cnname", String.valueOf(sourceinfo.getSourceCnname()));
						mm.addAttribute("source_enname", String.valueOf(sourceinfo.getSourceName()));
					}
				}
			}
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
			System.out.println("资源ID与用户参数(type)在T1中查询出现异常！");
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
	
	//递归查询资源ID，遍历资源ID。
	public void findPID(String groupname,String groupid,Long sourceid,Document doc,String sourceType) 
	{
		SourceConfigInfo t1Info = new SourceConfigInfo();
		List t1ParentList 		= null;
		if (sourceid != null) 
		{
			try 
			{
				//查询T1表，查看以当前ID为父ID的所有记录。
				t1ParentList = t1Info.GetsourceCFGInfo(sourceid);
			} catch (Exception e) 
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
					SourceConfigInfo p1Info 	= new SourceConfigInfo();
					try 
					{
						//查询T1表，查看是否存在以当前ID为父ID的记录。如果有，先添加当前ID信息到Document对象中，然后再遍历。
						//如果没有，添加当前ID信息到Document对象中，添加属性和动作。
						List plist = null;
						try
						{
							plist = p1Info.GetsourceCFGInfo(p_id);
						}catch(Exception e)
						{
							System.out.println("以当前ID查询当前ID的子ID时出现异常!");
							plist = null;
						}
						
						if (plist!=null&&plist.size()>0)
						{	
							RoleInterfaceAssociate getRole 	= new RoleInterfaceAssociate();
							//添加技能动作部分。关联查询（即得到属性，又得到动作。）
							List rolelist = null;
							try
							{
								rolelist = getRole.getRoleRelationSourceInfo(userid,String.valueOf(p_id));
							}
							catch(Exception e)
							{
								System.out.println("getRole.getRoleRelationSourceInfo(userid,String.valueOf(p_id))异常");
								rolelist = null;
							}
							if(rolelist!=null)
							{
								//调用方法添加属性和技能动作。
								try
								{
									getS(groupname,groupid,doc,sourceconfig,rolelist,sourceType);
								}
								catch(Exception e)
								{
									System.out.println("调用方法添加属性和技能动作出现异常！");
								}
							}
							//递归的调用。
							findPID(groupname,groupid,p_id,doc,sourceType);
						} else 
						{
							RoleInterfaceAssociate getRole 	= new RoleInterfaceAssociate();
							//添加技能动作部分。关联查询（即得到属性，又得到动作。）
							List rolelist = getRole.getRoleRelationSourceInfo(userid,String.valueOf(p_id));
							if(rolelist!=null)
							{
								//调用方法添加属性和技能动作。
								try
								{
									getS(groupname,groupid,doc,sourceconfig,rolelist,sourceType);
								}
								catch(Exception e)
								{
									System.out.println("调用方法添加属性和技能动作出现异常！");
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
	//根据参数添加属性与技能动作。(参数List 为在技能授权表中以用户ID与资源ID查询后返回的结果集。)
	public void getS(String groupname,String groupid,Document doc,Sourceconfig sourceConfig,List list,String sourceType) 
	{
		
		String id			= Function.nullString(String.valueOf(sourceConfig.getSourceId()));
		String parentid 	= Function.nullString(String.valueOf(sourceConfig.getSourceParentid()));
		String cnname 		= Function.nullString(sourceConfig.getSourceCnname());
		String enname 		= Function.nullString(sourceConfig.getSourceName());
		Element t 			= null;
		Element value 		= null;
		t 					= doc.elementByID(groupid+parentid);
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
					Element currentElement 		= doc.elementByID(groupid+sourceid);
					if(currentElement==null)
					{
						currentElement = t.addElement("source");
						currentElement.addAttribute("ID",groupid+sourceid);
						currentElement.addAttribute("source_id", sourceid);
						currentElement.addAttribute("parentid", String.valueOf(sourceconfig.getSourceParentid()));
						currentElement.addAttribute("source_cnname", String.valueOf(sourceconfig.getSourceCnname()));
						currentElement.addAttribute("source_enname", String.valueOf(sourceconfig.getSourceName()));
					}
						//调用添加属性的方法，添加属性。
						Long source_id = sourceconfig.getSourceId();
						String source_type = sourceType;
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
	
	//根据资源ID查询出此资源ID的下一级节点。
	public void addOneLevelElement(Document doc,String groupid,Long p_sourceid,String type)
	{
		GetRole getRole = new GetRole();
		//获得要添加一级节点的父节点，
		Element oneLevel = doc.elementByID(groupid+String.valueOf(p_sourceid));
		if(oneLevel!=null)
		{	SourceConfigInfo p1Info = new SourceConfigInfo();
			List plist	= null;
			try
			{
				//根据父ID等于当前ID查询记录，返回结果放在LIST中。
				plist 				= p1Info.GetsourceCFGInfo(p_sourceid);
			}
			catch(Exception e)
			{
				System.out.println("Exception in SourceRoleGrandInterface.getOneLevelElement()中"+
						"查询以当前ID为父ID的所有记录时出现异常。");
				logger.info("Exception in SourceRoleGrandInterface.getOneLevelElement()中"+
						"查询以当前ID为父ID的所有记录时出现异常。");
			}
			if (plist!=null)
			{
				Iterator it1 = plist.iterator();
				while(it1.hasNext())
				{
					Sourceconfig tmpinfo = (Sourceconfig)it1.next();
					Long tmpid = tmpinfo.getSourceId();
					List list = null;
					try
					{
						list = getRole.getRoleRelationSourceInfo(userid,String.valueOf(tmpid));
					}
					catch(Exception e)
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
								currentElement.addAttribute("ID",groupid+sourceid);
								currentElement.addAttribute("source_id", sourceid);
								currentElement.addAttribute("parentid", String.valueOf(sourceconfig.getSourceParentid()));
								currentElement.addAttribute("source_cnname", String.valueOf(sourceconfig.getSourceCnname()));
								currentElement.addAttribute("source_enname", String.valueOf(sourceconfig.getSourceName()));
							}
							//调用添加属性的方法，添加属性。
							Long source_id = sourceconfig.getSourceId();
							addAttribute(currentElement,source_id,type);
							
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
	
	/**
	 * 日期 2006-11-24
	 * 
	 * @author wangyanguang/王彦广 
	 * @param args void
	 *
	 */
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
		SourceDirectoryInterface sourceRoleGrandInterface = new SourceDirectoryInterface();
		
		try 
		{
			Document doc = sourceRoleGrandInterface.getMenuTree("Demo", sourceconfigBean,list1,"1");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/testgroup.xml")));
			writer.write(doc);
			writer.close();
		} catch (Exception e1) 
		{
			System.out.println("Document对象没有生成或发生异常！");
			e1.getMessage();
		}
	}
}
