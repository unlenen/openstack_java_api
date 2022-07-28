package unlenen.cloud.openstack.be.modules.network.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;

public class SecurityGroupCreateResult implements OpenStackResult{
    public SecurityGroup security_group;
}
