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
public class Quota {
    public String id;
    public Integer cores;
    public Integer instances;
    public Integer key_pairs;
    public Integer metadata_items;
    public Integer ram;
    public Integer server_groups;
    public Integer server_group_members;
    public Integer fixed_ips;
    public Integer floating_ips;
    public Integer networks;
    public Integer security_group_rules;
    public Integer security_groups;
    public Integer injected_file_content_bytes;
    public Integer injected_file_path_bytes;
    public Integer injected_files;
}
