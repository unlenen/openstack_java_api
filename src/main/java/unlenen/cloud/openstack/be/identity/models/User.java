package unlenen.cloud.openstack.be.identity.models;

import java.util.Date;

import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
public class User extends Entity {

    public Domain domain;

    public String domain_id;
    public boolean enabled;
    public String default_project_id;
    public Date password_expires_at;
    public UserOptions options;
    public Links links;
    public String email;
    public String description;
}
