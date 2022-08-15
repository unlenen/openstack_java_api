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

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Server extends Entity {

   public String status;
    public String tenant_id;
    public String user_id;
    public Metadata metadata;
    public String hostId;
    public Image image;
    public Flavor flavor;
    public Date created;
    public Date updated;
    @JsonIgnore
    public Addresses addresses;
    public String accessIPv4;
    public String accessIPv6;
    public List<Link> links;
    @JsonProperty("OS-DCF:diskConfig") 
    public String oSDCFDiskConfig;
    public int progress;
    @JsonProperty("OS-EXT-AZ:availability_zone") 
    public String oSEXTAZAvailabilityZone;
    public String config_drive;
    public String key_name;
    @JsonProperty("OS-SRV-USG:launched_at") 
    public Date oSSRVUSGLaunchedAt;
    @JsonProperty("OS-SRV-USG:terminated_at") 
    public Object oSSRVUSGTerminatedAt;
    @JsonProperty("OS-EXT-SRV-ATTR:host") 
    public String oSEXTSRVATTRHost;
    @JsonProperty("OS-EXT-SRV-ATTR:instance_name") 
    public String oSEXTSRVATTRInstanceName;
    @JsonProperty("OS-EXT-SRV-ATTR:hypervisor_hostname") 
    public String oSEXTSRVATTRHypervisorHostname;
    @JsonProperty("OS-EXT-STS:task_state") 
    public Object oSEXTSTSTaskState;
    @JsonProperty("OS-EXT-STS:vm_state") 
    public String oSEXTSTSVmState;
    @JsonProperty("OS-EXT-STS:power_state") 
    public int oSEXTSTSPowerState;
    @JsonProperty("os-extended-volumes:volumes_attached") 
    public List<Object> osExtendedVolumesVolumesAttached;
    public List<SecurityGroup> security_groups;
}
