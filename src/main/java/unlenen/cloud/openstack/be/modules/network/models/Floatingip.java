package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Floatingip {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public String floating_ip_address;
    @JsonInclude(Include.NON_NULL)
    public String floating_network_id;
    @JsonInclude(Include.NON_NULL)
    public String router_id;
    @JsonInclude(Include.NON_NULL)
    public String port_id;
    @JsonInclude(Include.NON_NULL)
    public String fixed_ip_address;
    @JsonInclude(Include.NON_NULL)
    public String status;
    @JsonInclude(Include.NON_NULL)
    public String description;
    @JsonInclude(Include.NON_NULL)
    public Object qos_policy_id;
    @JsonInclude(Include.NON_NULL)
    public PortDetails port_details;
    @JsonInclude(Include.NON_NULL)
    public String dns_domain;
    @JsonInclude(Include.NON_NULL)
    public String dns_name;
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
