package cn.com.ultrapower.eoms.user.kpi;

public class GsmInfoBean
{
	String tottraffic      = "";       //忙时话音信道总话务量
	String droptchnbr      = "";        //忙时话音信道掉话总次数（含切换
	String trafficdroprate = "";   //话务掉话比
	String attsdcchnbr     = "";       //SDCCH试呼总次数
	String ovrsdcchnbr     = "";       //SDCCH溢出总次数
	String ovrtchnhonbr    = "";      //话音溢出总次数（不含切换）
	String atttchnhonbr    = "";      //话音信道试呼总次数（不含切换）
	String radiosucrate    = "";      //无线接通率
	String kpilidu         = "";           //kpi的时间粒度0：日上报 1：月上报 2;时上报
	String time            = "";
	/**
	 * @return Returns the attsdcchnbr.
	 */
	public String getAttsdcchnbr()
	{
		return attsdcchnbr;
	}
	/**
	 * @param attsdcchnbr The attsdcchnbr to set.
	 */
	public void setAttsdcchnbr(String attsdcchnbr)
	{
		this.attsdcchnbr = attsdcchnbr;
	}
	/**
	 * @return Returns the atttchnhonbr.
	 */
	public String getAtttchnhonbr()
	{
		return atttchnhonbr;
	}
	/**
	 * @param atttchnhonbr The atttchnhonbr to set.
	 */
	public void setAtttchnhonbr(String atttchnhonbr)
	{
		this.atttchnhonbr = atttchnhonbr;
	}
	/**
	 * @return Returns the droptchnbr.
	 */
	public String getDroptchnbr()
	{
		return droptchnbr;
	}
	/**
	 * @param droptchnbr The droptchnbr to set.
	 */
	public void setDroptchnbr(String droptchnbr)
	{
		this.droptchnbr = droptchnbr;
	}
	/**
	 * @return Returns the kpilidu.
	 */
	public String getKpilidu()
	{
		return kpilidu;
	}
	/**
	 * @param kpilidu The kpilidu to set.
	 */
	public void setKpilidu(String kpilidu)
	{
		this.kpilidu = kpilidu;
	}
	/**
	 * @return Returns the ovrsdcchnbr.
	 */
	public String getOvrsdcchnbr()
	{
		return ovrsdcchnbr;
	}
	/**
	 * @param ovrsdcchnbr The ovrsdcchnbr to set.
	 */
	public void setOvrsdcchnbr(String ovrsdcchnbr)
	{
		this.ovrsdcchnbr = ovrsdcchnbr;
	}
	/**
	 * @return Returns the ovrtchnhonbr.
	 */
	public String getOvrtchnhonbr()
	{
		return ovrtchnhonbr;
	}
	/**
	 * @param ovrtchnhonbr The ovrtchnhonbr to set.
	 */
	public void setOvrtchnhonbr(String ovrtchnhonbr)
	{
		this.ovrtchnhonbr = ovrtchnhonbr;
	}
	/**
	 * @return Returns the radiosucrate.
	 */
	public String getRadiosucrate()
	{
		return radiosucrate;
	}
	/**
	 * @param radiosucrate The radiosucrate to set.
	 */
	public void setRadiosucrate(String radiosucrate)
	{
		this.radiosucrate = radiosucrate;
	}
	/**
	 * @return Returns the time.
	 */
	public String getTime()
	{
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(String time)
	{
		this.time = time;
	}
	/**
	 * @return Returns the tottraffic.
	 */
	public String getTottraffic()
	{
		return tottraffic;
	}
	/**
	 * @param tottraffic The tottraffic to set.
	 */
	public void setTottraffic(String tottraffic)
	{
		this.tottraffic = tottraffic;
	}
	/**
	 * @return Returns the trafficdroprate.
	 */
	public String getTrafficdroprate()
	{
		return trafficdroprate;
	}
	/**
	 * @param trafficdroprate The trafficdroprate to set.
	 */
	public void setTrafficdroprate(String trafficdroprate)
	{
		this.trafficdroprate = trafficdroprate;
	}
}
