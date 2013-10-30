package com.ultrapower.eoms.mobile.interfaces.exception;

public class InvalidXmlFormatException extends Exception
{
	private static final long serialVersionUID = -8921936435393921409L;

	public static final String ATTRIBUT_MISSED = "MISSED";
	
	public static final String ATTRIBUT_INVALID = "INVALID";
	
	public static final String XML_FORMAT = "FORMAT";
	
	
	public InvalidXmlFormatException(String exType, String attriName)
	{
		super(exType.equals(InvalidXmlFormatException.ATTRIBUT_MISSED) 
				? "XML参数缺少名为" +attriName + "的参数" :
					exType.equals(InvalidXmlFormatException.ATTRIBUT_INVALID) 
					? "XML参数" +attriName + "的值格式有误" : 
						exType.equals(InvalidXmlFormatException.XML_FORMAT) 
							? "XML格式有误" : "XML处理发生未知错误");
	}
}
