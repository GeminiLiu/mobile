package cn.com.ultrapower.ultrawf.control.config;
import java.util.List;
import cn.com.ultrapower.ultrawf.models.config.BaseState;
public class BaseStateManage {

	public  List GetList()
	{
		BaseState m_BaseState=new BaseState();
		return m_BaseState.GetList();
	}
	
}
