package unlenen.cloud.openstack.be.modules.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Domain;
import unlenen.cloud.openstack.be.modules.identity.models.Links;

/**
 *
 * @author Nebi
 */
public class DomainResult implements OpenStackResult {

    public List<Domain> domains;
    public Links links;

}
