package com.ultrapower.mobile.model.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class Test {
	
	public static List<DictInfo> ranDict() {
//		Random ran = new Random();
		List<DictInfo> list = new ArrayList<DictInfo>();
		for (int i = 0; i < 3; i++) {
			list.add(new DictInfo(i+"", "a", "a"));
		}
		return list;
	}
	
	public static void main(String[] args) {
		DictInfo d = new DictInfo("1", "rootKey", "rootValue");
		d.setDicts(ranDict());
//		d.getDicts().get(0).setDicts(ranDict());
		
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("dict", DictInfo.class);

        System.out.println(xstream.toXML(d));
	}
}
