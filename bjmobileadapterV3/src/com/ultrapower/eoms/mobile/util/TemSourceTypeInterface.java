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

public class TemSourceTypeInterface {

	static final Logger logger 			= (Logger) Logger.getLogger(TemSourceTypeInterface.class);

	List list1 = null;
	public Document getMenuTree(String userfullname,String username,String userid, SourceConfigInfoBean sourceConfigBean,List beanList,String showType,String treeType)
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
		
		if(userid == null)
		{
			System.out.println("用户名有误！");
			return null;
		}
		if(showType.equals("0"))
		{
			//遍历所有结点。
			returnDoc = getAllSourceElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(showType.equals(""))
		{
			//遍历所有结点。
			returnDoc = getAllSourceElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(showType.equals("1"))
		{
			//只查询一级节点。
			returnDoc = getOneLevelElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		return returnDoc;
	}
	//获得所有节点的方法。
	public Document getAllSourceElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		String moduleid = null;
		//添加根结点.
		addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
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
//				GetRole getRole_l = new GetRole();
//				List rolelist = getRole_l.getRoleRelationSourceInfo(userid,String.valueOf(id));
//				if(rolelist!=null)
//				{
					//遍历当前节点的所有父节点
					try
					{
						findLastElements(username,userid,id,doc,source_type,moduleid);
					}
					catch(Exception e)
					{
						System.out.println("遍历父节点时出现异常！");
					}
					//获得当前用户ID与资源ID的属性和技能授权。
					try
					{
						addCommissionGrand(username,doc,userid,String.valueOf(id),source_type);
					}
					catch(Exception e)
					{
						System.out.println("获得用户与资源属性和技能授权时出现异常！");
					}
					//遍历当前节点下的所有节点.
					try
					{
						findPID(username,userid,id,doc,source_type);
					}
					catch(Exception e)
					{
						System.out.println("遍历当前节点的子节点时出现异常！");
					}
//				}
			}
		}
		if(moduleid!=null)
		{
			//根据代办人ID，资源moduleID，查询role、sourceconfig表，取出所有代办人信息。
			List commissionlist = null; 
			try
			{
				commissionlist = getRole.getCommissionInfo(userid,moduleid);
			}
			catch(Exception e)
			{
				commissionlist = null;
				System.out.println("根据用户ID与资源ID查询技能表，查询代办人时出现异常！");
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
					String userfullname1    = peoplepo.getC630000003();
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
						
						findLastElements(roleskillname,roleskillid,new Long(rolesourceid),doc,source_type,moduleid);
					}
					//查询资源ID的技能授权。
					try
					{
						addCommissionGrand(roleskillname,doc,roleskillid,rolesourceid,source_type);
					}
					catch(Exception e)
					{
						System.out.println("调用addCommissionGrand添加资源技能授权时出现异常！");
					}
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
				sourceinfo = null;
				System.out.println("根据资源ID查询资源信息时出现异常！");
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
				
					SourceConfigInfo p1Info 	= new SourceConfigInfo();
					try 
					{
						//查询T1表，查看是否存在以当前ID为父ID的记录。如果有，先添加当前ID信息到Document对象中，然后再遍历。
						//如果没有，添加当前ID信息到Document对象中，添加属性和动作。
						List plist = null;
						try
						{
							 plist 				= p1Info.GetsourceCFGInfo(p_id);
						}
						catch(Exception e)
						{
							System.out.println("p1Info.GetsourceCFGInfo(p_id)此方法根据p_id查询子节点时出现异常！");
							plist = null;
						}
						if (plist!=null)
						{	
							//调用方法添加属性和技能动作。
							try
							{
								getS(username,doc,sourceconfig,sourceType);
							}
							catch(Exception e)
							{
								System.out.println("findPID调用添加属性和技能动作方法时出现异常！");
							}
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
							//调用方法添加属性和技能动作。
							try
							{
								getS(username,doc,sourceconfig,sourceType);
							}
							catch(Exception e)
							{
								System.out.println("调用添加属性和技能动作方法时出现异常！");
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
	public void getS(String username,Document doc,Sourceconfig sourceConfig,String sourceType) 
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
		
			Element t 			= null;
			Element value 		= null;
			t 					= doc.elementByID(username+parentid);
			if(t!=null)
			{
				String sourceid 			= String.valueOf(sourceConfig.getSourceId());
				Element currentElement 		= doc.elementByID(username+sourceid);
				if(currentElement==null)
				{
					currentElement = t.addElement("source");
					currentElement.addAttribute("ID",username+sourceid);
					currentElement.addAttribute("source_id", sourceid);
					currentElement.addAttribute("parentid", String.valueOf(sourceConfig.getSourceParentid()));
					currentElement.addAttribute("source_cnname", String.valueOf(sourceConfig.getSourceCnname()));
					currentElement.addAttribute("source_enname", String.valueOf(sourceConfig.getSourceName()));
				}
					//调用添加属性的方法，添加属性。
					Long source_id = sourceConfig.getSourceId();
					String source_type = sourceType;
					addAttribute(currentElement,source_id,source_type);
			}
		}
	}

	public void addCommissionGrand(String username,Document doc,String userid,String sourceid,String sourceType)
	{
		GetRole getRole = new GetRole();
		Element srcelement = doc.elementByID(username+sourceid);
		if(srcelement!=null)
		{
			addAttribute(srcelement,new Long(sourceid),sourceType);
	
		}
	}
	
	//获得一级节点的方法。
	public Document getOneLevelElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		String moduleid = "";
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
				System.out.println("..id:"+id);
				//遍历当前节点的所有父节点
				Element usrelement = doc.elementByID(userid);
				if(usrelement!=null)
				{
					Element eleme = doc.elementByID(username+String.valueOf(id));
					if(eleme==null)
					{
						Element e = usrelement.addElement("source");
						e.addAttribute("ID",username+String.valueOf(id));
						e.addAttribute("sourceid",String.valueOf(id));
					}
					try
					{
						addCommissionGrand(username,doc,userid,String.valueOf(id),source_type);
					}
					catch(Exception e)
					{
						System.out.println("getOneLevelElement调用addCommissionGrand 出现异常！");
					}
				}
			}
		}
		GetRole getRole 	= new GetRole();
		//根据Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1 = getRole.getFlag(sourceConfigBean,beanList);
		//list1为所有满足BEAN条件的集合。
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
						System.out.println("Exception 查询父ID等于参数ID时出现异常！");
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
//									String source_type = sourceConfigBean.getSource_type();
									try
									{
										addAttribute(currentElement,source_id,source_type);
									}
									catch(Exception e)
									{
										System.out.println("getOneLevelElement调用addAttribute出现异常！");
									}
								}
							}
						}
					}
				}
			}
		}
		
		/////***************////
		if(moduleid!=null)
		{
			//根据代办人ID，资源moduleID，查询role、sourceconfig表，取出所有代办人信息。
			System.out.println("代办人ID"+userid);
			System.out.println("资源模块类别："+moduleid);
			List commissionlist = getRole.getCommissionInfo(userid,moduleid);
			if(commissionlist!=null)
			{
				Iterator commissionit = commissionlist.iterator();
				while(commissionit.hasNext())
				{
					Object[] obj 				= (Object[])commissionit.next();
					SysSkillpo sysskill 		= (SysSkillpo)obj[0];
					Sourceconfig sourceconfig 	= (Sourceconfig)obj[1];
					String roleskillname = "";
					String userfullname1 = "";
					String roleskillid 	= sysskill.getC610000007();
					SysPeoplepo peoplepo = null;
					try
					{
						peoplepo = RoleInterfaceAssociate.findModify(roleskillid);
					}
					catch(Exception e)
					{
						peoplepo = null;
						System.out.println("根据技能ID查询技能信息时出现异常！");
					}
					if(peoplepo!=null)
					{
						roleskillname = peoplepo.getC630000001();
						userfullname1 = peoplepo.getC630000003();
					}
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
					try
					{
						addCommissionGrand(roleskillname,doc,roleskillid,rolesourceid,source_type);
					}
					catch(Exception e)
					{
						System.out.println("getOneLevelElement调用addCommissionGrand时出现异常！");
					}
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
		System.out.println(username);
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
			list1 = null;
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
//		sourceconfigBean.setSource_id(new Long(187));
		sourceconfigBean.setSource_parentid(new Long(232));
//		sourceconfigBean.setSource_type("4;");
		
		SourceAttQueryBean sourceatt = new SourceAttQueryBean();
		sourceatt.setsource_attname("source_type");
		sourceatt.setsource_attnamevalue("1");
		sourceatt.setsource_attqueryop("=");
		List list1 = new ArrayList();
		list1.add(sourceatt);
		GetRole getRole = new GetRole();
		TemSourceTypeInterface sourceRoleGrandInterface = new TemSourceTypeInterface();
	
		try 
		{
			Document doc = sourceRoleGrandInterface.getMenuTree("河北测试人3","hb3","000000000000139" ,sourceconfigBean,list1,"1","1");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/sourcetype.xml")));
			writer.write(doc);
			writer.close();
		} catch (Exception e1) 
		{
			System.out.println("Excepiton");
			e1.getMessage();
		}
	}
}
