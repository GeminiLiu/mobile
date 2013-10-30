package cn.com.ultrapower.eoms.util.excel;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImportExcel {
	
	private static DiskFileItemFactory factory;
  
	private static ServletFileUpload upload;
	
	private static java.text.DecimalFormat formatter = new java.text.DecimalFormat("########");

	static {
		factory = new DiskFileItemFactory();
		// 附件大于该尺寸时就要使用硬盘缓存:10M
		factory.setSizeThreshold(10485760);
		// 临时保存的位置,默认使用tomcat的临时目录,如:d:\tomcat5\temp
		upload = new ServletFileUpload(factory);
	}

	/**
	 * 用poi方式执行导入操作
	 * @param ins
	 * @return
	 */
	public static Content execute(InputStream ins, HttpServletResponse response) {
		Content content = new Content();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(ins);
			// 获得Excel的Sheet页
			// 在Excel文档中，第一工作表的缺省索引是0

			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = 0;
			int colCount = 0;
			HSSFRow hssfRow;// poi中定义的excel行
			
//			 用于格式化double数值型，防止出现科学计数法数字						

			if (sheet.getPhysicalNumberOfRows() > 0) {
				rowCount = sheet.getPhysicalNumberOfRows();
				if (sheet.getRow(0).getPhysicalNumberOfCells() > 0) {
					colCount = sheet.getRow(0).getPhysicalNumberOfCells();
				}
			}
			
			if (rowCount > 0 && colCount > 0) {
				for (int i = 0; i < rowCount; i++) {
					Row row = new Row();// 用于导入内容对象的行
					hssfRow = sheet.getRow(i);
					if (hssfRow != null) {
						for (int j = 0; j < colCount; j++) {
												
							if(hssfRow.getCell((short) j) == null )
							{
								row.addCell("");
							}
							else if(hssfRow.getCell((short) j).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)//当excel中存储的为常规数字时
							{
								row.addCell(formatter.format(hssfRow.getCell((short) j).getNumericCellValue()));
							}
							else
							{
								row.addCell(hssfRow.getCell((short) j).getStringCellValue());
							}							
						}
					}
					content.addRow(row);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return content;
	}
	/**
	 * 用jxl方式执行导入操作
	 * @param ins
	 * @return
	 */
	public static Content executeJxl(InputStream ins,HttpServletResponse response){
		Content content = new Content();
		 try {
			if (ins != null) {
				Workbook workbook = null;

				workbook = Workbook.getWorkbook(ins);
				Sheet sheet = workbook.getSheet(0);
				if (sheet != null && sheet.getRows() > 0) {
					for (int i = 0; i < sheet.getRows(); i++) {
						jxl.Cell[] cellArray = (jxl.Cell[]) sheet.getRow(i);
						Row row = new Row();
						if (cellArray != null && cellArray.length > 0) {
							for (int j = 0; j < cellArray.length; j++) {
								jxl.Cell cell = cellArray[j];		
								
								System.out.println("----"+j+"----"+i+"----"+cell.getType());
															    							      
								if(cell.getType() == CellType.LABEL){
									
									LabelCell lc = (LabelCell)cell;
									row.addCell(lc.getString(),j,i);
									
								}else if(cell.getType() == CellType.NUMBER){
									
									NumberCell  nc = (NumberCell)cell;
									row.addCell(formatter.format(nc.getValue()),j,i);
									
								}else if(cell.getType() == CellType.DATE){
									
								   // TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00")); //jdk1.5的bug,要是没有这句，时间就比当前时间早8小时									
									DateCell  dc = (DateCell)cell;
									
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									
									Date date = dc.getDate();
									if(date.getHours()-8 >=0){
										date.setHours(date.getHours()-8);
									}else{
										date.setHours((date.getHours()-8)+24);
									}
									
									row.addCell(formatter.format(date),j,i);
									System.out.println("----------------"+formatter.format(dc.getDate()));
									
								}else if(cell.getType() == CellType.EMPTY){
									
									row.addCell("",j,i);
									
								}else{
									row.addCell(cell.getContents(),j,i);
								}																
							}
						}
						content.addRow(row);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return content;
	}
	/**
	 * 获得输入流
	 * @param request
	 */
	public static InputStream getInputStream(HttpServletRequest request){
		if (request == null) {
			return null;
		}
		try {
			List items = upload.parseRequest(request);
			if (items != null && !items.isEmpty()) {
				for (int i = 0; i < items.size(); i++) {
					FileItem item = (FileItem) items.get(i);
					if (!item.isFormField()) {
						return item.getInputStream();
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	/**
	 * 返回指定名字的参数
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,String name){
		if (request == null) {
			return null;
		}
		HashMap params = getParameters(request);
		if(params != null){
			return (String)params.get(name);
		}
		else{
			return null;
		}
	}
	/**
	 * 返回form中的全部参数
	 * @param request
	 * @return
	 */
	public static HashMap getParameters(HttpServletRequest request){
		HashMap parameters= new HashMap();
		if (request == null) {
			return null;
		}
		try {
			List /* FileItem */items = upload.parseRequest(request);
			if (items.size() > 0) {
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem item = (FileItem) it.next();
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						String value = item.getString();
						if (value != null) {
							try {
								value = new String(value.getBytes("8859_1"),
										"UTF8");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						parameters.put(fieldName, value);
					}

				}
			}

		} catch (Exception e) {
			return null;
		}
		return parameters;
	}
}
