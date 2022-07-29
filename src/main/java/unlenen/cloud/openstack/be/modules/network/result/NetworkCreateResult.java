package unlenen.cloud.openstack.be.modules.network.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Network;

public class NetworkCreateResult implements OpenStackResult {
    public Network network;
}
