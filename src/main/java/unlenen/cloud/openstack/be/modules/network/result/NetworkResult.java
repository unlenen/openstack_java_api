package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Network;

@Getter
@Setter
@ToString
public class NetworkResult implements OpenStackResult {
    public ArrayList<Network> networks;
}
