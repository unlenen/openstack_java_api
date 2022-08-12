package unlenen.cloud.openstack.be.modules.network.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortDetails {
    public String name;
    public String network_id;
    public String mac_address;
    public boolean admin_state_up;
    public String status;
    public String device_id;
    public String device_owner;
}
