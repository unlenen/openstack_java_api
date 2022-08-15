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
package unlenen.cloud.openstack.be.test.compute;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Configuration
@Getter
@Setter
public class ComputeServiceTestConfig {

    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.compute_service.domain.name}")
    String domainName;

    @Value("${testData.compute_service.domain.project.name}")
    String projectName;

    @Value("${testData.compute_service.flavor.name}")
    String flavorName;

    @Value("${testData.compute_service.flavor.vcpus}")
    Integer flavorVcpus;

    @Value("${testData.compute_service.flavor.ram}")
    Integer flavorRam;

    @Value("${testData.compute_service.flavor.disk}")
    Integer flavorDisk;

    @Value("${testData.compute_service.keypair.name}")
    String keypairName;

    @Value("${testData.compute_service.keypair.public_key}")
    String keypairPublic_Key;

    @Value("${testData.compute_service.keypair.user.name}")
    String keypairUserName;

    @Value("${testData.compute_service.quota.domain}")
    String quotaDomainName;

    @Value("${testData.compute_service.quota.project}")
    String quotaProjectName;

    @Value("${testData.compute_service.server.name}")
    String serverName;

    @Value("${testData.compute_service.server.flavorRef}")
    String serverFlavorRef;

    @Value("${testData.compute_service.server.imageRef}")
    String serverImageRef;

    @Value("${testData.compute_service.server.key_name}")
    String serverKeyName;

    @Value("${testData.compute_service.server.availability_zone}")
    String serverAvailability_zone;

    @Value("${testData.compute_service.server.network_uuid}")
    String serverNetworkUuid;

    @Value("${testData.compute_service.server.securityGroupName}")
    String serverSecurityGroupName;
    
}
