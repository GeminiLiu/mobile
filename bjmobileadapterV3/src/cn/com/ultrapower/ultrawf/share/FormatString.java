package cn.com.ultrapower.ultrawf.share;

import java.io.UnsupportedEncodingException;

public class FormatString {

	/**
	 * 检查空字符串
	 * @param p_String
	 * @return
	 */
	public static String CheckNullString(String p_String)
	{
		if(p_String==null)
			return "";
		else
			return p_String;
	}
	
	public static String CheckNullString(Object p_obj)
	{
		if(p_obj==null)
			return "";
		else
			return p_obj.toString();
	}	
	
	public static String FormatObjectToString(Object p_obj)
	{
		if(p_obj==null)
			return "";
		else
			return p_obj.toString();
	}	
	
	
	public static boolean compareEqualsString(String str1,String str2)
	{
		str1=CheckNullString(str1).toLowerCase();
		str2=CheckNullString(str2).toLowerCase();
		if(str1.equals(str2))
			return true;
		else
			return false;
		
	}	
	
	
	public static String toChineseiso(String str)
	{
		
		if (str == null || str.length() < 1) {
			str = "";
		}
		else 
		{
			try 
			{
				
				str = new String(str.getBytes("iso-8859-1"),"UTF8");
			}
			catch (UnsupportedEncodingException e)
			{
				return str;
			}
		}
		return str;
	}
	
    /**
     * 将字符串截取为一定长度的字符串，对于大于该字符串长度的部分截掉，后边添加'...'
     * 
     * @param string
     *            待截取的字符串
     * @param maxLength
     *            假设该title全部为中文字符的最大长度
     * @return
     */
    public static String trunk(String string, int maxLength) {
        try {
			char[] ca = string.toCharArray();
			int iMaxlength = maxLength * 2;
			int iTotalLength = 0;
			int i = 0;
			int j = 0;
			for (; i < ca.length; i++) {
				if (ca[i] > 128)
					iTotalLength += 2;
				else
					iTotalLength += 1;

				if ((j == 0) && (iTotalLength >= iMaxlength - 4))
					j = i;

				if (iTotalLength > iMaxlength)
					break;
			}

			if (iTotalLength <= iMaxlength) //未超出最大长度。
				return string;

			StringBuffer buf = new StringBuffer(string);
			buf.setLength(j + 1);
			buf.append("...");

			return buf.toString();
			
		} catch (Exception e) {
			return "";
		}
    }	

	
}
