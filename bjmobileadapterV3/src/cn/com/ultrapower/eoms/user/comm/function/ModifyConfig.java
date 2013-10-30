package cn.com.ultrapower.eoms.user.comm.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;
import cn.com.ultrapower.eoms.user.comm.bean.ConfigBean;

public class ModifyConfig 
{
	static final Logger logger = (Logger) Logger.getLogger(ModifyConfig.class);
	public boolean modifyconfig(ConfigBean configBean)
	{
		try
		{
			Document doc = null;
			String path =Function.getProjectPath();
			String strpath		=path+"WEB-INF"+File.separator+"cfg"+File.separator+"BasicInfoConfig.xml";
			SAXReader xmlReader = new SAXReader(false);
			//xmlReader.setEntityResolver(new org.hibernate.util.DTDEntityResolver());
			try
			{
				Iterator iterDS;
				doc 			= xmlReader.read(new FileInputStream(strpath));
				Element root	= doc.getRootElement();
				iterDS 			= root.elementIterator("database");
				Node iterParam;
				if(iterDS.hasNext())
				{
				 	Element element	= (Element)iterDS.next();
				 	iterParam	= element.selectSingleNode("DataBaseType");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getdatabasetype());
						logger.info("更新数据库类型");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("DataBaseDriver");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getdatabasedriver());
						logger.info("更新数据库Driver");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("DataBaseUrl");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getdatabaseurl());
						logger.info("更新数据库URL");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("DataBaseUser");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getdatabaseuser());
						logger.info("更新数据库用户");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("DataBasePwd");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getdatabasepwd());
						logger.info("更新数据库用户密码");
						iterParam=null;
					}
				 	logger.info("更新数据库连接串成功");
				}
				//更新remedy 服务器名,用户信息
				
				iterDS 			= root.elementIterator("RemedyParam");
				if(iterDS.hasNext())
				{
					Element element	= (Element)iterDS.next();
					iterParam	= element.selectSingleNode("driverurl");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getremedyserver());
						logger.info("更新remdy服务器名称");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("user");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getremedyuser());
						logger.info("更新Remedy用户名");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("password");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getremedypwd());
						logger.info("更新Remedy用户密码");
						iterParam=null;
					}
					iterParam	= element.selectSingleNode("serverport");
					if(iterParam!=null)
					{
						iterParam.setText(configBean.getremedyport());
						logger.info("更新Remedy端口号");
						iterParam=null;
					}
				}
				Writer out = new OutputStreamWriter(new FileOutputStream(strpath),"UTF-8");
				OutputFormat format = OutputFormat.createPrettyPrint();		
				XMLWriter writer = new XMLWriter(out,format);
				writer.write(doc);
				out.close();
				doc=null;
				logger.info("更新配置文件成功！！"+strpath);
				return true;
			}
			catch(Exception e)
			{
				logger.error("更新配置文件失败！！"+strpath);
				System.out.println("Exception!");
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
