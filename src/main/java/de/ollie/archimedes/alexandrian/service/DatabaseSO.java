package de.ollie.archimedes.alexandrian.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A container class for databases in the service environment.
 * 
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
@NoArgsConstructor
public class DatabaseSO {

	private String name;
	private List<SchemeSO> schemes = new ArrayList<>();

}