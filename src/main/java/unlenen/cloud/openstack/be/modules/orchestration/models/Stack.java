package unlenen.cloud.openstack.be.modules.orchestration.models;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Nebi
 */
public class Stack {

    public String id;
    public List<Link> links;
    public String project;
    public String stack_name;
    public String description;
    public String stack_status;
    public String stack_status_reason;
    public Date creation_time;
    public Object updated_time;
    public Object deletion_time;
    public String stack_owner;
    public Object parent;
    public String stack_user_project_id;
    public Object tags;
}
