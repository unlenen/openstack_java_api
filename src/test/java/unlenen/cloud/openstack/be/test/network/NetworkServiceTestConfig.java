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

    @Value("${testData.network_service.security_group.name:}")
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
}
