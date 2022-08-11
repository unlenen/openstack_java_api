package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Router;

public class RouterResult implements OpenStackResult {
    public ArrayList<Router> routers;
}
