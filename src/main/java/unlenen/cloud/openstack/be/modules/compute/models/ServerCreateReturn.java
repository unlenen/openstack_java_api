package unlenen.cloud.openstack.be.modules.compute.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerCreateReturn {
    public String id;
    public ArrayList<Link> links;
    @JsonProperty("OS-DCF:diskConfig")
    public String oSDCFDiskConfig;
    public ArrayList<SecurityGroup> security_groups;
    public String adminPass;
}