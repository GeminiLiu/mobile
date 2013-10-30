package cn.com.ultrapower.ultrawf.share.queryanalyse;

/**
 * 
 * @author 徐发球
 * @date 2007-11-10
 */
public class ParFiledInfo {

	private String FieldID;
	private String FieldName;
	private int CompareLogic=ParCompareLogic.isEquals;//比较逻辑 
	private String RelateLogic=" and ";//关系逻辑
	
	private int FiledType;//字段类型
	private int OptionType=0; //1: 只显示 2:只做查询条件 3:显示和查询条件
	private int GroupLogic=0;// 1: 左括号"("符号  2: 右括号")" 符号 
	private String GroupID;
	private String UpGroupID;
	private int UPGroupRelateLogic;
	
	
	
	private String FiledValue="";
	
	
	public String getFieldID() {
		return FieldID;
	}

	public void setFieldID(String fieldID) {
		FieldID = fieldID;
	}

	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}

	public String getGroupID() {
		return GroupID;
	}

	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

	public int getCompareLogic() {
		return CompareLogic;
	}

	public void setCompareLogic(int logic) {
		CompareLogic = logic;
	}

	public int getOptionType() {
		return OptionType;
	}

	public void setOptionType(int optionType) {
		OptionType = optionType;
	}

	public String getUpGroupID() {
		return UpGroupID;
	}

	public void setUpGroupID(String upGroupID) {
		UpGroupID = upGroupID;
	}

	public int getUPGroupLogic() {
		return UPGroupRelateLogic;
	}

	public void setUPGroupLogic(int groupLogic) {
		UPGroupRelateLogic = groupLogic;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getFiledType() {
		return FiledType;
	}

	public void setFiledType(int filedType) {
		FiledType = filedType;
	}

	public String getRelateLogic() {
		if(RelateLogic==null)
			RelateLogic="";
		if(RelateLogic.equals(""))
			RelateLogic=" and ";
		return RelateLogic;
	}

	public void setRelateLogic(String relateLogic) {
		RelateLogic = relateLogic;
	}

	public int getUPGroupRelateLogic() {
		return UPGroupRelateLogic;
	}

	public void setUPGroupRelateLogic(int groupRelateLogic) {
		UPGroupRelateLogic = groupRelateLogic;
	}

	public String getFiledValue() {
		return FiledValue;
	}

	public void setFiledValue(String filedValue) {
		FiledValue = filedValue;
	}

	public int getGroupLogic() {
		return GroupLogic;
	}

	public void setGroupLogic(int groupLogic) {
		GroupLogic = groupLogic;
	}

}
