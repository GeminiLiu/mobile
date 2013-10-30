package cn.com.ultrapower.eoms.user.comm.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

/**
 * <p>
 * Description:获得配置文件路径
 * <p>
 * 
 * @author wangwenzhuo
 * @creattime 2006-11-24
 * @return IDataBase
 */
public final class ConfigContents {
	static final Logger logger = (Logger) Logger
			.getLogger(ConfigContents.class);
	public static boolean cacheflag = false;
	public static boolean complete = false;
	public static long lastmodify = 0;
	// ../root
	public static String ROOT;
	// ../ItEoms/WEB-INFO/
	public static String WEBINFO;
	// ../ItEoms/WEB-INFO/conf/
	public static String CONF;

	/**
	 * 描述：数据库类型
	 */
	public static String DataBaseType = "";

	/**
	 * 描述：数据库连接的URL
	 */
	public static String DataBaseUrl = "";

	/**
	 * 描述：连接的数据库驱动名
	 */
	public static String DataBaseDriver = "";

	/**
	 * 描述：数据库连接的用户名
	 */
	public static String DataBaseUser = "";

	/**
	 * 描述：数据库连接的密码
	 */
	public static String DataBasePwd = "";
	public static String DBPoolName = "";
	public static int DBPoolMinConnection = 1;
	public static int DBPoolMaxConnection = 20;
	public static int DBPoolTimeoutValue = 1000;

	public static HashMap mapParam = new HashMap();

	private ConfigContents() {
		super();
	}

	public synchronized static void initContents() {
		// arschema tables
		Map<String, String> arschema = new HashMap<String, String>();
		IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
		Statement stmt = dataBase.GetStatement();
		ResultSet rs = null;
		try {
			String sql = "select NAME,SCHEMAID from arschema";
			rs = dataBase.executeResultSet(stmt, sql);
			while (rs.next()) {
				arschema.put(rs.getString("NAME"), rs.getString("SCHEMAID"));
			}
			logger.info("读取ARSCHEMA成功!");
		} catch (Exception e) {
			e.getMessage();
			logger.error("读取ARSCHEMA失败!");
		} finally {
			Function.closeDataBaseSource(rs, stmt, dataBase);
		}
		// 需要更新的使用Hibernate的T表
		Map<String,String> hibernatetables = new HashMap<String,String>();
		
		Document doc = null;
		SAXReader xmlReader = new SAXReader();
		try {
			mapParam.clear();
			WEBINFO = ROOT + "WEB-INF" + File.separator;
			CONF = WEBINFO + "cfg" + File.separator;
			logger.info("开始读取配置文件!");
			String cfgpath = ConfigContents.CONF+ "BasicInfoConfig.xml";
			File xmlfile = new File(cfgpath);
			doc = xmlReader.read(xmlfile);
			Element root = doc.getRootElement();
			Element tsNode = (Element) root.selectSingleNode("tablesource");
			List<Element> paramsEle = tsNode.selectNodes("param");
			for (Element e : paramsEle) {
				hibernatetables.put(e.attributeValue("Tablename"), e
						.attributeValue("path"));
			}
			
			Element rpNode = (Element) root.selectSingleNode("RemedyParam");
			paramsEle = rpNode.elements();
			for (Element e : paramsEle) {
				mapParam.put(e.getName(), e.getText());
			}
			
			Element rtNode = (Element) root.selectSingleNode("RemedyTable");
			paramsEle = rtNode.elements();
			String tablename = null;
			for (Element e : paramsEle) {
				tablename = "T" + arschema.get(e.getText());
				mapParam.put(e.getName(), tablename);
				logger.info("读取配置文件成功!" + e.getName() + "=======" + tablename);
			}
			complete = true;
			xmlReader = null;
			doc = null;
			logger.info("读取配置文件成功!");
			
			//更新Hibernate配置文件路径
			Collection<String> tableNames = hibernatetables.keySet();
			for(String name : tableNames){
				String tname = "T" + arschema.get(name);
				writecfgXml(hibernatetables.get(name), tname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static boolean writecfgXml(String path, String Ttable) {
		Document doc = null;
		String[] pathsp = path.split("/");
		String newpath = "";
		if (pathsp.length > 0) {
			for (int i = 0; i < pathsp.length; i++) {
				if (newpath.equals("")) {
					newpath = pathsp[i];
				} else {
					newpath = newpath + File.separator + pathsp[i];
				}
			}
		}
		newpath = ConfigContents.ROOT + newpath;
		SAXReader xmlReader = new SAXReader(false);
		xmlReader.setValidation(false);
		xmlReader.setEntityResolver(new org.hibernate.util.DTDEntityResolver());
		try {
			doc = xmlReader.read(new FileInputStream(newpath));
			Element root = doc.getRootElement();
			Element classEle = (Element) root.selectSingleNode("class");
			Attribute attribute = classEle.attribute("table");
			logger.info(path + "=" + attribute.getText() + "->" + Ttable);
			attribute.setValue(Ttable);
			Writer out = new OutputStreamWriter(new FileOutputStream(newpath),
					"UTF-8");
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
			out.close();
			logger.info("更新配置文件成功！！" + newpath);
			return true;
		} catch (Exception e) {
			logger.error("更新配置文件失败！！" + newpath);
			e.printStackTrace();
			return false;
		}
	}
}