package unlenen.cloud.openstack.be.modules.compute.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Flavor;

/**
 *
 * @author Nebi
 */
public class FlavorResult implements OpenStackResult {

    public List<Flavor> flavors;
}
