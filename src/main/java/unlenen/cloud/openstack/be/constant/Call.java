package unlenen.cloud.openstack.be.constant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import unlenen.cloud.openstack.be.model.result.OpenStackResult;

/**
 *
 * @author Nebi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Call {

    HttpMethod type() default HttpMethod.GET;

    String url();

    Header[] parameters() default {};

    Header[] headers() default {};

    String bodyFile() default "";

    HttpStatus statusCode() default HttpStatus.OK;

    String mediaType() default "application/json";

    Class<? extends OpenStackResult> openstackResult() default OpenStackResult.class;
}
