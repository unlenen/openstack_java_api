package unlenen.cloud.openstack.be.modules.volume.models;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import unlenen.cloud.openstack.be.model.request.OpenStackRequest;

@Getter
@Setter
@ToString
public class Volume implements OpenStackRequest {

    @JsonInclude(Include.NON_NULL)
    public ArrayList<String> attachments;

    @JsonInclude(Include.NON_NULL)
    public String availability_zone;

    @JsonInclude(Include.NON_NULL)
    public String bootable;

    @JsonInclude(Include.NON_NULL)
    public String consistencygroup_id;

    @JsonInclude(Include.NON_NULL)
    public Date created_at;

    @JsonInclude(Include.NON_NULL)
    public String description;

    @JsonInclude(Include.NON_NULL)
    public boolean encrypted;

    @JsonInclude(Include.NON_NULL)
    public String id;

    @JsonInclude(Include.NON_NULL)
    public ArrayList<Link> links;

    @JsonInclude(Include.NON_NULL)
    public Metadata metadata = null;

    @JsonInclude(Include.NON_NULL)
    public String migration_status;

    @JsonInclude(Include.NON_NULL)
    public boolean multiattach;

    @JsonInclude(Include.NON_NULL)
    public String name;

    @JsonInclude(Include.NON_NULL)
    public String replication_status;

    @JsonInclude(Include.NON_NULL)
    public Integer size;

    @JsonInclude(Include.NON_NULL)
    public String snapshot_id;

    @JsonInclude(Include.NON_NULL)
    public String source_volid;

    @JsonInclude(Include.NON_NULL)
    public String imageRef;

    @JsonInclude(Include.NON_NULL)
    public String status;

    @JsonInclude(Include.NON_NULL)
    public String updated_at;

    @JsonInclude(Include.NON_NULL)
    public String user_id;

    @JsonInclude(Include.NON_NULL)
    public String volume_type;

    @JsonInclude(Include.NON_NULL)
    public String group_id;

    @JsonInclude(Include.NON_NULL)
    public String provider_id;

    @JsonInclude(Include.NON_NULL)
    public String service_uuid;

    @JsonInclude(Include.NON_NULL)
    public boolean shared_targets;

    @JsonInclude(Include.NON_NULL)
    public String cluster_name;

    @JsonInclude(Include.NON_NULL)
    public String volume_type_id;

    @JsonInclude(Include.NON_NULL)
    public boolean consumes_quota;
}
