package unlenen.cloud.openstack.be.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(InitialService.class);

    @Autowired
    IdentityService identityService;

    @Autowired
    OpenStackConfig config;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            log.warn("[Init] Connecting to Openstack for loading catalogs");
            LoginResult loginResult = identityService.login(config.getSystemDomainName(), config.getSystemProjectName(), config.getSystemUserName(), config.getSystemPassword());
            config.setSystemTokenId(loginResult.getId());
            config.loadServiceURLs(loginResult.getToken().catalog);
            log.warn("[Init] Catalogs loaded successfully");
        } catch (Exception ex) {
            log.error("[OpenStackAccessFailed] Can not connect to openstack via url : " + config.getIdentityURL() + " , System exits");
            System.exit(1);
        }
    }

}
