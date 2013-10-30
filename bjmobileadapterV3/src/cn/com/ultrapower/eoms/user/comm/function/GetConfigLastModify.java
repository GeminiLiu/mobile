package cn.com.ultrapower.eoms.user.comm.function;

import java.io.File;

import org.apache.log4j.Logger;

public class GetConfigLastModify 
{
	static final Logger logger = (Logger) Logger.getLogger(GetConfigLastModify.class);
	/**
	 *
	 * author wuwenlong
	 * @param args
	 * @return void
	 * 公用方法
	 */
	public static long getFileLastModify(String path)
	{
		try
		{
			long lastmodify			= 0;
			File xmlfile			= new File(path);
		    lastmodify=xmlfile.lastModified();
		    logger.info("获得文件最后修改时间成功");
		    xmlfile=null;
		    return lastmodify;
		}
		catch(Exception e)
		{
			e.getMessage();
			logger.error("获得文件最后修改时间失败！");
			return 0;
		}
	}
}