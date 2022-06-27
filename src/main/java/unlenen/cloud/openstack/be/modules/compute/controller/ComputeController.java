package unlenen.cloud.openstack.be.modules.compute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
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
            @RequestParam(required = false, name = "description", defaultValue = "") String description
    ) {
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
            @PathVariable() String flavorId
    ) {
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
