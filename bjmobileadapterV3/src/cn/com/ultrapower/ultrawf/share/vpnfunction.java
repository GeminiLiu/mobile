package cn.com.ultrapower.ultrawf.share;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class vpnfunction {

    public static String getSelectItem(String m_str_select,String splitChar,Row row,String m_ColName) {
		String m_str_selectValue = "";
		StringBuffer m_str_outSting1 = new StringBuffer("");
		StringBuffer m_str_outSting2 = new StringBuffer("");
		String m_flag		= "0";
		if (row != null){m_str_selectValue = row.getString(m_ColName);} else {m_str_selectValue = "";};
		String[] m_arr_selectValue = m_str_select.split(splitChar);
		for (int i=0;i<m_arr_selectValue.length;i++)
		{
			String m_one_selectValue = m_arr_selectValue[i];
			if (m_one_selectValue.equals(m_str_selectValue))
			{
				m_flag = "1";
				m_str_outSting2.append("<option value=\""+m_one_selectValue+"\" selected=\"selected\">"+m_one_selectValue+"</option>");
			}
			else
			{
				m_str_outSting2.append("<option value=\""+m_one_selectValue+"\">"+m_one_selectValue+"</option>");
			}
		}
		if (m_flag.equals("0"))
		{
			m_str_outSting1.append("<option value=\""+"请选择"+"\" selected=\"selected\">"+"请选择"+"</option>");
		}
		else
		{
			m_str_outSting1.append("<option value=\""+"请选择"+"\">"+"请选择"+"</option>");
		}
		return m_str_outSting1.toString() + m_str_outSting2.toString();
	}	
}
