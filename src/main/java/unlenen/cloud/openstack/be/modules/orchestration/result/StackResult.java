package unlenen.cloud.openstack.be.modules.orchestration.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.orchestration.models.Stack;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class StackResult implements OpenStackResult {

    public List<Stack> stacks;

}
