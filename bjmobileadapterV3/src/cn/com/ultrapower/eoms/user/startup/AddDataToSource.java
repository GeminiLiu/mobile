package cn.com.ultrapower.eoms.user.startup;

import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage.SourceConfigOp;

public class AddDataToSource extends Thread {

	/**
	 * 日期 2006-12-20
	 * 
	 * @author wangyanguang/王彦广 
	 * @param args void
	 *
	 */
	public static void main(String[] args) 
	{
		SourceConfigOp   Scon        = new SourceConfigOp();
		SourceConfigBean sourceCNF   = new SourceConfigBean();
		sourceCNF.setSource_id(new java.lang.Long(100000));
		sourceCNF.setSource_parentid(new java.lang.Long(0));
		sourceCNF.setSource_cnname("测试资源"+1);
		sourceCNF.setSource_name("testsource"+1);
		sourceCNF.setSource_module("0");
		sourceCNF.setSource_orderby(new java.lang.Long(1));
		sourceCNF.setSource_desc("测试资源"+1);
		sourceCNF.setSource_type("0;");
		sourceCNF.setSource_fieldtype("");
		boolean bl = Scon.sourceCnfInsert(sourceCNF);
		if(bl)
		{
			System.out.println("第"+1+"条记录插入成功！");
		}

	}
	public void run()
	{
		
		SourceConfigOp   Scon        = new SourceConfigOp();
		for(int i=500000;i<510001;i++)
		{
			int j = i - 500000+4100;
			SourceConfigBean sourceCNF   = new SourceConfigBean();
			sourceCNF.setSource_id(new java.lang.Long(j));
			sourceCNF.setSource_parentid(new java.lang.Long(500001));
			sourceCNF.setSource_cnname("测试资源"+j);
			sourceCNF.setSource_name("testsource"+j);
			sourceCNF.setSource_module("500000");
			sourceCNF.setSource_orderby(new java.lang.Long(j));
			sourceCNF.setSource_desc("测试资源"+j);
			sourceCNF.setSource_type("0;");
			sourceCNF.setSource_fieldtype("");
			boolean bl = Scon.sourceCnfInsert(sourceCNF);
			if(bl)
			{
				int m = j-4100+1;
				System.out.println("第"+m+"条记录插入成功！");
			}
		}
		
	}

}
