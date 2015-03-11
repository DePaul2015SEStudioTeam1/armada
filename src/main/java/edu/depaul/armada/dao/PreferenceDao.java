/*
 * The MIT License (MIT)
 * Coimport java.util.List;

import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;


/**
 * Used for CRUD operations associated with DashboardPreference instances
 * 
 * @author Roland Craddolph
 */
public interface PreferenceDao {
	
	/**
	 * Returns a List<DashboardPreference> of all of the preference data in the database.
	 * @return List<DashboardPreference>
	 */
	List<DashboardPreference> getAll();
	
	/**
	 * Accepts a Preference object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent preference data in the database.
	 * @param preference Preference
	 */
	void storePreference(Preference preference);

	/**
	 * Returns a single Preference object with a name field matching the one specified
	 * as a parameter.
	 * @param name String
	 * @return Preference
	 */
	Preference findWithPreferenceName(String name);
}
