package unlenen.cloud.openstack.be.test.volume;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class VolumeServiceTestConfig {
    @Value("${testData.system_user.domain}")
    String systemDomainName;

    @Value("${testData.system_user.project}")
    String systemProjectName;

    @Value("${testData.system_user.name}")
    String systemUserName;

    @Value("${testData.system_user.password}")
    String systemPassword;

    @Value("${testData.volume_service.volume.name}")
    String volumeName;

    @Value("${testData.volume_service.volume.bootable}")
    String volumeBootable;

    @Value("${testData.volume_service.volume.imageRef}")
    String volumeImageRef;

    @Value("${testData.volume_service.volume.size}")
    int volumeSize;
}