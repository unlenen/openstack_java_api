package unlenen.cloud.openstack.be.modules.image.models;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Nebi
 */
@Getter
@ToString
public enum ImageDiskFormat {
    ami, ari, aki, vhd, vhdx, vmdk, raw, qcow2, vdi, ploop, iso;
}
