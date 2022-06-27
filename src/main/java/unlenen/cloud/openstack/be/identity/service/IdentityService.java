package unlenen.cloud.openstack.be.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.Header;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.identity.result.CategoryResult;
import unlenen.cloud.openstack.be.identity.result.DomainResult;
import unlenen.cloud.openstack.be.identity.result.LoginResult;
import unlenen.cloud.openstack.be.identity.result.ProjectCreateResult;
import unlenen.cloud.openstack.be.identity.result.ProjectResult;
import unlenen.cloud.openstack.be.identity.result.RoleResult;
import unlenen.cloud.openstack.be.identity.result.UserCreateResult;
import unlenen.cloud.openstack.be.identity.result.UserResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi
 */
@Service
public class IdentityService extends CommonService {

    @Autowired
    OpenStackConfig openStackConfig;

    @Call(type = HttpMethod.POST,
            url = "/auth/tokens",
            headers = {
                @Header(key = "Content-Type", value = "application/json")},
            bodyFile = "payloads/identity/token_with_pass.json",
            statusCode = HttpStatus.CREATED,
            openstackResult = LoginResult.class
    )
    public LoginResult login(String domainName, String projectName, String username, String password) throws Exception {
        ResponseEntity<String> responseEntity = call(openStackConfig.getKeystoneURL(),
                new Parameter[0],
                new Parameter[]{
                    new Parameter("DOMAIN_NAME", domainName),
                    new Parameter("PROJECT_NAME", projectName),
                    new Parameter("USER_NAME", username),
                    new Parameter("PASSWORD", password),}
        );

        String body = responseEntity.getBody();

        LoginResult loginResult = getObjectMapper().readValue(body, LoginResult.class);
        loginResult.setId(getHeader(responseEntity, OpenStackHeader.TOKEN_CREATE.getKey()));
        return loginResult;
    }

    @Call(type = HttpMethod.GET,
            url = "/auth/catalog",
            statusCode = HttpStatus.OK,
            openstackResult = CategoryResult.class
    )
    public CategoryResult getCatalogs(String token) throws Exception {
        return (CategoryResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[0]
        );

    }

    @Call(type = HttpMethod.GET,
            url = "/domains",
            statusCode = HttpStatus.OK,
            openstackResult = DomainResult.class
    )
    public DomainResult getDomains(String token, String domainName) throws Exception {
        return (DomainResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("name", domainName)
                }
        );
    }

    @Call(type = HttpMethod.GET,
            url = "/projects",
            statusCode = HttpStatus.OK,
            openstackResult = ProjectResult.class
    )
    public ProjectResult getProjects(String token, String domainName, String projectName) throws Exception {
        return (ProjectResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("domain_id", domainName),
                    new Parameter("name", projectName)
                }
        );
    }

    @Call(type = HttpMethod.POST,
            url = "/projects",
            headers = {
                @Header(key = "Content-Type", value = "application/json")},
            bodyFile = "payloads/identity/project_create.json",
            statusCode = HttpStatus.CREATED,
            openstackResult = ProjectCreateResult.class
    )
    public ProjectCreateResult createProject(String token, String name, String description, String domain) throws Exception {
        return (ProjectCreateResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("name", name),
                    new Parameter("PROJECT_NAME", name),
                    new Parameter("DESCRIPTION", description),
                    new Parameter("DOMAIN", domain)
                }
        );
    }

    @Call(type = HttpMethod.GET,
            url = "/users",
            statusCode = HttpStatus.OK,
            openstackResult = UserResult.class
    )
    public UserResult getUsers(String token, String domainName, String name, String enabled) throws Exception {
        return (UserResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("domain_id", domainName),
                    new Parameter("name", name),
                    new Parameter("enabled", enabled),}
        );
    }

    @Call(type = HttpMethod.POST,
            url = "/users",
            headers = {
                @Header(key = "Content-Type", value = "application/json")},
            bodyFile = "payloads/identity/user_create.json",
            statusCode = HttpStatus.CREATED,
            openstackResult = UserCreateResult.class
    )
    public UserCreateResult createUser(String token, String username, String password, String email, String fullName, String description, String domain) throws Exception {
        return (UserCreateResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("name", username),
                    new Parameter("PASSWORD", password),
                    new Parameter("EMAIL", email),
                    new Parameter("FULL_NAME", fullName),
                    new Parameter("DESCRIPTION", description),
                    new Parameter("DOMAIN", domain)
                }
        );
    }

    @Call(type = HttpMethod.GET,
            url = "/roles",
            statusCode = HttpStatus.OK,
            openstackResult = RoleResult.class
    )
    public RoleResult getRoles(String token, String domainName, String name) throws Exception {
        return (RoleResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("domain_id", domainName),
                    new Parameter("name", name)
                }
        );
    }

    @Call(type = HttpMethod.PUT,
            url = "/domains/{DOMAIN_ID}/users/{USER_ID}/roles/{ROLE_ID}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void assignRoleToDomain(String token, String domainId, String userId, String roleId) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("DOMAIN_ID", domainId, ParameterType.URI),
                    new Parameter("USER_ID", userId, ParameterType.URI),
                    new Parameter("ROLE_ID", roleId, ParameterType.URI)
                }
        );
    }

    @Call(type = HttpMethod.PUT,
            url = "/projects/{PROJECT_ID}/users/{USER_ID}/roles/{ROLE_ID}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void assignRoleToProject(String token, String projectId, String userId, String roleId) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("PROJECT_ID", projectId, ParameterType.URI),
                    new Parameter("USER_ID", userId, ParameterType.URI),
                    new Parameter("ROLE_ID", roleId, ParameterType.URI)
                }
        );
    }
}
