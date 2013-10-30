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

public class SourceRoleGrandInterface 
{

	static final Logger logger 			= (Logger) Logger.getLogger(SourceRoleGrandInterface.class);

	List list1 = null;
	
	/**
	 * 根据参数返回资源树或者是组织结构目录树。
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param username              用户登陆名。
	 * @param sourceConfigBean		资源Bean信息。
	 * 								source_id:			资源ID。
	 * 								source_parentid：	资源父ID。
	 * 								source_cnname：		资源中文名。
	 * 								source_name：		资源英文名。
	 * 								source_module：		资源所属模块ID。
	 * 								source_orderby：		资源排序值。
	 * 								source_desc：		资源描述。
	 * 								source_type：		资源类别类型（0：目录节点，1：工单，2：环节表3：表资源，4：Menu资源5，字段资源）
	 * 
	 * @param beanList				条件List。(提取List中的信息与资源Bean一起查询资源T1，T2，T3表，查询出满足条件的资源List.)
	 * @param showType				返回类型。	0:返回当前节点及其子节点的技能权限和属性同时返回其父节点。 
	 * 											1:仅返回当前节点的下一级的技能权限和属性。
	 * 											2:仅返回当前节点的技能权限和属性。
	 * @param treeType				生成树的类型	0:为资源树形结构。(查技能权限表、T1、T2、T3表)
	 *          								1：组织结构目录树。(T1、T2、T3 表）
	 * @return Document
	 */
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
		String sourceconfigtype = null;
		try
		{
			sourceconfigtype = gftn.GetFormName("sourcecofigtype");
			System.out.println("配置表中的类型："+sourceconfigtype);
		}
		catch(Exception e)
		{
			logger.info("在属性配制表中取'sourcecofigtype'属性值时出现异常！");
			System.out.println("在属性配制表中取'sourcecofigtype'属性值时出现异常！");
			sourceconfigtype = "";
		}
		if(!sourceconfigtype.equals(""))
		{
			System.out.println(sourceconfigtype);
		}
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
		
		String userid		= "";
		String userfullname = "";   
		String str  		= "";
		if(syspeoplepo!=null)
		{
			userid 			=  syspeoplepo.getC1();
			userfullname 	= syspeoplepo.getC630000003();
		}
		
		if(sourceConfigBean!=null)
		{
			//资源信息类中的类型值。
			str = Function.nullString(sourceConfigBean.getSource_type());
		}
		
		if(userid == null||String.valueOf(userid).equals(""))
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
		else if(showType.equals("2")&&!str.equals(sourceconfigtype))
		{
			//只查询当前结点。
			returnDoc = getCurrentElement(userfullname,username,userid,sourceConfigBean,beanList);
		}
		else if(str.equals(sourceconfigtype))
		{
			SourceTypeInterface typeinterface = new SourceTypeInterface();
			//returnDoc = typeinterface.getMenuTree(userfullname,username,userid,sourceConfigBean,beanList,showType,treeType);
			returnDoc = typeinterface.getMenuTree(userfullname,username,userid,sourceConfigBean,beanList,showType);
		}
		return returnDoc;
	}
	/**
	 * 根据参数得到Document对象。（满足条件的所有资源的节点。）   
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
		String source_type ="";
		//得到资源类型。
		if(sourceConfigBean!=null)
		{
			source_type = sourceConfigBean.getSource_type();
		}
		//添加根结点.
		addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。
		if(list1!=null)
		{
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj 	= (Object[])its.next();
				Long id 		= (Long)obj[0];
				//获得资源所属模块ID。
				moduleid 		= (String)obj[1];
				if(String.valueOf(moduleid).equals("")||String.valueOf(moduleid).equals("null"))
				{
					moduleid = "0";
				}
				//遍历当前节点的所有父节点
				GetRole getRole 	= new GetRole();
				List rolelist 		= getRole.getRoleRelationSourceInfo(userid,String.valueOf(id));
				if(rolelist!=null)
				{
					try
					{
						findLastElements(username,userid,id,doc,source_type,moduleid);
					}
					catch(Exception e)
					{
						System.out.println("遍历父节点出出异常！");
					}
					//获得当前用户ID与资源ID的属性和技能授权。
					//*****后修改1----start 添加了if判断当前ID节点是否存在，如果此节点有父并且已经遍历过父节点了，那么如果不在此进行
					//*****再一次判断，那么就会重复的添加同一<grand>节点内容。此判断解决了程序先执行了父，然后又执行子节点时，这时子
					//*****节点在程序操作父节点时已经添加了，再一次执行时就会重复一个<grand>节点
					Element tmp = doc.elementByID(username+String.valueOf(id));
					if(tmp!=null)
					{
						Element e = (Element)tmp.selectSingleNode("grand");
						if(e==null)
						{
							try
							{
								addCommissionGrand(username,doc,userid,String.valueOf(id),source_type);
							}
							catch(Exception e1)
							{
								System.out.println("添加用户与资源属性和技能授权时出现异常！");
							}
							//遍历当前节点下的所有节点.
							try
							{
								findPID(username,userid,id,doc,source_type);
							}
							catch(Exception e2)
							{
								System.out.println("遍历当前节点的子节点时出现异常！");
							}
						}
					}
				}
			}
		}
		return doc;
	}
	
	/**
	 * 遍历当前节点的所有父节点。
	 *  1.首先判断当前用户的资源节点是否存在，如果存在不做任何操作，如果不存在：
	 *  2.根据资源ID取得资源信息Bean。
	 *  3.根据资源信息Bean取得资源父ID，判断资源父ID节点是否存在，不做任何操作，如果不存在：
	 *  4.判断资源父ID的值，如果资源ID是根据结点（parentid=='0'）将此资源ID添加到Document中。
	 *  5.如果资源ID的值不是根据结点，调用方法本身，取当前资源ID的父ID查询，最后递归出所有父ID的节点。
	 *  
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param username
	 * @param userid
	 * @param id
	 * @param doc
	 * @param sourceType
	 * @param moduleid void
	 *
	 */
	public void findLastElements(String username,String userid,Long id,Document doc,String sourceType,String moduleid)
	{
		System.out.println(moduleid);
		Element currentElement = doc.elementByID(username+String.valueOf(id));
		if(currentElement==null)
		{
			//先得到ID的父ID，如果等于moduleid加授权，加属性，否则递归。
			RoleInterfaceAssociate roleasso 	= new RoleInterfaceAssociate();
			Sourceconfig sourceinfo 			= new Sourceconfig();
			try
			{
				sourceinfo 						= roleasso.getSourceInfo(id);
			}
			catch(Exception e)
			{
				logger.info("sourceRoleGrandInterface.findLastElements()查询资源信息时出现异常！");
				System.out.println("sourceRoleGrandInterface.findLastElements()查询资源信息时出现异常！");
				sourceinfo = null;
			}
			if(sourceinfo!=null)
			{
				Long   parentid 		= sourceinfo.getSourceParentid();
				String parentid_str 	= Function.nullString(String.valueOf(parentid));
				if(parentid_str.equals("")||parentid_str.equals("null"))
				{
					parentid_str 		= "0";
				}
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
						//*****后修改3-----start 加此if判断作用如下：此函数的功能是添加当前ID的父ID节点，在这里先判断当前ID的
						//*****父ID是否存在如果存在就直接将当前ID添加到父ID，如果不存在执行遍历，添加当前节点的父ID。
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
	
	/**
	 * 递归查询资源ID，遍历资源ID。
	 *  1.根据参数传入的资源ID首先查询出资源表中所有父ID等于当前参数ID的List.
	 *  2.循环List取得每一条记录转化为资源信息Bean.
	 *  3.根据取得的Bean信息取得资源ID，调用添加属性和技能方法为当前资源节点添加信息。
	 *  4.根据取得的Bean信息取得资源父ID，查询资源表中所有父ID等于当前资源ID的List。
	 *  5.调用2直到所有子节点全部添加完成。
	 *  
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param username
	 * @param userid
	 * @param id
	 * @param doc
	 * @param sourceType void
	 *
	 */
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
				logger.info("查询T1表，查看以当前ID为父ID的所有记录 异常！");
				System.out.println("查询T1表，查看以当前ID为父ID的所有记录 异常！");
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
										System.out.println("调用方法添加属性和技能动作出现异常！");
									}
								}
								//递归的调用。
								findPID(username,userid,p_id,doc,sourceType);
							}
							else 
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
	}
	/**
	 * 根据参数添加属性与技能动作。(参数List 为在技能授权表中以用户ID与资源ID查询后返回的结果集。)
	 *  1.根据参数List查询List中的每一个Object对象，将其转化为资源信息Bean和技能信息Bean.
	 *  2.根据技能与资源信息Bean将资源属性与技能权限添加到Document对象中。
	 *  
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param username				用户登陆名
	 * @param doc
	 * @param sourceConfig			资源信息Bean
	 * @param list					技能授权表中以用户ID与资源ID查询后返回的结果集
	 * @param sourceType			资源类型
	 */
	public void getS(String username,Document doc,Sourceconfig sourceConfig,List list,String sourceType) 
	{
		String id			= Function.nullString(String.valueOf(sourceConfig.getSourceId()));
		String parentid 	= Function.nullString(String.valueOf(sourceConfig.getSourceParentid()));
		String cnname 		= Function.nullString(sourceConfig.getSourceCnname());
		String enname 		= Function.nullString(sourceConfig.getSourceName());
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
						Long source_id 		= sourceconfig.getSourceId();
						String source_type 	= sourceType;
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
	/**
	 * 根据用户ID与资源ID查询技能信息表，将用户的技能动用添加到Document对象中。
	 * 
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param username			用户登陆名
	 * @param doc				Document对象
	 * @param userid			用户ID
	 * @param sourceid			资源ID
	 * @param sourceType		资源类型
	 *
	 */
	public void addCommissionGrand(String username,Document doc,String userid,String sourceid,String sourceType)
	{
		GetRole getRole = new GetRole();
		Element srcelement = doc.elementByID(username+sourceid);
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
				logger.info("SourceRoleGrandInterface.addCommissinGrand()根据用户ID与资源ID查询技能表时出现异常！");
				System.out.println("SourceRoleGrandInterface.addCommissinGrand()根据用户ID与资源ID查询技能表时出现异常！");
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
	/**
	 * 添加根结点及用户结点和用户节点的属性信息，同时根据资源信息Bean与条件List查询出满足条件的所有资源集合。
	 * 
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param userfullname      用户中文名
	 * @param username			用户登陆名
	 * @param userid			用户ID
	 * @param doc				Document对象
	 * @param sourceConfigBean	资源信息Bean
	 * @param beanList			条件List
	 */
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
			//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
			//list1为所有满足BEAN条件的记录集。
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
	/**
	 * 根据资源ID与资源类型（在资源信息Bean中得到）查询出此资源ID的属性并将其添加到Element对象中。
	 * 
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param element			Element对象
	 * @param sourceid			资源ID
	 * @param type				资源类型(在资源信息Bean中得到)
	 *
	 */
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
	/**
	 * 获得一级节点的方法。
	 * 根据资源信息Bean和条件List查询出满足条件的资源信息集合.
	 * 循环资源信息集合中的信息,查询出当前资源信息属性与技能权限及当前资源信息的下一级资源信息的属性与技能权限.
	 * 
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param userfullname		  	用户中文名
	 * @param username				用户登陆名
	 * @param userid				用户ID
	 * @param sourceConfigBean		资源信息Bean
	 * @param beanList				条件List
	 * @return Document				Document对象
	 */
	public Document getOneLevelElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		try
		{
			addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		}
		catch(Exception e)
		{
			System.out.println("添加一级节点根节点时出现异常！");
		}
		String moduleid = "";
		String source_type = "";
		if(sourceConfigBean!=null)
		{
			source_type = sourceConfigBean.getSource_type();
		}
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。
		if(list1!=null)
		{
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj = (Object[])its.next();
				Long id = (Long)obj[0];
				//获得资源所属模块ID。
				moduleid = (String)obj[1];
				if(String.valueOf(moduleid).equals("")||String.valueOf(moduleid).equals("null"))
				{
					moduleid = "0";
				}
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
				//获得要添加一级节点的父节点，
				Element oneLevel = doc.elementByID(username+String.valueOf(id));
				if(oneLevel!=null)
				{	SourceConfigInfo p1Info = new SourceConfigInfo();
					List plist	= null;
					try
					{
						//根据父ID等于当前ID查询记录，返回结果放在LIST中。
						plist 				= p1Info.GetsourceCFGInfo(id);
					}catch(Exception e)
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
									Element currentElement 		= doc.elementByID(username+sourceid);
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
		return doc;
	}
	/**
	 * 只查询当前资源信息的属性与技能动作。
	 * 
	 * 日期 2006-12-7
	 * @author wangyanguang/王彦广 
	 * @param userfullname			用户中文名
	 * @param username				用户登陆名
	 * @param userid				用户ID
	 * @param sourceConfigBean		资源信息Bean
	 * @param beanList				条件List
	 * @return Document				Document对象
	 */
	public Document getCurrentElement(String userfullname,String username,String userid,SourceConfigInfoBean sourceConfigBean,List beanList)
	{
		Document doc 	= null;
		doc 			= org.dom4j.DocumentHelper.createDocument();
		try
		{
			addRootElement(userfullname,username,userid,doc,sourceConfigBean,beanList);
		}
		catch(Exception e)
		{
			logger.info("SourceRoleGrandInterface类中getCurrentElement方法中添加一级节点根节点时出现异常！");
			System.out.println("SourceRoleGrandInterface类中getCurrentElement方法中添加一级节点根节点时出现异常！");
		}
		String moduleid = "";
		String source_type = "";
		if(sourceConfigBean!=null)
		{
			source_type = sourceConfigBean.getSource_type();
		}
		//根据资源信息Bean查询资源T1表,得到满足Bean的所有记录集.
		//list1为所有满足BEAN条件的记录集。
		if(list1!=null)
		{
			
			Iterator its = list1.iterator();
			while(its.hasNext())
			{
				Object[] obj = (Object[])its.next();
				Long id = (Long)obj[0];
				//获得资源所属模块ID。
				GetRole getRole = new GetRole();
				List rolelist = getRole.getRoleRelationSourceInfo(userid,String.valueOf(id));
				if(rolelist!=null)
				{
					if(rolelist.size()>0)
					{
						moduleid = (String)obj[1];
						if(String.valueOf(moduleid).equals("")||String.valueOf(moduleid).equals("null"))
						{
							moduleid = "0";
						}
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
			}
		}
		return doc;
	}
	public static void main(String[] args) 
	{
		
		 SourceConfigInfoBean scb = new SourceConfigInfoBean();
//	     scb.setSource_module("17");
//	     scb.setSource_id("179");
		// scb.setSource_type("4");
		// scb.setSource_cnname("资料管理");
		 scb.setSource_name("UltraProcess:App_Base");
		 //scb.setSource_parentid(new Long(179));
		 //scb.setSource_id("17");
	     List listcondition = new ArrayList();
	   
	     SourceAttQueryBean saqb4 = new SourceAttQueryBean();
	     saqb4.setsource_attname("creater");
	     saqb4.setsource_attnamevalue("梁阳");
	     saqb4.setsource_attqueryop("=");
	     listcondition.add(saqb4);
	     String showType="0"; 
		
		SourceRoleGrandInterface sourceRoleGrandInterface = new SourceRoleGrandInterface();
		
		try 
		{
			long start = System.currentTimeMillis();
			Document doc = sourceRoleGrandInterface.getMenuTree("Demo", scb,null,"0","0");
			
			System.out.println("此次查询用时"+(System.currentTimeMillis()-start)/1000+"."+((System.currentTimeMillis()-start)%1000)+"秒！");
			XMLWriter writer = new XMLWriter(new FileOutputStream(new File("E:/testaaa.xml")));
			writer.write(doc);
			writer.close();
		} 
		catch (Exception e1) 
		{
			System.out.println("Excepiton");
			e1.getMessage();
		}
	}
}
