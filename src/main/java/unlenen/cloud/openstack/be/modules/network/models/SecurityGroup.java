package unlenen.cloud.openstack.be.modules.network.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter
@ToString
public class SecurityGroup implements OpenStackRequest {
    @JsonInclude(Include.NON_NULL)
    public String id;
    @JsonInclude(Include.NON_NULL)
    public String name;
    @JsonInclude(Include.NON_NULL)
    public boolean stateful;
    @JsonInclude(Include.NON_NULL)
    public String tenant_id;
    @JsonInclude(Include.NON_NULL)
    public String description;
    @JsonInclude(Include.NON_NULL)
    public ArrayList<SecurityGroupRule> security_group_rules;
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
