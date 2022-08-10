package unlenen.cloud.openstack.be.modules.compute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import unlenen.cloud.openstack.be.modules.compute.models.Quota;
import unlenen.cloud.openstack.be.modules.compute.models.ServerCreateRequest;
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

    @GetMapping("/flavors")
    public ResponseEntity<OpenStackResponse> getFlavors(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.getFlavors(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/flavor/{name}/{vcpus}/{ram}/{disk}")
    public ResponseEntity<OpenStackResponse> createFlavor(
            @RequestHeader("token") String token,
            @PathVariable() String name,
            @PathVariable() Integer vcpus,
            @PathVariable() Integer ram,
            @PathVariable() Integer disk,
            @RequestParam(required = false, name = "description", defaultValue = "") String description) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.createFlavor(token, name, vcpus, ram, disk));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/flavor/{flavorId}")
    public ResponseEntity<OpenStackResponse> deleteFlavor(
            @RequestHeader("token") String token,
            @PathVariable() String flavorId) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            computeService.deleteFlavor(token, flavorId);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/os-keypairs")
    public ResponseEntity<OpenStackResponse> getKeypairs(@RequestHeader("token") String token,@RequestHeader("userId") String userId){
    OpenStackResponse openStackResponse = new OpenStackResponse();
    HttpStatus httpStatus;
    try {
        openStackResponse.setOpenStackResult(computeService.getKeypairs(token,userId));
        httpStatus = HttpStatus.OK;
    } catch (Exception e) {
        httpStatus = handleError(openStackResponse, e);
    }
    return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/os-keypairs")
    public ResponseEntity<OpenStackResponse> createKeypair(
        @RequestHeader("token") String token,
        @PathVariable() String name,
        @PathVariable() String public_key,
        @RequestParam(required = false, name = "description", defaultValue = "") String description) {
    OpenStackResponse openStackResponse = new OpenStackResponse();
    HttpStatus httpStatus;
    try {
        openStackResponse.setOpenStackResult(computeService.createKeypair(token, name, public_key));
        httpStatus = HttpStatus.CREATED;
    } catch (Exception e) {
        httpStatus = handleError(openStackResponse, e);
    }
    return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/os-keypairs/{keypair_name}")
    public ResponseEntity<OpenStackResponse> deleteKeypair(
            @RequestHeader("token") String token,
            @PathVariable() String keypair_name) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            computeService.deleteKeypair(token, keypair_name);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/os-quota-sets/{tenant_id}")
    public ResponseEntity<OpenStackResponse> getQuota(@RequestHeader("token") String token,@PathVariable() String tenant_id){
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.getQuota(token, tenant_id));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }
    
    @PutMapping("/os-quota-sets/{tenant_id}" )
    public ResponseEntity<OpenStackResponse> updateQuota(@RequestHeader("token") String token,@PathVariable() String tenant_id,@RequestBody Quota quota){
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.updateQuota(token, tenant_id,quota));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/servers")
    public ResponseEntity<OpenStackResponse> getServers(@RequestHeader("token") String token,@RequestParam String projectId){
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.getServers(token, projectId));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }


    @PutMapping("/servers")
    public ResponseEntity<OpenStackResponse> createServer(@RequestHeader("token") String token,@RequestBody ServerCreateRequest serverCreateRequest){
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(computeService.createServer(token, serverCreateRequest));
            httpStatus = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/servers/{server_id}")
    public ResponseEntity<OpenStackResponse> deleteServer(
            @RequestHeader("token") String token,
            @PathVariable() String server_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            computeService.deleteServer(token, server_id);
            httpStatus = HttpStatus.NO_CONTENT;
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
