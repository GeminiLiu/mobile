package cn.com.ultrapower.eoms.util;


public class GetFirstDay {

	/**该类功能是得到下一个月的第一天
	 * author:zhaoqi
	 * @param year,month
	 */
	
	
	public static String getNextMonthFirstDay(String year,String month){
		String nextmonthday;
		String newyearstr;
		String newmonthstr;
		long newmonth=0;
		long newyear=0;
		try{					
		long lyear = new Long(year).longValue();
		long lmonth = new Long(month).longValue();
	      if(lmonth==12){
	         newmonth  = 1;
	         newyear  = lyear + 1;
	      }else{
	    	  newmonth = lmonth + 1;
	    	  newyear = lyear;
	      }	      	      
		}catch(Exception e){
			e.printStackTrace();
		}	
		newyearstr  =  new Long(newyear).toString();
		newmonthstr =  new Long(newmonth).toString();
		if(newmonthstr.length()==1){
			newmonthstr = "0"+newmonthstr;
		}
		nextmonthday = newyearstr+"-"+newmonthstr+"-"+"01";
		return nextmonthday;
	}
	
	public static String getThisMonthFirstDay(String year,String month){
		String thismonthday;	
		try{
			if(month.length()==1){
				month = "0"+month;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		thismonthday = year+"-"+month+"-"+"01";
		return thismonthday;
	}
	
	public static int getDateOfString(String date){		
		return new Long(date.substring(8, 10)).intValue();
	}
	public static int getMonthOfString(String date){		
		return new Long(date.substring(5, 7)).intValue();
	}
	public static int getYearOfString(String date){		
		return new Long(date.substring(0, 4)).intValue();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("2006-07-13".substring(8,10));
		System.out.println(getNextMonthFirstDay("2004","1").substring(8,10));
		

	}

}
