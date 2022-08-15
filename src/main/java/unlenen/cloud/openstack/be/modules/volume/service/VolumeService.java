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
package unlenen.cloud.openstack.be.modules.volume.service;

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
import unlenen.cloud.openstack.be.modules.volume.models.Volume;
import unlenen.cloud.openstack.be.modules.volume.models.VolumeRoot;
import unlenen.cloud.openstack.be.modules.volume.result.VolumeCreateResult;
import unlenen.cloud.openstack.be.modules.volume.result.VolumeResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Service
public class VolumeService extends CommonService {
        @Autowired
        OpenStackConfig openStackConfig;

        @Call(type = HttpMethod.GET, url = "/volumes", statusCode = HttpStatus.OK, openstackResult = VolumeResult.class)
        public VolumeResult getVolumes(String token) throws Exception {
                return (VolumeResult) callWithResult(getServiceURL(token, OpenStackModule.volumev3),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/volumes", statusCode = HttpStatus.ACCEPTED, openstackResult = VolumeCreateResult.class)
        public VolumeCreateResult createVolume(String token, String name, String bootable,String imageRef, int size)
                        throws Exception {
                VolumeRoot volumeRoot = new VolumeRoot();
                Volume volume = new Volume();
                volume.setName(name);
                volume.setBootable(bootable);
                volume.setImageRef(imageRef);
                volume.setSize(size);
                volumeRoot.volume = volume;

                return (VolumeCreateResult) callWithResult(getServiceURL(token, OpenStackModule.volumev3),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                volumeRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/volumes/{volume_id}", statusCode = HttpStatus.ACCEPTED)
        public void deleteVolume(String token, String volume_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.volumev3),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("volume_id", volume_id,
                                                                ParameterType.URI)
                                });
        }
}
