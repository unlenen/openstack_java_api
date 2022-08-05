package unlenen.cloud.openstack.be.modules.orchestration.controller;

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

    @GetMapping("/stacks")
    public ResponseEntity<OpenStackResponse> getStacks(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(orchestrationService.getStacks(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/stacks/{stack_name}/{stack_id}")
    public ResponseEntity<OpenStackResponse> deleteStack(@RequestHeader("token") String token,
            @PathVariable String stack_name, @PathVariable String stack_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            orchestrationService.deleteStack(token, stack_name, stack_id);
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
