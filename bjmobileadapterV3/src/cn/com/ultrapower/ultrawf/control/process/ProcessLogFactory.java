package cn.com.ultrapower.ultrawf.control.process;

public class ProcessLogFactory {
	
	public static IProcessLog CreateDealProcess()
	{
		ProcessLogFacade m_ProcessLogFacade=new ProcessLogFacade();
		return (IProcessLog)m_ProcessLogFacade;
	}
 
}
