package unlenen.cloud.openstack.be.modules.compute.models;

import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
public class Role extends Entity {

    public Object domain_id;
    public Object description;
    public RoleOptions options;
    public Links links;
}
