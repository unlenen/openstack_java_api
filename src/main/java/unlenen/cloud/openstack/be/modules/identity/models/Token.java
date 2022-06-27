package unlenen.cloud.openstack.be.modules.identity.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nebi
 */
public class Token {

    public List<String> methods;
    public User user;
    public ArrayList<String> audit_ids;
    public Date expires_at;
    public Date issued_at;
    public Project project;
    public boolean is_domain;
    public List<Role> roles;
    public List<Catalog> catalog;
}
