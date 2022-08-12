package unlenen.cloud.openstack.be.modules.network.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AllocationPool {
    public String start;
    public String end;
}
