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
package unlenen.cloud.openstack.be.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.modules.identity.models.Catalog;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
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
