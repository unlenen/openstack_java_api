package unlenen.cloud.openstack.be.modules.compute.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.ServerCreateReturn;

public class ServerCreateResult implements OpenStackResult {
    public ServerCreateReturn server;
}
