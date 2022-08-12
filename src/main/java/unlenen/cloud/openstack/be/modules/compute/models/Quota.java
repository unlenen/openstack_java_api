package unlenen.cloud.openstack.be.modules.compute.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Quota {
    public String id;
    public Integer cores;
    public Integer instances;
    public Integer key_pairs;
    public Integer metadata_items;
    public Integer ram;
    public Integer server_groups;
    public Integer server_group_members;
    public Integer fixed_ips;
    public Integer floating_ips;
    public Integer networks;
    public Integer security_group_rules;
    public Integer security_groups;
    public Integer injected_file_content_bytes;
    public Integer injected_file_path_bytes;
    public Integer injected_files;
}
