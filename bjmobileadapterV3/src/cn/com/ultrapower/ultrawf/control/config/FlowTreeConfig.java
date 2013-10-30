package cn.com.ultrapower.ultrawf.control.config;

import java.util.*;

import cn.com.ultrapower.ultrawf.control.design.ProcessRoleManager;
import cn.com.ultrapower.ultrawf.control.design.TplDesignManager;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryClassModel;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.design.ProcessRoleModel;
import cn.com.ultrapower.ultrawf.models.process.TplBaseModel;

public class FlowTreeConfig
{
	private static String strFlowTree = "";
	
	public static String getStrFlowTree()
	{
		if(strFlowTree.equals(""))
		{
			setFlowTree();
		}
		return strFlowTree;
	}

	public static void setStrFlowTree(String strFlowTree)
	{
		FlowTreeConfig.strFlowTree = strFlowTree;
	}
	
	public static void setFlowTree()
	{
		BaseCategoryClassManage bccm = new BaseCategoryClassManage();
		BaseCategoryManage bcm = new BaseCategoryManage();
		ProcessRoleManager prManager = new ProcessRoleManager();
		TplDesignManager tdManager = new TplDesignManager();

		List<BaseCategoryClassModel> bccList = bccm.getBaseCategoryClassList();
		List<BaseCategoryModel> bcList = bcm.getAllList();
		List<ProcessRoleModel> prList = prManager.getProcessRoleList();
		List<TplBaseModel> tbList = tdManager.getTplBaseList();

		StringBuffer xmlString = new StringBuffer(4000);
		xmlString.append("tree.nodes[\"x,BaseTypeManage\"]=\"text:流程综合管理\";");
		xmlString.append("tree.nodes[\"BaseTypeManage,BaseCategoryClassManage\"]=\"text:流程类型管理;url:../manageflow/BaseCategoryClassList.jsp\";");
		xmlString.append("tree.nodes[\"BaseTypeManage,BaseCategoryManage\"]=\"text:流程类别管理;url:../manageflow/BaseCategoryList.jsp\";");
		for(Iterator<BaseCategoryClassModel> it_class = bccList.iterator(); it_class.hasNext();)
		{
			BaseCategoryClassModel bccModel = it_class.next();
			xmlString.append("tree.nodes[\"BaseTypeManage,class_" + bccModel.getBaseCategoryClassID() + "\"]=\"text:" + bccModel.getBaseCategoryClassName() + ";url:../manageflow/BaseCategoryList.jsp?clsasname=" + bccModel.getBaseCategoryClassCode() + "\";");
			for(Iterator<BaseCategoryModel> it_cate = bcList.iterator(); it_cate.hasNext();)
			{
				BaseCategoryModel bcModel = it_cate.next();
				if(bcModel.getBaseCategoryClassCode() == bccModel.getBaseCategoryClassCode())
				{
					xmlString.append("tree.nodes[\"class_" + bccModel.getBaseCategoryClassID() + ",cate_" + bcModel.getBaseCategorySchama() + "\"]=\"text:" + bcModel.getBaseCategoryName() + "\";");
					
					if(bcModel.getBaseCategoryIsFlow() == 1)
					{
						xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_flow\"]=\"text:配置工单流程;url:../manageflow/TplBaseList.jsp?schema=" + bcModel.getBaseCategorySchama() + "\";");
						
						xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_role\"]=\"text:配置工单角色\";");
						
						for(Iterator<TplBaseModel> it_tpl = tbList.iterator(); it_tpl.hasNext();)
						{
							TplBaseModel tbModel = it_tpl.next();
							if(tbModel.getBaseTplSchema().equals(bcModel.getBaseCategorySchama()))
							{
								xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + "_role,cate_" + tbModel.getBaseTplID() + "\"]=\"text:配置" + tbModel.getBaseTplName() + "模板角色;url:../roledesign/RoleList.jsp?id=" + tbModel.getBaseTplID() + "\";");
								for(Iterator<ProcessRoleModel> it_role = prList.iterator(); it_role.hasNext();)
								{
									ProcessRoleModel prModel = it_role.next();
									if(prModel.getBaseID().equals(tbModel.getBaseTplID()))
									{
										xmlString.append("tree.nodes[\"cate_" + tbModel.getBaseTplID() + ",role_" + tbModel.getBaseTplID() + "_" + prModel.getRoleID() + "\"]=\"text:" + prModel.getRoleName() + ";url:../roledesign/RoleUserList.jsp?type=role&baseid=" + tbModel.getBaseTplID() + "&schema=" + bcModel.getBaseCategorySchama() + "&roleid=" + prModel.getRoleID() + "\";");
										
									}
								}
								
							}
						}
						
					}
					xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_field\"]=\"text:配置工单字段;url:../manageflow/BaseOwnFieldList.jsp?baseschama=" + bcModel.getBaseCategorySchama() + "\";");
					
					if(bcModel.getBaseCategoryIsFlow() == 1)
					{
						xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_dimensions\"]=\"text:配置角色细分维度;url:../manageflow/DimensionList.jsp?schema=" + bcModel.getBaseCategorySchama() + "\";");
						
					}
					xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_process\"]=\"text:该流程所有流程实例;url:../manageflow/BaseList.jsp?schema=" + bcModel.getBaseCategorySchama() + "\";");
					xmlString.append("tree.nodes[\"cate_" + bcModel.getBaseCategorySchama() + ",cate_" + bcModel.getBaseCategorySchama() + "_task\"]=\"text:该流程所有任务实例;url:../manageflow/BaseTaskQuery.jsp?querytype=schemaType&baseschema="+bcModel.getBaseCategorySchama() +"\";");
					
				}
			}
			
		}
		
		xmlString.append("tree.nodes[\"x,BaseTPLManage\"]=\"text:流程模板管理\";");
		xmlString.append("tree.nodes[\"BaseTPLManage,BaseCreateTPLManage\"]=\"text:我创建的流程模板;url:../manageflow/TplBaseListQuery.jsp?queryType=Create\";");
		xmlString.append("tree.nodes[\"BaseTPLManage,BaseModifyTPLManage\"]=\"text:我修改的流程模板;url:../manageflow/TplBaseListQuery.jsp?queryType=Modify\";");
		xmlString.append("tree.nodes[\"BaseTPLManage,BaseAllTPLManage\"]=\"text:所有的流程模板;url:../manageflow/TplBaseListQuery.jsp?queryType=All\";");

		xmlString.append("tree.nodes[\"x,FLowProcessManage\"]=\"text:流程实例管理\";");
		xmlString.append("tree.nodes[\"FLowProcessManage,FLowProcess_StartUp\"]=\"text:我启动的流程实例;url:../manageflow/BaseList.jsp?type=create\";");
		xmlString.append("tree.nodes[\"FLowProcessManage,FLowProcess_ShutDown\"]=\"text:我终止的流程实例;url:../manageflow/BaseList.jsp?type=end\";");
		xmlString.append("tree.nodes[\"FLowProcessManage,FLowProcess_All\"]=\"text:所有的流程实例;url:../manageflow/BaseList.jsp\";");
		xmlString.append("tree.nodes[\"x,FLowTaskManage\"]=\"text:任务实例管理\";");
		xmlString.append("tree.nodes[\"FLowTaskManage,FLowTask_MyWait\"]=\"text:我的待执行的任务;url:../manageflow/BaseTaskQuery.jsp?querytype=waitDeal\";");
		xmlString.append("tree.nodes[\"FLowTaskManage,FLowTask_All\"]=\"text:全部任务;url:../manageflow/BaseTaskQuery.jsp?querytype=all\";");
		
		xmlString.append("tree.nodes[\"x,BaseRoleManage\"]=\"text:工单角色管理\";");
		for(Iterator<BaseCategoryClassModel> it_class = bccList.iterator(); it_class.hasNext();)
		{
			BaseCategoryClassModel bccModel = it_class.next();
			xmlString.append("tree.nodes[\"BaseRoleManage,rclass_" + bccModel.getBaseCategoryClassID() + "\"]=\"text:" + bccModel.getBaseCategoryClassName() + "\";");
			for(Iterator<BaseCategoryModel> it_cate = bcList.iterator(); it_cate.hasNext();)
			{
				BaseCategoryModel bcModel = it_cate.next();
				if(bcModel.getBaseCategoryIsFlow() == 1 && bcModel.getBaseCategoryClassCode() == bccModel.getBaseCategoryClassCode())
				{
					xmlString.append("tree.nodes[\"rclass_" + bccModel.getBaseCategoryClassID() + ",rcate_" + bcModel.getBaseCategorySchama() + "\"]=\"text:" + bcModel.getBaseCategoryName() + "\";");
					for(Iterator<TplBaseModel> it_tpl = tbList.iterator(); it_tpl.hasNext();)
					{
						TplBaseModel tbModel = it_tpl.next();
						if(tbModel.getBaseTplSchema().equals(bcModel.getBaseCategorySchama()))
						{
							xmlString.append("tree.nodes[\"rcate_" + bcModel.getBaseCategorySchama() + ",rcate_" + tbModel.getBaseTplID() + "\"]=\"text:配置" + tbModel.getBaseTplName() + "模板角色;url:../roledesign/RoleList.jsp?id=" + tbModel.getBaseTplID() + "\";");
							for(Iterator<ProcessRoleModel> it_role = prList.iterator(); it_role.hasNext();)
							{
								ProcessRoleModel prModel = it_role.next();
								if(prModel.getBaseID().equals(tbModel.getBaseTplID()))
								{
									xmlString.append("tree.nodes[\"rcate_" + tbModel.getBaseTplID() + ",rrole_" + tbModel.getBaseTplID() + "_" + prModel.getRoleID() + "\"]=\"text:" + prModel.getRoleName() + ";url:../roledesign/RoleUserList.jsp?type=role&baseid=" + tbModel.getBaseTplID() + "&schema=" + bcModel.getBaseCategorySchama() + "&roleid=" + prModel.getRoleID() + "\";");
									
								}
							}
							
						}
					}
					xmlString.append("tree.nodes[\"rcate_" + bcModel.getBaseCategorySchama() + ",rd_" + bcModel.getBaseCategorySchama() + "_dimensions\"]=\"text:配置角色细分维度;url:../manageflow/DimensionList.jsp?schema=" + bcModel.getBaseCategorySchama() + "\";");
					
					
				}
			}
			
		}		
		
		
		xmlString.append("tree.nodes[\"x,UserViewBasePerformRoleManage\"]=\"text:人员角色视图;url:../manageflow/UserViewBasePerformRole.jsp\";");
		xmlString.append("tree.nodes[\"UserViewBasePerformRoleManage,UserViewBasePerformRoleManageZ\"]=\"text:根据工单流程方式查看;url:../manageflow/UserViewBasePerformRole.jsp\";");
		for(Iterator<BaseCategoryClassModel> it_class = bccList.iterator(); it_class.hasNext();)
		{
			BaseCategoryClassModel bccModel = it_class.next();
			xmlString.append("tree.nodes[\"UserViewBasePerformRoleManageZ,UserView_" + bccModel.getBaseCategoryClassID() + "\"]=\"text:" + bccModel.getBaseCategoryClassName() + ";url:../manageflow/UserViewBasePerformRole.jsp?basecategoryclasscode="+bccModel.getBaseCategoryClassCode()+"\";");
			for(Iterator<BaseCategoryModel> it_cate = bcList.iterator(); it_cate.hasNext();)
			{
				BaseCategoryModel bcModel = it_cate.next();
				if(bcModel.getBaseCategoryIsFlow() == 1 && bcModel.getBaseCategoryClassCode() == bccModel.getBaseCategoryClassCode())
				{
					xmlString.append("tree.nodes[\"UserView_" + bccModel.getBaseCategoryClassID() + ",UserView_" + bcModel.getBaseCategorySchama() + "\"]=\"text:" + bcModel.getBaseCategoryName() + ";url:../manageflow/UserViewBasePerformRole.jsp?basecategoryclasscode="+bccModel.getBaseCategoryClassCode()+"&baseschema="+bcModel.getBaseCategorySchama()+"\";");
					
				}
			}
			
		}
		xmlString.append("tree.nodes[\"UserViewBasePerformRoleManage,UserViewBasePerformRoleManageF\"]=\"text:根据组织结构方式查看;url:../manageflow/UserViewBasePerformRoleF.jsp\";");
		xmlString.append("tree.nodes[\"UserViewBasePerformRoleManageF,UserGroupAll\"]=\"text:所有组织结构;url:../manageflow/UserViewBasePerformRoleF.jsp\";");
		
		
		
		strFlowTree = xmlString.toString();
		System.out.println(strFlowTree);
		
	}
}

