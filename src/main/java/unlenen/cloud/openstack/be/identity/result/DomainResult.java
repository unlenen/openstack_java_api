package unlenen.cloud.openstack.be.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.identity.models.Domain;
import unlenen.cloud.openstack.be.identity.models.Links;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class DomainResult implements OpenStackResult {

    public List<Domain> domains;
    public Links links;

}
