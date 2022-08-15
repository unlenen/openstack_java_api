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
package unlenen.cloud.openstack.be.test.identity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import unlenen.cloud.openstack.be.Application;
import unlenen.cloud.openstack.be.modules.identity.result.DomainCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.DomainResult;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectResult;
import unlenen.cloud.openstack.be.modules.identity.result.UserCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.UserResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class IdentityServiceTest {

    @Autowired
    IdentityService identityService;

    @Autowired
    IdentityServiceTestConfig config;

    private String createSystemToken() throws Exception {

        LoginResult loginResult = identityService.login(
                config.getSystemDomainName(),
                config.getSystemProjectName(),
                config.getSystemUserName(),
                config.getSystemPassword());
        assert loginResult.getId() != null;
        return loginResult.getId();
    }

    @Test
    public void test_001_login() {
        assertDoesNotThrow(() -> {
            createSystemToken();
        });
    }

    @Test
    public void test_002_createDomain() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainCreateResult domainCreateResult = identityService.createDomain(token, config.getDomainName(), "");
            assert domainCreateResult.domain.id != null;
        });
    }

    @Test
    public void test_003_createUser() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            UserCreateResult userCreateResult = identityService.createUser(token, config.getUserName(),
                    config.getPassword(), "test@test.com", "", domainResult.domains.get(0).id);
            assert userCreateResult.user.id != null;
        });
    }

    @Test
    public void test_004_createProject() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectCreateResult projectCreateResult = identityService.createProject(
                    token,
                    config.getProjectName(),
                    "",
                    domainResult.domains.get(0).id);
            assert projectCreateResult.project.id != null;
        });
    }

    @Test
    public void test_005_listDomains() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            assert domainResult.domains.get(0).id != null;
        });
    }

    @Test
    public void test_006_listProject() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getProjectName());
            assert projectResult.projects.get(0).id != null;
        });
    }

    @Test
    public void test_007_listUser() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            UserResult userResult = identityService.getUsers(token, domainResult.domains.get(0).id,
                    config.getUserName(), "");
            assert userResult.users.get(0).id != null;
        });
    }

    @Test
    public void test_008_deleteProject() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getProjectName());
            identityService.deleteProject(token, projectResult.projects.get(0).id);
        });
    }

    @Test
    public void test_009_deleteUser() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            UserResult userResult = identityService.getUsers(token, domainResult.domains.get(0).id,
                    config.getUserName(), "");
            identityService.deleteUser(token, userResult.users.get(0).id);
        });
    }

    @Test
    public void test_010_deleteDomain() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            identityService.updateDomain(token, domainResult.domains.get(0).id, "false", "");
            identityService.deleteDomain(token, domainResult.domains.get(0).id);
        });
    }

    @Test
    public void test_011_getTokenInformation() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            identityService.getToken(token,token);
        });
    }
}
