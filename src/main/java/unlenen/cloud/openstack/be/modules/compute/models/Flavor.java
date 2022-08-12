package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import unlenen.cloud.openstack.be.model.entities.Entity;

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
public class Flavor extends Entity {

    public int ram;
    public int disk;
    public String swap;
    @JsonProperty("OS-FLV-EXT-DATA:ephemeral")
    public int oSFLVEXTDATAEphemeral;
    @JsonProperty("OS-FLV-DISABLED:disabled")
    public boolean oSFLVDISABLEDDisabled;
    public int vcpus;
    @JsonProperty("os-flavor-access:is_public")
    public boolean osFlavorAccessIsPublic;
    public double rxtx_factor;
    public List<Link> links;
}
