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
import unlenen.cloud.openstack.be.modules.network.models.Router;
import unlenen.cloud.openstack.be.modules.network.models.RouterRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRule;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.models.Subnet;
import unlenen.cloud.openstack.be.modules.network.models.SubnetRoot;
import unlenen.cloud.openstack.be.modules.network.result.NetworkCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRuleCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SubnetCreateResult;
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
    public void test_0001_createSecurityGroup() {
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
    public void test_0002_createSecurityGroupRule() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SecurityGroupRuleRoot securityGroupRuleRoot = new SecurityGroupRuleRoot();
            SecurityGroupRule securityGroupRule = new SecurityGroupRule();
            securityGroupRule.setDirection(config.getSecurityGroupRulesDirection());
            securityGroupRule.setProtocol(config.getSecurityGroupRulesProtocol());
            securityGroupRule.setPort_range_min(config.getSecurityGroupRulesPortRangeMin());
            securityGroupRule.setPort_range_max(config.getSecurityGroupRulesPortRangeMin());
            String securityGroupId = networkService.getSecurityGroups(token).security_groups.stream()
                    .filter(f -> f.name.equals(config.getSecurityGroupName())).findFirst().get().id;
            securityGroupRule.setSecurity_group_id(securityGroupId);
            securityGroupRuleRoot.security_group_rule = securityGroupRule;
            networkService.createSecurityGroupRules(token, securityGroupRuleRoot);
        });
    }

    @Test
    public void test_0003_listSecurityGroupRules() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getSecurityGroupRules(token) != null;
        });
    }

    @Test
    public void test_0004_listSecurityGroups() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getSecurityGroups(token) != null;
        });
    }

    @Test
    public void test_0005_deleteSecurityGroupRule() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String securityGroupRuleId = networkService.getSecurityGroupRules(token).securityGroupRules.stream().filter(
                    f -> f.direction.equals(config.getSecurityGroupRulesDirection()) &&
                            (f.protocol != null && f.protocol.equals(config.getSecurityGroupRulesProtocol())) &&
                            f.port_range_min == (config.getSecurityGroupRulesPortRangeMin()) &&
                            f.port_range_max == (config.getSecurityGroupRulesPortRangeMax()))
                    .findFirst().get().id;

            networkService.deleteSecurityGroupRule(token, securityGroupRuleId);
        });
    }

    @Test
    public void test_0006_deleteSecurityGroup() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String securityGroupId = networkService.getSecurityGroups(token).security_groups.stream()
                    .filter(f -> f.name.equals(config.getSecurityGroupName())).findFirst().get().id;
            networkService.deleteSecurityGroup(token, securityGroupId);
        });
    }

    @Test
    public void test_0011_createNetwork() {
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
    public void test_0012_createSubnet() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            SubnetRoot subnetRoot = new SubnetRoot();
            Subnet subnet = new Subnet();
            subnet.setName(config.getSubnetName());
            String networkId = networkService.getNetworks(token).networks.stream()
                    .filter(f -> f.name.equals(config.getNetworkName())).findFirst().get().id;
            subnet.setNetwork_id(networkId);
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
            networkService.createSubnet(token, subnetRoot);
        });
    }

    @Test
    public void test_0013_listNetworks() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getNetworks(token) != null;
        });
    }

    @Test
    public void test_0014_listSubnets() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getSubnets(token) != null;
        });
    }

    @Test
    public void test_0015_deleteSubnet() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String subnetId=networkService.getSubnets(token).subnets.stream().filter(f->f.name.equals(config.getSubnetName())).findFirst().get().id;
            networkService.deleteSubnet(token, subnetId);
        });
    }

    @Test
    public void test_0016_deleteNetwork() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String networkId = networkService.getNetworks(token).networks.stream()
            .filter(f -> f.name.equals(config.getNetworkName())).findFirst().get().id;
            networkService.deleteNetwork(token, networkId);
        });
    }

    @Test
    public void test_0021_createFloatingIp() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String floating_network_id = config.getFloating_network_id();
            String floating_ip_address = config.getFloating_ip_address();
            networkService.createFloatingip(token, floating_network_id, floating_ip_address);
        });
    }

    @Test
    public void test_0022_listFloatingIps() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert networkService.getFloatingIps(token)!=null;
        });
    }

    @Test
    public void test_0023_deleteFloatingIp() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String floatingipId= networkService.getFloatingIps(token).floatingips.stream().filter(f->f.floating_ip_address.equals(config.getFloating_ip_address())).findFirst().get().getId();
            networkService.deleteFloatingip(token,floatingipId);
        });
    }

    @Test
    public void test_0031_createRouter() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            RouterRoot routerRoot=new RouterRoot();
            Router router= new Router();
            router.setName(config.getRouterName());
            routerRoot.router=router;
            networkService.createRouter(token, routerRoot);
        });
    }

    @Test
    public void test_0032_listRouters() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            networkService.getRouters(token);
        });
    }

    @Test
    public void test_0033_deleteRouter() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String router_id= networkService.getRouters(token).routers.stream().filter(r->r.getName().equals(config.getRouterName())).findFirst().get().getId();
            networkService.deleteRouter(token, router_id);
        });
    }
    @Test
    public void test_0034_addRouterInterface() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String router_id = "ed78c9f4-467c-41ca-a900-70bb32338dcd";
            String subnet_id="04ab1c68-450c-4d6a-b42b-b2aa6975936b";
            networkService.addRouterInterface(token, router_id, subnet_id);
        });
    }

}
