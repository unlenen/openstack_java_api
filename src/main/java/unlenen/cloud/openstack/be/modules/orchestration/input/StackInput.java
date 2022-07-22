package unlenen.cloud.openstack.be.modules.orchestration.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StackInput {

    String name;
    String parameters;
    String template;
    boolean rollbackAllowed;
    int timeout;

}
