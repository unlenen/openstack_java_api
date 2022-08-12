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
public class Project extends Entity {

    public Domain domain;
    public String domain_id;
    public String description;
    public boolean enabled;
    public String parent_id;
    public boolean is_domain;
    @JsonIgnore
    public List<Object> tags;
    @JsonIgnore
    public Options options;
    public Links links;
}
