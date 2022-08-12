package unlenen.cloud.openstack.be.modules.network.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;

@Getter
@Setter
@ToString
public class SecurityGroupCreateResult implements OpenStackResult{
    public SecurityGroup security_group;
}
