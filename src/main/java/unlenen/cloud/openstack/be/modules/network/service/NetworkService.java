package unlenen.cloud.openstack.be.modules.network.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import unlenen.cloud.openstack.be.config.OpenStackConfig;
import unlenen.cloud.openstack.be.constant.Call;
import unlenen.cloud.openstack.be.constant.OpenStackHeader;
import unlenen.cloud.openstack.be.constant.OpenStackModule;
import unlenen.cloud.openstack.be.constant.Parameter;
import unlenen.cloud.openstack.be.constant.ParameterType;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroup;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRoot;
import unlenen.cloud.openstack.be.modules.network.models.SecurityGroupRuleRoot;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRuleCreateResult;
import unlenen.cloud.openstack.be.modules.network.result.SecurityGroupRulesResult;
import unlenen.cloud.openstack.be.service.CommonService;

@Service
public class NetworkService extends CommonService {

        @Autowired
        OpenStackConfig openStackConfig;

        @Call(type = HttpMethod.GET, url = "/v2.0/security-group-rules", statusCode = HttpStatus.OK, openstackResult = SecurityGroupRulesResult.class)
        public SecurityGroupRulesResult getSecurityGroupRules(String token) throws Exception {
                return (SecurityGroupRulesResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);
        }

        @Call(type = HttpMethod.POST, url = "/v2.0/security-group-rules", statusCode = HttpStatus.CREATED, openstackResult = SecurityGroupRuleCreateResult.class)
        public SecurityGroupRuleCreateResult createSecurityGroupRules(String token,
                        SecurityGroupRuleRoot securityGroupRuleRoot) throws Exception {

                return (SecurityGroupRuleCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                securityGroupRuleRoot);
        }

        @Call(type = HttpMethod.DELETE, url = "/v2.0/security-group-rules/{security_group_rule_id}", statusCode = HttpStatus.NO_CONTENT)

        public void deleteSecurityGroupRule(String token, String security_group_rule_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("security_group_rule_id", security_group_rule_id,
                                                                ParameterType.URI)
                                });
        }

        @Call(type = HttpMethod.GET, url = "/v2.0/security-groups", statusCode = HttpStatus.OK, openstackResult = SecurityGroupResult.class)

        public SecurityGroupResult getSecurityGroups(String token) throws Exception {
                return (SecurityGroupResult) callWithResult(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0]);

        }

        @Call(type = HttpMethod.POST, url = "/v2.0/security-groups", statusCode = HttpStatus.CREATED, openstackResult =  SecurityGroupCreateResult.class)
        public SecurityGroupCreateResult createSecurityGroup(String token, SecurityGroupRoot securityGroupRoot) throws Exception {
                return (SecurityGroupCreateResult) callWithResult(getServiceURL(token,
                                OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[0],
                                securityGroupRoot);
        }


        @Call(type = HttpMethod.DELETE, url = "/v2.0/security-groups/{security_group_id}", statusCode = HttpStatus.NO_CONTENT)

        public void deleteSecurityGroup(String token, String security_group_id) throws Exception {
                call(getServiceURL(token, OpenStackModule.network),
                                new Parameter[] {
                                                new Parameter(OpenStackHeader.TOKEN.getKey(), token)
                                },
                                new Parameter[] {
                                                new Parameter("security_group_id", security_group_id,
                                                                ParameterType.URI)
                                });
        }
}
