package unlenen.cloud.openstack.be.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.modules.identity.models.Catalog;

/**
 *
 * @author Nebi
 */
@Configuration
@Getter
@Setter
public class OpenStackConfig {

    @Value("${openstack.keystone.url}")
    String identityURL;

    @Value("${openstack.keystone.system_user.domain}")
    String systemDomainName;

    @Value("${openstack.keystone.system_user.project}")
    String systemProjectName;

    @Value("${openstack.keystone.system_user.name}")
    String systemUserName;

    @Value("${openstack.keystone.system_user.password}")
    String systemPassword;

    String systemTokenId;

    Map<OpenStackModule, String> serviceURLs = new ConcurrentHashMap<>();

    @Bean
    public ObjectMapper getObjectMapper() {
        //return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new ObjectMapper();
    }

    public void loadServiceURLs(List<Catalog> catalogs) {
        catalogs.stream().filter(c -> OpenStackModule.has(c.type)).forEach(c -> {
            OpenStackModule openStackModule = OpenStackModule.valueOf(c.type);
            c.endpoints.stream().filter(e -> "public".equals(e.interfaceType)).forEach(e -> {
                serviceURLs.put(openStackModule, e.url);
            });
        });
    }

    public String getServiceURL(OpenStackModule module) throws Exception {
        return serviceURLs.get(module);
    }
}
