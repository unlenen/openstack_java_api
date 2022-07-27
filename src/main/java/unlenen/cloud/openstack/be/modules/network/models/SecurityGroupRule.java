package unlenen.cloud.openstack.be.modules.network.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class SecurityGroupRule {
    @JsonInclude(Include.NON_NULL) public String id;
    @JsonInclude(Include.NON_NULL) public String tenant_id;
    @JsonInclude(Include.NON_NULL) public String security_group_id;
    @JsonInclude(Include.NON_NULL) public String ethertype;
    @JsonInclude(Include.NON_NULL) public String direction;
    @JsonInclude(Include.NON_NULL) public String protocol;
    @JsonInclude(Include.NON_NULL) public int port_range_min;
    @JsonInclude(Include.NON_NULL) public int port_range_max;
    @JsonInclude(Include.NON_NULL) public String remote_ip_prefix;
    @JsonInclude(Include.NON_NULL) public Object remote_address_group_id;
    @JsonInclude(Include.NON_NULL) public String normalized_cidr;
    @JsonInclude(Include.NON_NULL) public String remote_group_id;
    @JsonInclude(Include.NON_NULL) public String description;
    @JsonInclude(Include.NON_NULL) public Date created_at;
    @JsonInclude(Include.NON_NULL) public Date updated_at;
    @JsonInclude(Include.NON_NULL) public Integer revision_number;
    @JsonInclude(Include.NON_NULL) public String project_id;
}
