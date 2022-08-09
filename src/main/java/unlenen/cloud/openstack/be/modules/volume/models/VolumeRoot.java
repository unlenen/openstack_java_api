package unlenen.cloud.openstack.be.modules.volume.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class VolumeRoot implements OpenStackRequest{
    @JsonAlias("volume")
    public Volume volume;
}
