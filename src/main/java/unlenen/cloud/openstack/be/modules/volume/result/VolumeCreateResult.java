package unlenen.cloud.openstack.be.modules.volume.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.volume.models.Volume;

@Getter
@Setter
@ToString
public class VolumeCreateResult implements OpenStackResult {
    public Volume volume;
}
