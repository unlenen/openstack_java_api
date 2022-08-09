package unlenen.cloud.openstack.be.modules.identity.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Token;;

public class TokenResult implements OpenStackResult {
    public Token token;
}
