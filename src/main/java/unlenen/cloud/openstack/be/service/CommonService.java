package unlenen.cloud.openstack.be.service;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.result.TokenResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;

/**
 *
 * @author Nebi
 */
@Component
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    HttpService httpService;

    @Autowired
    OpenStackConfig config;

    @Autowired
    ObjectMapper objectMapper;

    protected String getServiceURL(String token, OpenStackModule openStackModule) throws Exception {
        if (openStackModule == OpenStackModule.orchestration || openStackModule == OpenStackModule.volumev3) {
            return getTokenBasedServiceUrl(token, openStackModule);
        } else {
            return config.getServiceURL(openStackModule);
        }
    }

    protected String getTokenBasedServiceUrl(String token, OpenStackModule openStackModule) throws Exception {
        return getTokenInformation(token, token).token.catalog.stream()
                .filter(c -> c.type.equals(openStackModule.name())).findFirst().get().endpoints.stream()
                .filter(e -> "public".equals(e.interfaceType)).findFirst().get().url;
    }

    protected ResponseEntity call() throws Exception {
        return call("", new Parameter[0], new Parameter[0]);
    }

    protected OpenStackResult callWithResult(String baseURL, Parameter[] extraHeaders, Parameter[] parameters)
            throws Exception {
        return callWithResult(baseURL, extraHeaders, parameters, (String) null, 4);
    }

    protected OpenStackResult callWithResult(String baseURL, Parameter[] extraHeaders, Parameter[] parameters,
            OpenStackRequest objectRequest) throws Exception {
        return callWithResult(baseURL, extraHeaders, parameters, getObjectMapper().writeValueAsString(objectRequest),
                4);
    }

    protected OpenStackResult callWithResult(String baseURL, Parameter[] extraHeaders, Parameter[] parameters,
            String reqBody, int index) throws Exception {
        Call call = getCall(index);
        ResponseEntity responseEntity = callOpenStack(call, baseURL, extraHeaders, parameters, reqBody);
        if (call.openstackResult() == OpenStackResult.class) {
            if (logger.isDebugEnabled()) {
                logger.debug("[Response][NO-RESPONSE] URL:" + baseURL + " , headers:" + Arrays.asList(extraHeaders)
                        + " , parameters: " + Arrays.asList(parameters));
            }
            return null;
        }
        String body = responseEntity.getBody().toString();
        if (logger.isDebugEnabled()) {
            logger.debug("[Response] URL:" + baseURL + " , headers:" + Arrays.asList(extraHeaders) + " , parameters: "
                    + Arrays.asList(parameters)
                    + " , response:" + body);
        }
        return (OpenStackResult) objectMapper.readValue(body, call.openstackResult());
    }

    protected ResponseEntity call(String baseURL, Parameter[] extraHeaders, Parameter[] parameters) throws Exception {
        Call call = getCall(3);
        return callOpenStack(call, baseURL, extraHeaders, parameters, null);
    }

    private ResponseEntity callOpenStack(Call call, String baseURL, Parameter[] extraHeaders, Parameter[] parameters,
            String reqBody) throws IOException, UnvalidCallException {
        ResponseEntity responseEntity = httpService.call(call, baseURL, extraHeaders, parameters, reqBody);
        if (responseEntity.getStatusCode() != call.statusCode()) {
            throw new UnvalidCallException(responseEntity.getStatusCode(), call.statusCode(),
                    responseEntity.getBody() + "");
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
        Method method = Arrays.stream(serviceClass.getDeclaredMethods()).filter(t -> t.getName().equals(methodName))
                .findFirst().get();
        return method.getAnnotation(Call.class);
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String convertYamlToJson(String yaml) throws JsonMappingException, JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }

    @Call(type = HttpMethod.GET, url = "/auth/tokens", statusCode = HttpStatus.OK, openstackResult = TokenResult.class)
    public TokenResult getTokenInformation(String authToken, String subjectToken) throws Exception {
        return (TokenResult) callWithResult(config.getIdentityURL(),
                new Parameter[] {
                        new Parameter(OpenStackHeader.TOKEN.getKey(), authToken),
                        new Parameter("X-Subject-Token", subjectToken)
                },
                new Parameter[0]

        );

    }

}
