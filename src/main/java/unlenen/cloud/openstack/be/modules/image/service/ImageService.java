package unlenen.cloud.openstack.be.modules.image.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.modules.image.models.Image;
import unlenen.cloud.openstack.be.modules.image.models.ImageContainerFormat;
import unlenen.cloud.openstack.be.modules.image.models.ImageDiskFormat;
import unlenen.cloud.openstack.be.modules.image.result.ImageResult;
import unlenen.cloud.openstack.be.service.CommonService;

/**
 *
 * @author Nebi
 */
@Service
public class ImageService extends CommonService {

        @Autowired
        OpenStackConfig openStackConfig;

        @Call(type = HttpMethod.GET, url = "/v2/images", statusCode = HttpStatus.OK, openstackResult = ImageResult.class)
        public ImageResult getImages(String token, String name, String tags) throws Exception {
                return (ImageResult) callWithResult(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("name", name),
                                                new Parameter("tags", tags), });
        }

        @Call(type = HttpMethod.POST, url = "/v2/images", bodyFile = "payloads/image/image_create.json", statusCode = HttpStatus.CREATED, openstackResult = Image.class)
        public Image createImage(String token, String name, ImageDiskFormat diskFormat,
                        ImageContainerFormat containerFormat) throws Exception {
                return (Image) callWithResult(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("NAME", name, ParameterType.JSON),
                                                new Parameter("DISK_FORMAT", diskFormat.name(), ParameterType.JSON),
                                                new Parameter("CONTAINER_FORMAT", containerFormat.name(),
                                                                ParameterType.JSON), });
        }

        @Call(type = HttpMethod.DELETE, url = "/v2/images/{image_id}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteImage(String token, String imageId) throws Exception {
                call(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("image_id", imageId, ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.PUT, url = "/v2/images/{image_id}/tags/{tag}", statusCode = HttpStatus.NO_CONTENT)
        public void addImageTag(String token, String image_id, String tag) throws Exception {
                call(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("image_id", image_id, ParameterType.URI),
                                                new Parameter("tag", tag, ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.DELETE, url = "/v2/images/{image_id}/tags/{tag}", statusCode = HttpStatus.NO_CONTENT)
        public void deleteImageTag(String token, String image_id, String tag) throws Exception {
                call(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("image_id", image_id, ParameterType.URI),
                                                new Parameter("tag", tag, ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.PUT, url = "/v2/images/{image_id}/file", mediaType = "application/octet-stream", statusCode = HttpStatus.NO_CONTENT)
        public void uploadImageData(String token, String image_id, String filePath) throws Exception {
                call(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("image_id", image_id, ParameterType.URI),
                                                new Parameter("", filePath,
                                                                ParameterType.FILE)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2/images/{image_id}/file", mediaType = "application/octet-stream", isDownload = true, statusCode = HttpStatus.OK)
        public void downloadImageData(String token, String image_id, String filePath) throws Exception {
                call(getServiceURL(token, OpenStackModule.image),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("image_id", image_id, ParameterType.URI),
                                                new Parameter("filePath", filePath,ParameterType.FILE)
                                });
        }

        public String getImageUrl(String token, String image_id) throws Exception{
                String serviceUrl=getServiceURL(token, OpenStackModule.image);
                String url = "/v2/images/{image_id}/file".replace("{image_id}", image_id);
                return serviceUrl+url;
        }

}
