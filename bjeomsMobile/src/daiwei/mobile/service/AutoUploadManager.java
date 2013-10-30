package daiwei.mobile.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.FileUtil;
import daiwei.mobile.util.StringUtil;

/**
 * 工单附件自动上传业务类
 * @author changxiaofei
 * @time 2013-3-27 下午4:02:39
 */
public class AutoUploadManager {
	private static final String TAG = "AutoUploadManager";
	private static AutoUploadManager autoUploadManager;	
	private static final int BUFFER = 1024 * 1024;// 缓存大小
	/**
	 * @return
	 */
	public synchronized static AutoUploadManager getInstance() {
		if (autoUploadManager == null) {
			autoUploadManager = new AutoUploadManager();
		}
		return autoUploadManager;
	}
	
	/**
	 * 获取SDCard里工单附件缓存文件夹路径。
	 * @param context
	 * @return
	 */
	public String getSDCardAttachmentPath(Context context) {
		String cacheFilePath = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果手机装有SDCard，并且可以进行读写
			cacheFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_ATTACHMENT).toString();// 获取SDCard目录
		}
		return cacheFilePath;
	}
	
	/**
	 * 获取手机内存里工单附件缓存文件夹路径。
	 * @param context
	 * @return
	 */
	public String getmemoryAttachmentPath(Context context) {
		return new StringBuilder(context.getFilesDir().getPath()).append(AppConstants.PATH_ATTACHMENT).toString();
	}
	
	/**
	 * 上传工单附件。每次从缓存文件夹里取第一个附件上传后移动到已上传目录下。
	 * 因为开启启动上传服务，没登录也能上传，cookie可传空。
	 * @param context
	 * @return
	 */
	public boolean uploadAttachment(Context context) {
		boolean result = true;
		String cacheFilePath = "";
		String cacheFilePathOld = "";
		TestXmlCreat tc;
		HTTPConnection hc;
		// 检查sdcard，有就挨个上传
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果手机装有SDCard，并且可以进行读写
				cacheFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_ATTACHMENT)
						.toString();// 获取SDCard目录
				cacheFilePathOld = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_ATTACHMENT_OLD)
						.toString();// 获取SDCard目录，已上传路径
				File sdcardFile = new File(cacheFilePath);
				if (sdcardFile.exists() && sdcardFile.isDirectory() && sdcardFile.list().length > 0) {
					tc = new TestXmlCreat(context);
					hc = new HTTPConnection(context);
					Map<String, String> parmas = new HashMap<String, String>();
					parmas.put("serviceCode", "G006");
					parmas.put("inputXml", tc.doc.asXML());
					String[] files = sdcardFile.list();
					for (int i = 0; i < files.length; i++) {// sdcard有附件
						// 上传一个附件
						File tempFile = new File(cacheFilePath, files[i]);
						if (tempFile != null && tempFile.exists() && tempFile.isFile()) {
							Map<String, File> map = new HashMap<String, File>();
							map.put(tempFile.getName(), tempFile);
							String sentResult = hc.Sent(parmas, map);
							if (isUploadSuccess(sentResult)) {
								File pahtOld = new File(cacheFilePathOld);
								if (!pahtOld.exists()) {
									pahtOld.mkdirs();
								}
								File oldFile = new File(new StringBuilder(cacheFilePathOld).append("/").append(tempFile.getName()).toString());
								tempFile.renameTo(oldFile);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			result = false;
			Log.e(TAG, new StringBuilder("uploadAttachment sdcard ").append(e.toString()).toString());
			e.printStackTrace();
		}
		// 检查手机内存，有就挨个上传
		try {
			cacheFilePath = new StringBuilder(context.getFilesDir().getPath()).append(AppConstants.PATH_ATTACHMENT).toString();
			cacheFilePathOld = new StringBuilder(context.getFilesDir().getPath()).append(AppConstants.PATH_ATTACHMENT_OLD).toString();// 获取手机目录，已上传路径
			File memoryFile = new File(cacheFilePath);
			if (memoryFile.exists() && memoryFile.isDirectory() && memoryFile.list().length > 0) {
				tc = new TestXmlCreat(context);
				hc = new HTTPConnection(context);
				Map<String, String> parmas = new HashMap<String, String>();
				parmas.put("serviceCode", "G006");
				parmas.put("inputXml", tc.doc.asXML());
				String[] files = memoryFile.list();
				for (int i = 0; i < files.length; i++) {// sdcard有附件
					// 上传一个附件
					File tempFile = new File(cacheFilePath, files[i]);
					if (tempFile != null && tempFile.exists() && tempFile.isFile()) {
						Map<String, File> map = new HashMap<String, File>();
						map.put(tempFile.getName(), tempFile);
						String sentResult = hc.Sent(parmas, map);
						if (isUploadSuccess(sentResult)) {
							File pahtOld = new File(cacheFilePathOld);
							if (!pahtOld.exists()) {
								pahtOld.mkdirs();
							}
							File oldFile = new File(new StringBuilder(cacheFilePathOld).append("/").append(tempFile.getName()).toString());
							tempFile.renameTo(oldFile);
						}
					}
				}
			}
		} catch (Exception e) {
			result = false;
			Log.e(TAG, new StringBuilder("uploadAttachment memory ").append(e.toString()).toString());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除已上传目录下的附件。
	 * @param context
	 * @return
	 */
	public boolean delOldAttachment(Context context) {
		boolean result = true;
		String cacheFilePathOld = "";
		// 检查sdcard，有就全部删除
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果手机装有SDCard，并且可以进行读写
				cacheFilePathOld = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_ATTACHMENT_OLD)
						.toString();// 获取SDCard目录，已上传路径
				File sdcardFile = new File(cacheFilePathOld);
				if (sdcardFile.exists() && sdcardFile.isDirectory() && sdcardFile.list().length > 0) {
					FileUtil.deletSubFileAndFolder(cacheFilePathOld);
				}
			}
		} catch (Exception e) {
			result = false;
			Log.e(TAG, new StringBuilder("uploadAttachment sdcard ").append(e.toString()).toString());
			e.printStackTrace();
		}
		// 检查手机内存，有就全部删除
		try {
			cacheFilePathOld = new StringBuilder(context.getFilesDir().getPath()).append(AppConstants.PATH_ATTACHMENT_OLD).toString();// 获取手机目录，已上传路径
			File memoryFile = new File(cacheFilePathOld);
			if (memoryFile.exists() && memoryFile.isDirectory() && memoryFile.list().length > 0) {
				FileUtil.deletSubFileAndFolder(cacheFilePathOld);
			}
		} catch (Exception e) {
			result = false;
			Log.e(TAG, new StringBuilder("uploadAttachment memory ").append(e.toString()).toString());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 解析上传结果xml，根据服务器返回的标志，判断上传成功还是失败。
	 * @param xml
	 * @return
	 */
	private boolean isUploadSuccess(String xml){
		boolean result = false;
		if(StringUtil.isEmpty(xml)){
			return false;
		}
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element isLegal = root.element("baseInfo").element("isLegal");
			Element success = root.element("baseInfo").element("success");
			if("1".equals(isLegal.getText()) && "1".equals(success.getText())){
				result = true;
			}
		} catch (DocumentException e) {
			Log.e(TAG, "isUploadSuccess() xml error " + e.toString());
			e.printStackTrace();
		}
		return result;
	}
	
	
	public boolean uploadCache(Context context){
		boolean flag = true;
		DBHelper helper = new DBHelper(context);
		System.out.println("!!!!!!!!!!!!!!!!");
		List<Map<String,String>> mapList = helper.executeQuery("select * from tb_gd_cache where IsUpload = 0 and IsWait = 0", new String[]{});
		System.out.println("mapList==========="+mapList);
		for(Map<String,String> map : mapList){
			Map<String, String> parmasInout = new HashMap<String, String>();
			parmasInout.put("serviceCode", map.get("serviceCode"));
			parmasInout.put("inputXml", map.get("inputXml"));
			parmasInout.put("hasPic", map.get("hasPic"));
			parmasInout.put("hasRec", map.get("hasRec"));
			System.out.println("serviceCode/////////////="+map.get("serviceCode"));
			System.out.println("xml========="+",,,"+map.get("inputXml"));
			System.out.println("picNum========="+map.get("hasPic"));
			System.out.println("recNum========="+map.get("hasRec"));
			HTTPConnection hc = new HTTPConnection(context);
			String x = hc.Sent(parmasInout);
			System.out.println("x==============="+x);
			XMLUtil xml = new XMLUtil(x.toString());
			System.out.println("xml.getSuccess()========="+xml.getSuccess());
			SQLiteDatabase db = helper.getWritableDatabase();
			if(xml.getSuccess()){
				if(db.isOpen()){
				db.execSQL("delete from [tb_gd_cache] where  TaskID = ?" , new String[]{map.get("TaskID")});
				db.close();
				AttachmentZip(context,map.get("BaseSN"),map.get("TaskID"),map.get("hasPic"),map.get("hasRec"));
				}
			}else{
				if(db.isOpen()){
				db.execSQL("update tb_gd_cache set actionCode = '' , actionText = '' , inputXml = '' ,hasPic = '' , hasRec = '' , IsWait = 1 , IsUpload = 0 where TaskID = ?",
						new String[]{map.get("TaskID")});
				db.close();
				}
				//Toast.makeText(context, map.get("BaseSummary")+"离线上传失败，请重新下载处理！",Toast.LENGTH_LONG).show();
			}
		}
		return flag;
	}
	
	private void AttachmentZip(Context context,String BaseSN,String taskId,String intPic,String intRec){
		try {
		String picPathChild = Environment.getExternalStorageDirectory()+"/daiwei" + "/" + BaseSN;// 工单号对应下的文件
		int picNum=0;
		int recNum=0;
		String zipFilePath = AutoUploadManager.getInstance()
				.getSDCardAttachmentPath(context);
		if (StringUtil.isEmpty(zipFilePath)) {
			zipFilePath = AutoUploadManager.getInstance()
					.getmemoryAttachmentPath(context);
		}

		
		File file = new File(zipFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyyMMdd HHmmss");
		String time = sDateFormat.format(new java.util.Date());
		File offLineFile=new File(picPathChild);
		if(intPic!=""||intPic!=null){
			picNum=Integer.parseInt(intPic);
		}
		if(intRec!=""||intRec!=null){
			recNum=Integer.parseInt(intRec);
		}
		System.out.println("picNum======="+picNum+",,,="+recNum);
		if (offLineFile != null && offLineFile.exists()
				&& offLineFile.isDirectory()) {
			File[] tempFiles = offLineFile.listFiles();
			for (int i = 0; i < tempFiles.length; i++) {
				File f = (File) tempFiles[i];
				String tempname = f.getName().toLowerCase();
				if (tempname != null
						&& tempname.length() > 4
						&& ".jpg".equals(tempname
								.substring(tempname.length() - 4))) {
					picNum++;
				} else if (tempname != null
						&& tempname.length() > 4
						&& ".amr".equals(tempname
								.substring(tempname.length() - 4))) {
					recNum++;
				}
			}
		}
		System.out.println("!!!!!zipFilePath======="+zipFilePath);
		System.out.println("picPathChild=========="+picPathChild);
		System.out.println("taskId=========="+taskId);
				if (picNum > 0) {
					zipFile(picPathChild, zipFilePath
							+ "/GD_" + taskId + "_PIC_"
							+ time + ".zip", ".JPG");
				}
				if (recNum > 0) {
					zipFile(picPathChild, zipFilePath
							+ "/GD_" + taskId + "_REC_"
							+ time + ".zip", ".amr");
				}
				FileUtil.deleteFolder(picPathChild);// 删除源文件
			} catch (Exception e) {
				e.printStackTrace();
			}			
	}
	/**
	 * zip压缩功能. 压缩baseDir(文件夹目录)下所有文件，包括子目录
	 * 
	 * @throws Exception
	 */
	public static void zipFile(String baseDir, String fileName,
			String fileSuffix) throws Exception {
		List fileList = getSubFiles(new File(baseDir));
		ZipOutputStream zos = new ZipOutputStream(
				new FileOutputStream(fileName));
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			String tempname = f.getName().toLowerCase();
			fileSuffix = fileSuffix.toLowerCase();
			if (tempname != null && tempname.indexOf(fileSuffix) > 0
					&& tempname.indexOf(fileSuffix) == tempname.length() - 4) {
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
			}
		}
		zos.close();
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}
}
