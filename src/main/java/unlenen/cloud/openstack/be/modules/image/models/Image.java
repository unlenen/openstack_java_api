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
package unlenen.cloud.openstack.be.modules.image.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
@Setter
@ToString
public class Image extends Entity implements OpenStackResult {

    @JsonProperty("owner_specified.openstack.md5")
    public String ownerSpecifiedOpenstackMd5;

    @JsonProperty("owner_specified.openstack.sha256")
    public String ownerSpecifiedOpenstackSha256;

    @JsonProperty("owner_specified.openstack.object")
    public String ownerSpecifiedOpenstackObject;

    public String disk_format;
    public String container_format;
    public String visibility;
    public Object size;
    public Object virtual_size;
    public String status;
    public String checksum;
    @JsonProperty("protected")
    public boolean protectedImage;
    public int min_ram;
    public int min_disk;
    public String owner;
    public boolean os_hidden;
    public String os_hash_algo;
    public String os_hash_value;

    public Date created_at;
    public Date updated_at;
    public List<String> tags;
    public String self;
    public String file;
    public String schema;
    public String stores;
}
