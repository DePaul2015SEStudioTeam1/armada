/**
 * 
 */
package edu.depaul.operations.converter;

/**
 * @author ptrzyna
 *
 */
public interface Converter<S, D> {
	
	/**
	 * Converts object of type S into object of type D
	 * 
	 * @param obj	object that is being converted
	 * @return object converted from D
	 */
	D convert(S obj);

}
