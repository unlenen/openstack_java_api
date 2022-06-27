package unlenen.cloud.openstack.be.modules.compute.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.boot.context.config.ConfigData.Options;
import unlenen.cloud.openstack.be.model.entities.Entity;

/**
 *
 * @author Nebi
 */
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
