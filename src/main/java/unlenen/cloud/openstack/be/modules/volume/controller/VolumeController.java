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
package unlenen.cloud.openstack.be.modules.volume.controller;

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
import org.springframework.web.bind.annotation.RestController;

import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
import unlenen.cloud.openstack.be.modules.volume.service.VolumeService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
@RequestMapping("/volume/v3")
public class VolumeController {

    @Autowired
    VolumeService volumeService;

    @GetMapping("/volumes")
    public ResponseEntity<OpenStackResponse> getVolumes(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(volumeService.getVolumes(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/volume")
    public ResponseEntity<OpenStackResponse> createVolume(@RequestHeader("token") String token,
            @RequestParam String name,
            @RequestParam String bootable,
            @RequestParam String imageRef,
            @RequestParam int size) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(volumeService.createVolume(token, name, bootable,imageRef, size));
            httpStatus = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/volume/{volume_id}")
    public ResponseEntity<OpenStackResponse> deleteVolume(
            @RequestHeader("token") String token,
            @PathVariable String volume_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            volumeService.deleteVolume(token, volume_id);
            httpStatus = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/volume/{volume_id}/extend")
    public ResponseEntity<OpenStackResponse> extendVolumeSize(
            @RequestHeader("token") String token,
            @PathVariable String volume_id,
            @RequestParam int new_size) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            volumeService.extendVolumeSize(token, volume_id, new_size);
            httpStatus = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/volume/{volume_id}/attach")
    public ResponseEntity<OpenStackResponse> attachVolumetoServer(
            @RequestHeader("token") String token,
            @PathVariable String volume_id,
            @RequestParam String instance_uuid,
            @RequestParam String mountpoint) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            volumeService.attachVolumetoServer(token, volume_id, instance_uuid, mountpoint);
            httpStatus = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/volume/{volume_id}/detach")
    public ResponseEntity<OpenStackResponse> detachVolumefromServer(
            @RequestHeader("token") String token,
            @PathVariable String volume_id,
            @RequestParam(required = false) String attachment_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            volumeService.detachVolumefromServer(token, volume_id, attachment_id);
            httpStatus = HttpStatus.ACCEPTED;
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
}
