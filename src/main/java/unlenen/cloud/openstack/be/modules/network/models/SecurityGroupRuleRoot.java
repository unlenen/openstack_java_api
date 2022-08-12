package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter
public class SecurityGroupRuleRoot implements OpenStackRequest {
    @JsonAlias("security_group_rule")
    public SecurityGroupRule security_group_rule;
}
