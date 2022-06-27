package unlenen.cloud.openstack.be.modules.identity.service;

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
import unlenen.cloud.openstack.be.modules.identity.result.CategoryResult;
import unlenen.cloud.openstack.be.modules.identity.result.DomainCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.DomainResult;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectResult;
import unlenen.cloud.openstack.be.modules.identity.result.RoleResult;
import unlenen.cloud.openstack.be.modules.identity.result.UserCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.UserResult;
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
                    new Parameter("DOMAIN_NAME", domainName, ParameterType.JSON),
                    new Parameter("PROJECT_NAME", projectName, ParameterType.JSON),
                    new Parameter("USER_NAME", username, ParameterType.JSON),
                    new Parameter("PASSWORD", password, ParameterType.JSON),}
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

    @Call(type = HttpMethod.POST,
            url = "/domains",
            headers = {
                @Header(key = "Content-Type", value = "application/json")},
            bodyFile = "payloads/identity/domain_create.json",
            statusCode = HttpStatus.CREATED,
            openstackResult = DomainCreateResult.class
    )
    public DomainCreateResult createDomain(String token, String name, String description) throws Exception {
        return (DomainCreateResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("DESCRIPTION", description, ParameterType.JSON),
                    new Parameter("DOMAIN_NAME", name, ParameterType.JSON),
                    new Parameter("name", name, ParameterType.REQUEST)
                }
        );
    }

    @Call(type = HttpMethod.PATCH,
            url = "/domains/{domain_id}",
            bodyFile = "payloads/identity/domain_update.json",
            statusCode = HttpStatus.OK,
            openstackResult = DomainCreateResult.class
    )
    public void updateDomain(String token, String domainId, String enabled, String description) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("domain_id", domainId, ParameterType.URI),
                    new Parameter("ENABLED", enabled, ParameterType.JSON),
                    new Parameter("DESCRIPTION", description, ParameterType.JSON),}
        );
    }

    @Call(type = HttpMethod.DELETE,
            url = "/domains/{domain_id}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void deleteDomain(String token, String domainId) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("domain_id", domainId, ParameterType.URI)
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
    public ProjectCreateResult createProject(String token, String name, String description, String domainId) throws Exception {
        return (ProjectCreateResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("PROJECT_NAME", name, ParameterType.JSON),
                    new Parameter("DESCRIPTION", description, ParameterType.JSON),
                    new Parameter("DOMAIN", domainId, ParameterType.JSON),
                    new Parameter("name", name, ParameterType.REQUEST)
                }
        );
    }

    @Call(type = HttpMethod.DELETE,
            url = "/projects/{project_id}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void deleteProject(String token, String projectId) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("project_id", projectId, ParameterType.URI)
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
    public UserCreateResult createUser(String token, String username, String password, String email, String description, String domainId) throws Exception {
        return (UserCreateResult) callWithResult(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("FULL_NAME", username, ParameterType.JSON),
                    new Parameter("PASSWORD", password, ParameterType.JSON),
                    new Parameter("EMAIL", email, ParameterType.JSON),
                    new Parameter("DESCRIPTION", description, ParameterType.JSON),
                    new Parameter("DOMAIN", domainId, ParameterType.JSON),
                    new Parameter("name", username, ParameterType.REQUEST)
                }
        );
    }

    @Call(type = HttpMethod.DELETE,
            url = "/users/{user_id}",
            statusCode = HttpStatus.NO_CONTENT
    )
    public void deleteUser(String token, String userId) throws Exception {
        call(openStackConfig.getKeystoneURL(),
                new Parameter[]{
                    new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[]{
                    new Parameter("user_id", userId, ParameterType.URI)
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
