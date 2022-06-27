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
public class OpenstackBeTestConfig {

    @Value("${testData.domain.name}")
    String domainName;

    @Value("${testData.domain.project.name}")
    String projectName;

    @Value("${testData.domain.users.test1.name}")
    String userName;

    @Value("${testData.domain.users.test1.password}")
    String password;

    @Value("${testData.domain.users.system.domain}")
    String systemDomainName;

    @Value("${testData.domain.users.system.project}")
    String systemProjectName;

    @Value("${testData.domain.users.system.name}")
    String systemUserName;

    @Value("${testData.domain.users.system.password}")
    String systemPassword;
}
