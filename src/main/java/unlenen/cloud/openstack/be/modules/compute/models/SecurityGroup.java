package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class SecurityGroup{
    @JsonInclude(Include.NON_NULL)
    public String name;
}
