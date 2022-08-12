package unlenen.cloud.openstack.be.modules.identity.models;

import java.util.List;

import org.springframework.boot.context.config.ConfigData.Options;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
@Getter
@Setter
@ToString
public class Domain extends Entity {

    public String description;
    public boolean enabled;
    public List<Object> tags;
    @JsonIgnore
    public Options options;
    public Links links;
}
