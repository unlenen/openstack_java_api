package unlenen.cloud.openstack.be.test.compute;

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
public class ComputeServiceTestConfig {

    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.compute_service.domain.name}")
    String domainName;

    @Value("${testData.compute_service.domain.project.name}")
    String projectName;

    @Value("${testData.compute_service.flavor.name}")
    String flavorName;

    @Value("${testData.compute_service.flavor.vcpus}")
    Integer flavorVcpus;

    @Value("${testData.compute_service.flavor.ram}")
    Integer flavorRam;

    @Value("${testData.compute_service.flavor.disk}")
    Integer flavorDisk;

    @Value("${testData.compute_service.keypair.name}")
    String keypairName;

    @Value("${testData.compute_service.keypair.public_key}")
    String keypairPublic_Key;

    @Value("${testData.compute_service.keypair.user.name}")
    String keypairUserName;

    @Value("${testData.compute_service.quota.domain}")
    String quotaDomainName;

    @Value("${testData.compute_service.quota.project}")
    String quotaProjectName;

    
}
