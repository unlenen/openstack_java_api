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
package unlenen.cloud.openstack.be.test.network;

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
public class NetworkServiceTestConfig {
    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.network_service.security_group.name}")
    String securityGroupName;

    @Value("${testData.network_service.security_group.project_id}")
    String securityGroupProjectId;

    @Value("${testData.network_service.security_group.security_group_rules.direction}")
    String securityGroupRulesDirection;

    @Value("${testData.network_service.security_group.security_group_rules.protocol}")
    String securityGroupRulesProtocol;

    @Value("${testData.network_service.security_group.security_group_rules.port_range_min}")
    int securityGroupRulesPortRangeMin;

    @Value("${testData.network_service.security_group.security_group_rules.port_range_max}")
    int securityGroupRulesPortRangeMax;

    @Value("${testData.network_service.network.name}")
    String networkName;

    @Value("${testData.network_service.network.shared}")
    boolean networkShared;

    @Value("${testData.network_service.network.router_external}")
    boolean networkRouterExternal;

    @Value("${testData.network_service.network.provider_network_type}")
    String networkProviderNetworkType;

    @Value("${testData.network_service.network.description}")
    String networkDescription;

    @Value("${testData.network_service.network.mtu}")
    Integer networkMtu;

    @Value("${testData.network_service.network.admin_state_up}")
    boolean networkAdminStateUp;

    @Value("${testData.network_service.network.port_security_enabled}")
    boolean networkPortSecurityEnabled;
    
    @Value("${testData.network_service.subnet.name}")
    String subnetName;

    @Value("${testData.network_service.subnet.ip_version}")
    int subnetIpVersion;

    @Value("${testData.network_service.subnet.cidr}")
    String subnetCidr;

    @Value("${testData.network_service.subnet.allocation_pool_start}")
    String subnetAllocationPoolStart;

    @Value("${testData.network_service.subnet.allocation_pool_end}")
    String subnetAllocationPoolEnd;

    @Value("${testData.network_service.subnet.gateaway_ip}")
    String subnetGateawayIp;

    @Value("${testData.network_service.floatingip.floating_network_id}")
    String floating_network_id;

    @Value("${testData.network_service.floatingip.floating_ip_address}")
    String floating_ip_address;

    @Value("${testData.network_service.router.name}")
    String routerName;

    @Value("${testData.network_service.router.external_network_id}")
    String routerExternalNetworkId;

}
