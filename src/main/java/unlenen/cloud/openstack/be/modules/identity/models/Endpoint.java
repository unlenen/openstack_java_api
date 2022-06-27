package unlenen.cloud.openstack.be.modules.identity.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Nebi
 */
public class Endpoint {

    public String id;
    @JsonProperty("interface")
    public String interfaceType;
    public String region_id;
    public String url;
    public String region;
}
