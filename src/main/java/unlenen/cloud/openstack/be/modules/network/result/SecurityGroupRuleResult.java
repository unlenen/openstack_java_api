package unlenen.cloud.openstack.be.modules.network.result;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;

public class SecurityGroupRuleResult implements OpenStackResult {
    @JsonAlias("security_group_rules")
    public SecurityGroupRule securityGroupRules;

}
