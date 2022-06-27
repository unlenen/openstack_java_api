package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
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
