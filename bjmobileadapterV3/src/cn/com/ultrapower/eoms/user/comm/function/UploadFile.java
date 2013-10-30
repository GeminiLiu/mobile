package cn.com.ultrapower.eoms.user.comm.function;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class UploadFile {
	public boolean UploadFileToServer(HttpServletRequest request, String path,
			String loginname) {
		String projectpath = "";
		projectpath = Function.getProjectPath();
		DiskFileItemFactory factory;
		ServletFileUpload upload;
		factory = new DiskFileItemFactory();
		// 附件大于该尺寸时就要使用硬盘缓存:10M
		factory.setSizeThreshold(10485760);
		// 临时保存的位置,默认使用tomcat的临时目录,如:d:\tomcat5\temp
		upload = new ServletFileUpload(factory);
		// 最大可上传文件大小:100M
		upload.setSizeMax(104857600);
		try {
			String flag = "";
			// 开始读取上传信息
			List fileItems = upload.parseRequest(request);
			// 依次处理每个上传的文件
			Iterator iter = fileItems.iterator();
			// 正则匹配，过滤路径取文件名
			String regExp = ".+\\\\(.+)$";

			// 过滤掉的文件类型
			String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".doc" };
			Pattern p = Pattern.compile(regExp);
			while (iter.hasNext()) {
				// System.out.println("aa");
				FileItem item = (FileItem) iter.next();
				// 忽略其他不是文件域的所有表单信息
				if (!item.isFormField()) {
					String name = item.getName();
					long size = item.getSize();
					if ((name == null || name.equals("")) && size == 0)
						continue;
					Matcher m = p.matcher(name);
					boolean result = m.find();
					if (result) {
						for (int temp = 0; temp < errorType.length; temp++) {
							if (m.group(1).endsWith(errorType[temp])) {
								throw new IOException(name + ": wrong type");
							}
						}
						try {
							// 保存上传的文件到指定的目录
							// 在下文中上传文件至数据库时，将对这里改写
							File newfile = new File(projectpath
									+ File.separator + path + File.separator
									+ m.group(1));
							// System.out.println(newfile.getAbsoluteFile());
							// System.out.println(newfile.getPath());
							// item.write(newfile);
							blobInsert(name, loginname, m.group(1), item
									.getInputStream());
						} catch (Exception e) {

						}
					} else {
						// throw new IOException("fail to upload");
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String existspic(String userloginname, String picname) {
		IDataBase dataBase = null;
		// 获得数据库查询结果集
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String newid = "";
			String sql = "select * from userpicinfo where Pic_LoginName='"
					+ userloginname + "'";
			String updatesql = "update userpicinfo set Pic_name='" + picname
					+ "' where Pic_LoginName='" + userloginname + "'";
			String insertsql = "";
			// 实例化一个类型为接口IDataBase类型的工厂类
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			stmt = dataBase.GetStatement();
			rs = stmt.executeQuery(sql);
			if (rs != null && rs.next()) {
				stmt.execute(updatesql);
			} else {
				newid = Function.getNewID("userpicinfo", "Pic_id");
				insertsql = "insert into userpicinfo(Pic_id,Pic_LoginName,Pic_name) values('"
						+ newid
						+ "','"
						+ userloginname
						+ "','"
						+ picname
						+ "')";
				stmt.execute(insertsql);
			}
			return "0";
		} catch (Exception e) {
			return "1";
		} finally {
			Function.closeDataBaseSource(rs, stmt, dataBase);
		}
	}

	// 插入BLOB信息 yinhonggang
	private  void blobInsert(String infile, String userloginname,
		String picname, InputStream im) throws Exception {
		IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
		Connection conn = dataBase.getConn();
		ResultSet rs = null;
		Statement stmt = dataBase.GetStatement();
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			String newid = "";
			newid = Function.getNewID("userpicinfo", "Pic_id");
			String sql = "select * from userpicinfo where Pic_LoginName='"
					+ userloginname + "'";
			String updatesql = "delete from userpicinfo u where u.pic_loginname='"
					+ userloginname + "'";
			rs = stmt.executeQuery(sql);
			if (rs != null && rs.next()) {
				stmt.execute(updatesql);
			}

			/* 插入一个空的BLOB对象 */
			stmt.executeUpdate("insert into userpicinfo(Pic_id,Pic_LoginName,Pic_att,Pic_name) values ('"
							+ newid
							+ "','"
							+ userloginname
							+ "',EMPTY_BLOB(),'" + picname + "')");
			/* 查询此BLOB对象并锁定 */
			rs = stmt.executeQuery("select Pic_att from userpicinfo where Pic_id='"
							+ newid + "' for update");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("Pic_att");
				/* 向BLOB对象中写入数据 */
				BufferedOutputStream out = null;
				BufferedInputStream in = null;
				try {
					out = new BufferedOutputStream(blob.getBinaryOutputStream());
					in = new BufferedInputStream(im);
				} catch (Exception e) {
					e.printStackTrace();
				}
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}

				in.close();
				out.close();
			}

			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}finally{
			/* 恢复原提交状态 */
			conn.setAutoCommit(defaultCommit);
			stmt.close();
			conn.close();
			dataBase.closeConn();
		}
		
	}

	// 读取BLOB yinhonggang
	private void blobRead(String outfile, String userloginname)
			throws Exception {
		IDataBase dataBase = DataBaseFactory.createDataBaseClassFromProp();
		Statement stmt = dataBase.GetStatement();
		ResultSet rs = null;
		try {
			/* 查询BLOB对象 */
			rs = stmt
					.executeQuery("select Pic_att from userpicinfo where Pic_LoginName='"
							+ userloginname + "'");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("Pic_att");
				/* 以二进制形式输出 */
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(outfile));
				BufferedInputStream in = new BufferedInputStream(blob
						.getBinaryStream());
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}

		} catch (Exception ex) {
			throw ex;
		}finally {
			/* 恢复原提交状态 */
			Function.closeDataBaseSource(rs, stmt, dataBase);
		}
		
	}
}