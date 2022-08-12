package unlenen.cloud.openstack.be.modules.compute.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;

@Getter
@Setter
@ToString
public class KeypairData extends Entity {
    public String name;
    public String public_key;
    public String fingerprint; 
    public String user_id;
}
