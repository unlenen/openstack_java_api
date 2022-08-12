package unlenen.cloud.openstack.be.modules.compute.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class Image extends Entity {

    public List<Link> links;
}
