package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter

public class Subnet implements OpenStackRequest {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String name;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public String network_id;
    @JsonInclude(Include.NON_NULL)
    public Integer ip_version;
    @JsonInclude(Include.NON_NULL)
    public Object subnetpool_id;
    @JsonInclude(Include.NON_NULL)
    public boolean enable_dhcp = true;
    @JsonInclude(Include.NON_NULL)
    public Object ipv6_ra_mode;
    @JsonInclude(Include.NON_NULL)
    public Object ipv6_address_mode;
    @JsonInclude(Include.NON_NULL)
    public String gateway_ip;
    @JsonInclude(Include.NON_NULL)
    public String cidr;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<AllocationPool> allocation_pools;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> host_routes;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> dns_nameservers;
    @JsonInclude(Include.NON_NULL)
    public String description;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> service_types;
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
