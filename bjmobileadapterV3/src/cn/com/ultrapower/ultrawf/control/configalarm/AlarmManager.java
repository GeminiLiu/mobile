package cn.com.ultrapower.ultrawf.control.configalarm;


import cn.com.ultrapower.ultrawf.control.configalarm.models.ConfigAlarmModel;
import cn.com.ultrapower.ultrawf.control.configalarm.models.SaveConfigAlarmHandler;

public class AlarmManager {
	
	public void saveConfigAlarmForm(ConfigAlarmModel ConfigAlarm)
	{
		SaveConfigAlarmHandler hander=new SaveConfigAlarmHandler();
		hander.SaveConfigAlarm(ConfigAlarm);
		
	}

	public int countNum(String sql){
		SaveConfigAlarmHandler hander=new SaveConfigAlarmHandler();
		return hander.countBySql(sql);
	}

	
	
	public void UpdateConfigAlarm(ConfigAlarmModel ConfigAlarm){
		SaveConfigAlarmHandler hander=new SaveConfigAlarmHandler();
		hander.UpdateConfigAlarm(ConfigAlarm);
	}
	
	public void DeleteConfigAlarm(String cfgid){
		SaveConfigAlarmHandler hander=new SaveConfigAlarmHandler();
		hander.DeleteConfigAlarm(cfgid);
	}

	
	public ConfigAlarmModel getConfigAlarmMode(String id){
		SaveConfigAlarmHandler hander=new SaveConfigAlarmHandler();
		return hander.getConfigAlarmModel(id);
	}

	
	
}
