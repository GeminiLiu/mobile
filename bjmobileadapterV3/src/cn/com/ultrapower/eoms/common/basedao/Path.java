package cn.com.ultrapower.eoms.common.basedao;

import java.io.File;

public final class Path {
 public static String ROOT;// ../eoms/
 public static String WEBINFO;// ../eoms/WebRoot/
 public static String CONF;// ../eoms/WebRoot/common/navigation
 private Path() {
     super();
 }	
 public static void initPath(String realPath) {
	ROOT=realPath;
	System.out.println("path="+ROOT);
	WEBINFO=ROOT + File.separator;
	CONF=WEBINFO + "common" +File.separator+"navigation"+File.separator;
}


}
