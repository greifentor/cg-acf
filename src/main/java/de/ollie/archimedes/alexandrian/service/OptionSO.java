package de.ollie.archimedes.alexandrian.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A container for option service objects.
 *
 * @author ollie
 *
 */
@Data
@Accessors(chain = true)
public class OptionSO {

	private String name;
	private String value;

}