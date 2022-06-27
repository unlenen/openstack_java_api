package unlenen.cloud.openstack.be.modules.compute.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Server;

/**
 *
 * @author Nebi
 */
public class ServerResult implements OpenStackResult {

    public List<Server> servers;
}
