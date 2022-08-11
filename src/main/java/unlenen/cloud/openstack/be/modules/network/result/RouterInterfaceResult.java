package unlenen.cloud.openstack.be.modules.network.result;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

@Getter
@Setter
public class RouterInterfaceResult implements OpenStackResult {
    @JsonInclude(Include.NON_NULL) public String id;
    @JsonInclude(Include.NON_NULL) public String tenant_id;
    @JsonInclude(Include.NON_NULL) public String port_id;
    @JsonInclude(Include.NON_NULL) public String network_id;
    @JsonInclude(Include.NON_NULL) public String project_id;
    @JsonInclude(Include.NON_NULL) public String subnet_id;
    @JsonInclude(Include.NON_NULL) public ArrayList<String> subnet_ids;
    @JsonInclude(Include.NON_NULL) public ArrayList<String> tags;
}
