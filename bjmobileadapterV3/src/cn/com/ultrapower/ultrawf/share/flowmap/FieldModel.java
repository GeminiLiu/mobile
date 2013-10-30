package cn.com.ultrapower.ultrawf.share.flowmap;

/**
 * 字段配置信息的实体类
 * 
 * @author BigMouse
 */
public class FieldModel
{
	/**
	 * 字段名称
	 */
	private String fieldName;

	/**
	 * 获取字段名称
	 * 
	 * @return 字段名称
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * 
	 * @param fieldName：字段名称
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	/**
	 * 字段类型
	 */
	private int fieldType;

	/**
	 * 获取字段类型的字段号，来自FieldType
	 * 
	 * @return 字段类型的字段号
	 */
	public int getFieldType()
	{
		return fieldType;
	}

	/**
	 * 设置字段类型的字段号，来自FieldType
	 * 
	 * @param fieldType：字段类型的字段号，来自FieldType
	 */
	public void setFieldType(int fieldType)
	{
		this.fieldType = fieldType;
	}

	/**
	 * 字段在实体类中的名称
	 */
	private String fieldModel;

	/**
	 * 获取字段在实体类中的名称
	 * 
	 * @return 字段在实体类中的名称
	 */
	public String getFieldModel()
	{
		return fieldModel;
	}

	/**
	 * 设置字段在实体类中的名称
	 * 
	 * @param fieldModel：字段在实体类中的名称
	 */
	public void setFieldModel(String fieldModel)
	{
		this.fieldModel = fieldModel;
	}

}
