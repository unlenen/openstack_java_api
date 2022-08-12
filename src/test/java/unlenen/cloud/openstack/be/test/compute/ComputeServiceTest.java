package unlenen.cloud.openstack.be.test.compute;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import unlenen.cloud.openstack.be.Application;
import unlenen.cloud.openstack.be.modules.compute.models.Flavor;
import unlenen.cloud.openstack.be.modules.compute.models.FlavorCreateRequest;
import unlenen.cloud.openstack.be.modules.compute.models.Keypair;
import unlenen.cloud.openstack.be.modules.compute.models.KeypairData;
import unlenen.cloud.openstack.be.modules.compute.models.Quota;
import unlenen.cloud.openstack.be.modules.compute.models.Server;
import unlenen.cloud.openstack.be.modules.compute.models.ServerCreate;
import unlenen.cloud.openstack.be.modules.compute.models.ServerCreateRequest;
import unlenen.cloud.openstack.be.modules.compute.result.FlavorCreateResult;
import unlenen.cloud.openstack.be.modules.compute.result.FlavorResult;
import unlenen.cloud.openstack.be.modules.compute.result.KeypairCreateResult;
import unlenen.cloud.openstack.be.modules.compute.result.KeypairResult;
import unlenen.cloud.openstack.be.modules.compute.result.QuotaResult;
import unlenen.cloud.openstack.be.modules.compute.result.ServerResult;
import unlenen.cloud.openstack.be.modules.compute.service.ComputeService;
import unlenen.cloud.openstack.be.modules.identity.models.Project;
import unlenen.cloud.openstack.be.modules.identity.models.User;
import unlenen.cloud.openstack.be.modules.identity.result.DomainResult;
import unlenen.cloud.openstack.be.modules.identity.result.LoginResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectCreateResult;
import unlenen.cloud.openstack.be.modules.identity.result.ProjectResult;
import unlenen.cloud.openstack.be.modules.identity.result.UserResult;
import unlenen.cloud.openstack.be.modules.identity.service.IdentityService;


/**
 *
 * @author Nebi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application.yaml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ComputeServiceTest {

    @Autowired
    ComputeService computeService;
    @Autowired
    IdentityService identityService;

    @Autowired
    ComputeServiceTestConfig config;

    private String createSystemToken() throws Exception {

        LoginResult loginResult = identityService.login(
                config.getSystemDomainName(),
                config.getSystemProjectName(),
                config.getSystemUserName(),
                config.getSystemPassword());
        assert loginResult.getId() != null;
        return loginResult.getId();
    }

    @Test
    public void test_0001_createFlavor() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            JSONObject root = new JSONObject();
            JSONObject flavor= new JSONObject();
            root.put("flavor", flavor);
            flavor.put("name", config.getFlavorName());
            flavor.put("vcpus", config.getFlavorVcpus());
            flavor.put("ram", config.getFlavorRam());
            flavor.put("disk", config.getFlavorDisk());

            ObjectMapper om = new ObjectMapper();
            String myJsonString=root.toString();
            FlavorCreateRequest flavorCreateRequest = om.readValue(myJsonString, FlavorCreateRequest.class);

            FlavorCreateResult flavorCreateResult = computeService.createFlavor(token,flavorCreateRequest);
            assert flavorCreateResult.flavor.id != null;
        });
    }

    @Test
    public void test_0002_listFlavors() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            FlavorResult flavorList = computeService.getFlavors(token);
            assert !flavorList.flavors.isEmpty();
        });
    }

    @Test
    public void test_0003_deleteFlavor() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            Flavor flavor = computeService.getFlavors(token).flavors.stream()
                    .filter(f -> f.name.equals(config.getFlavorName())).findFirst().get();
            computeService.deleteFlavor(token, flavor.id);
        });
    }

    @Test
    void test_0021_createKeypairs() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            KeypairCreateResult keypairCreateResult = computeService.createKeypair(
                    token,
                    config.getKeypairName(),
                    config.getKeypairPublic_Key());
            assert keypairCreateResult.keypair.name != null;
        });
    }

    @Test
    public void test_0022_listKeypairs() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            UserResult userResult = identityService.getUsers(token, null, config.getKeypairUserName(), null);

            User user = userResult.users.get(0);
            KeypairResult keypairResult = computeService.getKeypairs(token, user.id);
            KeypairData keypair = keypairResult.keypairs.stream()
                    .filter(k -> k.keypair.name.equals(config.getKeypairName())).findFirst().get().keypair;
            assert keypair.name.equals(config.getKeypairName());
        });
    }

    @Test
    public void test_0023_deleteKeypair() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            UserResult userResult = identityService.getUsers(token, null, config.getKeypairUserName(), null);
            User user = userResult.users.get(0);
            Keypair keypair = computeService.getKeypairs(token, user.id).keypairs.stream()
                    .filter(f -> f.keypair.name.equals(config.getKeypairName())).findFirst().get();
            computeService.deleteKeypair(token, keypair.keypair.name);
        });
    }

    @Test
    public void test_0031_showQuota() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getQuotaDomainName());
            ProjectResult projects = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getQuotaProjectName());
            Project project;
            if (projects.projects.isEmpty()) {
                ProjectCreateResult projectCreateResult = identityService.createProject(
                        token,
                        config.getQuotaProjectName(),
                        "",
                        domainResult.domains.get(0).id);
                project = projectCreateResult.project;
            } else {
                project = projects.projects.get(0);
            }
            try {

                QuotaResult quotaResult = computeService.getQuota(token, project.id);
                assert quotaResult.quota != null;
            } finally {
                identityService.deleteProject(token, project.id);
            }
        });
    }

    @Test
    public void test_0032_updateQuota() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getQuotaDomainName());
            ProjectResult projects = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getQuotaProjectName());
            Project project;
            if (projects.projects.isEmpty()) {
                ProjectCreateResult projectCreateResult = identityService.createProject(
                        token,
                        config.getQuotaProjectName(),
                        "",
                        domainResult.domains.get(0).id);
                project = projectCreateResult.project;
            } else {
                project = projects.projects.get(0);
            }
            try {

                QuotaResult quotaResult = computeService.getQuota(token, project.id);
                assert quotaResult.quota != null;
                Quota quota = new Quota();
                quota.setCores(50);
                quota.setRam(2048 * 50);
                QuotaResult quotaResultNew = computeService.getQuota(token, project.id);
                assert quotaResultNew.quota != null;

                computeService.updateQuota(token, project.id, quota);
            } finally {
                identityService.deleteProject(token, project.id);
            }
        });
    }

    @Test
    public void test_0041_createServer() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();

            JSONObject root = new JSONObject();
            JSONObject server= new JSONObject();
            server.put("name", config.getServerName());
            server.put("flavorRef", config.getServerFlavorRef());
            server.put("imageRef",config.getServerImageRef());
            server.put("key_name",config.getServerKeyName() );
            server.put("availability_zone", config.getServerAvailability_zone());

            JSONArray networkArray= new JSONArray();  
            JSONObject network = new JSONObject();   
            network.put("uuid", config.getServerNetworkUuid());  
            networkArray.put(network);
            server.put("networks", networkArray);

            JSONArray securitygroupArray= new JSONArray();  
            JSONObject securityGroup = new JSONObject();   
            securityGroup.put("name", config.getServerSecurityGroupName());  
            securitygroupArray.put(securityGroup);
            server.put("security_groups", securitygroupArray);
            
            root.put("server", server);
            ObjectMapper om = new ObjectMapper();
            String myJsonString=root.toString();
            ServerCreateRequest serverCreateRequest = om.readValue(myJsonString, ServerCreateRequest.class);

            assert computeService.createServer(token, serverCreateRequest)!=null;
        });
    }

    @Test
    public void test_0042_listServers() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getProjectName());
            ServerResult serverResult = computeService.getServers(token, projectResult.projects.get(0).id);
            assert !serverResult.servers.isEmpty();
        });
    }

    @Test
    public void test_0043_deleteServer() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getProjectName());
            String server_id=computeService.getServers(token, projectResult.projects.get(0).id).servers.stream().filter(s->s.name.equals(config.getServerName())).findFirst().get().id;
            computeService.deleteServer(token, server_id);
           
        });
    }

    @Test
    public void test_0050_associateFloatingIp(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            computeService.associateFloatingip(token, "bb81a0db-d70f-44ed-903d-67e1de151675", "192.168.231.107");

        });
    }  

    @Test
    public void test_0051_disassociateFloatingIp(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            computeService.disassociateFloatingip(token, "bb81a0db-d70f-44ed-903d-67e1de151675", "192.168.231.107");

        });
    }  


}
