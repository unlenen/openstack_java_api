package unlenen.cloud.openstack.be.modules.image.result;

import java.util.List;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.image.models.Image;

/**
 *
 * @author Nebi
 */
public class ImageResult implements OpenStackResult {

    public List<Image> images;
    public String first;
    public String schema;
}
