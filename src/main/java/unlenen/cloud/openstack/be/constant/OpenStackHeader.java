package unlenen.cloud.openstack.be.constant;

import lombok.Getter;

/**
 *
 * @author Nebi
 */
@Getter
public enum OpenStackHeader {
    TOKEN_CREATE("x-subject-token"),
    TOKEN("X-Auth-Token");

    private OpenStackHeader(String key) {
        this.key = key;
    }

    String key;
}
