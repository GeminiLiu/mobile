package com.ultrapower.eoms.common.plugin.swfupload.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;

public class SwfuploadUtil
{
	/**
	 * 文件名字符串,格式：{xx.jpg:3432423.jpg};{yy.jpg:43938943.jpg};
	 * @param fileNames
	 * @return
	 */
	public static String[] parseFileNamePair(String fileNames)
	{
		String[] fileNamePairs = new String[0];
		if (StringUtils.isNotBlank(fileNames))
		{
			fileNamePairs = fileNames.split(BATCH_DWN_FILE_SEPARATOR);
		}
		return fileNamePairs;
	}

	/**
	 * @param fileDir 拷贝文件所在目录
	 * @param fileNamePairs 要拷贝的文件名字对,格式：xxx.jpg:348934839.jpg
	 * @return 返回临时文件目录
	 */
	public static String batchCopyFile(String fileDir, String[] fileNamePairs)
	{
		String tempDir = "";
		File dirTestor = new File(fileDir);
		if (dirTestor.exists())
		{
			// 拷贝临时文件的目录
			tempDir = fileDir + File.separator
			        + UUIDGenerator.getUUIDoffSpace();
			File downInfo = new File(tempDir + File.separator + "下载错误信息.txt");
			PrintWriter pw = null;
			try 
			{
				org.apache.tools.ant.util.FileUtils.getFileUtils().createNewFile(downInfo, true);
				pw = new PrintWriter(new FileWriter(downInfo));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			boolean errorFlag = true;
			// 创建并拷贝存在的文件,如果nowName文件存在才穿件临时文件
			for (String fileNamePair : fileNamePairs)
			{
				String[] tempdata = fileNamePair.split(BATCH_DWN_FILE_NAME_SEPARATOR);
//				String oldName = tempdata[0]; //源文件名
//				String nowName = tempdata[1]; //实际文件名
//				String filepath = tempdata[2]; //实际路径
//				String oldName = StringUtils.substringBefore(fileNamePair, BATCH_DWN_FILE_NAME_SEPARATOR);
//				String nowName = StringUtils.substringAfter(fileNamePair, BATCH_DWN_FILE_NAME_SEPARATOR);
				File sourceFile = new File(fileDir + File.separator + tempdata[1]);
				if (sourceFile.exists())
				{
					File destFile = new File(tempDir + File.separator + tempdata[0]);
					try
					{
						if (org.apache.tools.ant.util.FileUtils.getFileUtils().createNewFile(destFile, true))
						{
							copyFile(sourceFile, destFile);
						}
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					pw.println("该文件不存在或被删除："+tempdata[0]);
					errorFlag = false;
				}
			}
			pw.close();
			if(errorFlag)
				downInfo.delete();
		}
		return tempDir;
	}
	
	private static void copyFile(File sourceFile,File destFile){
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
		try {
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(destFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    byte[] buff = new byte[1024];
	    int readed = -1 ;
	    try {
			while((readed = fis.read(buff)) > 0){
			  fos.write(buff, 0, readed);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			  try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 打包文件列表
	 * @param 文件列表路径
	 * @return 打包文件路径
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String packZip(String dir) throws IOException
	{
		String zipFileName = dir + File.separator + SWFUPLOAD_BATCH_FILENAME;
		File directory = new File(dir);
		Collection<File> files = FileUtils.listFiles(directory, null, false);
		// 不能把压缩文件算在内
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
		zipOutputStream.setEncoding("GBK");
		for (File file : files)
		{
			byte[] buf = new byte[1024 * 5];
			int len;
			String temp_filename = file.getName();
			ZipEntry zipEntry = new ZipEntry(temp_filename);
			//zipEntry.setUnixMode(755);//目录
			zipEntry.setUnixMode(644);//文件
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fin);
			zipOutputStream.putNextEntry(zipEntry);
			while ((len = in.read(buf)) != -1)
			{
				zipOutputStream.write(buf, 0, len);
			}
			zipOutputStream.closeEntry();
			in.close();
		}
		zipOutputStream.close();
		return zipFileName;
	}

	/**
	 * 删除目录和该目录下的文件
	 * @param directory
	 * @throws IOException
	 */
	public static void deleteDirectory(String directory) throws IOException
	{
		File dir = new File(directory);
		if (dir.exists())
		{
			FileUtils.deleteDirectory(dir);
		}
	}
	
	/**
	 * 判断是否是绝度路径
	 * @param path
	 * @return
	 */
	public static boolean isAbsolutePath(String path)
	{
		if(path==null || "".equals(path))
		{
			return false;
		}
		if(path.startsWith("/") || path.matches("[C-Zc-z]:.*"))
		{
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		String fileDir = "E:\\june\\JavaEEWorkSpace\\eoms4\\WebRoot\\attachment\\upload\\";
		String[] fileNamePairs = new String[] {
		        "xxx.jpg:0b0c0be35176406ea3c558f9de4023f2Chrysanthemum.jpg",
		        "test.jpg:0d1c19e0f2464dc78e6ca754378f50cbPenguins.jpg" };
		String tempDir = batchCopyFile(fileDir, fileNamePairs);
		try
		{
			String zipfile = packZip(tempDir);
			// deleteDirectory(tempDir);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public static String getFileName_yearMonth(String year, String month)
	{//2010年3月--》201003 2010年12月--》201012
		String yearMonth = null;
		if(year!=null && month!=null)
		{
			month = month.length()==1?"0"+month:month;
			yearMonth = year + month;
		}
		return yearMonth;
	}
	
	public static String getFileName_yearMonth()
	{//2010年3月--》201003 2010年12月--》201012
		String yearMonth = null;
		Date tempDate = new Date();
		String year = String.valueOf(TimeUtils.getYearOfDate(tempDate));
		String month = String.valueOf(TimeUtils.getMonthOfDate(tempDate)).length()==1?"0"+String.valueOf(TimeUtils.getMonthOfDate(tempDate))
									:String.valueOf(TimeUtils.getMonthOfDate(tempDate));
		yearMonth = year + month;
		return yearMonth;
	}
	
	//根据操作系统处理路径分隔符
	public static String pathProcess(String path)
	{
		if(path!=null)
		{
			path = path.replace("\\", System.getProperty("file.separator"));
			path = path.replace("/", System.getProperty("file.separator"));
			if(path.endsWith(System.getProperty("file.separator")))
			{
				path = path.substring(0,path.lastIndexOf(System.getProperty("file.separator")));
			}
		}
		return path;
	}
	
	// 批量下载文件名串分隔符
	public static final String BATCH_DWN_FILE_SEPARATOR = "@semi@";
	// 批量下载文件名分隔符
	public static final String BATCH_DWN_FILE_NAME_SEPARATOR = "@comm@";
	// div
	public static final String SWFUPLOAD_DIV = "UploaderContainer";
	//form
	public static final String SWFUPLOAD_FORM = "_SWFUploaderForm";
	// 上传路径(可以使用相对路径，也可以使用绝对路径)
	// public static final String SWFUPLOAD_UPLOAD_PATH = "attachment/upload";
	public static String SWFUPLOAD_UPLOAD_PATH;
	// 批量下载文件名
	public static final String SWFUPLOAD_BATCH_FILENAME = "attachment.zip";
}
