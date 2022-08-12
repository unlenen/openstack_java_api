package unlenen.cloud.openstack.be.modules.compute.models;

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
public class Link {

    public String rel;
    public String href;
}
