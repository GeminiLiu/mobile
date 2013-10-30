package com.ultrapower.mobile.service;

import java.util.List;

import com.ultrapower.mobile.model.WfType;
import com.ultrapower.mobile.model.WorkSheet;
import com.ultrapower.mobile.model.xml.ActionModel;
import com.ultrapower.mobile.model.xml.FieldInfo;

public interface SheetQueryService {

	/**
	 * 查询待办列表
	 * @param userName 登录名
	 * @param itSysName 业务系统标识
	 * @param baseSchema 工单标识
	 * @return 待办列表
	 */
	public List<WorkSheet> queryWaitDeal(String userName, String itSysName, String baseSchema);
	
	/**
	 * 查询已办列表
	 * @param userName 登录名
	 * @param itSysName 业务系统标识
	 * @param baseSchema 工单标识
	 * @return 已办列表
	 */
	public List<WorkSheet> dealedSheet(String userName, String itSysName, String baseSchema);
	
	/**
	 * 获取工单所有字段信息
	 * @param itSysName 业务系统标识
	 * @param baseId 工单id
	 * @param baseSchema 工单标识
	 * @param tplId 版本号
	 * @return 工单字段信息列表
	 */
	public List<FieldInfo> workSheetInfo(String itSysName, String baseId, String baseSchema, String tplId);
	
	/**
	 * 获取工单处理信息列表
	 * @param itSysName 业务系统标识
	 * @param baseId 工单id
	 * @param baseSchema 工单标识
	 * @param tplId 版本号
	 * @return 处理信息列表
	 */
	public List<FieldInfo> dealInfo(String itSysName, String baseId, String baseSchema, String tplId);
	
	/**
	 * 获取当前动作的可编辑字段信息
	 * @param itSysName 业务系统标识
	 * @param baseId 工单id
	 * @param baseSchema 工单标识
	 * @param tplId 版本号
	 * @param taskId 任务id
	 * @param actionStr 动作字符串
	 * @return 可编辑字段信息
	 */
	public String getEditFieldsByAction(String itSysName, String baseId, String baseSchema, String tplId, String taskId, String actionStr);

	/**
	 * 获取当前的可执行动作列表
	 * @param itSysName 业务系统标识
	 * @param baseId 工单id
	 * @param baseSchema 工单标识
	 * @param tplId 版本号
	 * @param processId 环节id
	 * @param baseStatus 工单状态
	 * @return 动作列表
	 */
	public List<ActionModel> getAvailableActions(String itSysName, String baseId, String baseSchema, String tplId, String processId, String baseStatus);
	
	/**
	 * 表单提交
	 * @param itSysName 业务系统标识
	 * @param baseId 工单id
	 * @param baseSchema 工单标识
	 * @param userName 登录名
	 * @param actionStr 执行动作字符串
	 * @param dealActorStr 处理人字符串
	 * @param bizFields 处理的业务字段
	 * @return 成功 或 失败
	 */
	public String saveSheet(String itSysName, String baseId, String baseSchema, String userName, String actionStr, String dealActorStr, List<FieldInfo> bizFields);

	/**
	 * 分页查询待办工单
	 * @param userName 登录名
	 * @param itSysName 业务系统标识
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param baseSchema 工单标识
	 * @return 
	 */
	public String queryWaitDeal(String userName, String itSysName, int page, int pageSize, String baseSchema);
	
	/**
	 * 获取设置接入手机系统的工单类型列表
	 * @param itSysName 业务系统标识
	 * @return 接入手机系统的工单类型列表
	 */
	public List<WfType> getAllWfTypes(String itSysName);
}
