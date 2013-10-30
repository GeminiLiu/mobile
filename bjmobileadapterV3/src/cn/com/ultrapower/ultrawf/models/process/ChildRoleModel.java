package cn.com.ultrapower.ultrawf.models.process;

public class ChildRoleModel implements Cloneable
{
	private	String	RequestID;
	private	String	CBDBaseName;
	private	String	CBDBaseSchema;
	private	String	ChildRoleDesc;
	private	String	ChildRoleID;
	private	String	ChildRoleName;
	private	int	ChlidMatchDegree;
	private	String	TopRoleID;
	private	String	TopRoleName;
	private String ChildRoleManager;
	private String ChildRoleManagerID;
	
	public String getRequestID() {
		return RequestID;
	}
	public void setRequestID(String requestID) {
		RequestID = requestID;
	}
	public String getCBDBaseName() {
		return CBDBaseName;
	}
	public void setCBDBaseName(String baseName) {
		CBDBaseName = baseName;
	}
	public String getCBDBaseSchema() {
		return CBDBaseSchema;
	}
	public void setCBDBaseSchema(String baseSchema) {
		CBDBaseSchema = baseSchema;
	}
	public String getChildRoleDesc() {
		return ChildRoleDesc;
	}
	public void setChildRoleDesc(String childRoleDesc) {
		ChildRoleDesc = childRoleDesc;
	}
	public String getChildRoleID() {
		return ChildRoleID;
	}
	public void setChildRoleID(String childRoleID) {
		ChildRoleID = childRoleID;
	}
	public String getChildRoleName() {
		return ChildRoleName;
	}
	public void setChildRoleName(String childRoleName) {
		ChildRoleName = childRoleName;
	}
	public int getChlidMatchDegree() {
		return ChlidMatchDegree;
	}
	public void setChlidMatchDegree(int chlidMatchDegree) {
		ChlidMatchDegree = chlidMatchDegree;
	}
	public String getTopRoleID() {
		return TopRoleID;
	}
	public void setTopRoleID(String topRoleID) {
		TopRoleID = topRoleID;
	}
	public String getTopRoleName() {
		return TopRoleName;
	}
	public void setTopRoleName(String topRoleName) {
		TopRoleName = topRoleName;
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
