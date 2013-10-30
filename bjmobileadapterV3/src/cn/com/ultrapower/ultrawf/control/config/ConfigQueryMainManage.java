package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;
import java.util.Iterator;

import cn.com.ultrapower.ultrawf.models.config.ConfigQueryMain;
import cn.com.ultrapower.ultrawf.models.config.ConfigQueryMainModel;
import cn.com.ultrapower.ultrawf.models.config.ParConfigQueryMainModel;

public class ConfigQueryMainManage {

	public ConfigQueryMainModel getOneModel(String p_Code)
	{
		ConfigQueryMain m_ConfigQueryMain=new ConfigQueryMain();
		return m_ConfigQueryMain.getOneModel(p_Code);
	}
	public List getList(ParConfigQueryMainModel p_ConfigQueryMainModel,int p_PageNumber,int p_StepRow)
	{
		ConfigQueryMain m_ConfigQueryMain=new ConfigQueryMain();
		return m_ConfigQueryMain.getList(p_ConfigQueryMainModel,p_PageNumber,p_StepRow);
	}
	public boolean insert(ConfigQueryMainModel p_ConfigQueryMainModel)
	{
		ConfigQueryMain m_ConfigQueryMain=new ConfigQueryMain();
		return m_ConfigQueryMain.insert(p_ConfigQueryMainModel);
	}
	public int update(ConfigQueryMainModel p_ConfigQueryMainModel)
	{
		ConfigQueryMain m_ConfigQueryMain=new ConfigQueryMain();
		return m_ConfigQueryMain.update(p_ConfigQueryMainModel);
	}
    public int delete(List deleteList)
    {
        Iterator deleteIterator=(Iterator)deleteList.iterator();
        int deleteCount=deleteList.size();
        if(deleteCount==0)
            return deleteCount;
        ConfigQueryMain p_ConfigQueryMain=new ConfigQueryMain();
        while(deleteIterator.hasNext())
        {
            String fldCode=(String)deleteIterator.next();
            if(p_ConfigQueryMain.delete(fldCode))
                deleteCount--;
        }
        return deleteCount;
    }
    
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		//ConfigQueryMainManage aa = new ConfigQueryMainManage();
        //boolean flag;
        ConfigQueryMainModel p_ParConfigQueryMainModel=new ConfigQueryMainModel();
        //p_ParConfigQueryMainModel.setCode("a");
        //p_ParConfigQueryMainModel.setConfdesc("ab");
        ConfigQueryMainManage p_ConfigQueryMainManage=new ConfigQueryMainManage();
        //flag=p_ConfigQueryMainManage.insert(p_ParConfigQueryMainModel);
        //if(flag)
        //    System.out.println("成功");
        //else
        //   System.out.println("失败");
        p_ParConfigQueryMainModel=p_ConfigQueryMainManage.getOneModel("a");
	}

}
