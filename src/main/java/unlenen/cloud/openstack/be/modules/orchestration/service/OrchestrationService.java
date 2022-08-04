package unlenen.cloud.openstack.be.modules.orchestration.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.modules.orchestration.input.StackInput;
import unlenen.cloud.openstack.be.modules.orchestration.result.StackCreateResult;
import unlenen.cloud.openstack.be.modules.orchestration.result.StackResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi
 */
@Service
public class OrchestrationService extends CommonService {

        @Autowired
        OpenStackConfig openStackConfig;

        @Call(type = HttpMethod.GET, url = "/stacks", statusCode = HttpStatus.OK, openstackResult = StackResult.class)
        public StackResult getStacks(String token) throws Exception {
                return (StackResult) callWithResult(getServiceURL(token, OpenStackModule.orchestration),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/stacks", statusCode = HttpStatus.CREATED, openstackResult = StackCreateResult.class)
        public StackCreateResult createStack(String token, String name, String templateFile, String envFile) throws Exception {

                JSONObject stack_create_json = new JSONObject();
                JSONObject fileJson = new JSONObject();

                fileJson.put(templateFile, "template description");
                fileJson.put(envFile, "enviroment description");

                stack_create_json.put("files", fileJson);
                stack_create_json.put("disable_rollback", true);

                Path templatePath = Path.of(templateFile);
                Path envPath = Path.of(envFile);

                JSONObject templateJson = new JSONObject(convertYamlToJson(Files.readString(templatePath)));
                JSONObject envJson = new JSONObject(convertYamlToJson(Files.readString(envPath)));

                stack_create_json.put("parameters", envJson);
                stack_create_json.put("stack_name", name);
                stack_create_json.put("template", templateJson);

                return (StackCreateResult) callWithResult(getServiceURL(token, OpenStackModule.orchestration),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                stack_create_json.toString(),
                                3);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2/stacks/{stack_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteStack(String token, String stackId) throws Exception {
                call(getServiceURL(token, OpenStackModule.orchestration),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("stack_id", stackId, ParameterType.URI)
                                });
        }
}
