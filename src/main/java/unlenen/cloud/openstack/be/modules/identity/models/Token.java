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
package unlenen.cloud.openstack.be.modules.identity.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Getter
@Setter
@ToString
public class Token {
    public List<String> methods;
    public User user;
    public ArrayList<String> audit_ids;
    public Date expires_at;
    public Date issued_at;
    public Project project;
    public boolean is_domain;
    public List<Role> roles;
    public List<Catalog> catalog;
}
