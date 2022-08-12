package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Router {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String name;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public boolean admin_state_up = true;
    @JsonInclude(Include.NON_NULL)
    public String status;
    @JsonInclude(Include.NON_NULL)
    public ExternalGatewayInfo external_gateway_info;
    @JsonInclude(Include.NON_NULL)
    public String description;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> availability_zones;
    @JsonInclude(Include.NON_NULL)
    public Boolean distributed;
    @JsonInclude(Include.NON_NULL)
    public Boolean ha;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> availability_zone_hints;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> routes;
    @JsonInclude(Include.NON_NULL)
    public Object flavor_id;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> tags;
    @JsonInclude(Include.NON_NULL)
    public Date created_at;
    @JsonInclude(Include.NON_NULL)
    public Date updated_at;
    @JsonInclude(Include.NON_NULL)
    public Integer revision_number;
    @JsonInclude(Include.NON_NULL)
    public String project_id;
}
