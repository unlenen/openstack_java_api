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
package unlenen.cloud.openstack.be.test.image;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

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
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;
import unlenen.cloud.openstack.be.modules.image.constant.ImageVisibility;
import unlenen.cloud.openstack.be.modules.image.models.Image;
import unlenen.cloud.openstack.be.modules.image.models.ImageContainerFormat;
import unlenen.cloud.openstack.be.modules.image.models.ImageDiskFormat;
import unlenen.cloud.openstack.be.modules.image.result.ImageResult;
import unlenen.cloud.openstack.be.modules.image.service.ImageService;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ImageServiceTest {

    @Autowired
    ImageService imageService;
    @Autowired
    IdentityService identityService;

    @Autowired
    ImageServiceTestConfig config;

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
    public void test_0001_createImage() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            Image imageCreateResult = imageService.createImage(token, config.getImageName(), ImageDiskFormat.qcow2, ImageContainerFormat.bare, ImageVisibility.PUBLIC);
            assert imageCreateResult.id != null;
        });
    }

    @Test
    public void test_0002_listImages() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            ImageResult imageList = imageService.getImages(token, config.getImageName(), "");
            assert !imageList.images.isEmpty();
        });
    }

    @Test
    public void test_0011_addImageTag(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String image_id = imageService.getImages(token, config.getImageName(), "").images.stream().filter(f -> f.name.equals(config.getImageName())).findFirst().get().id;
            
            imageService.addImageTag(token, image_id, config.getImageTag());
        });
    }


    @Test
    public void test_0012_deleteImageTag(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String image_id = imageService.getImages(token, config.getImageName(), "").images.stream().filter(f -> f.name.equals(config.getImageName())).findFirst().get().id;
            
            imageService.deleteImageTag(token, image_id, config.getImageTag());
        });
    }

    @Test
    public void test_0003_deleteImage() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            List<Image> images = imageService.getImages(token, config.getImageName(), "").images.stream().filter(f -> f.name.equals(config.getImageName())).toList();
            for (Image image : images) {
                imageService.deleteImage(token, image.id);
            }

        });
    }

    @Test
    public void test_0021_uploadImageData(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String image_id = imageService.getImages(token, config.getImageName(), "").images.stream().filter(f -> f.name.equals(config.getImageName())).findFirst().get().id;
            String filepath="/home/argela/argela/test/cirros-0.5.2-x86_64-disk.img";
            imageService.uploadImageData(token,image_id,filepath);
        });
    }


    @Test
    public void test_0022_downloadImageData(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            String image_id = imageService.getImages(token, config.getImageName(), "").images.stream().filter(f -> f.name.equals(config.getImageName())).findFirst().get().id;
            String filePath= "/home/argela/argela/test/downloads/cirros.img";
            imageService.downloadImageData(token,image_id,filePath);
        });
    }
}
