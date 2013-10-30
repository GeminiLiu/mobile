package cn.com.ultrapower.eoms.common.appconfig;

import java.io.File;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.ultrapower.eoms.util.Log;
import cn.com.ultrapower.eoms.util.XMLUtil;

/**
 * 解析XML文件
 * @author wanghongbing
 *
 */
public class ParametersManager {
	private File file;
	private Document doc;
	private static HashMap paraMap = new HashMap();
	
	public ParametersManager(){}
	public ParametersManager(String _filePath) {
		file = new File(_filePath + File.separator + "appconfig.xml");
		try {
			doc = (Document)XMLUtil.getDocument(file);
			//Log.logger.info("appconfig.xml解析初始化成功");
		} catch (Exception e) {
			Log.logger.error("appconfig.xml解析初始化失败");
			e.printStackTrace();
		}
	}

	/**
	 * 读取参数
	 * @return
	 */
	public void readParameters() {
		//Log.logger.info("appconfig.xml开始读入");
		Node root = null;
		NodeList appNodeList = null;
		NodeList modNodeList = null;
		NodeList prpNodeList = null;
		Node appNode = null;
		Node modNode = null;
		Node prpNode = null;
		String appName = null;
		String modName = null;
		String prpName = null;
		//解析XML文件
		try {
			root = doc.getFirstChild();
			//For debug
			if (root == null || !root.hasChildNodes()){
				Log.logger.error("root has no child nodes.");
			}
			if (root != null && root.hasChildNodes()) {
				//Log.logger.info("rootName is: " + root.getNodeName());
				appNodeList = root.getChildNodes();
				for (int i = 0; i < appNodeList.getLength(); i++) {
					//应用名称节点
					appNode = (Node) appNodeList.item(i);
					appName = appNode.getNodeName();
					//Log.logger.info("appName is: "+appName);
					if (appNode != null && appNode.hasChildNodes()){
						modNodeList = appNode.getChildNodes();
						for (int j = 0; j < modNodeList.getLength(); j++){
							//模块名称节点
							modNode = (Node)modNodeList.item(j);
							modName = modNode.getNodeName();
							//Log.logger.info("modName is: "+modName);
							if (modNode != null && modNode.hasChildNodes()){
								prpNodeList = modNode.getChildNodes();
								for (int k = 0; k < prpNodeList.getLength(); k++){
									//特性名称节点
									prpNode = (Node)prpNodeList.item(k);
									prpName = prpNode.getNodeName();
									//Log.logger.info("prpName is: "+prpName);
									StringBuffer sb = new StringBuffer();
									sb.append(appName.trim());
									sb.append(modName.trim());
									sb.append(prpName.trim());
									//Log.logger.info("sb is: "+sb.toString());
									////////////////////////////////////////////////////////////
									if (sb.toString().equalsIgnoreCase("plantemplateseparate")){
										PlanTemplateSeparate pts = new PlanTemplateSeparate();
										NamedNodeMap attributeList = prpNode.getAttributes();
										pts.setRowseparate(attributeList.getNamedItem("rowseparate").getNodeValue());
										pts.setCellseparate(attributeList.getNamedItem("cellseparate").getNodeValue());
										//Log.logger.info("appconfig.xml读入:plantemplateseparate");
										paraMap.put("plantemplateseparate", pts);
									}
									////////////////////////////////////////////////////////////
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Log.logger.error("读取appconfig.xml时，发现异常！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取需要的参数信息
	 * @param appName：应用名称，可以取{resource,myworklist,plan,repository,duty,forum}
	 * @param modName：模块名称，可以取每个应用下的模块的名字，例如对于plan应用，可以取template表示作业计划模板
	 * @param prpName：特性名称，某个模块下的某个特性的配置参数集合
	 * @return Object
	 */
	public static Object getParameters(String appName, String modName, String prpName){
		StringBuffer sb = new StringBuffer();
		sb.append(appName.trim());
		sb.append(modName.trim());
		sb.append(prpName.trim());
		//Log.logger.info("the paraMap size is: " + paraMap.size());
		return paraMap.get(sb.toString());
    }
}
