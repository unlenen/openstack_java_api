package unlenen.cloud.openstack.be.modules.network.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;

public class SecurityGroupRuleResult implements OpenStackResult{
    @JsonAlias("security_group_rules")
    public List <SecurityGroupRule> securityGroupRules; 
}
