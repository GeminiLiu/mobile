package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.ParSysUserModel;
import cn.com.ultrapower.ultrawf.models.config.SysUser;
import cn.com.ultrapower.ultrawf.models.config.SysUserModel;


public class SysUserManage {

	private int m_PageCounts=0;
	
	public SysUserModel GetOneForKey(String p_LoginName) 
	{
		SysUser m_SysUser=new SysUser();
		return m_SysUser.getOneForKey(p_LoginName);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public List getList(ParSysUserModel p_ParSysUserModel,int p_PageNumber,int p_StepRow)
	{
		SysUser m_SysUser=new SysUser();
		List m_List=m_SysUser.getList(p_ParSysUserModel,p_PageNumber,p_StepRow);
		m_PageCounts=m_SysUser.getQueryResultRows();
		CalculatePages(m_PageCounts,p_StepRow);			
		return m_List;
	}

	/**
	 * 计算总页数

	 * @param p_RusultRows
	 * @param p_StepRow
	 */
	private void CalculatePages(int p_RusultRows,int p_StepRow)
	{
		int intPages=0;
		if(p_StepRow>0)
		{
			intPages=p_RusultRows/p_StepRow;
			if(p_RusultRows%p_StepRow>0)
				intPages++;
		}
		m_PageCounts=intPages;
	}
	/**
	 * 返回页数
	 */
	public int getPageCount()
	{
		return m_PageCounts;
	}
	
}
