package unlenen.cloud.openstack.be.modules.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Links;
import unlenen.cloud.openstack.be.modules.identity.models.Project;

/**
 *
 * @author Nebi
 */
public class ProjectResult implements OpenStackResult {

    public List<Project> projects;
    public Links links;

}
