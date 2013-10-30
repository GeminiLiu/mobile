package cn.com.ultrapower.ultrawf.control.configalarm.config;

import cn.com.ultrapower.ultrawf.control.configalarm.models.AutoQualityConfigHandler;
import cn.com.ultrapower.ultrawf.control.configalarm.models.AutoQualityConfigModel;


public class AutoQualityConfigManager {
	
	public void insertAutoQualityConfig(AutoQualityConfigModel configModel){
		AutoQualityConfigHandler handler=new AutoQualityConfigHandler();
		handler.insertAutoQualityConfig(configModel);	
	}
	
	public void updateAutoQualityConfig(AutoQualityConfigModel configModel){
		AutoQualityConfigHandler handler=new AutoQualityConfigHandler();
		handler.updateAutoQualityConfig(configModel);
	}
	
	public void deleteAutoQualityConfig(String ids){
		AutoQualityConfigHandler handler=new AutoQualityConfigHandler();
		handler.deleteAutoQualityConfig(ids);
	}

	public AutoQualityConfigModel getAutoQualityConfig(String id){
		AutoQualityConfigHandler handler=new AutoQualityConfigHandler();;
		return handler.getAutoQualityConfig(id);
	}	
}
