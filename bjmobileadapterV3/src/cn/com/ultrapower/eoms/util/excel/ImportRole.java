package cn.com.ultrapower.eoms.util.excel;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ultrapower.system.util.FormatInt;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.ultrawf.control.design.ProcessRoleManager;
import cn.com.ultrapower.ultrawf.control.design.UserManager;
import cn.com.ultrapower.ultrawf.models.design.ChildRoleHandler;
import cn.com.ultrapower.ultrawf.models.design.ChildRoleModel;
import cn.com.ultrapower.ultrawf.models.design.DimensionHandler;
import cn.com.ultrapower.ultrawf.models.design.DimensionModel;
import cn.com.ultrapower.ultrawf.models.design.ProcessRoleModel;

/**
 * 导入角色以及人员
 * @author Administrator
 *
 */
public class ImportRole {
	
	public void importData(HttpServletRequest request, HttpServletResponse response)
	{
		Content content = (Content) ControllCenter.executeImortExcel(request, response);
		if (content != null && content.getContent() != null && !content.getContent().isEmpty())
		{
			//获取模板和角色的信息
			Row row0 = (Row)(content.getContent().get(0));
			List<Cell> arrayList0 = row0.getRow();
			String baseID = arrayList0.get(0).getCaption().trim();
			String baseSchema = arrayList0.get(1).getCaption().trim();
			String roleID = arrayList0.get(2).getCaption().trim();
			boolean isRebuild = FormatInt.FormatStringToInt(arrayList0.get(3).getCaption().trim()) == 1 ? true : false;
			
			//获取角色维度
			List<DimensionModel> dimList = new ArrayList<DimensionModel>();
			ProcessRoleManager prManager = new ProcessRoleManager();
			ProcessRoleModel prModel = prManager.getProcessRole(baseID, roleID);
			String[] conds = prModel.getRoleCondition().split("#");
			for(int i = 0; i < conds.length; i++)
			{
				List<Object[]> dList = new ArrayList<Object[]>();
				String[] condfield = conds[i].split("&");
				String fieldID = condfield[0];
				String fieldText = condfield[1];
				String fieldName = condfield[2];
				
				DimensionHandler dHandler = new DimensionHandler();
				DimensionModel dModel = dHandler.getDimension(fieldID, prModel.getBaseSchema());
				dimList.add(dModel);
			}
			Map<Integer, String> dimMap = new HashMap<Integer, String>();
			Row row1 = (Row)(content.getContent().get(1));
			List<Cell> arrayList1 = row1.getRow();
			for(int i = 0; i < dimList.size(); i++)
			{
				for(int j = 0; j < dimList.size(); j++)
				{
					String excelDim = arrayList1.get(i).getCaption().trim();
					String dbDim = dimList.get(j).getDFieldText();
					if(excelDim.equals(dbDim))
					{
						dimMap.put(i, dimList.get(j).getDFieldText());
						break;
					}
				}
			}
			
			//循环细分
			ChildRoleHandler crHandler = new ChildRoleHandler();
			if(isRebuild)
			{
				crHandler.deleteMatchConditions(prModel.getRoleID());
			}
			for(int i = 2; i < content.getContent().size(); i++)
			{
				Row row = (Row)(content.getContent().get(i));
				List<Cell> arrayList = row.getRow();
				
				//创建ChildRoleModel并填充信息
				ChildRoleModel crModel = new ChildRoleModel();
				crModel.setParentRoleID(prModel.getRoleID());
				crModel.setParentRoleName(prModel.getRoleName());
				crModel.setBaseID(prModel.getBaseID());
				crModel.setBaseSchema(prModel.getBaseSchema());
				crModel.setBaseName(prModel.getBaseName());
				int tmpInt = 0;
				StringBuffer sbRoleName = new StringBuffer();
				StringBuffer sbCondition = new StringBuffer();
				for(int j = 0; j < dimMap.size(); j++)
				{
					String dimText = dimMap.get(tmpInt).trim();
					String dimvalue = arrayList.get(tmpInt).getCaption().trim();
					for(DimensionModel dm : dimList)
					{
						if(dm.getDFieldText().equals(dimText))
						{
							dm.setFieldValue(dimvalue);
						}
					}
					sbRoleName.append("[" +dimvalue + "]");
					sbCondition.append(dimText + "='" + dimvalue + "';");
					tmpInt++;
				}
				crModel.setRoleName(sbRoleName.toString());
				crModel.setCondition(sbCondition.toString());
				crModel.getDimensionList().addAll(dimList);
				List<ChildRoleModel> crList = new ArrayList<ChildRoleModel>();
				crList.add(crModel);
			
				System.out.println("导入角色：" + sbRoleName);
				//创建角色细分
				List<String> groupList = crHandler.setMatchConditions(crList, prModel.getBaseID(), prModel.getRoleID(), false);
				
				//创建角色细分
				UserManager uManager = new UserManager();
				List<String> addUsers = new ArrayList<String>();
				String[] users = arrayList.get(tmpInt).getCaption().trim().split(",");
				for(String strUser : users)
				{
					addUsers.add(strUser);
				}
				if(groupList.size() > 0)
				{
					uManager.addUserGroup(addUsers, groupList.get(0));
				}
				else
				{
					System.out.println("该角色已存在：" + sbRoleName);
				}
			}
		}
	}
}
