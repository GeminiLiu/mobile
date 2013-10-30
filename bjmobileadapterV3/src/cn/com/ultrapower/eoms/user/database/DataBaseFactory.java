package cn.com.ultrapower.eoms.user.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetConfigLastModify;
import cn.com.ultrapower.eoms.user.startup.TableBean;
public class DataBaseFactory
{
	static final Logger logger = (Logger) Logger.getLogger(DataBaseFactory.class);
    public static IDataBase createDataBaseClass()
    {
    	Function.initPram();
        OracleConsole oc = new OracleConsole(ConfigContents.DataBaseType,ConfigContents.DataBaseDriver,ConfigContents.DataBaseUrl,ConfigContents.DataBaseUser,ConfigContents.DataBasePwd);
        return (IDataBase)oc;
    }
    
    /**
     * <p>Description:通过属性文件获得数据库连接<p>
     * @author wangwenzhuo
     * @creattime 2006-11-24
     * @return IDataBase
     */
    public static IDataBase createDataBaseClassFromProp()
    {
    	try
    	{
    		Function.initPram();
			OracleConsole oc = new OracleConsole(ConfigContents.DataBaseType,ConfigContents.DataBaseDriver,ConfigContents.DataBaseUrl,ConfigContents.DataBaseUser,ConfigContents.DataBasePwd);
	        return (IDataBase)oc;
    	}
    	catch(Exception ex)
		{
			ex.getMessage();
			return null;
		}    	
    }

    public static IDataBase createDataBaseClass(String databasetype, String databasedriver, String databaseurl, String username, String password)
    {
        if(databasetype == "Oracle")
        {
            OracleConsole oc = new OracleConsole("Oracle", databasedriver,databaseurl,username,password);
            return (IDataBase)oc;
        }
        else
        {
            return null;
        }
    }
    

}
