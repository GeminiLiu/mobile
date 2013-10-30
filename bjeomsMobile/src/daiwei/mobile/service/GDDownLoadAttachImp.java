package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;

import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import android.content.Context;
import android.os.Environment;
/**
 * 工单下载附件接口的实现类
 * @author qch
 * 2013/5/20
 */
public class GDDownLoadAttachImp implements GDDownLoadAttach {

	@Override
	public String downLoadAttach(Context context, String baseId, String category) {
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		
		tc.addElement(tc.recordInfo, "baseId", baseId);
		tc.addElement(tc.recordInfo, "category", category);
		
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "G008");//工单下载附件
		parmas.put("inputXml", tc.doc.asXML());
		
		hc.setInputType("File");
		String zipfile=baseId+".zip";
		String path=Environment.getExternalStorageDirectory()+"/attach/"+baseId;
		
		hc.setFileName(zipfile);
		hc.setFilePath(path);
		hc.setZip(true);//设置可压缩
		String str = hc.Sent(parmas);
		System.out.println("G008下载附件返回结果:"+str);
		return str;
	}

}
