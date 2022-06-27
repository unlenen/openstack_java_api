package unlenen.cloud.openstack.be.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi Volkan UNLENEN
 */
@Getter
@Setter
@NoArgsConstructor
public class OpenStackResponse {

    OpenStackResult openStackResult;
    ErrorInfo error;

}
