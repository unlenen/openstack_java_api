package unlenen.cloud.openstack.be.modules.identity.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.User;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class UserCreateResult implements OpenStackResult {

    public User user;

}
