package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Nebi
 */
public class Metadata {

    @JsonProperty("metering.server_group")
    public String meteringServerGroup;
    public String product;
    public String app;
}
