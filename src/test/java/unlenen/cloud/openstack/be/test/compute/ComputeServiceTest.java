package unlenen.cloud.openstack.be.test.compute;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Field;

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

import unlenen.cloud.openstack.be.Application;
import unlenen.cloud.openstack.be.modules.compute.models.Flavor;
import unlenen.cloud.openstack.be.modules.compute.models.Keypair;
import unlenen.cloud.openstack.be.modules.compute.models.KeypairData;
import unlenen.cloud.openstack.be.modules.compute.models.Quota;
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
            FlavorCreateResult flavorCreateResult = computeService.createFlavor(
                    token,
                    config.getFlavorName(),
                    config.getFlavorVcpus(),
                    config.getFlavorRam(),
                    config.getFlavorDisk());
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
    public void test_0011_listServers() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getDomainName());
            ProjectResult projectResult = identityService.getProjects(token, domainResult.domains.get(0).id,
                    config.getProjectName());
            ServerResult serverResult = computeService.getServers(token, projectResult.projects.get(0).id);
            assert !serverResult.servers.isEmpty();
        });
    }

 

    @Test void test_0021_createKeypairs(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            KeypairCreateResult keypairCreateResult= computeService.createKeypair(
                    token,
                    config.getKeypairName(),
                    config.getKeypairPublic_Key()
                    );
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
            KeypairData keypair =  keypairResult.keypairs.stream().filter(k -> k.keypair.name.equals(config.getKeypairName())).findFirst().get().keypair ;
            assert keypair.name.equals(config.getKeypairName());
        });
    }

    @Test
    public void test_0023_deleteKeypair() {
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            UserResult userResult = identityService.getUsers(token, null, config.getKeypairUserName(), null);
            User user = userResult.users.get(0);
            Keypair keypair = computeService.getKeypairs(token,user.id).keypairs.stream()
                    .filter(f -> f.keypair.name.equals(config.getKeypairName())).findFirst().get();
            computeService.deleteKeypair(token, keypair.keypair.name);
        });
    }

    @Test
    public void test_0031_showQuota(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getQuotaDomainName());
            ProjectResult projects=  identityService.getProjects(token, domainResult.domains.get(0).id, config.getQuotaProjectName());
            Project project ;
            if(projects.projects.isEmpty()){
                ProjectCreateResult projectCreateResult = identityService.createProject(
                    token,
                    config.getQuotaProjectName(),
                    "",
                    domainResult.domains.get(0).id);
                    project = projectCreateResult.project;
            }else{
                project = projects.projects.get(0);
            }
            try{
        
            QuotaResult quotaResult=computeService.getQuota(token, project.id);
            assert quotaResult.quota != null;
            }finally{
                identityService.deleteProject(token, project.id);
            }
        });
    }

    @Test
    public void test_0032_updateQuota(){
        assertDoesNotThrow(() -> {
            String token = createSystemToken();
            DomainResult domainResult = identityService.getDomains(token, config.getQuotaDomainName());
            ProjectResult projects=  identityService.getProjects(token, domainResult.domains.get(0).id, config.getQuotaProjectName());
            Project project ;
            if(projects.projects.isEmpty()){
                ProjectCreateResult projectCreateResult = identityService.createProject(
                    token,
                    config.getQuotaProjectName(),
                    "",
                    domainResult.domains.get(0).id);
                    project = projectCreateResult.project;
            }else{
                project = projects.projects.get(0);
            }
            try{
        
            QuotaResult quotaResult=computeService.getQuota(token, project.id);
            assert quotaResult.quota != null;
            Quota quota= new Quota();
            quota.setCores(50);
            quota.setRam(2048*50);
            QuotaResult quotaResultNew=computeService.getQuota(token, project.id);
            assert quotaResultNew.quota != null;
        
            computeService.updateQuota(token, project.id, quota);
            }finally{
                identityService.deleteProject(token, project.id);
            }
        });
    }




    @Test
    public void test() throws Exception{

        Quota hzKota = new Quota();
        hzKota.setCores(5);
        hzKota.setInstances(10);

        JSONObject root = new JSONObject();
        JSONObject quota_set = new JSONObject();
        root.put("quota_set", quota_set);

        for (Field field : hzKota.getClass().getDeclaredFields()){
            String name = field.getName();
            Object val = field.get(hzKota);
            if(val!=null)
            quota_set.put(name, val);
            }
    
        System.out.println(root);
    }
}


