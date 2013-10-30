package daiwei.mobile.util.TempletUtil.TMPModel;


import java.util.HashMap;
import java.util.Map;

import daiwei.mobile.util.TempletUtil.TMPModelXJ.RecordInfo;


public class TempletModel{
	public static Map<String,Templet> TempletMap = new  HashMap<String,Templet>();
	public static Map<String,HashMap<String,RecordInfo>> TempletMapXJ = new  HashMap<String,HashMap<String,RecordInfo>>();
	public static Map<String,Caetgory> CaetgoryByCode = new  HashMap<String,Caetgory>();
	public static Map<String,Caetgory> CaetgoryById = new  HashMap<String,Caetgory>();
	
	public static boolean isWEB = true;
}
