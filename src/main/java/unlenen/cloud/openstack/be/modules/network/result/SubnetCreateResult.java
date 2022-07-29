package unlenen.cloud.openstack.be.modules.network.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Subnet;

public class SubnetCreateResult implements OpenStackResult {
    public Subnet subnet;
}
