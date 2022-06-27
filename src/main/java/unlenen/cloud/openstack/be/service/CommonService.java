package unlenen.cloud.openstack.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
        Call call = getCall();
        ResponseEntity responseEntity = callOpenStack(call, baseURL, extraHeaders, parameters);
        return (OpenStackResult) objectMapper.readValue(responseEntity.getBody().toString(), call.openstackResult());
    }

    protected ResponseEntity call(String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws Exception {
        Call call = getCall();
        return callOpenStack(call, baseURL, extraHeaders, parameters);
    }

    private ResponseEntity callOpenStack(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws IOException, UnvalidCallException {
        ResponseEntity responseEntity = httpService.call(call, baseURL, extraHeaders, parameters);
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

    private Call getCall() throws ClassNotFoundException, SecurityException {
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        Class serviceClass = Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
        Method method = Arrays.stream(serviceClass.getDeclaredMethods()).filter(t -> t.getName().equals(methodName)).findFirst().get();
        return method.getAnnotation(Call.class);
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
