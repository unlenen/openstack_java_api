package unlenen.cloud.openstack.be.modules.network.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;

@Getter
@Setter
@ToString
public class SecurityGroupRuleCreateResult implements OpenStackResult {
    public SecurityGroupRule security_group_rule;
}
