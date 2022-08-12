package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class Metadata {

    @JsonProperty("metering.server_group")
    public String meteringServerGroup;
    public String product;
    public String app;
}
