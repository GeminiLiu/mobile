package com.ultrapower.eoms.common.core.component.sms.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderForm;

/**
 * 短信订阅--工单管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午09:44:07
 */
public interface SmOrderFormService {

	/**
	 * 添加工单短信订阅信息
	 * @param smsOrderForm 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean addOrderInfo(SmsOrderForm smsOrderForm);
	
	/**
	 * 添加工单短信订阅信息(先删除,再添加)
	 * @param smsOrderFormList 订阅集合信息
	 */
	public void addOrderInfo(String loginname,String formschema,List<SmsOrderForm> smsOrderFormList);
	
	/**
	 * 获取工单短信订阅集合信息
	 * @param loginname 当前用户登录名
	 * @param formSchema 工单类别
	 * @return 工单短信订阅集合
	 */
	public List<SmsOrderForm> get(String loginname,String formSchema);
	
	/**
	 * 删除工单短信订阅信息
	 * @param id 唯一标识
	 * @return 返回 true 或 false
	 */
	public boolean deleteOrderInfo(String id);
	
	/**
	 * 删除工单短信订阅信息
	 * @param loginname 当前用户登录名
	 * @param formSchema 工单类别
	 * @return 返回 true 或 false
	 */
	public boolean deleteOrderInfo(String loginname,String formSchema);
	
	/**
	 * 根据给定的规则ID和模板标识取得该动作的规则展示列表（包括规则的属性、已经规则属性所对应模板属性输入数据源、输入类型、输入数据值）
	 * @param ruleId 规则ID
	 * @param modelMark 模板标识
	 * @return 规则展示列表
	 */
	public List<SlaRulePropertyShow> getSlaRuleProShowList(String ruleId, String modelMark);
	
	/**
	 * 保存故障工单短信预订的时候需要保存规则属性信息，这些信息存储在SLA规则属性信息表中，记录的规则ID为故障工单的字典值
	 * @param srplst --规则属性集合
	 * @param formSchema --工单类别字典值
	 * @return 返回 true 或 false
	 */
	public boolean saveRulePropertyInfo(List<SlaRuleProperty> srplst, String formSchema);
	
	/**
	 * 根据工单类别删除SLA规则属性信息表中对于该工单类别的规则属性信息
	 * @param formSchema
	 * @return 返回 true 或 false
	 */
	public boolean deleteRulePropertyInfo(String formSchema);
}
