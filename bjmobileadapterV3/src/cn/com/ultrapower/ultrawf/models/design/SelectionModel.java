package cn.com.ultrapower.ultrawf.models.design;

/**
 * 模板中的下拉菜单的实体类
 * @版本 V0.1
 * @Build 0001
 * @作者 BigMouse
 * @说明
 */
public class SelectionModel
{
	/**
	 * 下拉菜单的value
	 */
	private String value = "";
	
	/**
	 * 获取下拉菜单的value
	 * @return 下拉菜单的value
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * 设置下拉菜单的value
	 * @param 下拉菜单的value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * 下拉菜单的text
	 */
	private String text = "";

	/**
	 * 获取下拉菜单的text
	 * @return 下拉菜单的text
	 */
	public String getText()
	{
		return text;
	}


	/**
	 * 设置下拉菜单的text
	 * @param 下拉菜单的text
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	 * 构造函数
	 * @param value 下拉菜单的value
	 * @param text 下拉菜单的text
	 */
	public SelectionModel(String value, String text)
	{
		this.value = value;
		this.text = text;
	}
}
