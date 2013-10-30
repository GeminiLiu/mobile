package cn.com.ultrapower.ultrawf.control.config;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.SysGroup;
import cn.com.ultrapower.ultrawf.models.config.ParSysGroupModel;

public class SysGroupManage {

	private int m_PageCounts=0;
	
	public List GetList(int p_PageNumber,int p_StepRow)
	{
		
		SysGroup m_SysGroupr=new SysGroup();
		List m_List=m_SysGroupr.GetList(p_PageNumber,p_StepRow);
		m_PageCounts=m_SysGroupr.getQueryResultRows();
		CalculatePages(m_PageCounts,p_StepRow);	
		return m_List;
	}
	public List GetList(ParSysGroupModel p_ParGroupModel,int p_PageNumber,int p_StepRow)
	{
		SysGroup m_SysGroupr=new SysGroup();
		List m_List=m_SysGroupr.GetList(p_ParGroupModel,p_PageNumber,p_StepRow);
		m_PageCounts=m_SysGroupr.getQueryResultRows();
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
