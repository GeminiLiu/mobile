package cn.com.ultrapower.ultrawf.models.design;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import cn.com.ultrapower.ultrawf.models.process.TplBaseModel;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class WFDHandler
{
	public void saveDesignXML(TplBaseModel baseModel, String wfxml)
	{
		String pathStr = Constants.sysPath + File.separator + "tplstore" + File.separator;
		File path = new File(pathStr);
		if(!path.exists())
		{
			path.mkdir();
		}
		String filepath = path + File.separator + baseModel.getBaseTplSchemaName() + "_" + baseModel.getBaseTplSchema().substring(3) + "_" + baseModel.getBaseTplID() + ".xml";
		System.out.println("保存流程文件至" + filepath);
		System.out.println("开始保存。。。");
		try
		{
			SAXBuilder sb1 = new SAXBuilder();
			Document doc1 = sb1.build((Reader)new StringReader(wfxml));
			XMLOutputter writer1 = new XMLOutputter();
			new File(filepath).delete();
			FileOutputStream fos = new FileOutputStream(filepath);
			writer1.output(doc1, fos);
			System.out.println("保存成功。。。");
			fos.close();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
}
