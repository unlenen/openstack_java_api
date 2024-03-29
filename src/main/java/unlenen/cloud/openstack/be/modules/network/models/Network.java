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
package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
@Setter
@ToString
public class Network implements OpenStackRequest {
    @JsonInclude(Include.NON_NULL)
    public String id;

    @JsonInclude(Include.NON_NULL)
    public String name;

    @JsonInclude(Include.NON_NULL)
    public String tenant_id;

    @JsonInclude(Include.NON_NULL)
    public Boolean admin_state_up;

    @JsonInclude(Include.NON_NULL)
    public Integer mtu;

    @JsonInclude(Include.NON_NULL)
    public String status;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> subnets;

    @JsonInclude(Include.NON_NULL)
    public Boolean shared;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> availability_zone_hints;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> availability_zones;

    @JsonInclude(Include.NON_NULL)
    public Object ipv4_address_scope;

    @JsonInclude(Include.NON_NULL)
    public Object ipv6_address_scope;

    @JsonProperty("router:external")
    @JsonInclude(Include.NON_NULL)
    public Boolean routerExternal;

    @JsonInclude(Include.NON_NULL)
    public String description;

    @JsonInclude(Include.NON_NULL)
    public Object qos_policy_id;

    @JsonInclude(Include.NON_NULL)
    public Boolean port_security_enabled;

    @JsonInclude(Include.NON_NULL)
    public String dns_domain;

    @JsonAlias("is_default")
    @JsonProperty("is_default")
    @JsonInclude(Include.NON_NULL)
    public Boolean is_default;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> tags;

    @JsonInclude(Include.NON_NULL)
    public Date created_at;

    @JsonInclude(Include.NON_NULL)
    public Date updated_at;

    @JsonInclude(Include.NON_NULL)
    public Integer revision_number;

    @JsonInclude(Include.NON_NULL)
    public String project_id;

    @JsonProperty("provider:network_type")
    @JsonInclude(Include.NON_NULL)
    public String providerNetworkType;

    @JsonProperty("provider:physical_network")
    @JsonInclude(Include.NON_NULL)
    public String providerPhysicalNetwork;

    @JsonProperty("provider:segmentation_id")
    @JsonInclude(Include.NON_NULL)
    public Integer providerSegmentationId;
}
