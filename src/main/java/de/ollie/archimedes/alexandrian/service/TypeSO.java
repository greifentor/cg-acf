package de.ollie.archimedes.alexandrian.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A container for column service objects.
 *
 * @author ollie
 *
 */
@Data
@Accessors(chain = true)
public class TypeSO {

	private int sqlType;
	private Integer length;
	private Integer precision;

}