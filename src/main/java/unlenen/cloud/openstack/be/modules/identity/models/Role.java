package unlenen.cloud.openstack.be.modules.identity.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class Role extends Entity {
    public Object domain_id;
    public Object description;
    public RoleOptions options;
    public Links links;
}
