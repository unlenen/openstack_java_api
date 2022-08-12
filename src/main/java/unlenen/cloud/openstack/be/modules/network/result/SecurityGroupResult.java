package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;

@Getter
@Setter
@ToString
public class SecurityGroupResult implements OpenStackResult {
    public ArrayList<SecurityGroup> security_groups;
}
