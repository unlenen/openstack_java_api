package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;

public class SecurityGroupResult implements OpenStackResult {
    public ArrayList<SecurityGroup> security_groups;
}
