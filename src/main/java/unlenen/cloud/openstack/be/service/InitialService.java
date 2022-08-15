/*
# Copyright © 2022 Nebi Volkan UNLENEN
#
# Licensed under the GNU Affero General Public License v3.0
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://github.com/unlenen/openstack_java_api/blob/master/LICENSE
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */
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
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
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
