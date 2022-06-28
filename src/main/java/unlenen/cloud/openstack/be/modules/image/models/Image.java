package unlenen.cloud.openstack.be.modules.image.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import unlenen.cloud.openstack.be.model.entities.Entity;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
public class Image extends Entity implements OpenStackResult {

    @JsonProperty("owner_specified.openstack.md5")
    public String ownerSpecifiedOpenstackMd5;

    @JsonProperty("owner_specified.openstack.sha256")
    public String ownerSpecifiedOpenstackSha256;

    @JsonProperty("owner_specified.openstack.object")
    public String ownerSpecifiedOpenstackObject;

    public String disk_format;
    public String container_format;
    public String visibility;
    public Object size;
    public Object virtual_size;
    public String status;
    public String checksum;
    @JsonProperty("protected")
    public boolean protectedImage;
    public int min_ram;
    public int min_disk;
    public String owner;
    public boolean os_hidden;
    public String os_hash_algo;
    public String os_hash_value;

    public Date created_at;
    public Date updated_at;
    public List<String> tags;
    public String self;
    public String file;
    public String schema;
    public String stores;
}
