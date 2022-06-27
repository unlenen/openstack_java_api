package unlenen.cloud.openstack.be.modules.compute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.modules.compute.result.FlavorCreateResult;
import unlenen.cloud.openstack.be.modules.compute.result.FlavorResult;
import unlenen.cloud.openstack.be.modules.compute.result.ServerResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi
 */
@Service
public class ComputeService extends CommonService {

    @Autowired
    OpenStackConfig openStackConfig;

    @Call(type = HttpMethod.GET,
            url = "/flavors/detail",
            statusCode = HttpStatus.OK,
            openstackResult = FlavorResult.class
    )
    public FlavorResult getFlavors(String token) throws Exception {
        return (FlavorResult) callWithResult(getServiceURL(token, OpenStackModule.compute),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[0]
        );
    }

    @Call(type = HttpMethod.POST,
            url = "/flavors",
            bodyFile = "payloads/compute/flavor_create.json",
            statusCode = HttpStatus.OK,
            openstackResult = FlavorCreateResult.class
    )
    public FlavorCreateResult createFlavor(String token, String name, int vcpus, int ram, int disk) throws Exception {
        return (FlavorCreateResult) callWithResult(getServiceURL(token, OpenStackModule.compute),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("NAME", name, ParameterType.JSON),
                    new Parameter("RAM", ram + "", ParameterType.JSON),
                    new Parameter("VCPUS", vcpus + "", ParameterType.JSON),
                    new Parameter("DISK", disk + "", ParameterType.JSON)
                }
        );
    }

    @Call(type = HttpMethod.DELETE,
            url = "/flavors/{flavor_id}",
            statusCode = HttpStatus.ACCEPTED
    )
    public void deleteFlavor(String token, String flavorId) throws Exception {
        call(getServiceURL(token, OpenStackModule.compute),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("flavor_id", flavorId, ParameterType.URI)
                }
        );
    }

    @Call(type = HttpMethod.GET,
            url = "/servers/detail",
            statusCode = HttpStatus.OK,
            openstackResult = ServerResult.class
    )
    public ServerResult getServers(String token, String projectId) throws Exception {
        return (ServerResult) callWithResult(getServiceURL(token, OpenStackModule.compute),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("project_id", projectId, ParameterType.REQUEST),
                    new Parameter("all_tenants", "true", ParameterType.REQUEST)
                }
        );

    }
}
