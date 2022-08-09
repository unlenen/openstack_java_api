package unlenen.cloud.openstack.be.modules.volume.result;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.volume.models.Volume;

public class VolumeCreateResult implements OpenStackResult {
    public Volume volume;
}
