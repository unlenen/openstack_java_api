package unlenen.cloud.openstack.be.modules.volume.result;

import java.util.ArrayList;

import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.volume.models.Volume;

public class VolumeResult implements OpenStackResult {
    public ArrayList<Volume> volumes;
}
