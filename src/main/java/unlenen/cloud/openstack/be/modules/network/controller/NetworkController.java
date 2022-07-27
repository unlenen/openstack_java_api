package unlenen.cloud.openstack.be.modules.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unlenen.cloud.openstack.be.modules.network.service.NetworkService;

@RestController
@RequestMapping("/networking/v2.0")
public class NetworkController {

    @Autowired
    NetworkService networkService;
}
