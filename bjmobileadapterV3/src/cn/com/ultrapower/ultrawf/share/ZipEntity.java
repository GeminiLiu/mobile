package cn.com.ultrapower.ultrawf.share;

import   java.io.*; 

import org.apache.tools.zip.*;

public class ZipEntity
{
	static final int BUFFER = 2048;
	static boolean flag = false;
	
/*	
    public static boolean createZip11(String compressFileName,String args[]){    
        boolean flag = false;    
        try{            
            byte b[] = new byte[1024];       
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(compressFileName));       
            for(int i = 0; i < args.length; i++){       
               InputStream in = new FileInputStream(args[i]);     
               File file=new File(args[i]);    
               String filename = file.getName();//取得文件名    
//             ZipEntry e = new ZipEntry(args[i].replace(File.separatorChar,'/')); //压缩后带路径     
               ZipEntry e = new ZipEntry(filename);         //压缩后不带路径    
               zout.putNextEntry(e);       
               int len=0;    
               while((len=in.read(b)) != -1){    
                  zout.write(b,0,len);    
               }    
               zout.closeEntry();                
             }    
             zout.close();    
             flag = true;    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        return flag;    
    }
	*/
    
    public static boolean createZip(String filePath,String zipFileName,String[]   files){    
        boolean flag = false;    
        try{            
            byte b[] = new byte[512];       
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filePath+File.separator+zipFileName));       
            for(int i = 0; i < files.length; i++){       
               InputStream in = new FileInputStream(filePath+File.separator+files[i]);     
               File file=new File(files[i]);    
               String filename = file.getName();//取得文件名    
//             ZipEntry e = new ZipEntry(args[i].replace(File.separatorChar,'/')); //压缩后带路径     
               ZipEntry e = new ZipEntry(filename);         //压缩后不带路径    
               zout.putNextEntry(e);       
               int len=0;    
               while((len=in.read(b)) != -1){    
                  zout.write(b,0,len);    
               }    
               zout.closeEntry();                
             }    
             zout.close();    
             flag = true;    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        return flag;    
    }   
	/*
    
	public static void Entity(String filePath,String zipFileName, String[]   files)
	{
		try{
			
		  FileOutputStream   f   =   new   FileOutputStream(filePath+File.separator+zipFileName);   
		  CheckedOutputStream   csum   =   new   CheckedOutputStream(f,new   Adler32());   
		  ZipOutputStream   zos   =   new   ZipOutputStream(csum);   
		  BufferedOutputStream   out   =   new   BufferedOutputStream(zos);   
		  zos.setComment("UltraProcess Exp CSV ");   
		    
		  for(int   i=0;i<files.length;i++){   
		  System.out.println("Writing   file   "+files[i]);   
		  BufferedReader   in   =   new   BufferedReader(new   FileReader(filePath+File.separator+files[i]));   
		  zos.putNextEntry(new   ZipEntry(files[i]));   
		  int   c;   
		  while((c=in.read())!=-1)   
		          out.write(c);   
		  in.close();   
		  }   
		  out.close();  
		  System.out.println("Checksum:   "+csum.getChecksum().getValue());   
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}  
	*/
	
	public static boolean createZip(String zipFileName,String[]   files) throws Exception
	{
		BufferedInputStream origin = null;
		// 创建ZipOutputStream对象，将向它传递希望写入文件的输出流
		File zipFile = new File(zipFileName);
		//  FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		  
		// 创建临时文件tempFile,使用后删除
		File tempFile = null;
		try
		{
			for(int i = 0; i < files.length; i++)
			{
				tempFile = new File(files[i]);
				System.out.println("Compress file: " + tempFile.getName());
				FileInputStream fis = new FileInputStream(tempFile);
				origin = new BufferedInputStream(fis, BUFFER);
				// 为被读取的文件创建压缩条目
				ZipEntry entry = new org.apache.tools.zip.ZipEntry(tempFile.getName());
				byte data[] = new byte[BUFFER];
				int count;
				// 在向ZIP输出流写入数据之前，必须首先使用out.putNextEntry(entry); 方法安置压缩条目对象
				out.putNextEntry(entry);
				// 向ZIP 文件写入数据
				while((count = origin.read(data, 0, BUFFER)) != -1)
				{
					out.write(data, 0, count);
				}
			}
			// 关闭输入流
			out.closeEntry();
			origin.close();
			// tempFile是临时生成的ZIP文件,删除它
			if(flag == true)
			{
				flag = tempFile.delete();
				System.out.println("Delete file:" + tempFile.getName()+" " + flag);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		// 递归返回
		out.close();

		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
