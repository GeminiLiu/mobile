package cn.com.ultrapower.eoms.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 导出excel
 * @author lijupeng
 *
 */
public class ExportExcel {

	/**
	 * 导出excel
	 * @param os
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean execute(OutputStream os, Title title, Content content) {

		try {
			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
			WritableSheet wsheet = wbook.createSheet("第一页", 0); // sheet名称
			WritableFont wfont;

			// 设置excel标题
			wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD,
					false, UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			try {
				if (title != null && title.getRow() != null
						&& !title.getRow().isEmpty()) {

					for (int i = 0; i < title.getRow().size(); i++) {
						Cell cell = (Cell) title.getRow().get(i);
						wsheet.addCell(new Label(cell.getXPos(),
								cell.getYPos(), cell.getCaption(), wcfFC));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			wfont = new WritableFont(WritableFont.ARIAL, 16,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
			wcfFC = new WritableCellFormat(wfont);
			// 设置主体内容      
			if (content != null && content.getContent() != null
					&& !content.getContent().isEmpty()) {
				for (int i = 0; i < content.getContent().size(); i++) {
					Row row = (Row) content.getContent().get(i);
					try {
						if (row != null && row.getRow() != null
								&& !row.getRow().isEmpty()) {

							for (int j = 0; j < row.getRow().size(); j++) {
								Cell cell = (Cell) row.getRow().get(j);
								wsheet.addCell(new Label(cell.getXPos(), cell
										.getYPos(), cell.getCaption(), wcfFC));
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			wbook.write(); // 写入文件
			wbook.close();
		} catch (Exception e) {
		}
		return true;
	}
	
	public boolean executeBySheet(OutputStream os, List title, List content,List sheetList) {
		
		try {
			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
			
			List list = title.size()>= content.size()?content:title;
						
			for(int i=0;i<list.size();i++){
				
				WritableSheet wsheet = wbook.createSheet((String)sheetList.get(i), i); // sheet名称
				
				execute(wsheet,(Title)(title.get(i)),(Content)(content.get(i)));
			
			}
			wbook.write(); // 写入文件
			try {
				wbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
		
	}
	
	
	public void execute(WritableSheet wsheet,Title title, Content content) {

		try {
			
			WritableFont wfont;

			// 设置excel标题
			wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD,
					false, UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			try {
				if (title != null && title.getRow() != null
						&& !title.getRow().isEmpty()) {

					for (int i = 0; i < title.getRow().size(); i++) {
						Cell cell = (Cell) title.getRow().get(i);
						wsheet.addCell(new Label(cell.getXPos(),
								cell.getYPos(), cell.getCaption(), wcfFC));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			wfont = new WritableFont(WritableFont.ARIAL, 16,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
			wcfFC = new WritableCellFormat(wfont);
			// 设置主体内容      
			if (content != null && content.getContent() != null
					&& !content.getContent().isEmpty()) {
				for (int i = 0; i < content.getContent().size(); i++) {
					Row row = (Row) content.getContent().get(i);
					try {
						if (row != null && row.getRow() != null
								&& !row.getRow().isEmpty()) {

							for (int j = 0; j < row.getRow().size(); j++) {
								Cell cell = (Cell) row.getRow().get(j);
								wsheet.addCell(new Label(cell.getXPos(), cell
										.getYPos(), cell.getCaption(), wcfFC));
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
		}
	}

}
