package unlenen.cloud.openstack.be.modules.identity.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Links;
import unlenen.cloud.openstack.be.modules.identity.models.Role;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class RoleResult implements OpenStackResult {

    public List<Role> roles;
    public Links links;

}
