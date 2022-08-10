package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


/**
 *
 * @author Nebi
 */
@Getter
@Setter
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
