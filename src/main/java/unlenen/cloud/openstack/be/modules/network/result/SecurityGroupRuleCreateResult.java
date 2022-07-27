package unlenen.cloud.openstack.be.modules.network.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;

public class SecurityGroupRuleCreateResult implements OpenStackResult{
    public SecurityGroupRule security_group_rule;
}
