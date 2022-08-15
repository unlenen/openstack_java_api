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
package unlenen.cloud.openstack.be.modules.compute.models;

import java.util.ArrayList;

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
public class ServerCreate {

    @JsonInclude(Include.NON_NULL)
    public String name;
    @JsonInclude(Include.NON_NULL)
    public String imageRef;
    @JsonInclude(Include.NON_NULL)
    public String flavorRef;
    @JsonInclude(Include.NON_NULL)
    public String availability_zone;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<SecurityGroup> security_groups;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Network> networks;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<BlockDeviceMappingV2> block_device_mapping_v2;
    @JsonInclude(Include.NON_NULL)
    public String user_data;
    @JsonInclude(Include.NON_NULL)
    public String key_name;
}
