package unlenen.cloud.openstack.be.modules.orchestration.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.orchestration.models.Stack;

public class StackCreateResult implements OpenStackResult {
    public Stack stack;
}
