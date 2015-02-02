/**
 * 
 */
package edu.depaul.armada.util;

/**
 * @author ptrzyna
 *
 */
public class AssertUtil {

	public static void assertNotNull(Object obj, String msg) throws IllegalArgumentException {
		if(obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}
	
}
