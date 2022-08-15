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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class Subnet implements OpenStackRequest {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String name;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public String network_id;
    @JsonInclude(Include.NON_NULL)
    public Integer ip_version;
    @JsonInclude(Include.NON_NULL)
    public Object subnetpool_id;
    @JsonInclude(Include.NON_NULL)
    public boolean enable_dhcp = true;
    @JsonInclude(Include.NON_NULL)
    public Object ipv6_ra_mode;
    @JsonInclude(Include.NON_NULL)
    public Object ipv6_address_mode;
    @JsonInclude(Include.NON_NULL)
    public String gateway_ip;
    @JsonInclude(Include.NON_NULL)
    public String cidr;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<AllocationPool> allocation_pools;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> host_routes;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> dns_nameservers;
    @JsonInclude(Include.NON_NULL)
    public String description;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> service_types;
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
}
