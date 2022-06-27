package unlenen.cloud.openstack.be.identity.result;

import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.identity.models.Token;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
public class LoginResult implements OpenStackResult {

 
    String id;
    Token token;
}
