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
public class UserOptions {
    public boolean ignore_change_password_upon_first_use;
    public boolean ignore_password_expiry;
    public boolean ignore_lockout_failure_attempts;
    public boolean lock_password;

}
