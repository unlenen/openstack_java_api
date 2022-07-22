package unlenen.cloud.openstack.be.modules.orchestration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unlenen.cloud.openstack.be.modules.orchestration.service.OrchestrationService;

/**
 *
 * @author Nebi
 */
@RestController
@RequestMapping("/orchestration/v1")
public class OrchestrationController {

    @Autowired
    OrchestrationService orchestrationService;
}
