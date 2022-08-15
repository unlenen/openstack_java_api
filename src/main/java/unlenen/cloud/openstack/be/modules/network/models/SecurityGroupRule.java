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

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
@Setter
@ToString
public class SecurityGroupRule {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public String security_group_id;
    @JsonInclude(Include.NON_NULL)
    public String ethertype;
    @JsonInclude(Include.NON_NULL)
    public String direction;
    @JsonInclude(Include.NON_NULL)
    public String protocol;
    @JsonInclude(Include.NON_NULL)
    public Integer port_range_min;
    @JsonInclude(Include.NON_NULL)
    public Integer port_range_max;
    @JsonInclude(Include.NON_NULL)
    public String remote_ip_prefix;
    @JsonInclude(Include.NON_NULL)
    public Object remote_address_group_id;
    @JsonInclude(Include.NON_NULL)
    public String normalized_cidr;
    @JsonInclude(Include.NON_NULL)
    public String remote_group_id;
    @JsonInclude(Include.NON_NULL)
    public Integer standard_attr_id;
    @JsonInclude(Include.NON_NULL)
    public String description;
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
