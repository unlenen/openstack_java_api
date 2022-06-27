package unlenen.cloud.openstack.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;

/**
 *
 * @author Nebi
 */
@Service
public class InitialService {

    @Autowired
    IdentityService identityService;

    @Autowired
    OpenStackConfig config;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            LoginResult loginResult = identityService.login(config.getSystemDomainName(), config.getSystemProjectName(), config.getSystemUserName(), config.getSystemPassword());
            config.setSystemTokenId(loginResult.getId());
            config.loadServiceURLs(loginResult.getToken().catalog);
        } catch (Exception ex) {
            System.exit(1);
        }
    }

}
