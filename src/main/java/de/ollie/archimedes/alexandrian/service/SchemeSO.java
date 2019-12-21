package de.ollie.archimedes.alexandrian.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A container class for database schemes in the service environment.
 * 
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
public class SchemeSO {

	private String name;
	private List<TableSO> tables = new ArrayList<>();

}