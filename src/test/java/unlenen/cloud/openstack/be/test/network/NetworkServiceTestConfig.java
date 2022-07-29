package unlenen.cloud.openstack.be.test.network;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class NetworkServiceTestConfig {
    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.network_service.security_group.name}")
    String securityGroupName;

    @Value("${testData.network_service.security_group.id}")
    String securityGroupId;

    @Value("${testData.network_service.security_group.project_id}")
    String securityGroupProjectId;

    @Value("${testData.network_service.security_group.security_group_rules.id}")
    String securityGroupRulesId;

    @Value("${testData.network_service.security_group.security_group_rules.security_group_id}")
    String securityGroupRulesGroupId;
    
    @Value("${testData.network_service.security_group.security_group_rules.direction}")
    String securityGroupRulesDirection;

    @Value("${testData.network_service.security_group.security_group_rules.protocol}")
    String securityGroupRulesProtocol;

    @Value("${testData.network_service.network.id}")
    String networkId;

    @Value("${testData.network_service.network.name}")
    String networkName;

    @Value("${testData.network_service.network.shared}")
    boolean networkShared;

    @Value("${testData.network_service.network.router_external}")
    boolean networkRouterExternal;

    @Value("${testData.network_service.network.provider_network_type}")
    String networkProviderNetworkType;

    @Value("${testData.network_service.network.description}")
    String networkDescription;

    @Value("${testData.network_service.network.mtu}")
    Integer networkMtu;

    @Value("${testData.network_service.network.admin_state_up}")
    boolean networkAdminStateUp;

    @Value("${testData.network_service.network.port_security_enabled}")
    boolean networkPortSecurityEnabled;

    @Value("${testData.network_service.subnet.id}")
    String subnetId;
    
    @Value("${testData.network_service.subnet.name}")
    String subnetName;

    @Value("${testData.network_service.subnet.network_id}")
    String subnetNetworkId;

    @Value("${testData.network_service.subnet.ip_version}")
    int subnetIpVersion;

    @Value("${testData.network_service.subnet.cidr}")
    String subnetCidr;

    @Value("${testData.network_service.subnet.allocation_pool_start}")
    String subnetAllocationPoolStart;

    @Value("${testData.network_service.subnet.allocation_pool_end}")
    String subnetAllocationPoolEnd;

    @Value("${testData.network_service.subnet.gateaway_ip}")
    String subnetGateawayIp;

}
