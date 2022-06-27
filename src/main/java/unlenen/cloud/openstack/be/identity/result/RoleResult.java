package unlenen.cloud.openstack.be.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.identity.models.Links;
import unlenen.cloud.openstack.be.identity.models.Role;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class RoleResult implements OpenStackResult {

    public List<Role> roles;
    public Links links;

}
