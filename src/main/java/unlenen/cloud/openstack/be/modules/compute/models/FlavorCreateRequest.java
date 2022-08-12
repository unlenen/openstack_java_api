package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class FlavorCreateRequest implements OpenStackRequest {
    @JsonAlias("flavor")
    public FlavorCreate flavor;
}
