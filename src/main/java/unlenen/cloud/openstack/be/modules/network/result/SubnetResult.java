package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Subnet;

@Getter
@Setter
@ToString
public class SubnetResult implements OpenStackResult {
    public ArrayList<Subnet> subnets;
}
