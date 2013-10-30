package cn.com.ultrapower.eoms.util;
/**
 * 横向菜单的静态变量,用接口则这些变量直接
 * 是静态的和不可以修改的.
 * @author LY
 * 2006-12-15
 */
public interface MenuModule{
	
	String WORK_FORM  = "workflow";//工单
	String DUTY_MANAGE = "dutymanage";//值班管理
	String PLAN_MANAGE = "PLANMANAGE";//作业计划
	String MY_DESKTOP = "MYDESKTOP";//个人工作平台
	String KNOWLEDGE_LIB = "experience";//经验库
	String INFO_LIB = "ziliao";//资料管理
	String FORUM = "FORUM";//论坛
	String BULLETIN = "UltraProcess:App_Note";//公告
	String SYSTEM_MANAGE = "systemmanage";//系统管理
	String PLATFORM_FOR_MYSELF = "platformformyself";//个人工作平台
	String CHANGYON_GGONGDANQICAO="ChangYongGongDanQiCao";//常用工单起草
	String KUAISU_DAOHANG = "KuiSuDaoHang";//快速导航
	String REPORT = "report";//报表
	String N_S = "ns";//网络资源
	String XIEGUAN = "xieguan";//人人争当网络监督员
	String WANGLUOXINXIFABU = "release";
	String DAIWEIGUANLI = "daiwei";//代维管理
	String SHUJUSHANGBAO="dateupdate";//数据上报
	String KPI_INDEX = "KPI";//KPI报表展示
	String OTHER_INDEX = "other";//人员信息管理 培训管理
	String WORKTASK_INDEX = "worktaskmanager"; //工作任务管理
	String WORK_FORM_MANAGER="workflowmanager";//工单管理
}
