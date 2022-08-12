package unlenen.cloud.openstack.be.modules.volume.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Link {
    public String rel;
    public String href;

}
