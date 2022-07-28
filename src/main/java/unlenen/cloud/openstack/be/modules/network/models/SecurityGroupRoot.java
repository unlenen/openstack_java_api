package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class SecurityGroupRoot implements OpenStackRequest{
    @JsonAlias("security_group")
    public SecurityGroup security_group; 
}
