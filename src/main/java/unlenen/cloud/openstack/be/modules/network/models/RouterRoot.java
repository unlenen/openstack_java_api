package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class RouterRoot implements OpenStackRequest{
    @JsonAlias("router")
    public Router router;
}
