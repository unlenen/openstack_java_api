package unlenen.cloud.openstack.be.model.response;

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
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {

    String code;
    String message;
}
