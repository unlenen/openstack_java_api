package unlenen.cloud.openstack.be.modules.compute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unlenen.cloud.openstack.be.modules.compute.service.ComputeService;

/**
 *
 * @author Nebi Volkan UNLENEN
 */
@RestController
@RequestMapping("/compute/v2.1")
public class ComputeController {

    @Autowired
    ComputeService computeService;
}
