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
package unlenen.cloud.openstack.be.modules.network.service;

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
import unlenen.cloud.openstack.be.modules.network.models.ExternalGatewayInfo;
import unlenen.cloud.openstack.be.modules.network.models.Floatingip;
import unlenen.cloud.openstack.be.modules.network.models.FloatingipRoot;
import unlenen.cloud.openstack.be.modules.network.models.NetworkRoot;
import unlenen.cloud.openstack.be.modules.network.models.Router;
import unlenen.cloud.openstack.be.modules.network.models.RouterRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.models.SubnetRoot;
import unlenen.cloud.openstack.be.modules.network.result.FloatingipCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.FloatingipResult;
import unlenen.cloud.openstack.be.modules.network.result.NetworkCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.NetworkResult;
import unlenen.cloud.openstack.be.modules.network.result.RouterCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.RouterInterfaceResult;
import unlenen.cloud.openstack.be.modules.network.result.RouterResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRuleCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRulesResult;
import unlenen.cloud.openstack.be.modules.network.result.SubnetCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SubnetResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Service
public class NetworkService extends CommonService {

        @Autowired
        OpenStackConfig openStackConfig;

        @Call(type = HttpMethod.GET, url = "/v2.0/security-group-rules", statusCode = HttpStatus.OK, openstackResult = SecurityGroupRulesResult.class)
        public SecurityGroupRulesResult getSecurityGroupRules(String token) throws Exception {
                return (SecurityGroupRulesResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/security-group-rules", statusCode = HttpStatus.CREATED, openstackResult = SecurityGroupRuleCreateResult.class)
        public SecurityGroupRuleCreateResult createSecurityGroupRules(String token,
                        SecurityGroupRuleRoot securityGroupRuleRoot) throws Exception {

                return (SecurityGroupRuleCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                securityGroupRuleRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/security-group-rules/{security_group_rule_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteSecurityGroupRule(String token, String security_group_rule_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("security_group_rule_id", security_group_rule_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/security-groups", statusCode = HttpStatus.OK, openstackResult = SecurityGroupResult.class)
        public SecurityGroupResult getSecurityGroups(String token) throws Exception {
                return (SecurityGroupResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);

        }

        @Call(type = HttpMethod.POST, url = "/v2.0/security-groups", statusCode = HttpStatus.CREATED, openstackResult = SecurityGroupCreateResult.class)
        public SecurityGroupCreateResult createSecurityGroup(String token,String name)
                        throws Exception {
                SecurityGroupRoot securityGroupRoot=new SecurityGroupRoot();
                securityGroupRoot.setSecurity_group(new SecurityGroup());
                securityGroupRoot.getSecurity_group().setName(name);
                return (SecurityGroupCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                securityGroupRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/security-groups/{security_group_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteSecurityGroup(String token, String security_group_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("security_group_id", security_group_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/networks", statusCode = HttpStatus.OK, openstackResult = NetworkResult.class)
        public NetworkResult getNetworks(String token) throws Exception {
                return (NetworkResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/networks", statusCode = HttpStatus.CREATED, openstackResult = NetworkCreateResult.class)
        public NetworkCreateResult createNetwork(String token, NetworkRoot networkRoot)
                        throws Exception {
                return (NetworkCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                networkRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/networks/{network_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteNetwork(String token, String network_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("network_id", network_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/subnets", statusCode = HttpStatus.OK, openstackResult = SubnetResult.class)
        public SubnetResult getSubnets(String token) throws Exception {
                return (SubnetResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/subnets", statusCode = HttpStatus.CREATED, openstackResult = SubnetCreateResult.class)
        public SubnetCreateResult createSubnet(String token, SubnetRoot subnetRoot) throws Exception {
                return (SubnetCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                subnetRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/subnets/{subnet_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteSubnet(String token, String subnet_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("subnet_id", subnet_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/floatingips", statusCode = HttpStatus.OK, openstackResult = FloatingipResult.class)
        public FloatingipResult getFloatingIps(String token) throws Exception {
                return (FloatingipResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/floatingips", statusCode = HttpStatus.CREATED, openstackResult = FloatingipCreateResult.class)
        public FloatingipCreateResult createFloatingip(String token, String floating_network_id,
                        String floating_ip_address) throws Exception {
                FloatingipRoot floatingipRoot = new FloatingipRoot();
                Floatingip floatingip = new Floatingip();
                if (!floating_ip_address.equals("")) {
                        floatingip.setFloating_ip_address(floating_ip_address);
                }

                floatingip.setFloating_network_id(floating_network_id);
                floatingipRoot.floatingip = floatingip;
                return (FloatingipCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                floatingipRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/floatingips/{floatingip_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteFloatingip(String token, String floatingip_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("floatingip_id", floatingip_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/routers", statusCode = HttpStatus.OK, openstackResult = RouterResult.class)
        public RouterResult getRouters(String token) throws Exception {
                return (RouterResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/routers", statusCode = HttpStatus.CREATED, openstackResult = RouterCreateResult.class)
        public RouterCreateResult createRouter(String token, String name, String external_network_id) throws Exception {
                RouterRoot routerRoot = new RouterRoot();
                Router router = new Router();
                routerRoot.router = router;
                router.setName(name);
                router.setExternal_gateway_info(new ExternalGatewayInfo());
                router.getExternal_gateway_info().network_id = external_network_id;

                return (RouterCreateResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                routerRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/routers/{router_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteRouter(String token, String router_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("router_id", router_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.PUT, url = "/v2.0/routers/{router_id}/add_router_interface", statusCode = HttpStatus.OK, openstackResult = RouterInterfaceResult.class)
        public RouterInterfaceResult addRouterInterface(String token, String router_id, String subnet_id)
                        throws Exception {
                JSONObject subnetId = new JSONObject();
                subnetId.put("subnet_id", subnet_id);
                return (RouterInterfaceResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("router_id", router_id, ParameterType.URI),
                                },
                                subnetId.toString(), 3);
        }
}
