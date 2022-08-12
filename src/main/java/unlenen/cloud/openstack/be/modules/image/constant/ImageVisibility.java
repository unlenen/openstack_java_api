package unlenen.cloud.openstack.be.modules.image.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ImageVisibility {
    PUBLIC, PRIVATE, SHARED, COMMUNITY;
}
