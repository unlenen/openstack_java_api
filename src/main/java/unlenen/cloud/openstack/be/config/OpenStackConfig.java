package unlenen.cloud.openstack.be.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Nebi
 */
@Configuration
@Getter
public class OpenStackConfig {

    @Value("${openstack.keystone.url}")
    String keystoneURL;

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
