package unlenen.cloud.openstack.be.modules.volume;

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
import unlenen.cloud.openstack.be.service.CommonService;

@Service
public class VolumeService extends CommonService{
    @Autowired
    OpenStackConfig openStackConfig;

    @Call(type = HttpMethod.GET, url = "/volumes", statusCode = HttpStatus.OK)
    public void getVolumes(String token) throws Exception {
            callWithResult(getServiceURL(token, OpenStackModule.volumev3),
                            new Parameter[] {
                                            new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                            },
                            new Parameter[0]);
    }


}
