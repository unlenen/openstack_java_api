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
package unlenen.cloud.openstack.be.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
public class UnvalidCallException extends Exception {

    HttpStatus currentStatusCode;
    HttpStatus expectedStatusCode;

    public UnvalidCallException(HttpStatus currentStatusCode, HttpStatus expectedStatusCode, String message) {
        super("Expected:"+expectedStatusCode+", current:"+currentStatusCode+", msg:"+message);
        this.currentStatusCode = currentStatusCode;
        this.expectedStatusCode = expectedStatusCode;
    }

}
