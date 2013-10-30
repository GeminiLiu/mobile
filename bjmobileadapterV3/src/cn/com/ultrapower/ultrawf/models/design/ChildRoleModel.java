package cn.com.ultrapower.ultrawf.models.design;

import java.util.*;

public class ChildRoleModel
{
	private String roleC1;
	private String roleID;
	private String roleName;
	private String parentRoleID;
	private String parentRoleName;
	private String baseID;
	private String baseSchema;
	private String baseName;
	private String condition;
	private String ChildRoleManager;
	private String ChildRoleManagerID;
	
	
	private List<DimensionModel> dimensionList = new ArrayList<DimensionModel>();
	public List<DimensionModel> getDimensionList()
	{
		return dimensionList;
	}
	public void setDimensionList(List<DimensionModel> dimensionList)
	{
		this.dimensionList = dimensionList;
	}
	public String getRoleID()
	{
		return roleID;
	}
	public void setRoleID(String roleID)
	{
		this.roleID = roleID;
	}
	public String getRoleName()
	{
		return roleName;
	}
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	public String getCondition()
	{
		return condition;
	}
	public void setCondition(String condition)
	{
		this.condition = condition;
	}
	public String getParentRoleID()
	{
		return parentRoleID;
	}
	public void setParentRoleID(String parentRoleID)
	{
		this.parentRoleID = parentRoleID;
	}
	public String getParentRoleName()
	{
		return parentRoleName;
	}
	public void setParentRoleName(String parentRoleName)
	{
		this.parentRoleName = parentRoleName;
	}
	public String getBaseID()
	{
		return baseID;
	}
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}
	public String getBaseSchema()
	{
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	public String getBaseName()
	{
		return baseName;
	}
	public void setBaseName(String baseName)
	{
		this.baseName = baseName;
	}
	public String getRoleC1() {
		return roleC1;
	}
	public void setRoleC1(String roleC1) {
		this.roleC1 = roleC1;
	}
	public void setChildRoleManager(String childRoleManager) {
		ChildRoleManager = childRoleManager;
	}
	public String getChildRoleManager() {
		return ChildRoleManager;
	}
	public void setChildRoleManagerID(String childRoleManagerID) {
		ChildRoleManagerID = childRoleManagerID;
	}
	public String getChildRoleManagerID() {
		return ChildRoleManagerID;
	}
}
