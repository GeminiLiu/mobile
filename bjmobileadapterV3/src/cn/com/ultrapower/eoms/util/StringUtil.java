package cn.com.ultrapower.eoms.util;
/**
 * 对字符串进行操作
 * @author lijupeng
 *
 */
public class StringUtil {

	/**
	 * 检查字符串对象是否为null，如果为null则转换为空字符串
	 * @param string
	 * @return
	 */
	public static String checkNull(String string) {
		String value = "";
		try{
			if(string != null){
				value = string;
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return value;
	}
	
	/**
	 * 检查Object对象是否为null，如果为null则转换为空字符串
	 * @param Object
	 * @return String
	 */
	public static String checkNull(Object string) {
		String value = "";
		try{
			if(string != null){
				value = string.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 取得字符,如果为null则转换为空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static String get(String param) {
		if (param != null) {
			return param.trim();
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @param lon
	 * @return
	 */
	public static String checkNumberNull(Long lon){
		String value="";
		try{
			if(lon !=null){
				value = lon.longValue()+"";
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return value;
	}
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static String trimComma(String string){
		try{
			if(string != null && string.trim().length()>0){
				if(string.lastIndexOf(",")==string.length()-1){
				  string = string.substring(0,string.length()-1);	
				}
			}else{
				string = "";
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return string;
	}
	/**
	 * @param args
	 */
	
	public static Long javaLong(String nulong){
		Long longvalue=new Long(0);
		try{
			longvalue = new Long(Long.parseLong(nulong));
			if (longvalue==new Long(0)){
				longvalue=new Long(0);
			}
		}catch(Exception e){
			longvalue=new Long(0);
			return longvalue;
		}
		return longvalue;
	}
	
	public static Long LongNull(String nulong){
		Long longvalue=null;
		try{
			if (nulong!=null && nulong.trim().length()>0 ){
				longvalue= new Long(nulong);
			}
		}catch(Exception e){
			longvalue=null;
			return longvalue;
		}
		return longvalue;
	}
	
	public static String getEncodeString(String string,String fromCharset,String toCharset){
		String value = "";
		try {
			if (checkNull(string).trim().length() > 0) {
				value = new String(string.getBytes(fromCharset), toCharset);
			}
			
		} catch (Exception e) {
			Log.logger.error("无法按指定的字符集进行格式转换！"+e.getMessage());
		}
		return value;
	}
	
	public static String getEncodeString(String string){
		return getEncodeString(string,"ISO8859_1","UTF-8");
	}
	
	public static String getAbnormalEncodeString(String string){
		String value = "";
		if(string.trim().length()>0){
//			value = getEncodeString(string);
//			if(getEncodeString(string).endsWith("补")){
//		      value = getEncodeString(string).substring(0,getEncodeString(string).length() - 1);
//			}
//			else{
				value = getEncodeString(string);
//			}
		}
		return value;
	}
	
	public static String getStrZero(Long zeroid)
	{
		String returnStr= "";
		StringBuffer strzero=new StringBuffer();
		strzero.append("0000000000000000");
		strzero.append(zeroid);
		
		returnStr=(String) strzero.subSequence(strzero.length()-15,strzero.length());
		
	
		return returnStr;
	}
	
	
	/**
	 * 功能：将用户ID或组ID前面的0去掉。
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param zeroid
	 * @return String
	 */
	public static String dropZero(String zeroid)
	{
		String returnStr= "";
		for (int i=0;i<zeroid.length();i++)
		{
			String str = zeroid.substring(i,i+1);
			if(str.equals("0"))
			{
				returnStr = zeroid.substring(i+1);
			}
			else
			{
				break;
			}
		}
		return returnStr;
	}
	
	
	
	/**
	 * @describe 传入字符串,返回boolean之,等价于以前在网页中的
	 *   if(str != null && str.equls(""))
	 * @param string
	 * @return
	 */
	public static boolean isNULL(String string){
		return !StringUtil.checkNull(string).equals("");
	}
	
	/**
	 * @describe 将敏感的字符过滤掉,换成安全的编码
	 * 			防止sql注入,并且为了显示的时候不出现乱码
	 * @param string
	 * @return
	 */
	public static String filtrateSensitiveCharacter(String string){
		String returnString = string;
		if(StringUtil.isNULL(string)){
			returnString = string.replaceAll(" ","&nbsp;");
			returnString = returnString.replaceAll("<","&lt;");
			returnString = returnString.replaceAll(">","&gt;");
			returnString = returnString.replaceAll("'","''");
			return returnString;
		}else{
			return "";
		}
	}
	
	
	/**
	 * @describe 将敏感的字符过滤掉,换成安全的编码
	 * 			防止sql注入
	 * @param string
	 * @return
	 */
	public static String filtrateCharacter(String string){
		String returnString = string;
		if(StringUtil.isNULL(string)){
			returnString = returnString.replaceAll("<","&lt;");
			returnString = returnString.replaceAll(">","&gt;");
			returnString = returnString.replaceAll("'","''");
			return returnString;
		}else{
			return "";
		}
	}
	
	
	public static int isHasSubString(String parentString,String childString){
		if(StringUtil.isNULL(parentString) && StringUtil.isNULL(childString) ){
			return parentString.toLowerCase().indexOf(childString);
		}else{
			return -1;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.dropZero("00000044"));
	}
	
	
	/**
	 * 将字符串数组以逗号连接成字符串
	 * 
	 * @param args
	 * @return
	 */
	public static String toString(String[] args) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sbf.append(args[i]).append(",");
		}
		if (sbf.length() > 0) {
			sbf.deleteCharAt(sbf.length() - 1);
		}
		return sbf.toString();
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
			buf.append(" ...");

			return buf.toString();
			
		} catch (Exception e) {
			return "";
		}
    }
    
    public static String toFullMonth(String month){
    	String fullMonth = "";
    	try {
    		//如果参数为空，则返回空字串
			if(month==null){
				return fullMonth;
			}
			fullMonth = String.valueOf(Integer.parseInt(month)+100);
			fullMonth = fullMonth.substring(1,3);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fullMonth;
    }
}
