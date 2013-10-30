package cn.com.ultrapower.ultrawf.control.process;

public class BaseFactory {

	public static IBase CreateEntityClass()
	{
		BaseFacade m_BaseFacade=new BaseFacade();
		return (IBase)m_BaseFacade;
	}
	
}
