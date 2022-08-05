package unlenen.cloud.openstack.be.test.orchestration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
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
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;
import unlenen.cloud.openstack.be.modules.orchestration.service.OrchestrationService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class OrchestrationServiceTest {

    @Autowired
    IdentityService identityService;

    @Autowired
    OrchestrationService orchestrationService;

    @Autowired
    OrchestrationServiceTestConfig config;

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
    public void test_0001_createStack(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
           orchestrationService.createStack(token, config.getStackName(),config.getStackTemplateFile(),config.getStackEnvFile());
        });
    }

    @Test
    public void test_0002_listStacks() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
           orchestrationService.getStacks(token);
        });
    }

    @Test
    public void test_0003_deleteStack(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String stackId= orchestrationService.getStacks(token).stacks.stream().filter(f->f.stack_name.equals(config.getStackName())).findFirst().get().id;
            orchestrationService.deleteStack(token,config.getStackName(), stackId);
        });
    }



}