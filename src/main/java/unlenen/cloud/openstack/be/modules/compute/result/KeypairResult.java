package unlenen.cloud.openstack.be.modules.compute.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Keypair;

@Getter
@Setter
@ToString
public class KeypairResult implements OpenStackResult {
    public List<Keypair> keypairs;
}
