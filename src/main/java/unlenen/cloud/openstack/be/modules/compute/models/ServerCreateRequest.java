package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class ServerCreateRequest implements OpenStackRequest{
    @JsonAlias("server")
    public ServerCreate server;
}
