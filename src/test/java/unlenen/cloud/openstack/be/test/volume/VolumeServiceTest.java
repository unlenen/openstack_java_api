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
package unlenen.cloud.openstack.be.test.volume;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import unlenen.cloud.openstack.be.Application;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;
import unlenen.cloud.openstack.be.modules.volume.service.VolumeService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class VolumeServiceTest {

    @Autowired
    IdentityService identityService;

    @Autowired
    VolumeService volumeService;

    @Autowired
    VolumeServiceTestConfig config;

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
    public void test_0001_createVolume() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            int size= config.getVolumeSize();
            String name= config.getVolumeName();
            String bootable= config.getVolumeBootable();
            String imageRef= config.getVolumeImageRef();
            assert volumeService.createVolume(token,name,bootable,imageRef,size) != null;
        });
    }

    @Test
    public void test_0002_listVolumes() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            assert volumeService.getVolumes(token) != null;
        });
    }

    @Test
    public void test_0003_deleteVolume() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String volume_id= volumeService.getVolumes(token).volumes.stream().filter(v-> v.getName().equals(config.getVolumeName())).findFirst().get().getId();
            volumeService.deleteVolume(token, volume_id);
        });
    }

    @Test
    public void test_0011_extendVolumeSize(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String volume_id= volumeService.getVolumes(token).volumes.stream().filter(v-> v.getName().equals(config.getVolumeName())).findFirst().get().getId();
            int new_size=config.getVolumeNewSize();
            volumeService.extendVolumeSize(token, volume_id, new_size);
        });
    }

    @Test
    public void test_0012_attachVolumetoServer(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String volume_id= volumeService.getVolumes(token).volumes.stream().filter(v-> v.getName().equals(config.getVolumeName())).findFirst().get().getId();
            String instance_uuid=config.getVolumeInstanceUuid();
            String mountpoint=config.getVolumeMountpoint();
            volumeService.attachVolumetoServer(token, volume_id, instance_uuid, mountpoint);
        });
    }

    @Test
    public void test_0013_detachVolumefromServer(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String volume_id= volumeService.getVolumes(token).volumes.stream().filter(v-> v.getName().equals(config.getVolumeName())).findFirst().get().getId();
            volumeService.detachVolumefromServer(token, volume_id, null);
        });
    }
}