package cn.com.ultrapower.ultrawf.control.queryinterface;

public class QueryIntegrationBaseAndProcessFactory {
	public static IQueryIntegrationBaseAndProcess createQueryManagerProcessClass()
	{
		QueryIntegrationBaseAndProcess m_QueryIntegrationBaseAndProcess=new QueryIntegrationBaseAndProcess();
		return (IQueryIntegrationBaseAndProcess)m_QueryIntegrationBaseAndProcess;
	}	
 
}
