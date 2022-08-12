package unlenen.cloud.openstack.be.modules.identity.models;

import java.util.List;

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
public class Catalog {

    public List<Endpoint> endpoints;
    public String id;
    public String type;
    public String name;
}
