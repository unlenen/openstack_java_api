package unlenen.cloud.openstack.be.modules.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
import unlenen.cloud.openstack.be.modules.network.models.NetworkRoot;
import unlenen.cloud.openstack.be.modules.network.models.RouterRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.models.SubnetRoot;
import unlenen.cloud.openstack.be.modules.network.service.NetworkService;

@RestController
@RequestMapping("/networking/v2.0")
public class NetworkController {

    @Autowired
    NetworkService networkService;

    @GetMapping("/v2.0/security-group-rules")
    public ResponseEntity<OpenStackResponse> getSecurityGroupRules(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getSecurityGroupRules(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/v2.0/security-group-rules")
    public ResponseEntity<OpenStackResponse> createSecurityGroupRules(@RequestHeader("token") String token,
            @RequestBody SecurityGroupRuleRoot securityGroupRuleRoot) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createSecurityGroupRules(token, securityGroupRuleRoot));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/security-group-rules/{security_group_rule_id}")
    public ResponseEntity<OpenStackResponse> deleteSecurityGroupRule(
            @RequestHeader("token") String token,
            @PathVariable() String security_group_rule_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteSecurityGroupRule(token, security_group_rule_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/v2.0/security-groups")
    public ResponseEntity<OpenStackResponse> getSecurityGroups(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getSecurityGroups(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/v2.0/security-groups")
    public ResponseEntity<OpenStackResponse> createSecurityGroup(@RequestHeader("token") String token,
            @RequestParam String name) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createSecurityGroup(token, name));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/security-groups/{security_group_id}")
    public ResponseEntity<OpenStackResponse> deleteSecurityGroup(
            @RequestHeader("token") String token,
            @PathVariable() String security_group_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteSecurityGroup(token, security_group_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/v2.0/networks")
    public ResponseEntity<OpenStackResponse> getNetworks(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getNetworks(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/v2.0/network")
    public ResponseEntity<OpenStackResponse> createNetwork(@RequestHeader("token") String token,
            @RequestBody NetworkRoot networkRoot) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createNetwork(token, networkRoot));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/networks/{network_id}")
    public ResponseEntity<OpenStackResponse> deleteNetwork(
            @RequestHeader("token") String token,
            @PathVariable() String network_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteNetwork(token, network_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/v2.0/subnets")
    public ResponseEntity<OpenStackResponse> getSubnets(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getSubnets(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/v2.0/subnets")
    public ResponseEntity<OpenStackResponse> createSubnet(@RequestHeader("token") String token,
            @RequestBody SubnetRoot SubnetRoot) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createSubnet(token, SubnetRoot));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/subnets/{subnet_id}")
    public ResponseEntity<OpenStackResponse> deleteSubnet(
            @RequestHeader("token") String token,
            @PathVariable() String subnet_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteSubnet(token, subnet_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/v2.0/floatingips")
    public ResponseEntity<OpenStackResponse> getFloatingips(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getFloatingIps(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("//v2.0/floatingips")
    public ResponseEntity<OpenStackResponse> createFloatingip(@RequestHeader("token") String token,
           @RequestParam String floating_network_id,
           @RequestParam String floating_ip_address ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createFloatingip(token, floating_network_id, floating_ip_address));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/floatingips/{floatingip_id}}")
    public ResponseEntity<OpenStackResponse> deleteFloatingip(
            @RequestHeader("token") String token,
            @PathVariable() String floatingip_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteFloatingip(token, floatingip_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/v2.0/routers")
    public ResponseEntity<OpenStackResponse> getRouters(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.getRouters(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/v2.0/routers")
    public ResponseEntity<OpenStackResponse> createRouter(@RequestHeader("token") String token,@RequestParam String name, @RequestParam String external_network_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(networkService.createRouter(token, name,external_network_id));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/v2.0/routers/{router_id}")
    public ResponseEntity<OpenStackResponse> deleteRouter(
            @RequestHeader("token") String token,
            @PathVariable() String router_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            networkService.deleteRouter(token, router_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PutMapping("/v2.0/routers/{router_id}/add_router_interface")
    public ResponseEntity<OpenStackResponse> addRouterInterface(
        @RequestHeader("token") String token,
        @PathVariable() String router_id,
        @RequestParam String subnet_id) {
    OpenStackResponse openStackResponse = new OpenStackResponse();
    HttpStatus httpStatus;
    try {
        openStackResponse.setOpenStackResult(networkService.addRouterInterface(token, router_id, subnet_id));
        httpStatus = HttpStatus.OK;
    } catch (Exception e) {
        httpStatus = handleError(openStackResponse, e);
    }
    return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
}



    private HttpStatus handleError(OpenStackResponse openStackResponse, Exception e) {
        HttpStatus httpStatus;
        openStackResponse.setError(new ErrorInfo(e.getClass().getSimpleName(), e.getMessage()));
        if (e instanceof UnvalidCallException) {
            httpStatus = ((UnvalidCallException) e).getCurrentStatusCode();
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }
}
