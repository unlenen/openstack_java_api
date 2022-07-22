package unlenen.cloud.openstack.be.modules.compute.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.KeypairData;

public class KeypairCreateResult implements OpenStackResult {
    public KeypairData keypair;
}
