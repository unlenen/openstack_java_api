/*
# Copyright © 2022 Nebi Volkan UNLENEN
#
# Licensed under the GNU Affero General Public License v3.0
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://github.com/unlenen/openstack_java_api/blob/master/LICENSE
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */
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
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
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

    boolean isDownload() default false;

    Class<? extends OpenStackResult> openstackResult() default OpenStackResult.class;
}
