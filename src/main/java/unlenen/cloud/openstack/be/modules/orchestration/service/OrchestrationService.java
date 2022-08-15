/*
# Copyright © 2022 Nebi Volkan UNLENEN
#
# Licensed under the GNU Affero General Public License v3.0
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://github.com/unlenen/openstack_java_api/blob/master/LICENSE
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */
package unlenen.cloud.openstack.be.modules.orchestration.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.modules.orchestration.result.StackCreateResult;
import unlenen.cloud.openstack.be.modules.orchestration.result.StackResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
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

        @Call(type = HttpMethod.DELETE, url = "/stacks/{stack_name}/{stack_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteStack(String token,String stackName ,String stackId) throws Exception {
                call(getServiceURL(token, OpenStackModule.orchestration),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("stack_name", stackName, ParameterType.URI),
                                                new Parameter("stack_id", stackId, ParameterType.URI)
                                });
        }
}
