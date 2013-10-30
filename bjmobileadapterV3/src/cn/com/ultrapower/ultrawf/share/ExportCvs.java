package cn.com.ultrapower.ultrawf.share;

import java.io.*;

public class ExportCvs {

	private String m_filePath="";
	private String m_fileName="";
	private OutputStreamWriter osw = null;
	private BufferedWriter out = null;
	private FileOutputStream fos = null;
	FileWriter fw;
	
	public ExportCvs()
	{
		
	}
	 
	public ExportCvs(String filePath,String fileName)
	{
		m_filePath=filePath; 
		m_fileName=fileName;
		try{
			File file = new File(filePath+File.separator+fileName);
			fos = new FileOutputStream(file);
			//osw = new OutputStreamWriter(fos,"gb2312"); // 如果要写入全角字符，应该选择合适的编码
			osw = new OutputStreamWriter(fos);
			//fw = new FileWriter(filePath+File.separator+fileName);
			out = new BufferedWriter(osw);
			
			  //StreamWriter   sw   =   new   StreamWriter(strPath,blnAppend,System.Text.Encoding.GetEncoding("GB2312"));
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	public void setRowData(String dataLine)
	{
		if(dataLine==null)
			dataLine="";
		
			
		try{
			dataLine=dataLine.replaceAll("\n","");
			dataLine=dataLine.replaceAll("\r","");		
			//dataLine=dataLine.replaceAll("\r","\"");	
			
			//dataLine="\""+dataLine+"\"";
			out.write(dataLine);
			out.newLine();
		}catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	/*
	public void setRowData(String[] dataLine)
	{
		String split=",";
		StringBuffer stringBuffer=new StringBuffer();
		if(dataLine!=null)
		{
			for(int len=0;len<dataLine.length;len++)
			{
				if(len==0)
					stringBuffer.append(dataLine[len]);
				else
				{
					stringBuffer.append(split);
					stringBuffer.append(dataLine[len]);
				}
					
			}
		}
		try{
			out.write(stringBuffer.toString());
			out.newLine();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}	
	*/
	public boolean flush()
	{
		try{
			if(out!=null)
				out.flush();			

		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	} 
	
	public boolean close()
	{
		flush();
		try{
			if(out!=null)
				out.close();
			
			if(fw!=null)
				fw.close();
			
			if(fos!=null)
				fos.close();			
			if(osw!=null)		
				osw.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		fos = null;
		out = null;
		osw = null;
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xlsPath="";
		String xlsName="";
		ExportCvs m_ExportCvs=new ExportCvs(xlsPath,xlsName);
	}

}
