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
package unlenen.cloud.openstack.be.modules.orchestration.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
import unlenen.cloud.openstack.be.modules.orchestration.service.OrchestrationService;
import unlenen.cloud.openstack.be.util.IOUtil;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
@RequestMapping("/orchestration/v1")
public class OrchestrationController {

    @Autowired
    OrchestrationService orchestrationService;

    @PostMapping("/stack")
    public ResponseEntity<OpenStackResponse> createStack(@RequestHeader("token") String token,
            @RequestParam String name,
            @RequestPart(name = "template", required = true) MultipartFile template,
            @RequestPart(name = "environment", required = true) MultipartFile environment)
            throws IOException {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        File templateFile = null, envFile = null;
        try {

            templateFile = writeToTemp(template);
            envFile = writeToTemp(environment);

            openStackResponse.setOpenStackResult(
                    orchestrationService.createStack(token, name, templateFile.getPath(), envFile.getPath()));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        } finally {
            if (templateFile != null)
                templateFile.delete();
            if (envFile != null)
                envFile.delete();
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/stacks")
    public ResponseEntity<OpenStackResponse> getStacks(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(orchestrationService.getStacks(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/stack/{stack_id}/{stack_name}")
    public ResponseEntity<OpenStackResponse> deleteStack(@RequestHeader("token") String token,
            @PathVariable String stack_id,
            @PathVariable String stack_name) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            orchestrationService.deleteStack(token, stack_name, stack_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    private HttpStatus handleError(OpenStackResponse openStackResponse, Exception e) {
        HttpStatus httpStatus;
        openStackResponse.setError(new ErrorInfo(e.getClass().getSimpleName(), e.getMessage()));
        if (e instanceof UnvalidCallException) {
            httpStatus = ((UnvalidCallException) e).getCurrentStatusCode();
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }

    private File writeToTemp(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("os_stack_file", ".yaml");
        IOUtil.copyStream(multipartFile.getInputStream(), new FileOutputStream(file), true);
        return file;
    }
}
