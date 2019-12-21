package de.ollie.acf.utils;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A provider for different names.
 *
 * @author ollie (20.12.2019)
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@Generated
public class NamesProvider {

	private String className;
	private String packageName;
	private String pluralName;

	public String getQualifiedName() {
		return packageName + "." + className;
	}

}