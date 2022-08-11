package unlenen.cloud.openstack.be.modules.network.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Floatingip;

public class FloatingipCreateResult implements OpenStackResult {
    public Floatingip floatingip;
}
