package unlenen.cloud.openstack.be.modules.compute.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.KeypairData;

@Getter
@Setter
@ToString
public class KeypairCreateResult implements OpenStackResult {
    public KeypairData keypair;
}
