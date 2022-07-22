package unlenen.cloud.openstack.be.modules.orchestration.service;

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
import unlenen.cloud.openstack.be.modules.orchestration.input.StackInput;
import unlenen.cloud.openstack.be.modules.orchestration.result.StackResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi
 */
@Service
public class OrchestrationService extends CommonService {

    @Autowired
    OpenStackConfig openStackConfig;

    @Call(type = HttpMethod.GET,
            url = "/stacks",
            statusCode = HttpStatus.OK,
            openstackResult = StackResult.class
    )
    public StackResult getStacks(String token, String name, String status) throws Exception {
        return (StackResult) callWithResult(getServiceURL(token, OpenStackModule.orchestration),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("name", name),
                    new Parameter("status", status)
                }
        );
    }

    @Call(type = HttpMethod.POST,
            url = "/stacks",
            bodyFile = "payloads/orchestration/stack_create.json",
            statusCode = HttpStatus.CREATED
    )
    public void createStack(String token, StackInput stackInput) throws Exception {

        
        
        callWithResult(getServiceURL(token, OpenStackModule.orchestration),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("NAME", stackInput.getName()),
                    new Parameter("TIMEOUT", stackInput.getTimeout() + ""),
                    new Parameter("ROLLBACK_DISABLED", (!stackInput.isRollbackAllowed()) + ""),}
        );
    }

    @Call(type = HttpMethod.DELETE,
            url = "/v2/stacks/{stack_id}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void deleteStack(String token, String stackId) throws Exception {
        call(getServiceURL(token, OpenStackModule.orchestration),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("stack_id", stackId, ParameterType.URI)
                }
        );
    }
}
