package daiwei.mobile.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import daiwei.mobile.activity.BaseApplication;
import daiwei.mobile.animal.HttpResultData;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.Zipper;
import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class HTTPConnection {
	private static final String TAG = "HTTPConnection";
	public static final int SOCKET_TIMEOUT = 120000;
	public static final int CONNECTION_TIMEOUT = 120000;
	private static String URL;
	public static String cookies = null;
	private String inputType = "String"; 
	private String filePath;
	private String fileName;
	private boolean isZip = true;
	/**
	 * 构造器<br>
	 * <b>注： context用getApplicationContext()<b>
	 * @param context 用getApplicationContext()
	 */
	public HTTPConnection(Context context) {
		URL = new StringBuilder("http://").append(((BaseApplication) context).getCustomIpAddress()).append(AppConfigs.URL_PATH).toString();
	}
	
	public HTTPConnection(String serCaller, String loginName, String serType, String callTime, String opDetail) {
	}
	
	public static void addPrams(String PramsName, String PramsValue) {
	}
	
	public String Sent(Map<String, String> parmas) {
		String returnConnection = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		if (cookies != null) {
			httpPost.setHeader("Cookie", "JSESSIONID=" + cookies);
		}
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (parmas != null) {
			Set<String> keys = parmas.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = i.next();
				pairs.add(new BasicNameValuePair(key, parmas.get(key)));
			}
		}
		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf-8");
			httpPost.setEntity(p_entity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			if(inputType.equals("String"))
				returnConnection = convertStreamToString(content);
			else if(inputType.equals("File")){
				WriteContentFromStream(content);
				if(isZip){
					if(Zipper.unzip(filePath + "/" + fileName, filePath))
						returnConnection = "OK";
				}else
					returnConnection = "OK";
			}
			if (cookies == null) {
				getCookies(response);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnConnection;
	}
	
	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static void getCookies(HttpResponse httpResponse) {
		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		String headerstr = headers.toString();
		if (headers == null) {
			return;
		}
		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue();
			String[] cookievalues = cookie.split(";");
			for (int j = 0; j < cookievalues.length; j++) {
				String[] keyPair = cookievalues[j].split("=");
				String key = keyPair[0].trim();
				String value = keyPair.length > 1 ? keyPair[1].trim() : "";
				Log.d("cookie", key + "=" + value);
				if (key.equals("JSESSIONID")) {
					cookies = value;
				}
			}
		}
	}
	
	public String Sent(Map<String, String> parmas, Map<String, File> files) throws IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		InputStreamReader in = null;
		String result = "";
		try {
			URL uri = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			if (cookies != null) {
			}
			conn.setRequestProperty("Cookie", "JSESSIONID=" + cookies);
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : parmas.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			// 发送文件数据
			if (files != null) {
				for (Map.Entry<String, File> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());
					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					is.close();
					outStream.write(LINEND.getBytes());
				}
			}
			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			in = new InputStreamReader(conn.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			outStream.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	

	
	/**
	 * 通用的HTTP的POST方法
	 * @param context
	 * @param strUrl
	 * @param params
	 * @return
	 */
	public static HttpResultData post(Context context, HashMap<String, String> parmas) {
		//String url = new StringBuilder("http://").append(((BaseApplication) context).getCustomIpAddress()).append(AppConfigs.URL_PATH).toString();
		//url = "http://192.168.1.124:8010/ultramobile/mobile/call.action";//???测试
		HttpResultData resultData = new HttpResultData();
		HttpPost httpRequest = new HttpPost(URL);
		try {
			if (parmas != null && !parmas.isEmpty()) {
				ArrayList<NameValuePair> listParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : parmas.entrySet()) {
					listParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpRequest.setEntity(new UrlEncodedFormEntity(listParams, HTTP.UTF_8));
			}
			// 设置一些基本参数
			DefaultHttpClient mHttpClient = new DefaultHttpClient();
			HttpParams httpParams = mHttpClient.getParams();
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT); // Socket请求超时
			HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT); // 连接超时
			HttpResponse httpResponse = mHttpClient.execute(httpRequest); // 发送请求并等待响应
			int intStatusCode = httpResponse.getStatusLine().getStatusCode();
			if (intStatusCode == HttpStatus.SC_OK) { // 状态码为200
				InputStream in = httpResponse.getEntity().getContent();//返回InputStream 
				
				File sd = Environment.getExternalStorageDirectory();
				File dir=new File(sd+"/1");
				System.out.println("dir===================="+dir);
				if(!dir.exists()){
					dir.mkdirs();
					System.out.println("目录创建成功");
				}else{
					System.out.println("目录存在");
				}
				File file=new File(dir+"/aaaaaaaaaaaa.zip");
				System.out.println("文件："+file);
				System.out.println("创建文件成功");
				System.out.println("文件路径为"+file.getPath());
				
//				WriteContentFromStream(in, Environment.getExternalStorageDirectory().getPath()+"/1/aaaaaaaaaaaa.zip");
				//WriteContentFromStream(in, file.getPath());
				//resultData.setStrResult(EntityUtils.toString(httpResponse.getEntity())); // 读返回数据
				resultData.setHttpStatusOK(true);
				resultData.setErrorCode(0);
				return resultData;
			} else {
				resultData.setErrorCode(AppConstants.ERROR_HTTP_SERVER_ERROR);
				resultData.setErrorMsg("statusCode not 200: " + httpResponse.getStatusLine().toString());
			}
		} catch (UnknownHostException e) {
			resultData.setErrorCode(AppConstants.ERROR_HTTP_UNKNOWNHOST);
		} catch (SocketTimeoutException e) {
			resultData.setErrorCode(AppConstants.ERROR_HTTP_TIMEOUT);
		} catch (ConnectTimeoutException e) {
			resultData.setErrorCode(AppConstants.ERROR_HTTP_TIMEOUT);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "doPost " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "doPost " + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, "doPost " + e.toString());
			e.printStackTrace();
		}
		return resultData;
	}
	/*
     * 解压ZIP格式文件的方法
     * @zipFileName：是传进来你要解压的文件路径，包括文件的名字；
     * @outputDirectory:选择要保存的路径
     * @author qch
     */ 
	 public boolean unzip(String zipFileName, String outputDirectory) { 
	    	try {
	            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName)); 
	            ZipEntry z; 
	            String name = ""; 
	            String extractedFile = ""; 
	            int counter = 0; 

	            while ((z = in.getNextEntry()) != null) { 
	                name = z.getName(); 
	                Log.d(TAG, "unzipping file: " + name); //要解压的文件名
	                if (z.isDirectory()) { //如果要解压的文件是目录
	                    Log.d(TAG, name + "is a folder"); 
	                    name = name.substring(0, name.length() - 1); 
	                    File folder = new File(outputDirectory + File.separator + name); 
	                    folder.mkdirs(); 
	                    if (counter == 0) { 
	                        extractedFile = folder.toString(); 
	                    } 
	                    counter++; 
	                    Log.d(TAG, "mkdir " + outputDirectory + File.separator + name); 
	                } else { //要解压的是文件
	                    Log.d(TAG, name + "is a normal file"); 
	                    File file = new File(outputDirectory + File.separator + name); 
	                    file.createNewFile(); 
	                    FileOutputStream out = new FileOutputStream(file); 
	                    int ch; 
	                    byte[] buffer = new byte[1024]; 
	                    while ((ch = in.read(buffer)) != -1) { 
	                        out.write(buffer, 0, ch); 
	                        out.flush(); 
	                    } 
	                    out.close(); 
	                } 
	            } 
	            in.close(); 
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    	return true;//压缩成功
	    } 

	/**
	 * 把流写入文件中
	 * @param is
	 * @param filePath
	 * @author chaoxiaofei
	 */
	private void WriteContentFromStream(InputStream is) {
		FileOutputStream ou = null;
		try {
			
			File dir=new File(filePath);
			if(!dir.exists())
				dir.mkdirs();
			ou = new FileOutputStream(new File(dir,fileName));
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				ou.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ou != null) {
				try {
					ou.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isZip() {
		return isZip;
	}

	public void setZip(boolean isZip) {
		this.isZip = isZip;
	}
}