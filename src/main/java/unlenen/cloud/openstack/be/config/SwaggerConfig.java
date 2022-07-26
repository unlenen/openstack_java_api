package unlenen.cloud.openstack.be.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("unlenen.cloud.openstack.be.modules"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Openstack Rest API",
                "This project aims to call Openstack Rest API's in openapi calls",
                "V1.0",
                "TERMS OF SERVICE URL",
                new Contact("Nebi Volkan UNLENEN", "https://www.linkedin.com/in/nebi-volkan-unlenen-3143275/",
                        "unlenen@gmail.com"),
                "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }

}