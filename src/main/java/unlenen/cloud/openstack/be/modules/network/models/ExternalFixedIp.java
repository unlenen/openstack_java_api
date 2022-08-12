package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExternalFixedIp {
    @JsonInclude(Include.NON_NULL)
    public String subnet_id;
    @JsonInclude(Include.NON_NULL)
    public String ip_address;
}
