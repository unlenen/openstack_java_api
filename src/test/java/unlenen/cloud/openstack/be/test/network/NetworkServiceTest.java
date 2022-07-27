package unlenen.cloud.openstack.be.test.network;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRuleResult;
import unlenen.cloud.openstack.be.modules.network.service.NetworkService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class NetworkServiceTest {
    @Autowired
    IdentityService identityService;

    @Autowired
    NetworkService networkService;
    
    @Autowired
    NetworkServiceTestConfig config;

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
    public void test_0001_createSecurityGroupRule(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRuleRoot securityGroupRuleRoot= new SecurityGroupRuleRoot();
            SecurityGroupRule securityGroupRule = new SecurityGroupRule();
            securityGroupRule.setDirection(config.getSecurityGroupRulesDirection());
            securityGroupRule.setProtocol(config.getSecurityGroupRulesProtocol());
            securityGroupRule.setPort_range_min(80);
            securityGroupRule.setPort_range_max(80);
            securityGroupRule.setSecurity_group_id(config.getSecurityGroupRulesGroupId());
            securityGroupRuleRoot.security_group_rule=securityGroupRule;
            networkService.createSecurityGroupRule(token, securityGroupRuleRoot);
        });
    }

    @Test
    public void test_0002_listSecurityGroupRules() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRuleResult securityGroupRuleResult = networkService.getSecurityGroupRules(token);
            SecurityGroupRule securityGroupRule =securityGroupRuleResult.securityGroupRules.stream().filter(s -> s.security_group_id.equals(config.getSecurityGroupRulesGroupId())).findFirst().get();
            assert securityGroupRule.security_group_id.equals(config.getSecurityGroupRulesGroupId());
        });
    }
}
