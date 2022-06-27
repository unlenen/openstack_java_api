package unlenen.cloud.openstack.be.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Nebi
 */
@Configuration
@Getter
@Setter
public class IdentityServiceTestConfig {

    @Value("${testData.identity_service.domain.name}")
    String domainName;

    @Value("${testData.identity_service.domain.project.name}")
    String projectName;

    @Value("${testData.identity_service.domain.users.test1.name}")
    String userName;

    @Value("${testData.identity_service.domain.users.test1.password}")
    String password;

    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;
}
