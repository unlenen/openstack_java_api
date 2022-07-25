package unlenen.cloud.openstack.be.modules.compute.result;

import java.util.List;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Keypair;

public class KeypairResult implements OpenStackResult {
    public List<Keypair> keypairs;
}
