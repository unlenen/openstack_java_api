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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;
/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
@Setter
@ToString
public class Flavor extends Entity {

    public int ram;
    public int disk;
    public String swap;
    @JsonProperty("OS-FLV-EXT-DATA:ephemeral")
    public int oSFLVEXTDATAEphemeral;
    @JsonProperty("OS-FLV-DISABLED:disabled")
    public boolean oSFLVDISABLEDDisabled;
    public int vcpus;
    @JsonProperty("os-flavor-access:is_public")
    public boolean osFlavorAccessIsPublic;
    public double rxtx_factor;
    public List<Link> links;
}
