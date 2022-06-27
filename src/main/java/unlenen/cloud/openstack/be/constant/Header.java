package unlenen.cloud.openstack.be.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Nebi
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Header {

    String key();

    String value();

}
