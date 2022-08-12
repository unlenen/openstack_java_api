package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter
@ToString
public class SubnetRoot implements OpenStackRequest {
    @JsonAlias("subnet")
    public Subnet subnet; 
}
