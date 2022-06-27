package unlenen.cloud.openstack.be.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.identity.models.Links;
import unlenen.cloud.openstack.be.identity.models.User;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class UserResult implements OpenStackResult {

    public List<User> users;
    public Links links;

}
