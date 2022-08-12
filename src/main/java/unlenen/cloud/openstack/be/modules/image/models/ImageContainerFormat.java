package unlenen.cloud.openstack.be.modules.image.models;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Nebi
 */
@Getter
@ToString
public enum ImageContainerFormat {
    ami, ari, aki, bare, ovf, ova, docker;
}
