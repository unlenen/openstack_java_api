package unlenen.cloud.openstack.be.identity.result;

import java.util.List;
import unlenen.cloud.openstack.be.identity.models.Catalog;
import unlenen.cloud.openstack.be.identity.models.Links;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class CategoryResult implements OpenStackResult {

    public List<Catalog> catalog;
    public Links links;
}
