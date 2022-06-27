package unlenen.cloud.openstack.be.modules.compute.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Flavor;

/**
 *
 * @author Nebi
 */
public class FlavorCreateResult implements OpenStackResult {

    public Flavor flavor;
}
