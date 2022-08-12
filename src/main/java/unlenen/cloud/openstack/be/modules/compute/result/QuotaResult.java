package unlenen.cloud.openstack.be.modules.compute.result;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;
import unlenen.cloud.openstack.be.modules.compute.models.Quota;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuotaResult implements OpenStackResult {
    @JsonAlias("quota_set")
    public Quota quota;

}