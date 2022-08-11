package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockDeviceMappingV2 {
    @JsonInclude(Include.NON_NULL)
    public String uuid;
    @JsonInclude(Include.NON_NULL)
    public String source_type;
    @JsonInclude(Include.NON_NULL)
    public String destination_type;
    @JsonInclude(Include.NON_NULL)
    public int boot_index;
    @JsonInclude(Include.NON_NULL)
    public String volume_size;
    @JsonInclude(Include.NON_NULL)
    public String tag;
}
