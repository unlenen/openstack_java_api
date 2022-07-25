package unlenen.cloud.openstack.be.modules.compute.models;

import unlenen.cloud.openstack.be.model.entities.Entity;

public class KeypairData extends Entity {
    public String name;
    public String public_key;
    public String fingerprint; 
    public String user_id;
}
