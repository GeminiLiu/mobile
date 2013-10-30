
package cn.com.ultrapower.interfaces.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "CrmProcessSheetPortType", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
public interface CrmProcessSheetPortType {


    @WebMethod(operationName = "replyWorkSheet", action = "")
    @WebResult(name = "out", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
    public String replyWorkSheet(
        @WebParam(name = "in0", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in0,
        @WebParam(name = "in1", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in1,
        @WebParam(name = "in2", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in3,
        @WebParam(name = "in4", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in4,
        @WebParam(name = "in5", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in5,
        @WebParam(name = "in6", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in6,
        @WebParam(name = "in7", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in7,
        @WebParam(name = "in8", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in8,
        @WebParam(name = "in9", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in9,
        @WebParam(name = "in10", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in10,
        @WebParam(name = "in11", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in11,
        @WebParam(name = "in12", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in12,
        @WebParam(name = "in13", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in13);

    @WebMethod(operationName = "notifyWorkSheet", action = "")
    @WebResult(name = "out", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
    public String notifyWorkSheet(
        @WebParam(name = "in0", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in0,
        @WebParam(name = "in1", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in1,
        @WebParam(name = "in2", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in3,
        @WebParam(name = "in4", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in4,
        @WebParam(name = "in5", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in5,
        @WebParam(name = "in6", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in6,
        @WebParam(name = "in7", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in7,
        @WebParam(name = "in8", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in8,
        @WebParam(name = "in9", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in9,
        @WebParam(name = "in10", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in10,
        @WebParam(name = "in11", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in11,
        @WebParam(name = "in12", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in12,
        @WebParam(name = "in13", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in13);

    @WebMethod(operationName = "isAlive", action = "")
    @WebResult(name = "out", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
   public String isAlive(
        @WebParam(name = "in0", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in0,String in1);

    @WebMethod(operationName = "withdrawWorkSheet", action = "")
    @WebResult(name = "out", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
    public String withdrawWorkSheet(
        @WebParam(name = "in0", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in0,
        @WebParam(name = "in1", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in1,
        @WebParam(name = "in2", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in3,
        @WebParam(name = "in4", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in4,
        @WebParam(name = "in5", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in5,
        @WebParam(name = "in6", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in6,
        @WebParam(name = "in7", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in7,
        @WebParam(name = "in8", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in8,
        @WebParam(name = "in9", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in9,
        @WebParam(name = "in10", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in10,
        @WebParam(name = "in11", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in11,
        @WebParam(name = "in12", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in12,
        @WebParam(name = "in13", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in13);

    @WebMethod(operationName = "confirmWorkSheet", action = "")
    @WebResult(name = "out", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
    public String confirmWorkSheet(
        @WebParam(name = "in0", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in0,
        @WebParam(name = "in1", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        Integer in1,
        @WebParam(name = "in2", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in2,
        @WebParam(name = "in3", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in3,
        @WebParam(name = "in4", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in4,
        @WebParam(name = "in5", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in5,
        @WebParam(name = "in6", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in6,
        @WebParam(name = "in7", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in7,
        @WebParam(name = "in8", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in8,
        @WebParam(name = "in9", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in9,
        @WebParam(name = "in10", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in10,
        @WebParam(name = "in11", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in11,
        @WebParam(name = "in12", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in12,
        @WebParam(name = "in13", targetNamespace = "http://service.emosv3.cci.csp.huawei.com")
        String in13);

}
