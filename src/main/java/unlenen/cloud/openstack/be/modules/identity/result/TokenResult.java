package unlenen.cloud.openstack.be.modules.identity.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Token;;

@Getter
@Setter
@ToString
public class TokenResult implements OpenStackResult {
    public Token token;
}
