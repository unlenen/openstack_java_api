package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter

public class Network implements OpenStackRequest {
    @JsonInclude(Include.NON_NULL)
    public String id;

    @JsonInclude(Include.NON_NULL)
    public String name;

    @JsonInclude(Include.NON_NULL)
    public String tenant_id;

    @JsonInclude(Include.NON_NULL)
    public boolean admin_state_up = true;

    @JsonInclude(Include.NON_NULL)
    public Integer mtu;

    @JsonInclude(Include.NON_NULL)
    public String status;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> subnets;

    @JsonInclude(Include.NON_NULL)
    public boolean shared =false;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<Object> availability_zone_hints;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> availability_zones;

    @JsonInclude(Include.NON_NULL)
    public Object ipv4_address_scope;

    @JsonInclude(Include.NON_NULL)
    public Object ipv6_address_scope;

    @JsonProperty("router:external")
    @JsonInclude(Include.NON_NULL)
    public boolean routerExternal=false;

    @JsonInclude(Include.NON_NULL)
    public String description;

    @JsonInclude(Include.NON_NULL)
    public Object qos_policy_id;

    @JsonInclude(Include.NON_NULL)
    public boolean port_security_enabled=false;

    @JsonInclude(Include.NON_NULL)
    public String dns_domain;

    @JsonAlias("is_default")
    @JsonProperty("is_default")
    @JsonInclude(Include.NON_NULL)
    public boolean is_default=false;

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

    @JsonProperty("provider:network_type")
    @JsonInclude(Include.NON_NULL)
    public String providerNetworkType;

    @JsonProperty("provider:physical_network")
    @JsonInclude(Include.NON_NULL)
    public String providerPhysicalNetwork;

    @JsonProperty("provider:segmentation_id")
    @JsonInclude(Include.NON_NULL)
    public Integer providerSegmentationId;
}
