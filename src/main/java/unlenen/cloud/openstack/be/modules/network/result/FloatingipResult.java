package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Floatingip;

public class FloatingipResult implements OpenStackResult {
    public ArrayList<Floatingip> floatingips;
}
