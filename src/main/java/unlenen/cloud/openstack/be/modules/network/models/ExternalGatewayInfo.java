package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExternalGatewayInfo {
    @JsonInclude(Include.NON_NULL)
    public String network_id;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<ExternalFixedIp> external_fixed_ips;
    @JsonInclude(Include.NON_NULL)
    public Boolean enable_snat;
}
