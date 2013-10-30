package cn.com.ultrapower.interfaces.server;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
public class Tests {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 指出service所在URL     
		   
        String endpoint = "http://10.223.3.116:8080/ultrawf/services/EOMSProcessSheet";    
   
        // 创建一个服务(service)调用(call)     
   
        Service service = new Service();    
   
        Call call = (Call) service.createCall();// 通过service创建call对象     
   
        // 设置service所在URL     
   
        call.setTargetEndpointAddress(new java.net.URL(endpoint));    
   
        // 方法名(processService)与MyService.java方法名保持一致     
   
        call.setOperationName("checkinWorkSheet");
        //call.setProperty(Call.SOAPACTION_URI_PROPERTY,"http://service.eoms.chinamobile.com/Bulletin/newBulletin");   
   
        // Object 数组封装了参数，参数为"This is Test!",调用processService(String arg)    
        
        /*String ss = "<opDetail><recordInfo>" +
				"<fieldInfo><fieldChName>EOMS用户名称</fieldChName><fieldEnName>userName</fieldEnName><fieldContent>Demo</fieldContent></fieldInfo>" +
				"<fieldInfo><fieldChName>EOMS用户密码</fieldChName><fieldEnName>userPassword</fieldEnName><fieldContent>!@#</fieldContent></fieldInfo>" +
				"</recordInfo></opDetail>";*/
        String ss = "<opDetail><recordInfo><fieldInfo><fieldChName>主题</fieldChName><fieldEnName>title</fieldEnName><fieldContent>按时打发士大夫</fieldContent></fieldInfo><fieldInfo><fieldChName>受理时限</fieldChName><fieldEnName>dealTime1</fieldEnName><fieldContent>2008-11-20 20:41:00</fieldContent></fieldInfo><fieldInfo><fieldChName>处理时限</fieldChName><fieldEnName>dealTime2</fieldEnName><fieldContent>2008-11-20 20:41:00</fieldContent></fieldInfo><fieldInfo><fieldChName>紧急程度</fieldChName><fieldEnName>urgentDegree</fieldEnName><fieldContent>003</fieldContent></fieldInfo><fieldInfo><fieldChName>派发联系人</fieldChName><fieldEnName>btype1</fieldEnName><fieldContent>郝璐珊</fieldContent></fieldInfo><fieldInfo><fieldChName>派发联系人电话</fieldChName><fieldEnName>bdeptContact</fieldEnName><fieldContent>15909518085</fieldContent></fieldInfo><fieldInfo><fieldChName>是否大面积投诉</fieldChName><fieldEnName>bdeptContactPhone</fieldEnName><fieldContent>0</fieldContent></fieldInfo><fieldInfo><fieldChName>重复投诉次数</fieldChName><fieldEnName>repeatComplaintTimes</fieldEnName><fieldContent>0</fieldContent></fieldInfo><fieldInfo><fieldChName>投诉分类</fieldChName><fieldEnName>complaintType</fieldEnName><fieldContent>999</fieldContent></fieldInfo><fieldInfo><fieldChName>用户姓名</fieldChName><fieldEnName>customerName</fieldEnName><fieldContent>测试</fieldContent></fieldInfo><fieldInfo><fieldChName>联系电话</fieldChName><fieldEnName>customPhone</fieldEnName><fieldContent>15800001111</fieldContent></fieldInfo><fieldInfo><fieldChName>投诉电话</fieldChName><fieldEnName>ApplyPhone</fieldEnName><fieldContent>15800001111</fieldContent></fieldInfo><fieldInfo><fieldChName>投诉来源</fieldChName><fieldEnName>ComplainResource</fieldEnName><fieldContent>001</fieldContent></fieldInfo><fieldInfo><fieldChName>客服人员工号</fieldChName><fieldEnName>ComplaiinNO</fieldEnName><fieldContent>NXKF0034</fieldContent></fieldInfo><fieldInfo><fieldChName>用户终端类型</fieldChName><fieldEnName>CustomType</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>用户类型</fieldChName><fieldEnName>customType</fieldEnName><fieldContent>002</fieldContent></fieldInfo><fieldInfo><fieldChName>用户品牌</fieldChName><fieldEnName>customBrand</fieldEnName><fieldContent>002</fieldContent></fieldInfo><fieldInfo><fieldChName>用户级别</fieldChName><fieldEnName>customLevel</fieldEnName><fieldContent>001</fieldContent></fieldInfo><fieldInfo><fieldChName>用户归属地</fieldChName><fieldEnName>customAttribution</fieldEnName><fieldContent>广东广州</fieldContent></fieldInfo><fieldInfo><fieldChName>投诉时间</fieldChName><fieldEnName>complaintTime</fieldEnName><fieldContent>2008-11-19 20:44:10</fieldContent></fieldInfo><fieldInfo><fieldChName>故障时间</fieldChName><fieldEnName>faultTime</fieldEnName><fieldContent>2008-11-19 20:44:10</fieldContent></fieldInfo><fieldInfo><fieldChName>受理地市</fieldChName><fieldEnName>startDealCity</fieldEnName><fieldContent>001</fieldContent></fieldInfo><fieldInfo><fieldChName>投诉内容</fieldChName><fieldEnName>complaintDesc</fieldEnName><fieldContent>0811190000001319 测试 0811190000001319</fieldContent></fieldInfo><fieldInfo><fieldChName>故障现象</fieldChName><fieldEnName>faultDesc</fieldEnName><fieldContent>测试 工单</fieldContent></fieldInfo><fieldInfo><fieldChName>故障行政区</fieldChName><fieldEnName>faultArea</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>故障路段名</fieldChName><fieldEnName>faultRoad</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>故障门牌号</fieldChName><fieldEnName>faultNo</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>故障交叉路段名1</fieldChName><fieldEnName>faultRoad1</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>故障交叉路段名2</fieldChName><fieldEnName>faultRoad2</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>楼宇名称</fieldChName><fieldEnName>faultVill</fieldEnName><fieldContent></fieldContent></fieldInfo><fieldInfo><fieldChName>是否上门服务</fieldChName><fieldEnName>isVisit</fieldEnName><fieldContent></fieldContent></fieldInfo></recordInfo></opDetail>";
        String chech = "<opDetail><recordInfo><fieldInfo><fieldChName>归档满意度</fieldChName><fieldEnName>closeSatisfyDegree</fieldEnName><fieldContent>1</fieldContent></fieldInfo><fieldInfo><fieldChName>归档意见</fieldChName><fieldEnName>closeDesc</fieldEnName><fieldContent>请查明原因并处理，在规定时限内返单，谢谢！</fieldContent></fieldInfo></recordInfo></opDetail>";
		String ret = (String) call.invoke(new Object[] {56, 90,"20081129153045681543",
				"EOMS","CRM", "123456",
				"2008-12-01 16:32:43", "<attachRef></attachRef>", "曹玉荣", "宁夏移动区公司",
				"客服中心", "13895002006", "2008-12-01 16:32:43", chech});
   
		System.out.println("ddddddddddddddd"+ret);
		
		
		// String helloWorldURL = "http://10.223.6.228:8080/eoms/services/EOMSProcessSheet";
//       EOMSProcessSheetServiceClient client = new EOMSProcessSheetServiceClient();
//       EOMSProcessSheetServiceImpl  remoteEoms = (EOMSProcessSheetServiceImpl)client.getEOMSProcessSheet(helloWorldURL);     
//
     /*
		 Service serviceModel = new ObjectServiceFactory().create(CrmProcessSheetClient.class);    
		 try {    
			 Tests service = (Tests) new XFireProxyFactory().create(    
		             serviceModel,    
		             "http://10.223.20.242:8080/csp/services/cci/crmService");
		     String sss = service.getBook();//串代表书    
		     System.out.println(">>>" + sss);    
		 } catch (Exception e) {    
		     e.printStackTrace();    
		 }    */
	
		
		
		
		
		
		/*CrmProcessSheetPortType remoteEoms = null;
		 String helloWorldURL = "http://10.223.20.242:8080/csp/services/cci/crmService";
	      Service srvcModel = new ObjectServiceFactory().create(CrmProcessSheetPortType.class);
	      XFireProxyFactory factory =new XFireProxyFactory(XFireFactory.newInstance().getXFire());
	      try
	    {
	        remoteEoms = (CrmProcessSheetPortType)factory.create(srvcModel, helloWorldURL);
	    }
	    catch (Exception e)
	    {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }*/
	}
}
