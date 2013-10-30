package cn.com.ultrapower.ultrawf.share;

public class Guid {

	private long seed; //Make seed! 
	private final static long multiplier = 0x5DEECE66DL; 
	private final static long adder = 0xBL; 
	private final static long mask = (1L<<48)-1; 

	public Guid() { 
		this.seed = System.currentTimeMillis(); 
	} 

	public Guid(long seed) { 
		if (seed == 0) 
			this.seed = System.currentTimeMillis(); 
		else 
			this.seed = System.currentTimeMillis() + seed; 
	} 

	//	Make a number between 1 to (n-1) 
	public int random(int n) { 
		if (n <= 0) 
		throw new IllegalArgumentException("n must be positive"); 
		seed = (seed * multiplier + adder) & mask; 
		return ((int)(seed >>> 17) % n); 
	} 
	
	/**
	 * 描述：的到GUID
	 */
	public static String Get_GUID(String p_Guid_Top,int p_intSeed) {
		Thread Thread_currentThread = Thread.currentThread();
		Guid m_obj_Guid = new Guid(p_intSeed);
		Integer m_Intege_random 		= new Integer(m_obj_Guid.random(100000));
		Long 	m_Intege_currentTime 	= new Long(System.currentTimeMillis());
		String m_str_Guid_tmp_Pro_BaseID	= p_Guid_Top + "-" + m_Intege_currentTime.toString() + "-" + Thread_currentThread.getName() + "-" + m_Intege_random.toString();
		return m_str_Guid_tmp_Pro_BaseID;
	}
}
