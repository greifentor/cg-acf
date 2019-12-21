package de.ollie.archimedes.alexandrian.service;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A container for column service objects.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
@NoArgsConstructor
public class TypeSO {

	private int sqlType;
	private Integer length;
	private Integer precision;

}