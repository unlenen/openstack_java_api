package unlenen.cloud.openstack.be.modules.identity.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.identity.models.Catalog;
import unlenen.cloud.openstack.be.modules.identity.models.Links;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class CategoryResult implements OpenStackResult {

    public List<Catalog> catalog;
    public Links links;
}
