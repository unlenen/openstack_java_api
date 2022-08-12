package unlenen.cloud.openstack.be.modules.volume.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter
@ToString
public class VolumeRoot implements OpenStackRequest{
    @JsonAlias("volume")
    public Volume volume;
}
