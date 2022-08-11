package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;

public class ExternalGatewayInfo {
    @JsonInclude(Include.NON_NULL)
    public String network_id;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<ExternalFixedIp> external_fixed_ips;
    @JsonInclude(Include.NON_NULL)
    public Boolean enable_snat;
}
