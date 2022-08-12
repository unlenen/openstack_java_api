package unlenen.cloud.openstack.be.modules.network.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.network.models.Router;

@Getter
@Setter
@ToString
public class RouterCreateResult implements OpenStackResult {
    public Router router;
}
