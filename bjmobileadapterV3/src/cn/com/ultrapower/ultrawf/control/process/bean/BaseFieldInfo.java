package cn.com.ultrapower.ultrawf.control.process.bean;

public class BaseFieldInfo {
	private String 		strFieldName;
	private String 		strFieldID;
	private String 		strFieldValue;
	private int 		intFieldType;
	private int 		intIsBaseOwnField;	//1 表示是，表示不是
	private String 		BaseFree_field_ShowStep;	//显示阶段	intIsBaseOwnField = 1 才有值
	private String 		BaseFree_field_EditStep;	//编辑阶段	intIsBaseOwnField = 1 才有值
	public BaseFieldInfo()
	{
		
	}		
	/**
	 * 初始化字段信息类
	 * @param intFieldType 		字段类型
	 * @		 AR_DATA_TYPE_NULL           0	空
	 * @		 AR_DATA_TYPE_KEYWORD        1	AR关键字
	 * @		 AR_DATA_TYPE_INTEGER        2	整形
	 * @		 AR_DATA_TYPE_REAL           3	浮点型
	 * @		 AR_DATA_TYPE_CHAR           4	字符型
	 * @		 AR_DATA_TYPE_DIARY          5	日志型
	 * @		 AR_DATA_TYPE_ENUM           6	
	 * @		 AR_DATA_TYPE_TIME           7	时间型，传递秒
	 * @		 AR_DATA_TYPE_BITMASK        8
	 * @		 AR_DATA_TYPE_BYTES          9
	 * @		 AR_DATA_TYPE_DECIMAL       10
	 * @		 AR_DATA_TYPE_ATTACH        11	附件，传具体文件名，带路径，例如：“D:\dddd\1.exe”
	 * @		 AR_DATA_TYPE_CURRENCY      12	
	 * @		 AR_DATA_TYPE_DATE          13	日期，传递秒
	 * @		 AR_DATA_TYPE_TIME_OF_DAY   14	日期时间，传递秒
	 */	
	public BaseFieldInfo(
			String 	strFieldName,
			String 	strFieldID,
			String 	strFieldValue,
			int 	intFieldType,
			int 	intIsBaseOwnField,
			String	strBaseFree_field_ShowStep,
			String	strBaseFree_field_EditStep)
	{
		this.setStrFieldName(strFieldName);
		this.setStrFieldID(strFieldID);
		this.setStrFieldValue(strFieldValue);
		this.setIntFieldType(intFieldType);
		this.setIntIsBaseOwnField(intIsBaseOwnField);
		this.setBaseFree_field_ShowStep(strBaseFree_field_ShowStep);
		this.setBaseFree_field_EditStep(strBaseFree_field_EditStep);
	}
	
	public BaseFieldInfo(
			String	strFieldName,
			String 	strFieldID,
			String 	strFieldValue,
			int 	intFieldType)
	{
		this.setStrFieldName(strFieldName);
		this.setStrFieldID(strFieldID);
		this.setStrFieldValue(strFieldValue);
		this.setIntFieldType(intFieldType);
		this.setIntIsBaseOwnField(0);
		this.setBaseFree_field_ShowStep("");
		this.setBaseFree_field_EditStep("");
	}	
	

	
	public int getIntFieldType() {
		return intFieldType;
	}
	public void setIntFieldType(int intFieldType) {
		this.intFieldType = intFieldType;
	}
	public String getStrFieldID() {
		return strFieldID;
	}
	public void setStrFieldID(String strFieldID) {
		this.strFieldID = strFieldID;
	}
	public String getStrFieldValue() {
		return strFieldValue;
	}
	public void setStrFieldValue(String strFieldValue) {
		this.strFieldValue = strFieldValue;
	}	
	
	public int getIntIsBaseOwnField() {
		return intIsBaseOwnField;
	}

	public void setIntIsBaseOwnField(int intIsBaseOwnField) {
		this.intIsBaseOwnField = intIsBaseOwnField;
	}
	public String getBaseFree_field_EditStep() {
		return BaseFree_field_EditStep;
	}
	public void setBaseFree_field_EditStep(String baseFree_field_EditStep) {
		BaseFree_field_EditStep = baseFree_field_EditStep;
	}
	public String getBaseFree_field_ShowStep() {
		return BaseFree_field_ShowStep;
	}
	public void setBaseFree_field_ShowStep(String baseFree_field_ShowStep) {
		BaseFree_field_ShowStep = baseFree_field_ShowStep;
	}
	public String getStrFieldName() {
		return strFieldName;
	}
	public void setStrFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}
}
