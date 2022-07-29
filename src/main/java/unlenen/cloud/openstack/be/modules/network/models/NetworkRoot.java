package unlenen.cloud.openstack.be.modules.network.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

public class NetworkRoot implements OpenStackRequest {
    @JsonAlias("network")
    public Network network;
}
