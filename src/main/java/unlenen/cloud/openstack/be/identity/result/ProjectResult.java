package unlenen.cloud.openstack.be.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.identity.models.Links;
import unlenen.cloud.openstack.be.identity.models.Project;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class ProjectResult implements OpenStackResult {

    public List<Project> projects;
    public Links links;

}
