package daiwei.mobile.service;

import android.content.Context;

public interface GetTreeService {
	public String getTree(Context context, String showCorp, String showCenter,
			String showStation, String showTeam, String showPerson,
			String multi, String selectObjs, String cityID, String specialtyID);
	public String getOrgTrr(Context context, String showTeam,
			String showPerson, String multi, String selectObjs, String parentID);
}
