package unlenen.cloud.openstack.be.constant;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nebi
 */
public enum OpenStackModule {
    identity,
    compute,
    image,
    network;

    final static Set<String> enums = new HashSet();

    static {
        for (OpenStackModule openStackModule : values()) {
            enums.add(openStackModule.name());
        }
    }

    public static boolean has(String key) {
        return enums.contains(key);
    }
}
