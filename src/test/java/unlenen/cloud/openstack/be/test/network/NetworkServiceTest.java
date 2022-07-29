package unlenen.cloud.openstack.be.test.network;

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
import unlenen.cloud.openstack.be.modules.network.models.AllocationPool;
import unlenen.cloud.openstack.be.modules.network.models.Network;
import unlenen.cloud.openstack.be.modules.network.models.NetworkRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.models.Subnet;
import unlenen.cloud.openstack.be.modules.network.models.SubnetRoot;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRulesResult;
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
    public void test_0001_createSecurityGroupRule() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRuleRoot securityGroupRuleRoot = new SecurityGroupRuleRoot();
            SecurityGroupRule securityGroupRule = new SecurityGroupRule();
            securityGroupRule.setDirection(config.getSecurityGroupRulesDirection());
            securityGroupRule.setProtocol(config.getSecurityGroupRulesProtocol());
            securityGroupRule.setPort_range_min(80);
            securityGroupRule.setPort_range_max(80);
            securityGroupRule.setSecurity_group_id(config.getSecurityGroupRulesGroupId());
            securityGroupRuleRoot.security_group_rule = securityGroupRule;
            networkService.createSecurityGroupRules(token, securityGroupRuleRoot);
        });
    }

    @Test
    public void test_0002_listSecurityGroupRules() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRulesResult securityGroupRulesResult = networkService.getSecurityGroupRules(token);
            SecurityGroupRule securityGroupRule = securityGroupRulesResult.securityGroupRules.stream()
                    .filter(s -> s.security_group_id.equals(config.getSecurityGroupRulesGroupId())).findFirst().get();
            assert securityGroupRule.security_group_id.equals(config.getSecurityGroupRulesGroupId());
        });
    }

    @Test
    public void test_0003_deleteSecurityGroupRule() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            networkService.deleteSecurityGroupRule(token, config.getSecurityGroupRulesId());
        });
    }

    @Test
    public void test_0011_createSecurityGroup() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRoot securityGroupRoot = new SecurityGroupRoot();
            SecurityGroup securityGroup = new SecurityGroup();
            securityGroup.setName(config.getSecurityGroupName());
            securityGroup.setProject_id(config.getSecurityGroupProjectId());
            securityGroup.setTenant_id(config.getSecurityGroupProjectId());
            securityGroupRoot.security_group = securityGroup;
            networkService.createSecurityGroup(token, securityGroupRoot);
        });
    }

    @Test
    public void test_0012_listSecurityGroups() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getSecurityGroups(token) != null;
        });
    }

    @Test
    public void test_0013_deleteSecurityGroup() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            networkService.deleteSecurityGroup(token, config.getSecurityGroupId());
        });
    }

    @Test
    public void test_0021_createNetwork() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            NetworkRoot networkRoot = new NetworkRoot();
            Network network = new Network();
            network.setName(config.getNetworkName());
            network.setAdmin_state_up(config.isNetworkAdminStateUp());
            network.setPort_security_enabled(config.isNetworkPortSecurityEnabled());
            network.setShared(config.isNetworkShared());
            network.setRouterExternal(config.isNetworkRouterExternal());
            network.setProviderNetworkType(config.getNetworkProviderNetworkType());
            network.setDescription(config.getNetworkDescription());
            network.setMtu(config.getNetworkMtu());
            networkRoot.network = network;
            networkService.createNetwork(token, networkRoot);
        });
    }

    @Test
    public void test_0022_listNetworks() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getNetworks(token) != null;
        });
    }

    @Test
    public void test_0023_deleteNetwork() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            networkService.deleteNetwork(token, config.getNetworkId());
        });
    }

    @Test
    public void test_0031_createSubnet() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SubnetRoot subnetRoot = new SubnetRoot();
            Subnet subnet = new Subnet();
            subnet.setName(config.getSubnetName());
            subnet.setNetwork_id(config.getSubnetNetworkId());
            subnet.setCidr(config.getSubnetCidr());
            subnet.setIp_version(config.getSubnetIpVersion());

            AllocationPool allocationPool = new AllocationPool();
            allocationPool.start = config.getSubnetAllocationPoolStart();
            allocationPool.end = config.getSubnetAllocationPoolEnd();
            ArrayList<AllocationPool> allocationPools = new ArrayList<AllocationPool>();
            allocationPools.add(allocationPool);
            subnet.setAllocation_pools(allocationPools);

            subnet.setGateway_ip(config.getSubnetGateawayIp());

            subnetRoot.subnet = subnet;
            assert networkService.createSubnet(token, subnetRoot) != null;

        });
    }

    @Test
    public void test_0032_listSubnets() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getSubnets(token) != null;
        });
    }

    @Test
    public void test_0033_deleteSubnet() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            networkService.deleteSubnet(token, config.getSubnetId());
        });
    }
}