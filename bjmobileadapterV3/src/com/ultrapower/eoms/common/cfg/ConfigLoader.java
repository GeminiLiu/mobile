package com.ultrapower.eoms.common.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;

import com.ultrapower.eoms.common.cache.CacheEngine;
import com.ultrapower.eoms.common.exception.GeneralException;

public class ConfigLoader {
	private static Log log = LogFactory.getLog(ConfigLoader.class);
	private static CacheEngine cache;

	private static ConfigLoader instance = new ConfigLoader();

	private ConfigLoader() {
	}

	public static ConfigLoader getInstance() {
		return instance;
	}

	/**
	 * 启动缓存引擎
	 * 
	 * @throws GeneralException
	 */
	public static void startCacheEngine() throws Exception {
		String cacheImplementation = Config
				.getValue(ConfigKeys.CACHE_IMPLEMENTATION);
		log.info("Using cache engine: " + cacheImplementation);
		cache = (CacheEngine) Class.forName(cacheImplementation).newInstance();
		cache.init();
	}

	/**
	 * 停止缓存引擎
	 */
	public static void stopCacheEngine() {
		if (cache != null) {
			cache.stop();
		}
	}

	/**
	 * 返回缓存引擎
	 * 
	 * @return
	 */
	public static CacheEngine getCacheEngine() {
		return cache;
	}


	/**
	 * 加载配置文件
	 */
	public static void initConfig() {
		Config.init(ConfigKeys.ROOT);
	}

	/**
	 * 更新使用了Remedy T表的Hibernate配置文件
	 */
	public static void updateHibernateMappingFile() {
		try {
			Document doc = null;
			SAXReader xmlReader = new SAXReader();
			String cfgpath = ConfigKeys.ROOT + File.separator + "WEB-INF"
					+ File.separator + "cfg" + File.separator
					+ "BasicInfoConfig.xml";
			doc = xmlReader.read(cfgpath);
			Element root = doc.getRootElement();
			Element elementDS;
			Iterator iterDS;
			Iterator iterParam;
			iterDS = root.elementIterator("tablesource");
			if (iterDS.hasNext()) {
				Element elementParam;
				elementDS = (Element) iterDS.next();
				iterParam = elementDS.elementIterator("param");
				while (iterParam.hasNext()) {
					elementParam = (Element) iterParam.next();
					String name = elementParam.attributeValue("Tablename");
					String path = elementParam.attributeValue("path");
					writeFile(name, path);
				}
			}
			doc = null;
		} catch (Exception e) {
			log.error("读配置文件失败");
			e.getCause();
		}
	}

	private static void writeFile(String name, String path) {
		Document doc = null;
		String[] pathsp;
		String newpath = "";
		pathsp = path.split("/");
		if (pathsp.length > 0) {
			for (int i = 0; i < pathsp.length; i++) {
				if (newpath.equals("")) {
					newpath = pathsp[i];
				} else {
					newpath = newpath + File.separator + pathsp[i];
				}
			}
		}
		String strpath = ConfigContents.ROOT + newpath;
		String tablename = Config.getValue(name.trim());
		if (tablename == null || "".equals(tablename.trim())) {
			return;
		}
		log.info("Table=" + tablename + "||" + "path=" + strpath);
		SAXReader xmlReader = new SAXReader(false);
		xmlReader.setValidation(false);
		xmlReader.setEntityResolver(new org.hibernate.util.DTDEntityResolver());
		try {
			doc = xmlReader.read(new FileInputStream(strpath));
			Element root = doc.getRootElement();
			Iterator iterDS;
			iterDS = root.elementIterator("class");
			if (iterDS.hasNext()) {
				Element element = (Element) iterDS.next();
				Attribute attribute = element.attribute("table");
				attribute.setValue(tablename);
			}
			Writer out = new OutputStreamWriter(new FileOutputStream(strpath),
					"UTF-8");
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			out.close();
			log.info("更新Hiernate Mapping配置文件成功！！" + strpath);
		} catch (Exception e) {
			log.error("更新Hiernate Mapping配置文件失败！！" + strpath);
		}
	}

}
