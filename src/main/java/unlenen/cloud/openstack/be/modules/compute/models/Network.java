package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Network {
    @JsonInclude(Include.NON_NULL)
    public String uuid;
    @JsonInclude(Include.NON_NULL)
    public String tag;
}
