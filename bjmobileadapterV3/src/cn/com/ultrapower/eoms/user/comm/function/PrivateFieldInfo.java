package cn.com.ultrapower.eoms.user.comm.function;

/**
 * @创建作者：wuwenlong
 * @创建时间：2006-11-19
 * @类的说明：将Remedy的Form中的field进行封装
 */

public class PrivateFieldInfo {
	
	private String 		strFieldID;
	private String 		strFieldValue;
	private int 		intFieldType;
	private String 		sparator;
	public PrivateFieldInfo()
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
	public PrivateFieldInfo(
			String 	strFieldID,
			String 	strFieldValue,
			int 	intFieldType)
	{
		this.setStrFieldID(strFieldID);
		this.setStrFieldValue(strFieldValue);
		this.setIntFieldType(intFieldType);
	}
	public PrivateFieldInfo(
			String 	strFieldID,
			String 	strFieldValue,
			int 	intFieldType,String sparator)
	{
		this.setStrFieldID(strFieldID);
		this.setStrFieldValue(strFieldValue);
		this.setIntFieldType(intFieldType);
		this.setSparator(sparator);
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

	public String getSparator() {
		return sparator;
	}

	public void setSparator(String sparator) {
		this.sparator = sparator;
	}
	
}
