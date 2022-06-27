package unlenen.cloud.openstack.be.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
import unlenen.cloud.openstack.be.identity.service.IdentityService;

/**
 *
 * @author Nebi Volkan UNLENEN
 */
@RestController
@RequestMapping("/identity/v3")
public class IdentityController {

    @Autowired
    IdentityService identityService;

    @GetMapping("/login/{domain}/{project}/{user}/{password}")
    public ResponseEntity<OpenStackResponse> login(@PathVariable String domain, @PathVariable String project, @PathVariable String user, @PathVariable String password) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.login(domain, project, user, password));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);

    }

    @GetMapping("/catalog")
    public ResponseEntity<OpenStackResponse> getCatalogs(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.getCatalogs(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/domains")
    public ResponseEntity<OpenStackResponse> getDomains(
            @RequestHeader("token") String token,
            @RequestParam(required = false, name = "name") String domainName
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.getDomains(token, domainName));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/projects")
    public ResponseEntity<OpenStackResponse> getProjects(
            @RequestHeader("token") String token,
            @RequestParam(required = false, name = "domain") String domainName,
            @RequestParam(required = false, name = "name") String projectName
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.getProjects(token, domainName, projectName));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/domain/{domain}/project/{name}")
    public ResponseEntity<OpenStackResponse> createProject(
            @RequestHeader("token") String token,
            @PathVariable() String domain,
            @PathVariable() String name,
            @RequestParam(required = false, name = "description", defaultValue = "") String description
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.createProject(token, name, description, domain));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/users")
    public ResponseEntity<OpenStackResponse> getUsers(
            @RequestHeader("token") String token,
            @RequestParam(required = false, name = "domain") String domainName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "enabled") String enabled
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.getUsers(token, domainName, name, enabled));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PostMapping("/domain/{domain}/user/{username}")
    public ResponseEntity<OpenStackResponse> getUsers(
            @RequestHeader("token") String token,
            @PathVariable() String domain,
            @PathVariable() String username,
            @RequestParam(required = true, name = "password") String password,
            @RequestParam(required = false, name = "email", defaultValue = "") String email,
            @RequestParam(required = false, name = "fullName") String fullName,
            @RequestParam(required = false, name = "description", defaultValue = "") String description
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.createUser(token, username, password, email, fullName, description, domain));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/roles")
    public ResponseEntity<OpenStackResponse> getRoles(
            @RequestHeader("token") String token,
            @RequestParam(required = false, name = "domain") String domainName,
            @RequestParam(required = false, name = "name") String name
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(identityService.getRoles(token, domainName, name));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PutMapping("/role/{roleId}/user/{userId}/domain/{domainId}")
    public ResponseEntity<OpenStackResponse> assignRoleToDomain(
            @RequestHeader("token") String token,
            @PathVariable String roleId,
            @PathVariable String userId,
            @PathVariable String domainId
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            identityService.assignRoleToDomain(token, domainId, userId, roleId);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @PutMapping("/role/{roleId}/user/{userId}/project/{projectId}")
    public ResponseEntity<OpenStackResponse> assignRoleToProject(
            @RequestHeader("token") String token,
            @PathVariable String roleId,
            @PathVariable String userId,
            @PathVariable String projectId
    ) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            identityService.assignRoleToProject(token, projectId, userId, roleId);
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
