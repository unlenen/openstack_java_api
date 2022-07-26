package unlenen.cloud.openstack.be.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
@Component
public class CommonService {

    Logger logger= LoggerFactory.getLogger(CommonService.class);

    @Autowired
    HttpService httpService;

    @Autowired
    OpenStackConfig config;

    @Autowired
    ObjectMapper objectMapper;

    protected String getServiceURL(String token, OpenStackModule openStackModule) throws Exception {
        return config.getServiceURL(openStackModule);
    }

    protected ResponseEntity call() throws Exception {
        return call("", new Parameter[0], new Parameter[0]);
    }

    protected OpenStackResult callWithResult(String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws Exception {
        return callWithResult(baseURL, extraHeaders, parameters, null);
    }

    protected OpenStackResult callWithResult(String baseURL, Parameter[] extraHeaders, Parameter[] parameters,String reqBody) throws Exception {
        Call call = getCall(reqBody ==null ?4:3);
        ResponseEntity responseEntity = callOpenStack(call, baseURL, extraHeaders, parameters,reqBody);
        String body= responseEntity.getBody().toString();
        if(logger.isDebugEnabled()){
            logger.debug("[Response] URL:"+ baseURL+" , headers:"+extraHeaders+" , parameters: "+ parameters+" , response:"+body);
        }
        return (OpenStackResult) objectMapper.readValue(body, call.openstackResult());
    }

    protected ResponseEntity call(String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws Exception {
        Call call = getCall(3);
        return callOpenStack(call, baseURL, extraHeaders, parameters,null);
    }

    private ResponseEntity callOpenStack(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters,String reqBody) throws IOException, UnvalidCallException {
        ResponseEntity responseEntity = httpService.call(call, baseURL, extraHeaders, parameters,reqBody);
        if (responseEntity.getStatusCode() != call.statusCode()) {
            throw new UnvalidCallException(responseEntity.getStatusCode(), call.statusCode(), responseEntity.getBody() + "");
        }
        return responseEntity;
    }

    protected String getHeader(ResponseEntity responseEntity, String key) {
        List<String> result = responseEntity.getHeaders().get(key);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    private Call getCall(int pos) throws ClassNotFoundException, SecurityException {
        String methodName = Thread.currentThread().getStackTrace()[pos].getMethodName();
        Class serviceClass = Class.forName(Thread.currentThread().getStackTrace()[pos].getClassName());
        Method method = Arrays.stream(serviceClass.getDeclaredMethods()).filter(t -> t.getName().equals(methodName)).findFirst().get();
        return method.getAnnotation(Call.class);
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
