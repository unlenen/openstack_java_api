package unlenen.cloud.openstack.be.modules.identity.models;

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
public class Links {
    public String self;
    public String next;
    public String previous;
}
