package unlenen.cloud.openstack.be.test;

import org.junit.FixMethodOrder;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import unlenen.cloud.openstack.be.Application;
import unlenen.cloud.openstack.be.modules.compute.result.FlavorResult;
import unlenen.cloud.openstack.be.modules.compute.result.ServerResult;
import unlenen.cloud.openstack.be.modules.compute.service.ComputeService;
import unlenen.cloud.openstack.be.modules.identity.result.DomainResult;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;

/**
 *
 * @author Nebi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ComputeServiceTest {

    @Autowired
    ComputeService computeService;
    @Autowired
    IdentityService identityService;

    @Autowired
    ComputeServiceTestConfig config;

    private String createSystemToken() throws Exception {

        LoginResult loginResult = identityService.login(
                config.getSystemDomainName(),
                config.getSystemProjectName(),
                config.getSystemUserName(),
                config.getSystemPassword());
        assert loginResult.getId() != null;
        return loginResult.getId();
    }

    @Test
    public void test_001_listFlavors() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            FlavorResult flavorList = computeService.getFlavors(token);
            assert !flavorList.flavors.isEmpty();
        });
    }

    @Test
    public void test_002_listServers() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id, config.getProjectName());
            ServerResult serverResult = computeService.getServers(token, projectResult.projects.get(0).id);
            assert !serverResult.servers.isEmpty();
        });
    }

}
