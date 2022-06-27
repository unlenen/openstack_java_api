package unlenen.cloud.openstack.be.constant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class Parameter {

    String key;
    String value;
    ParameterType parameterType;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
        this.parameterType = ParameterType.REQUEST;
    }

    public Parameter(String key, String value, ParameterType parameterType) {
        this.key = key;
        this.value = value;
        this.parameterType = parameterType;
    }

}
