package unlenen.cloud.openstack.be.test.orchestration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class OrchestrationServiceTestConfig {
    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.orchestration_service.stack.name}")
    String stackName;

    @Value("${testData.orchestration_service.stack.templateFile}")
    String stackTemplateFile;

    @Value("${testData.orchestration_service.stack.envFile}")
    String stackEnvFile;
}
