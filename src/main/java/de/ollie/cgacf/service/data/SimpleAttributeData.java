package de.ollie.cgacf.service.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 *
 * @author ollie (17.02.2020)
 */

@AllArgsConstructor
@Data
public class SimpleAttributeData {

	private String attributeName;
	private String attributeAsClassName;
	private String keySOClassName;
	private String setterName;
	private String typeName;

}