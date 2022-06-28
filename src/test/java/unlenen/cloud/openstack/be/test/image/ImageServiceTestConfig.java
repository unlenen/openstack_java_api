package unlenen.cloud.openstack.be.test.image;

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
public class ImageServiceTestConfig {

    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.image_service.domain.name}")
    String domainName;

    @Value("${testData.image_service.domain.project.name}")
    String projectName;

    @Value("${testData.image_service.image.name}")
    String imageName;

}
