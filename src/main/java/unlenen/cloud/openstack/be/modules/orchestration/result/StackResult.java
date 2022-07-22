package unlenen.cloud.openstack.be.modules.orchestration.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.orchestration.models.Stack;

/**
 *
 * @author Nebi
 */
public class StackResult implements OpenStackResult {

    public List<Stack> stacks;

}
