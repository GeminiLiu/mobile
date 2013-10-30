package daiwei.mobile.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.EncodingUtils;

import android.util.Log;

/**
 * 文件上传、下载操作类
 * 
 * @author
 * 
 */
public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 文件上传
	 * 
	 * @param serverUrl
	 *            上传服务器的url: http://192.168.0.8:8080/upload/UploadServlet
	 * @param uploadFilePath
	 *            手机上的文件路径： /mnt/sdcard/upload/writchie.xml;
	 * @return
	 */
	public static boolean uploadFile(String serverUrl, String uploadFilePath) {
		boolean flag = false;
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(serverUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ uploadFilePath.substring(uploadFilePath.lastIndexOf("/") + 1)
					+ "\"" + end);// file对应 <input type="file" name="file" />
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(uploadFilePath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close();
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();// 服务器端返回的结果
			// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			dos.close();
			is.close();
			flag = true;
		} catch (Exception e) {
			System.out.println("上传文件异常:" + e);
		}
		return flag;
	}

	/**
	 * 文件下载
	 * 
	 * @param serverUrl
	 *            下载服务器的url: http://192.168.0.8:8080/download/writchie.xml
	 * @param downloadPath
	 *            手机保存的路： /mnt/sdcard/download/
	 * @param fileName
	 *            保存在手机的名称：writchie.xml
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式，扩展用，用""即可
	 * @return URL所代表远程资源的响应
	 */
	public static boolean download(String serverUrl, String downloadPath,
			String fileName, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		FileWriter fileWriter;
		BufferedWriter writer = null;
		String result = "";
		boolean flag = false;
		try {
			URL realUrl = new URL(serverUrl);// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流
			out.print(params);// 发送请求参数
			out.flush();// flush输出流的缓冲
			File downloadPathFolder = new File(downloadPath);
			if (!downloadPathFolder.exists())
				downloadPathFolder.mkdirs();
			File saveFile = new File(downloadPathFolder + File.separator
					+ fileName);
			if (!saveFile.exists())
				saveFile.createNewFile();
			fileWriter = new FileWriter(saveFile);
			writer = new BufferedWriter(fileWriter);

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
			flag = true;
		} catch (Exception e) {
			System.out.println("下载文件出现异常：" + e);
		} finally // 使用finally块来关闭输出流、输入流
		{
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
				System.out.println("下载文件关闭文件流出现异常：" + ex);
			}
		}
		return flag;
	}

	/**
	 * 功能：创建目录
	 * 
	 * @param file
	 *            文件或目录
	 */
	public static void createDir(File file) {
		if (file.isDirectory() && !file.exists()) {
			file.mkdirs();
		} else {
			String path = file.getPath();
			int i = path.lastIndexOf(File.separator);
			path = path.substring(0, i);
			new File(path).mkdirs();
		}
	}

	/**
	 * 功能：字符分割
	 * 
	 * @param srcStr
	 *            :分割字符串 writchie.splite.test
	 * @param pattern
	 *            ：分割符 ，如，# _等
	 * @param indexOrLast
	 *            : 取分割符的第一个还是最后一个，index取indexOf,否则取lastIndexOf
	 * @param frontOrBack
	 *            : 取分割符前部分还 是后部分：front取分割符前部分，否则取分割符后部分
	 * @return
	 */
	public static String spliteString(String srcStr, String pattern,
			String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern);
		} else {
			loc = srcStr.lastIndexOf(pattern);
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc);
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length());
		}
		return result;
	}

	/**
	 * 获取文件名及后缀
	 * 
	 * @param filePath
	 *            :文件路径字符串 /sdcard/mwp/files/writchie.xml
	 * @return 字符串：如writchie.xml
	 */
	public static String getFileName(String filePath) {
		String fileName = "";
		try {
			int slashLoc = filePath.lastIndexOf(File.separator);
			if (slashLoc != -1) {
				fileName = filePath.substring(slashLoc + 1, filePath.length());
			}
		} catch (Exception e) {
			System.out.println("获文件名异常：" + e);
		}
		return fileName;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param dir
	 * @return boolean true：表示删除文件夹成功；false:表示删除文件夹失败
	 */
	public static boolean deleteFolder(String delFolderPath) {
		boolean flag = false;
		File dir = new File(delFolderPath);
		Log.i("FileUtil", "删除的文件夹:" + dir.getPath());
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File temp = new File(dir, children[i]);
				if (temp.isDirectory()) {
					Log.d("FileUtil", "Recursive Call:" + temp.getPath());
					deleteFolder(temp.getAbsolutePath());
				} else {
					Log.i("FileUtil", "删除文件：" + temp.getPath());
					boolean b = temp.delete();
					if (b == false) {
						Log.i("FileUtil", "删除文件失败。");
					}
				}
			}
			dir.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除A目录下的所有文件和子目录，并保留该A目录。
	 * 
	 * @param mainPath
	 * @return true：表示删除文件夹成功；false:表示删除文件夹失败
	 */
	public static boolean deletSubFileAndFolder(String mainPath) {
		boolean flag = false;
		try {
			File dir = new File(mainPath);
			Log.d("FileUtil", "deletSubFileAndFolder() " + dir.getPath());
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					File temp = new File(dir, children[i]);
					if (temp.isDirectory()) {
						deleteFolder(temp.getAbsolutePath());
					} else {
						Log.d("FileUtil", "deletSubFileAndFolder() delete() "
								+ temp.getPath());
						temp.delete();
					}
				}
			}
			flag = true;
		} catch (Exception e) {
			Log.e(TAG, "deletSubFileAndFolder " + e.toString());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            /mnt/sdcard/writchie.xml
	 * @return true：表示删除成功；false:表示删除失败
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File delFile = new File(filePath);
		if (delFile.exists()) {
			delFile.delete();
			flag = true;
		}
		return flag;
	}

	/*
	 * 读取文件文本内容
	 */
	public static String readFile(String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];
			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}