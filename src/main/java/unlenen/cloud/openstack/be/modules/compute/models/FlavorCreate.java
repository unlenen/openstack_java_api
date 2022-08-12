package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlavorCreate {
    public String name;
    @JsonInclude(Include.NON_NULL)
    public Integer id;
    public Integer vcpus;
    public Integer ram;
    public Integer disk;
}
