package unlenen.cloud.openstack.be.modules.orchestration.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.orchestration.models.Stack;

@Getter
@Setter
@ToString
public class StackCreateResult implements OpenStackResult {
    public Stack stack;
}
