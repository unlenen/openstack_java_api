package unlenen.cloud.openstack.be.modules.volume.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.modules.volume.result.VolumeResult;
import unlenen.cloud.openstack.be.service.CommonService;

@Service
public class VolumeService extends CommonService {
    @Autowired
    OpenStackConfig openStackConfig;

    @Call(type = HttpMethod.GET, url = "/volumes", statusCode = HttpStatus.OK,openstackResult = VolumeResult.class)
    public VolumeResult getVolumes(String token) throws Exception {
        return (VolumeResult)callWithResult(getServiceURL(token, OpenStackModule.volumev3),
                new Parameter[] {
                        new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                },
                new Parameter[0]);
    }

}
