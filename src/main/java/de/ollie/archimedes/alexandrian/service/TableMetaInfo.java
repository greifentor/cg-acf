package de.ollie.archimedes.alexandrian.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A container for table meta information like stereo types and options.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
public class TableMetaInfo {

	private List<OptionSO> options = new ArrayList<>();
	private List<StereotypeSO> stereotypes = new ArrayList<>();

}